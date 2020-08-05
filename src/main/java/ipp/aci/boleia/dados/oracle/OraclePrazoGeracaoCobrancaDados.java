package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPrazoGeracaoCobrancaDados;
import ipp.aci.boleia.dominio.PrazoGeracaoCobranca;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

/**
 * Repositório da entidade PrazoGeracaoCobranca.
 */
@Repository
public class OraclePrazoGeracaoCobrancaDados extends OracleRepositorioBoleiaDados<PrazoGeracaoCobranca> implements IPrazoGeracaoCobrancaDados {

    private static final String QUERY_PESQUISA_PRAZO_GERACAO_COBRANCA =
            " SELECT p " +
                    " FROM PrazoGeracaoCobranca p " +
                    " WHERE :prazoPgto >= p.prazoPagamentoMin " +
                    " AND (:prazoPgto <= p.prazoPagamentoMax OR p.prazoPagamentoMax IS NULL) " +
                    " ORDER BY p.dataInicioVigencia DESC ";

    /**
     * Instancia o repositório
     */
    public OraclePrazoGeracaoCobrancaDados() {
        super(PrazoGeracaoCobranca.class);
    }

    @Override
    public PrazoGeracaoCobranca obterPorPrazoPagamento(Long prazoPagamento) {
        return pesquisar(null, QUERY_PESQUISA_PRAZO_GERACAO_COBRANCA, new ParametroPesquisaIgual("prazoPgto", prazoPagamento) ).getRegistros().stream().findFirst().orElse(null);
    }
}
