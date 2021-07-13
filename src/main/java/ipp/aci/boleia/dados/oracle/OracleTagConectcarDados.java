package ipp.aci.boleia.dados.oracle;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

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

	private static final String QUERY_QUANTIDADE_TOTAL_TAGS =
            " SELECT COUNT(0) " +
            " FROM TagConectcar t " +
            " WHERE t.frota.id  = :idFrota " +
            " AND t.dataExclusao IS NULL ";
	
	private static final String QUERY_QUANTIDADE_TOTAL_TAGS_ATIVAS =
			" SELECT COUNT(0) " +
            " FROM TagConectcar t " +
            " WHERE t.frota.id  = :idFrota " +
            " AND t.dataBloqueio IS NULL AND t.dataExclusao IS NULL ";
	
	
	private static final String QUERY_PRIMEIRA_TAG_ATIVA =
   		 " SELECT t " +
         " FROM TagConectcar t " +
         " WHERE t.frota.id  = :idFrota " +
         " AND t.dataAtivacao IS NOT NULL " +
         " AND t.dataExclusao IS NULL " +
         " ORDER BY t.id ASC ";

    private static final String QUERY_TAG =
            " SELECT t " +
                    " FROM TagConectcar t " +
                    " WHERE t.id  = :idTag ";

	private static final String QUERY_TAGS_ATIVAS =
			" SELECT t " +
		            " FROM TagConectcar t " +
		            " WHERE t.frota.id  = :idFrota " +
		            " AND t.dataBloqueio IS NULL AND t.dataExclusao IS NULL ";

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
        		parametros.add(new ParametroPesquisaNulo("dataBloqueio", false));
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

	@Override
	public long obterQuantidadeTotalTags(Long codigoFrota) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("idFrota", codigoFrota));
        
        return pesquisarUnicoSemIsolamentoDados(QUERY_QUANTIDADE_TOTAL_TAGS, parametros.toArray(new ParametroPesquisa[parametros.size()]));
	}

    public TagConectcar obterTagPorId(Long idTag) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("idTag", idTag));

        return pesquisarUnicoSemIsolamentoDados(QUERY_TAG, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

	@Override
	public long obterQuantidadeTotalTagsAtivas(Long codigoFrota) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("idFrota", codigoFrota));
        
        return pesquisarUnicoSemIsolamentoDados(QUERY_QUANTIDADE_TOTAL_TAGS_ATIVAS, parametros.toArray(new ParametroPesquisa[parametros.size()]));
	}

	@Override
	public TagConectcar obtemPrimeiraTagAtivaPorFrota(Long idFrota) {
		Query query = getGerenciadorDeEntidade().createQuery(QUERY_PRIMEIRA_TAG_ATIVA);		 
		query.setParameter("idFrota", idFrota);	    
		query.setMaxResults(1);
		
		try {
			return (TagConectcar) query.getResultList().get(0);
		} catch (NoResultException e) {
			return null;
		}	
	}

	@Override
	public List<TagConectcar> obterTagsAtivas(Long codigoFrota) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("idFrota", codigoFrota));
        
        return pesquisar(null, QUERY_TAGS_ATIVAS, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
	}

}