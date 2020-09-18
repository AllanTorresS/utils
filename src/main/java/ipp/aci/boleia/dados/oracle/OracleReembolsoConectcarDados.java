package ipp.aci.boleia.dados.oracle;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import ipp.aci.boleia.dados.IReembolsoConectcarDados;
import ipp.aci.boleia.dominio.ReembolsoConectcar;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoReembolsoConectcarJde;
import ipp.aci.boleia.dominio.enums.StatusPagamentoReembolsoConectcar;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPedidoTagVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaReembolsoConectcarVo;
import ipp.aci.boleia.util.Ordenacao;

/**
 * Respositorio de entidades de Reembolso
 */
@Repository
public class OracleReembolsoConectcarDados extends OracleRepositorioBoleiaDados<ReembolsoConectcar> implements IReembolsoConectcarDados {

	/**
	 * Instancia o repositorio
	 */
	public OracleReembolsoConectcarDados() {
		super(ReembolsoConectcar.class);
	}

	@Override
	public ResultadoPaginado<ReembolsoConectcar> pesquisar(FiltroPesquisaReembolsoConectcarVo filtro) {
		List<ParametroPesquisa> parametros = criarParametrosPesquisa(filtro);

        povoarParametrosOrdenacao(filtro);		
		return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
	}
	
	/**
     * Povoa os parametros de pesquisa referente a paginacao no filtro
     *
     * @param filtro de pesquisa reembolso
     */
    private void povoarParametrosOrdenacao(FiltroPesquisaReembolsoConectcarVo filtro) {
        if (filtro.getPaginacao() != null && CollectionUtils.isNotEmpty(filtro.getPaginacao().getParametrosOrdenacaoColuna())) {
            ParametroOrdenacaoColuna parametro = filtro.getPaginacao().getParametrosOrdenacaoColuna().get(0);
            String nomeOrdenacao = parametro.getNome();
            if (nomeOrdenacao != null) {
            	if (nomeOrdenacao.contentEquals("quantidade")) {
            		filtro.getPaginacao().getParametrosOrdenacaoColuna().remove(0);
                    filtro.getPaginacao().getParametrosOrdenacaoColuna().add(0, new ParametroOrdenacaoColuna("quantidadeTag", parametro.getSentidoOrdenacao()));                    
                } else if (nomeOrdenacao.contentEquals("status")) {
                	filtro.getPaginacao().getParametrosOrdenacaoColuna().remove(0);
                    filtro.getPaginacao().getParametrosOrdenacaoColuna().add(0, new ParametroOrdenacaoColuna("status", parametro.getSentidoOrdenacao()));                    
                } else if (nomeOrdenacao.contentEquals("statusIntegracao")) {
                	filtro.getPaginacao().getParametrosOrdenacaoColuna().remove(0);
                    filtro.getPaginacao().getParametrosOrdenacaoColuna().add(0, new ParametroOrdenacaoColuna("statusIntegracao", parametro.getSentidoOrdenacao()));                    
                }
            	
            }
        }
    }

	

	/**
	 * Cria uma lista de parametros para a montagem da consulta de reembolso a ser exibida no grid
	 *
	 * @param filtro O filtro informado pelo usuario
	 * @return Uma lista de parametros
	 */
	private List<ParametroPesquisa> criarParametrosPesquisa(FiltroPesquisaReembolsoConectcarVo filtro) {
		List<ParametroPesquisa> parametros = new ArrayList<>();
		 if(filtro.getDe() != null) {
        	parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataPagamento", filtro.getDe()));
        }
        
        if(filtro.getAte() != null) {
        	parametros.add(new ParametroPesquisaDataMenorOuIgual("dataPagamento", filtro.getAte()));
        }
	    	        
        if (filtro.getStatusIntegracao() != null && filtro.getStatusIntegracao().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("statusIntegracao", StatusIntegracaoReembolsoConectcarJde.valueOf(filtro.getStatusIntegracao().getName()).getValue()));
        }
        
        if (filtro.getStatusPagamento() != null && filtro.getStatusPagamento().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("status", StatusPagamentoReembolsoConectcar.valueOf(filtro.getStatusPagamento().getName()).getValue()));
        }		
		
		povoarParametroLike("numeroDocumento", filtro.getNumeroDocumento(), parametros);
		

		return parametros;
	}

}
