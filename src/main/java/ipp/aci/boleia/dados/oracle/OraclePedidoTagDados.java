package ipp.aci.boleia.dados.oracle;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import ipp.aci.boleia.dados.IPedidoTagDados;
import ipp.aci.boleia.dominio.PedidoTag;
import ipp.aci.boleia.dominio.enums.StatusPedidoTag;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPedidoTagVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;

/**
 * Repositório de entidades PedidoTag
 */

@Repository
public class OraclePedidoTagDados extends OracleRepositorioBoleiaDados<PedidoTag> implements IPedidoTagDados {

    /**
     * Instancia o repositório
     */
    public OraclePedidoTagDados() {
        super(PedidoTag.class);
    }

    @Override
    public ResultadoPaginado<PedidoTag> pesquisar(FiltroPesquisaPedidoTagVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        
        if(filtro.getFrota() != null) {
        	parametros.add(new ParametroPesquisaIgual("frota.id", filtro.getFrota().getId()));
        }
        
        if(filtro.getDe() != null) {
        	parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataPedido", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDe())));
        }
        
        if(filtro.getAte() != null) {
        	parametros.add(new ParametroPesquisaDataMenorOuIgual("dataPedido", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getAte())));
        }
        
        if (filtro.getNumeroPedidoConectcar() != null) {
            parametros.add(new ParametroPesquisaIgual("numeroPedidoConectcar", filtro.getNumeroPedidoConectcar()));
        }
        
        if (filtro.getStatus() != null && filtro.getStatus().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("status", StatusPedidoTag.valueOf(filtro.getStatus().getName()).getValue()));
        }
        
        povoarParametrosOrdenacao(filtro);        
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }
    
    /**
     * Povoa os parametros de pesquisa referente a paginacao no filtro
     *
     * @param filtro de pesquisa pedido tag
     */
    private void povoarParametrosOrdenacao(FiltroPesquisaPedidoTagVo filtro) {
        if (filtro.getPaginacao() != null && CollectionUtils.isNotEmpty(filtro.getPaginacao().getParametrosOrdenacaoColuna())) {
            ParametroOrdenacaoColuna parametro = filtro.getPaginacao().getParametrosOrdenacaoColuna().get(0);
            String nomeOrdenacao = parametro.getNome();
            if (nomeOrdenacao != null) {
            	if (nomeOrdenacao.contentEquals("quantidade")) {
            		filtro.getPaginacao().getParametrosOrdenacaoColuna().remove(0);
                    filtro.getPaginacao().getParametrosOrdenacaoColuna().add(0, new ParametroOrdenacaoColuna("quantidadeTag", parametro.getSentidoOrdenacao()));                    
                } else if (nomeOrdenacao.contentEquals("statusPedido")) {
                	filtro.getPaginacao().getParametrosOrdenacaoColuna().remove(0);
                    filtro.getPaginacao().getParametrosOrdenacaoColuna().add(0, new ParametroOrdenacaoColuna("status", parametro.getSentidoOrdenacao()));                    
                }
            }
        }
    }

	@Override
	public PedidoTag obterPorCobranca(Long idCobranca) {
		return pesquisarUnico(new ParametroPesquisaIgual("cobranca.id", idCobranca));
	}

	@Override
	public List<PedidoTag> obterPedidosSemStatusRastreio() {
		return pesquisar(new ParametroOrdenacaoColuna("id", Ordenacao.CRESCENTE), new ParametroPesquisaNulo("statusRastreio", false), new ParametroPesquisaNulo("numeroPedidoConectcar", true));
	}

}