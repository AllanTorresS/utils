package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAjusteCobrancaDados;
import ipp.aci.boleia.dominio.AjusteCobranca;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Implementação do repositório de dados da entidade {@link AjusteCobranca}.
 */
@Repository
public class OracleAjusteCobrancaDados extends OracleRepositorioBoleiaDados<AjusteCobranca> implements IAjusteCobrancaDados {

    /**
     * Construtor do repositório.
     */
    public OracleAjusteCobrancaDados() {
        super(AjusteCobranca.class);
    }

    @Override
    public List<AjusteCobranca> obterAjustesDeValorPorCobranca(Long idCobranca) {
        return pesquisar((ParametroOrdenacaoColuna) null,
                new ParametroPesquisaIgual("cobranca.id", idCobranca),
                new ParametroPesquisaNulo("valorAjuste", true),
                new ParametroPesquisaMaior("valorAjuste", BigDecimal.ZERO)
        );
    }
}
