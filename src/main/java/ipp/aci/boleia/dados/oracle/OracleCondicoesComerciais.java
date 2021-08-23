package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ICondicoesComerciaisDados;
import ipp.aci.boleia.dominio.CondicoesComerciais;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.Ordenacao;
import org.springframework.stereotype.Repository;

import java.util.List;

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
	public CondicoesComerciais buscarPorFrota(Long idFrota) {	
        List<CondicoesComerciais> lista = pesquisar(new ParametroOrdenacaoColuna("versao", Ordenacao.DECRESCENTE), new ParametroPesquisaIgual("frota.id", idFrota));
        if (lista != null && !lista.isEmpty()) {
        	return lista.get(0);
        }
        return new CondicoesComerciais();
	}

}