package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IReembolsoAntecipadoDados;
import ipp.aci.boleia.dominio.ReembolsoAntecipado;
import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoReembolsoJde;
import ipp.aci.boleia.dominio.enums.TipoAntecipacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação do repositório de dados da entidade {@link ReembolsoAntecipado}.
 */
@Repository
public class OracleReembolsoAntecipadoDados extends OracleRepositorioBoleiaDados<ReembolsoAntecipado> implements IReembolsoAntecipadoDados {

    private static final String QUERY_TOTAL_ANTECIPADO_POR_CONSOLIDADOS =
            " SELECT COALESCE(SUM(a.valorTotal), 0)" +
            " FROM ReembolsoAntecipado ra" +
            " JOIN ra.autorizacoesPagamento a" +
            " LEFT JOIN ra.propostaAntecipacao pa" +
            " WHERE" +
            "     COALESCE(a.transacaoConsolidadaPostergada.id, a.transacaoConsolidada.id) IN (:idsConsolidados)" +
            "     AND ((ra.tipoAntecipacao = " + TipoAntecipacao.PARCEIRO_XP.getValue() +
            "           AND pa.isAceito = true)" +
            "         OR" +
            "          (ra.tipoAntecipacao = " + TipoAntecipacao.SOLUCAO.getValue() +
            "           AND ra.statusIntegracao = " + StatusIntegracaoReembolsoJde.REALIZADO.getValue() +
            " ))";

    /**
     * Instancia o repositorio
     */
    public OracleReembolsoAntecipadoDados() {
        super(ReembolsoAntecipado.class);
    }

    @Override
    public BigDecimal calcularTotalAntecipadoBruto(List<TransacaoConsolidada> transacoesConsolidadas) {
        ParametroPesquisa[] parametros = { new ParametroPesquisaIgual("idsConsolidados",
                transacoesConsolidadas.stream()
                .map(TransacaoConsolidada::getId)
                .collect(Collectors.toList())) };

        return pesquisar(QUERY_TOTAL_ANTECIPADO_POR_CONSOLIDADOS, BigDecimal.class, parametros).stream()
                .findFirst()
                .orElse(BigDecimal.ZERO);
    }
}
