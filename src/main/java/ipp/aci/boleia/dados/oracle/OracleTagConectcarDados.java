package ipp.aci.boleia.dados.oracle;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import ipp.aci.boleia.dados.ITagConectcarDados;
import ipp.aci.boleia.dominio.TagConectcar;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgualIgnoreCase;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaTagConectcarVo;

/**
 * Repositório de entidades TagConectcar
 */

@Repository
public class OracleTagConectcarDados extends OracleRepositorioBoleiaDados<TagConectcar> implements ITagConectcarDados {

    /**
     * Instancia o repositório
     */
    public OracleTagConectcarDados() {
        super(TagConectcar.class);
    }

    @Override
    public ResultadoPaginado<TagConectcar> pesquisar(FiltroPesquisaTagConectcarVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        
        if(filtro.getFrota() != null) {
        	parametros.add(new ParametroPesquisaIgual("frota.id", filtro.getFrota().getId()));
        }
        
        if (filtro.getId() != null) {
            parametros.add(new ParametroPesquisaIgual("id", filtro.getId()));
        }
        if (filtro.getPlaca() != null) {
            parametros.add(new ParametroPesquisaIgualIgnoreCase("placa", filtro.getPlaca()));
        }
        if (filtro.getStatus() != null && filtro.getStatus().getName() != null) {
        	if(filtro.getStatus().getName().equals(StatusAtivacao.ATIVO.name())) {
        		parametros.add(new ParametroPesquisaNulo("dataAtivacao", true));
        	}else if(filtro.getStatus().getName().equals(StatusAtivacao.INATIVO.name())) {
        		parametros.add(new ParametroPesquisaNulo("dataBloqueio", true));
        	}
        }
        
        povoarParametrosOrdenacao(filtro);        
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }
    
    /**
     * Povoa os parametros de pesquisa referente a paginacao no filtro
     *
     * @param filtro de pesquisa pedido tag
     */
    private void povoarParametrosOrdenacao(FiltroPesquisaTagConectcarVo filtro) {
        if (filtro.getPaginacao() != null && CollectionUtils.isNotEmpty(filtro.getPaginacao().getParametrosOrdenacaoColuna())) {
            ParametroOrdenacaoColuna parametro = filtro.getPaginacao().getParametrosOrdenacaoColuna().get(0);
            String nomeOrdenacao = parametro.getNome();
            if (nomeOrdenacao != null && nomeOrdenacao.contentEquals("statusTag")) {            	
            	filtro.getPaginacao().getParametrosOrdenacaoColuna().remove(0);
                filtro.getPaginacao().getParametrosOrdenacaoColuna().add(0, new ParametroOrdenacaoColuna("dataBloqueio", parametro.getSentidoOrdenacao()));                    
                
            }
        }
    }

   
}