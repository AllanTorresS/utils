package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ITaxaTarifadorDados;
import ipp.aci.boleia.dominio.enums.tarifador.TipoTaxaTarifador;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.tarifador.TaxaTarifador;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositório de entidades TaxaTarifador
 */
@Repository
public class OracleTaxaTarifadorDados extends OracleRepositorioBoleiaDados<TaxaTarifador> implements ITaxaTarifadorDados {

    private static final String QUERY_TAXA_VIGENTE_POR_VALOR_FROTA =
            " SELECT tt" +
            " FROM TaxaTarifador tt" +
            " JOIN tt.tarifador t" +
            " LEFT JOIN t.frotas f" +
            " WHERE" +
            "     (f IS NULL OR f.id = :idFrota)" +
            "     AND ((" +
            "         t.tipoTaxa = " + TipoTaxaTarifador.REAIS.getValue() +
            "         AND tt.inicioFaixaReais < :valorReais" +
            "         AND :valorReais <= tt.fimFaixaReais" +
            "     ) OR (" +
            "         t.tipoTaxa = " + TipoTaxaTarifador.LITRAGEM.getValue() +
            "         AND tt.inicioFaixaLitragem < :valorLitragem" +
            "         AND :valorLitragem <= tt.fimFaixaLitragem" +
            "     ) OR t.tipoTaxa = " + TipoTaxaTarifador.FIXO_REAIS.getValue() + ")" +
            "     AND t.dataInicioVigencia <= SYSDATE" +
            "     AND (SYSDATE <= t.dataFimVigencia OR t.dataFimVigencia IS NULL)" +
            "     AND NOT EXISTS (" +
            "         SELECT 1" +
            "         FROM Tarifador t_" +
            "         LEFT JOIN t_.frotas f_" +
            "         WHERE" +
            "             t.id <> t_.id" +
            "             AND ((f IS NULL AND f_ IS NULL) OR (f = f_))" +
            "             AND t_.dataInicioVigencia <= SYSDATE" +
            "             AND (SYSDATE <= t_.dataFimVigencia OR t_.dataFimVigencia IS NULL)" +
            "             AND t.dataInicioVigencia < t_.dataInicioVigencia" +
            "     )" +
            " ORDER BY f.id NULLS LAST";

    /**
     * Instancia o repositório
     */
    public OracleTaxaTarifadorDados() {
        super(TaxaTarifador.class);
    }

    @Override
    public TaxaTarifador obterTaxaTarifadorVigente(BigDecimal valorReais, BigDecimal valorLitragem, Long idFrota) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("valorReais", valorReais));
        parametros.add(new ParametroPesquisaIgual("valorLitragem", valorLitragem));
        parametros.add(new ParametroPesquisaIgual("idFrota", idFrota));

        List<TaxaTarifador> resultado = pesquisar(QUERY_TAXA_VIGENTE_POR_VALOR_FROTA, TaxaTarifador.class, parametros.toArray(new ParametroPesquisa[0]));
        return resultado != null ? resultado.stream().findFirst().orElse(null) : null;
    }
}
