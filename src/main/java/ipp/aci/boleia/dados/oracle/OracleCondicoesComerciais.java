package ipp.aci.boleia.dados.oracle;

import java.util.List;

import org.springframework.stereotype.Repository;

import ipp.aci.boleia.dados.ICondicoesComerciaisDados;
import ipp.aci.boleia.dominio.CondicoesComerciais;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.Ordenacao;

/**
 * Repositório de entidades Condicoes Comerciais
 */

@Repository
public class OracleCondicoesComerciais extends OracleRepositorioBoleiaDados<CondicoesComerciais> implements ICondicoesComerciaisDados {

    /**
     * Instancia o repositório
     */
    public OracleCondicoesComerciais() {
        super(CondicoesComerciais.class);
    }

	@Override
	public List<CondicoesComerciais> buscarPorFrota(Long idFrota) {	
        		
        return pesquisar(new ParametroOrdenacaoColuna("versao", Ordenacao.DECRESCENTE), new ParametroPesquisaIgual("frota.id", idFrota));
	}

   
}