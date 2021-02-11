package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoDados;
import ipp.aci.boleia.dominio.AtividadeComponente;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.EmpresaAgregada;
import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.Unidade;
import ipp.aci.boleia.dominio.enums.ClassificacaoAgregado;
import ipp.aci.boleia.dominio.enums.ModalidadePagamento;
import ipp.aci.boleia.dominio.enums.StatusAutorizacao;
import ipp.aci.boleia.dominio.enums.StatusConfirmacaoTransacao;
import ipp.aci.boleia.dominio.enums.StatusEdicao;
import ipp.aci.boleia.dominio.enums.StatusFrota;
import ipp.aci.boleia.dominio.enums.StatusNotaFiscalAbastecimento;
import ipp.aci.boleia.dominio.enums.TipoAutorizacaoPagamento;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaAnd;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaEmpty;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaFetch;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgualIgnoreCase;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.EntidadeVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaAbastecimentoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaDetalheCobrancaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaDetalheReembolsoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaQtdTransacoesFrotaVo;
import ipp.aci.boleia.dominio.vo.QuantidadeAbastecidaVeiculoVo;
import ipp.aci.boleia.dominio.vo.TransacaoPendenteVo;
import ipp.aci.boleia.dominio.vo.apco.InformacoesVolumeVo;
import ipp.aci.boleia.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo;
import ipp.aci.boleia.dominio.vo.frotista.InformacaoPaginacaoFrtVo;
import ipp.aci.boleia.dominio.vo.frotista.ResultadoPaginadoFrtVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.negocio.ParametrosPesquisaBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static ipp.aci.boleia.dominio.enums.StatusNotaFiscalAbastecimento.EMITIDA;
import static ipp.aci.boleia.dominio.enums.StatusNotaFiscalAbastecimento.PENDENTE;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterPrimeiroInstanteDia;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterUltimoInstanteDia;

/**
 * Respositorio de entidades AutorizacaoPagamento
 */

@Repository
public class OracleAutorizacaoPagamentoDados extends OracleRepositorioBoleiaDados<AutorizacaoPagamento> implements IAutorizacaoPagamentoDados {

    private static final Integer LIMITE_INFERIOR_CONCILIACAO = -30;

    private static final Integer LIMITE_SUPERIOR_CONCILIACAO = -48;

    private static final String REMOVER_ACENTO = "TRANSLATE( %s, " +
            "'âãäåāăąÁÂÃÄÅĀĂĄèééêëēĕėęěĒĔĖĘĚìíîïìĩīĭÌÍÎÏÌĨĪĬóôõöōŏőÒÓÔÕÖŌŎŐùúûüũūŭůÙÚÛÜŨŪŬŮ'," +
            "'aaaaaaaaaaaaaaaeeeeeeeeeeeeeeeiiiiiiiiiiiiiiiiooooooooooooooouuuuuuuuuuuuuuuu')";

    private static final String TO_LOWER = "LOWER(%s)";

    private static final String QUERY_ATUALIZACAO_COTA_VEICULO =
            " SELECT new ipp.aci.boleia.dominio.vo.QuantidadeAbastecidaVeiculoVo(" +
                    "   a.id, " +
                    "   a.veiculo.id, " +
                    "   a.totalLitrosAbastecimento, " +
                    "   a.valorUnitarioAbastecimento, " +
                    "   a.dataRequisicao, " +
                    "   a.hodometro, " +
                    "   a.horimetro, " +
                    "   a.hodometroAnterior, " +
                    "   a.horimetroAnterior)" +
                    " FROM AutorizacaoPagamento a " +
                    " JOIN a.veiculo v " +
                    " WHERE " +
                    "   v.excluido = 0 " +
                    "   AND a.frota.id = :idFrota " +
                    "   AND a.tipoAutorizacaoPagamento != " + TipoAutorizacaoPagamento.ACPI.getValue() +
                    "   AND a.status = " + StatusAutorizacao.AUTORIZADO.getValue() +
                    "   AND a.valorTotal >= 0 " +
                    "   AND a.valorUnitarioAbastecimento >= 0 " +
                    "   AND a.dataRequisicao BETWEEN :dataInicial AND :dataFinal " +
                    "   AND a.idAutorizacaoEstorno IS NULL";

    private static final String QUERY_AUTORIZACOES_AVISO_DEBITO =
            " SELECT a " +
                    " FROM AutorizacaoPagamento a " +
                    " JOIN a.transacaoFrota tf " +
                    " WHERE " +
                    "   (a.status = " + StatusAutorizacao.AUTORIZADO.getValue() + " OR a.status = " + StatusAutorizacao.CANCELADO.getValue() + ") " +
                    "   AND tf.consumiuCreditoPrePago = false " +
                    "   AND EXISTS ( " +
                    "      SELECT 1 " +
                    "      FROM TransacaoConsolidada tc " +
                    "      JOIN tc.frotaPtov fpv " +
                    "      WHERE " +
                    "           ((tc.id = a.transacaoConsolidada.id AND a.transacaoConsolidadaPostergada.id IS NULL) OR tc.id = a.transacaoConsolidadaPostergada.id) " +
                    "           AND tc.cobranca.id = :idCobranca " +
                    "   ) " +
                    " ORDER BY a.dataRequisicao ASC ";

    private static final String QUERY_LITRAGEM_ULTIMO_ABASTECIMENTO =
            "SELECT a.totalLitrosAbastecimento " +
                    "FROM " +
                    "AutorizacaoPagamento a " +
                    "WHERE a.status = " + StatusAutorizacao.AUTORIZADO.getValue() +
                    " AND a.totalLitrosAbastecimento > 0 " +
                    " AND a.veiculo.placa = :placaVeiculo " +
                    " AND a.dataRequisicao <= :dataAbastecimento " +
                    " AND a.cnpjFrota = :cnpjFrota " +
                    " AND a.id <> :idAbastecimento " +
                    " ORDER BY a.dataRequisicao DESC";

    private static final String QUERY_ABASTECIMENTOS_AUTORIZADOS_PENDENTES_CONFIRMACAO =
            "SELECT new ipp.aci.boleia.dominio.vo.TransacaoPendenteVo(" +
                    "abast.id, " +
                    "tfl.nsuZacc" +
                    ") " +
                    "FROM AutorizacaoPagamento abast " +
                    "INNER JOIN abast.pedido ped " +
                    "INNER JOIN ped.transacoesFrotasLeves tfl " +
                    "WHERE abast.status = " + StatusAutorizacao.AUTORIZADO.getValue() +
                    " AND abast.tipoAutorizacaoPagamento = " + TipoAutorizacaoPagamento.POS_FL.getValue() +
                    " AND tfl.statusConfirmacao = " + StatusConfirmacaoTransacao.NAO_CONFIRMADO.getValue() +
                    " AND tfl.dataRequisicao BETWEEN :limiteInferiorData AND :limiteSuperiorData";

    private static final String CONSULTA_VENDA_PROFROTAS = "SELECT new ipp.aci.boleia.dominio.vo.apco.InformacoesVolumeVo(" +
            "a.pontoVenda, " +
            "a.frota.id, " +
            "i.combustivel.codigoCombustivelCorporativo, " +
            "TRUNC(a.dataRequisicao), SUM(a.totalLitrosAbastecimento)) " +
            "FROM AutorizacaoPagamento AS a " +
            "INNER JOIN  a.pontoVenda AS p " +
            "INNER JOIN a.items AS i " +
            "WHERE i.combustivel IS NOT NULL AND i.combustivel.codigoCombustivelCorporativo IS NOT NULL AND " +
            "a.status = "+ StatusAutorizacao.AUTORIZADO.getValue() +
            " AND a.dataRequisicao BETWEEN :dataInicial AND :dataFinal" + " AND a.frota.status != " + StatusFrota.PRE_CADASTRO.getValue() +
            " GROUP BY a.pontoVenda, a.frota.id, i.combustivel.codigoCombustivelCorporativo, TRUNC(a.dataRequisicao)" +
            "ORDER BY TRUNC(a.dataRequisicao) ASC";

    private static final String QUERY_AUTORIZACOES_EM_LISTA_DE_ABASTECIMENTOS_POR_FLAG_JUSTIFICATIVA = "" +
            " SELECT a FROM AutorizacaoPagamento a " +
            " JOIN a.notasFiscais nf " +
            " where a.id in (:idsAutorizacoes) " +
            " and nf.isJustificativa = :isJustificativa ";

    private static final String CONSULTA_QUANTIDADE_ABASTECIMENTOS_POSTERGADOS =
            "SELECT COUNT(DISTINCT a) " +
                    "FROM AutorizacaoPagamento a " +
                    "LEFT JOIN a.notasFiscais nf " +
                    "WHERE a.transacaoConsolidada.id = :idConsolidado AND a.transacaoConsolidadaPostergada IS NOT NULL " +
                    "AND (a.dataRequisicao >= :dataRequisicaoDe OR :dataRequisicaoDe IS NULL) " +
                    "AND (a.dataRequisicao <= :dataRequisicaoAte OR :dataRequisicaoAte IS NULL) " +
                    "AND (" + String.format(TO_LOWER, String.format(REMOVER_ACENTO, "a.placaVeiculo")) + " LIKE :placaVeiculo OR :placaVeiculo IS NULL) ";

    private static final String CLAUSULA_STATUS_AUTORIZACAO =
            " AND ((A.status = 1 AND (A.valorTotal > 0 OR " +
            " ((SELECT AB.transacaoConsolidada.id FROM AutorizacaoPagamento AB WHERE AB.id = A.idAutorizacaoEstorno) <> A.transacaoConsolidada.id AND A.transacaoConsolidada.quantidadeAbastecimentos > 0))) " +
            " OR (A.status = -1 AND (SELECT AB.transacaoConsolidada.id FROM AutorizacaoPagamento AB WHERE AB.idAutorizacaoEstorno = A.id AND AB.valorTotal < 0) <> A.transacaoConsolidada.id)) ";

    private static final String CONSULTA_ABASTECIMENTOS_COBRANCA =
            "SELECT DISTINCT A " +
                    "FROM AutorizacaoPagamento A " +
                    "LEFT JOIN A.notasFiscais NF " +
                    "LEFT JOIN A.transacaoConsolidada TC " +
                    "LEFT JOIN A.transacaoConsolidadaPostergada TCP " +
                    " WHERE " +
                    " (:idConsolidado IS NULL OR A.transacaoConsolidada.id = :idConsolidado OR A.transacaoConsolidadaPostergada.id = :idConsolidado) " +
                    CLAUSULA_STATUS_AUTORIZACAO +
                    " AND (:dataRequisicao IS NULL OR TRUNC(A.dataRequisicao) = :dataRequisicao) " +
                    " AND (:idMotorista IS NULL OR A.motorista.id = :idMotorista) " +
                    " AND (:placaVeiculo IS NULL OR " + String.format(TO_LOWER, String.format(REMOVER_ACENTO, "A.placaVeiculo")) + " LIKE :placaVeiculo ) " +
                    " AND (:statusNotaFiscal IS NULL OR A.statusNotaFiscal = :statusNotaFiscal) " +
                    " AND (:idPv IS NULL OR A.pontoVenda.id = :idPv) " +
                    " AND (:numeroNf IS NULL OR LOWER(NF.numero) LIKE :numeroNf) " +
                    " AND (:serieNf IS NULL OR LOWER(NF.numeroSerie) LIKE :serieNf) " +
                    " AND (:idFrota IS NULL OR A.frota.id = :idFrota) " +
                    " AND (" +
                            "(:dataInicioPeriodo IS NULL AND :dataFimPeriodo IS NULL) " +
                            " OR (:dataInicioPeriodo = CASE WHEN TCP IS NOT NULL THEN TO_CHAR(TCP.dataInicioPeriodo, 'DD/MM/YYYY') ELSE TO_CHAR(TC.dataInicioPeriodo, 'DD/MM/YYYY') END " +
                                " AND :dataFimPeriodo = CASE WHEN TCP IS NOT NULL THEN TO_CHAR(TCP.dataFimPeriodo, 'DD/MM/YYYY') ELSE TO_CHAR(TCP.dataFimPeriodo, 'DD/MM/YYYY') END " +
                    "            ) " +
                    ")" +
                    " %s " +
                    " ORDER BY %s ";

    private static final String CONSULTA_ABASTECIMENTOS_POR_NFE =
            " SELECT a" +
            " FROM AutorizacaoPagamento a" +
            " WHERE a.status = " + StatusAutorizacao.AUTORIZADO.getValue() +
            "     AND a.statusNotaFiscal = " + PENDENTE.getValue() +
            "     AND a.dataProcessamento <= :dataEmissao" +
            "     AND a.valorTotal <= :limiteSuperiorTotalNf" +
            "     AND a.valorTotal >= :limiteInferiorTotalNf" +
            "     AND EXISTS (" +
            "         SELECT 1" +
            "         FROM AutorizacaoPagamento AS a1" +
            "         INNER JOIN a1.pontoVenda AS pv" +
            "         INNER JOIN pv.componentes AS c" +
            "         WHERE c.codigoPessoa = :cnpjEmit" +
            "             AND a1.id = a.id" +
            "     ) AND EXISTS (" +
            "         SELECT 1" +
            "         FROM AutorizacaoPagamento AS a2" +
            "         INNER JOIN a2.frota AS f" +
            "         LEFT JOIN f.unidades AS u" +
            "         LEFT JOIN f.empresasAgregadas AS eag" +
            "         WHERE (f.cnpj = :cnpjDest OR u.cnpj = :cnpjDest OR eag.cnpj = :cnpjDest)" +
            "             AND a.id = a2.id)";

    private static final String CONSULTA_VIGENTES_POR_CICLOS =
            " SELECT a FROM AutorizacaoPagamento a " +
            " JOIN FETCH a.transacaoConsolidada tc " +
            " JOIN FETCH tc.prazos tc_p " +
            " LEFT JOIN FETCH a.transacaoConsolidadaPostergada tcp " +
            " LEFT JOIN FETCH tcp.prazos tcp_p " +
            " WHERE " +
            "     ((tc.id IN (:idsTransacoesConsolidadas) AND tcp IS NULL) OR tcp.id IN (:idsTransacoesConsolidadas)) ";

    private static final String CLAUSULA_DATA_LIMITE_EMISSAO = "CASE " +
            "WHEN %s.possuiPrazoAjuste = 1 THEN trunc(%s.dataLimiteEmissaoNfe) " +
            "ELSE trunc(%s.dataFimPeriodo) " +
            "END ";

    private static final String CLAUSULA_DATA_VENCIMENTO = "CASE WHEN (%s IS NOT NULL AND %s.dataVencimentoVigente IS NOT NULL) THEN %s.dataVencimentoVigente ELSE %s.dataLimitePagamento END ";

    private static final String CLAUSULA_COMUM_CONSULTAS_AGRUPAMENTOS =
            "AND (:idFrota IS NULL OR F.id = :idFrota) " +
            "AND (:statusConsolidacao IS NULL OR (TC.statusConsolidacao = :statusConsolidacao OR TP.statusConsolidacao = :statusConsolidacao))  " +
            "AND (:idCobranca IS NULL OR C.id = :idCobranca OR CP.id = :idCobranca) " +
            "AND (:dataLimiteEmissao IS NULL OR :dataLimiteEmissao = " + String.format(CLAUSULA_DATA_LIMITE_EMISSAO, "TCP", "TCP", "TC") +
            " OR :dataLimiteEmissao = " + String.format(CLAUSULA_DATA_LIMITE_EMISSAO, "TPP", "TPP", "TP") + " ) " +
            " AND (:dataVencimento IS NULL OR :dataVencimento = " + String.format(CLAUSULA_DATA_VENCIMENTO,"C", "C", "C", "TCP") +
            " OR :dataVencimento = " + String.format(CLAUSULA_DATA_VENCIMENTO,"CP", "CP", "CP", "TPP")  + " ) " +
            CLAUSULA_STATUS_AUTORIZACAO;

    private static final String CONSULTA_QUANTIDADE_NOTAS =
            "SELECT COUNT(DISTINCT N) " +
                    "FROM AutorizacaoPagamento A " +
                    "LEFT JOIN A.frota F " +
                    "LEFT JOIN A.pontoVenda PV " +
                    "JOIN A.transacaoConsolidada TC " +
                    "JOIN TC.prazos TCP " +
                    "LEFT JOIN A.transacaoConsolidadaPostergada TP " +
                    "LEFT JOIN TP.prazos TPP " +
                    "LEFT JOIN TC.cobranca C " +
                    "LEFT JOIN TP.cobranca CP " +
                    "JOIN A.notasFiscais N " +
                    "WHERE " +
                    "((A.dataProcessamento >= :dataInicioPeriodo AND A.dataProcessamento <= :dataFimPeriodo AND A.transacaoConsolidadaPostergada IS NULL) " +
                    "OR (A.dataPostergacao >= :dataInicioPeriodo AND A.dataPostergacao <= :dataFimPeriodo)) " +
                    "AND N.isJustificativa = 0 " +
                    CLAUSULA_COMUM_CONSULTAS_AGRUPAMENTOS;

    /**
     * Instancia o repositorio
     */
    public OracleAutorizacaoPagamentoDados() {
        super(AutorizacaoPagamento.class);
    }

    @Override
    public List<AutorizacaoPagamento> obterUltimos30AbastecimentosFrota(Long idFrota) {
        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.setPagina(1);
        paginacao.setTamanhoPagina(30);
        paginacao.setParametrosOrdenacaoColuna(
                Collections.singletonList(new ParametroOrdenacaoColuna("transacaoFrota.dataTransacao", Ordenacao.DECRESCENTE))
        );

        ResultadoPaginado<AutorizacaoPagamento> autorizacoes = pesquisar(paginacao,
                new ParametroPesquisaIgual("status", StatusAutorizacao.AUTORIZADO.getValue()),
                new ParametroPesquisaIgual("transacaoFrota.frota.id", idFrota));

        return autorizacoes.getRegistros();
    }

    @Override
    public List<AutorizacaoPagamento> obterAutorizadasPorFrotaDataMinima(Long idFrota, Date dataMinima) {
        return pesquisar(
                new ParametroOrdenacaoColuna("dataProcessamento", Ordenacao.DECRESCENTE),
                new ParametroPesquisaIgual("transacaoFrota.frota.id", idFrota),
                new ParametroPesquisaIgual("status", StatusAutorizacao.AUTORIZADO.getValue()),
                new ParametroPesquisaDiferente("tipoAutorizacaoPagamento", TipoAutorizacaoPagamento.ACPI.getValue()),
                new ParametroPesquisaDataMaiorOuIgual("dataProcessamento",dataMinima));
    }

    @Override
    public AutorizacaoPagamento obterPorCodigoPagamentoAutorizado(String codigoPagamento) {
        return pesquisarUnico(new ParametroPesquisaIgual("codigoPagamento", codigoPagamento), new ParametroPesquisaIgual("status", StatusAutorizacao.AUTORIZADO.getValue()));
    }

    @Override
    public AutorizacaoPagamento obterSimilarAutorizada(Long idMotorista, Long idVeiculo, Long idPv, Long idFrota, BigDecimal valorTotal, Date dataLimite) {
        return pesquisarUnico(  new ParametroPesquisaIgual("status", StatusAutorizacao.AUTORIZADO.getValue()), new ParametroPesquisaIgual("motorista.id", idMotorista),
                new ParametroPesquisaIgual("veiculo.id", idVeiculo), new ParametroPesquisaIgual("pontoVenda.id", idPv),
                new ParametroPesquisaIgual("transacaoFrota.frota.id", idFrota), new ParametroPesquisaIgual("transacaoFrota.valorTotal", valorTotal.negate()),
                new ParametroPesquisaDataMaiorOuIgual("transacaoFrota.dataTransacao", dataLimite));
    }

    @Override
    public AutorizacaoPagamento obterAbastecimentoSimilarAutorizada(Long cpf, Long idVeiculo, Long idFrota, BigDecimal litragemTotal, Date dataLimite) {
        return pesquisarUnico(  new ParametroPesquisaIgual("status", StatusAutorizacao.AUTORIZADO.getValue()),
                new ParametroPesquisaIgual("cpfMotorista", cpf),
                new ParametroPesquisaIgual("veiculo.id", idVeiculo),
                new ParametroPesquisaIgual("transacaoFrota.frota.id", idFrota),
                new ParametroPesquisaIgual("totalLitrosAbastecimento", litragemTotal.negate()),
                new ParametroPesquisaDataMaiorOuIgual("dataProcessamento", dataLimite));
    }

    @Override
    public List<AutorizacaoPagamento> obterNotasPorFrotaMotorista(Long idFrota, Long idMotorista) {
        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.setPagina(1);
        paginacao.setTamanhoPagina(10);
        paginacao.setParametrosOrdenacaoColuna(
                Collections.singletonList(new ParametroOrdenacaoColuna("transacaoFrota.dataTransacao", Ordenacao.DECRESCENTE))
        );

        ResultadoPaginado<AutorizacaoPagamento> notas = pesquisar(paginacao,
                new ParametroPesquisaIgual("status", StatusAutorizacao.AUTORIZADO.getValue()),
                new ParametroPesquisaMaior("valorTotal", BigDecimal.ZERO),
                new ParametroPesquisaIgual("motorista.id", idMotorista),
                new ParametroPesquisaIgual("transacaoFrota.frota.id", idFrota));

        return notas.getRegistros();
    }

    @Override
    public List<AutorizacaoPagamento> obterUltimasTresNotasPorVeiculoData(Long idVeiculo, Date dataRequisicao) {
        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.setPagina(1);
        paginacao.setTamanhoPagina(3);
        paginacao.setParametrosOrdenacaoColuna(
                Arrays.asList(new ParametroOrdenacaoColuna("dataRequisicao", Ordenacao.DECRESCENTE),
                        new ParametroOrdenacaoColuna("id", Ordenacao.DECRESCENTE))
        );

        ResultadoPaginado<AutorizacaoPagamento> notas = pesquisar(paginacao,
                new ParametroPesquisaIgual("status", StatusAutorizacao.AUTORIZADO.getValue()),
                new ParametroPesquisaDataMenorOuIgual("dataRequisicao", dataRequisicao),
                new ParametroPesquisaIgual("veiculo.id", idVeiculo));

        return notas.getRegistros();
    }

    @Override
    public AutorizacaoPagamento obterUltimoAbastecimentoVeiculo(Long idVeiculo) {
        return obterUltimoAbastecimentoVeiculo(idVeiculo,null, null);
    }

    @Override
    public AutorizacaoPagamento obterUltimoAbastecimentoVeiculo(Long idVeiculo, Date dataMaxima) {
        return obterUltimoAbastecimentoVeiculo(idVeiculo, dataMaxima, null);
    }

    @Override
    public AutorizacaoPagamento obterAutorizacaoPagamentoPosterior(AutorizacaoPagamento autorizacaoPagamento){
        InformacaoPaginacao paginacao = new InformacaoPaginacao(1, 1, new ParametroOrdenacaoColuna("dataProcessamento", Ordenacao.CRESCENTE));

        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("status", StatusAutorizacao.AUTORIZADO.getValue()));
        parametros.add(new ParametroPesquisaIgual("veiculo.id", autorizacaoPagamento.getVeiculo().getId()));
        parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataRequisicao", autorizacaoPagamento.getDataRequisicao()));

        parametros.add(new ParametroPesquisaDiferente("id", autorizacaoPagamento.getId()));

        ResultadoPaginado<AutorizacaoPagamento> resultado = pesquisar(paginacao,
                parametros.toArray(new ParametroPesquisa[parametros.size()]));

        return resultado.getRegistros().isEmpty() ? null : resultado.getRegistros().get(0);

    }

    @Override
    public AutorizacaoPagamento obterUltimoAbastecimentoVeiculo(Long idVeiculo, Date dataMaxima, Boolean filtrarPorEstorno) {
        InformacaoPaginacao paginacao = new InformacaoPaginacao(1, 1, new ParametroOrdenacaoColuna("dataProcessamento", Ordenacao.DECRESCENTE));

        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("status", StatusAutorizacao.AUTORIZADO.getValue()));
        parametros.add(new ParametroPesquisaIgual("veiculo.id", idVeiculo));

        if(dataMaxima != null) {
            parametros.add(new ParametroPesquisaDataMenorOuIgual("dataRequisicao", dataMaxima));
        }

        if(filtrarPorEstorno != null && filtrarPorEstorno) {
            parametros.add(new ParametroPesquisaNulo("idAutorizacaoEstorno"));
        }

        ResultadoPaginado<AutorizacaoPagamento> resultado = pesquisarSemIsolamentoDados(paginacao,
                parametros.toArray(new ParametroPesquisa[parametros.size()]));

        return resultado.getRegistros().isEmpty() ? null : resultado.getRegistros().get(0);
    }

    @Override
    public AutorizacaoPagamento obterAutorizacaoPagamentoAnterior(AutorizacaoPagamento autorizacaoPagamento) {
        InformacaoPaginacao paginacao = new InformacaoPaginacao(1, 1, new ParametroOrdenacaoColuna("dataProcessamento", Ordenacao.DECRESCENTE));

        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("status", StatusAutorizacao.AUTORIZADO.getValue()));
        parametros.add(new ParametroPesquisaIgual("veiculo.id", autorizacaoPagamento.getVeiculo().getId()));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataRequisicao", autorizacaoPagamento.getDataRequisicao()));
        parametros.add(new ParametroPesquisaDiferente("id", autorizacaoPagamento.getId()));


        ResultadoPaginado<AutorizacaoPagamento> resultado = pesquisar(paginacao,
                parametros.toArray(new ParametroPesquisa[parametros.size()]));

        return resultado.getRegistros().isEmpty() ? null : resultado.getRegistros().get(0);
    }

    @Override
    public AutorizacaoPagamento obterNotaParaSolicitacaoAjudaMotorista(Long idAutorizacaoPagamento, Long idFrota, Long idMotorista) {
        return pesquisarUnico(
                new ParametroPesquisaIgual("status", StatusAutorizacao.AUTORIZADO.getValue()),
                new ParametroPesquisaIgual("id", idAutorizacaoPagamento),
                new ParametroPesquisaIgual("motorista.id", idMotorista),
                new ParametroPesquisaIgual("transacaoFrota.frota.id", idFrota));
    }

    /**
     * Monta os parametros de pesquisa a partir do filtro de busca recebido
     *
     * @param filtro O filtro de busca
     * @return Uma lista de parametros de pesquisa
     */
    private List<ParametroPesquisa> montarParametroPesquisa(FiltroPesquisaAbastecimentoVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        povoarParametroIgual("frota", filtro.getFrota() != null ? filtro.getFrota().getId() : null, parametros);
        povoarParametroIgual("pontoVenda", filtro.getPontoDeVenda() != null ? filtro.getPontoDeVenda().getId() : null, parametros);
        montarParametroComPostergacao(
                montarParametroPeriodoData("dataProcessamento", filtro.getDe(), filtro.getAte()),
                montarParametroPeriodoData("dataPostergacao", filtro.getDe(), filtro.getAte()),
                filtro.isConsiderarPostergados(),
                parametros
        );

        ParametroPesquisa parametroDataRequisicao = montarParametroPeriodoData("dataRequisicao", filtro.getRequisicaoDe(), filtro.getRequisicaoAte());
        if (parametroDataRequisicao != null) {
            parametros.add(parametroDataRequisicao);
        }

        montarParametroComPostergacao(
                montarParametroPeriodoData("dataProcessamento", filtro.getProcessamentoDe(), filtro.getProcessamentoAte()),
                montarParametroPeriodoData("dataPostergacao", filtro.getProcessamentoDe(), filtro.getProcessamentoAte()),
                filtro.isConsiderarPostergados(),
                parametros);

        povoarParametroDataMaiorIgual("dataRequisicao", filtro.getDataAbastecimento(), parametros);
        povoarParametroDataMenorIgual("dataRequisicao", filtro.getDataAbastecimento(), parametros);
        povoarParametroLike("placaVeiculo", filtro.getPlaca(), parametros);
        povoarParametroPesquisaUnidade(filtro, parametros);
        povoarParametroOrdenacaoColuna(filtro);
        povoarParametroLike("notasFiscais.numero", filtro.getNotaFiscal(), parametros);
        povoarParametroIgual("notasFiscais.numeroSerie", filtro.getNumeroSerie(), parametros);
        povoarParametroEmpresaAgregada(filtro, parametros);
        povoarParametroModalidadePagementoPre(filtro, parametros);
        povoarParametrosStatus(filtro, parametros);
        povoarParametrosEstorno(filtro, parametros);

        montarParametroComPostergacao(
                montarParametroPeriodoData("dataProcessamento", filtro.getDataHoraProcessamentoDe(), filtro.getDataHoraProcessamentoAte()),
                montarParametroPeriodoData("dataPostergacao", filtro.getDataHoraProcessamentoDe(), filtro.getDataHoraProcessamentoAte()),
                filtro.isConsiderarPostergados(), parametros);

        povoarParametrosAjustados(filtro, parametros);
        povoarParametroIgual("veiculo.identificadorInterno", filtro.getIdentificadorInterno(), parametros);

        if(filtro.isContingencia()){
            parametros.add(new ParametroPesquisaIn("tipoAutorizacaoPagamento", Arrays.asList(TipoAutorizacaoPagamento.PCC.getValue(), TipoAutorizacaoPagamento.MAN.getValue())));
        }
        if (filtro.getClassificacao() != null && filtro.getClassificacao().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("agregadoVeiculo", ClassificacaoAgregado.valueOf(filtro.getClassificacao().getName()).getValue()));
        }
        // se a pesquisa se refere aos abastecimentos de uma cobranca, entao apenas os pos pagos devem ser listados
        if(filtro.getIdCobranca() != null) {
            parametros.add(new ParametroPesquisaIgual("transacaoFrota.consumiuCreditoPrePago", false));
            parametros.add(new ParametroPesquisaOr(
                new ParametroPesquisaAnd(
                        new ParametroPesquisaIgual("transacaoConsolidada.cobranca.id", filtro.getIdCobranca()),
                        new ParametroPesquisaNulo("transacaoConsolidadaPostergada")
                ),
                new ParametroPesquisaIgual("transacaoConsolidadaPostergada.cobranca.id", filtro.getIdCobranca())
            ));
        }
        if(filtro.getIdConsolidado() != null){
            parametros.add(new ParametroPesquisaIgual("empresaAgregadaExigeNf", filtro.getEmpresaAgregada() != null));
            parametros.add(new ParametroPesquisaIgual("unidadeExigeNf", filtro.getUnidade() != null));

            ParametroPesquisaIgual abastecimentoPostergadoParaCiclo = new ParametroPesquisaIgual("transacaoConsolidadaPostergada.id", filtro.getIdConsolidado());
            if(filtro.isConsiderarPostergados()) {
                parametros.add(new ParametroPesquisaOr(new ParametroPesquisaIgual("transacaoConsolidada.id", filtro.getIdConsolidado()), abastecimentoPostergadoParaCiclo));
            } else {
                ParametroPesquisaAnd abastecimentoNaoPostergado = new ParametroPesquisaAnd(
                        new ParametroPesquisaIgual("transacaoConsolidada.id", filtro.getIdConsolidado()),
                        new ParametroPesquisaNulo("dataPostergacao")
                );
                parametros.add(new ParametroPesquisaOr(abastecimentoNaoPostergado, abastecimentoPostergadoParaCiclo));
            }
        }
        if(filtro.isPdv()){
            parametros.add(new ParametroPesquisaIgual("tipoAutorizacaoPagamento", TipoAutorizacaoPagamento.PDV_WEB.getValue()));
        }
        if(filtro.getIdsPontoVenda() != null){
            parametros.add(new ParametroPesquisaIn("pontoVenda.id", filtro.getIdsPontoVenda()));
        }
        if(filtro.getPendenteConfirmacaoPOS()){
            parametros.add(new ParametroPesquisaIgual("pedido.transacoesFrotasLeves.statusConfirmacao", StatusConfirmacaoTransacao.NAO_CONFIRMADO.getValue()));
        }
        if (filtro.getIdReembolso() != null) {
            parametros.add(new ParametroPesquisaIgual("transacaoConsolidada.reembolso.id", filtro.getIdReembolso()));
        }
        if (CollectionUtils.isNotEmpty(filtro.getIdsFrotas())) {
            parametros.add(new ParametroPesquisaIn("frota", filtro.getIdsFrotas()));
        }
        return parametros;
    }

    /**
     * Monta parâmetro de pesquisa para buscar datas dentro de um intervalo fechado de datas
     * @param campoData nome do campo de data da entidade
     * @param de a data inicial, ou nulo
     * @param ate a data final, ou nulo
     * @return o parametro de pesquisa para o intervalo, ou nulo caso de e ate sejam nulos
     */
    private ParametroPesquisa montarParametroPeriodoData(String campoData, Date de, Date ate) {
        ParametroPesquisaDataMaiorOuIgual parametroDe = null;
        ParametroPesquisaDataMenorOuIgual parametroAte = null;
        if (de != null) {
            parametroDe = new ParametroPesquisaDataMaiorOuIgual(campoData, obterPrimeiroInstanteDia(de));
        }
        if (ate != null) {
            parametroAte = new ParametroPesquisaDataMenorOuIgual(campoData, obterUltimoInstanteDia(ate));
        }

        if (parametroDe != null && parametroAte != null) {
            return new ParametroPesquisaAnd(parametroDe, parametroAte);
        } else if (parametroDe != null) {
            return parametroDe;
        } else if (parametroAte != null) {
            return parametroAte;
        }
        return null;
    }

    /**
     * Combina um parâmetro de data com um parâmetro de data de postergação
     * @param parametroData o parâmetro de data
     * @param parametroPostergacao o parâmetro de pesquisa por data de postergação
     * @param considerarPostergacao indica se a consulta espera ver postergados no período
     */
    private void montarParametroComPostergacao(ParametroPesquisa parametroData, ParametroPesquisa parametroPostergacao, boolean considerarPostergacao, List<ParametroPesquisa> parametros) {
        ParametroPesquisa parametroDataComPostergacao = null;
        if (parametroData != null) {
            if (parametroPostergacao != null && considerarPostergacao) {
                 parametroDataComPostergacao = new ParametroPesquisaOr(parametroData, parametroPostergacao);
            } else {
                 parametroDataComPostergacao = parametroData;
            }
        }

        if (parametroDataComPostergacao != null) {
            parametros.add(parametroDataComPostergacao);
        }
    }

    @Override
    public ResultadoPaginado<AutorizacaoPagamento> pesquisaPaginada(FiltroPesquisaAbastecimentoVo filtro) {
        return pesquisaPaginada(filtro, false);
    }

    @Override
    public ResultadoPaginado<AutorizacaoPagamento> pesquisaPaginada(FiltroPesquisaAbastecimentoVo filtro, Boolean fetchCamposExportacao) {
        List<ParametroPesquisa> parametros = montarParametroPesquisa(filtro);

        if (fetchCamposExportacao) {
            parametros.add(new ParametroPesquisaFetch("empresaAgregada"));
            parametros.add(new ParametroPesquisaFetch("unidade"));
            parametros.add(new ParametroPesquisaFetch("frota"));
            parametros.add(new ParametroPesquisaFetch("pontoVenda"));
        }

        if(filtro.getPaginacao() != null && filtro.getPaginacao().getParametrosOrdenacaoColuna().isEmpty()) {
            filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("dataProcessamento",Ordenacao.DECRESCENTE));
        }
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }



    @Override
    public List<Long> obterAguardandoSaldo() {
        List<AutorizacaoPagamento> autorizacoes = pesquisar(new ParametroOrdenacaoColuna("dataRequisicao", Ordenacao.DECRESCENTE), new ParametroPesquisaIgual("status", StatusAutorizacao.AGUARDANDO_SALDO.getValue()));
        return autorizacoes.stream().map(AutorizacaoPagamento::getId).collect(Collectors.toList());
    }

    @Override
    public AutorizacaoPagamento desanexar(AutorizacaoPagamento entidade){
        return super.desanexar(entidade);
    }

    @Override
    public List<QuantidadeAbastecidaVeiculoVo> obterAbastecimentosNoPeriodo(Long idFrota, Date dataInicial, Date dataFinal) {
        return pesquisar(null, QUERY_ATUALIZACAO_COTA_VEICULO, QuantidadeAbastecidaVeiculoVo.class, new ParametroPesquisaIgual("idFrota", idFrota), new ParametroPesquisaIgual("dataInicial", dataInicial), new ParametroPesquisaIgual("dataFinal", dataFinal) ).getRegistros();
    }

    @Override
    public List<AutorizacaoPagamento> obterAbastecimentosCanceladosNoPeriodo(Long codigoFrota, Long codigoPV, Date dataInicioPeriodo, Date dataFimPeriodo) {
        List<ParametroPesquisa> params = new ArrayList<>();
        params.add(new ParametroPesquisaIgual("status",StatusAutorizacao.CANCELADO.getValue()));
        params.add(new ParametroPesquisaIgual("transacaoFrota.frota.id", codigoFrota));
        params.add(new ParametroPesquisaIgual("pontoVenda.id", codigoPV));
        params.add(new ParametroPesquisaDataMaiorOuIgual("dataProcessamento", dataInicioPeriodo));
        params.add(new ParametroPesquisaDataMenorOuIgual("dataProcessamento", dataFimPeriodo));
        return pesquisar((ParametroOrdenacaoColuna) null, params.toArray(new ParametroPesquisa[params.size()]));
    }

    @Override
    public List<AutorizacaoPagamento> obterAbastecimentosPendenteAutorizacao(Date dataExpiracao) {
        return pesquisar(new ParametroOrdenacaoColuna("dataRequisicao",Ordenacao.DECRESCENTE), new ParametroPesquisaIgual("status", StatusAutorizacao.PENDENTE_AUTORIZACAO.getValue()), new ParametroPesquisaDataMenorOuIgual("dataRequisicao", dataExpiracao));
    }

    @Override
    public AutorizacaoPagamento buscarEstornoNegativoAbastecimento(Long idAutorizacaoPagamento) {
        return pesquisarUnico(
                new ParametroPesquisaIgual("idAutorizacaoEstorno", idAutorizacaoPagamento),
                new ParametroPesquisaMenor("valorTotal", BigDecimal.ZERO)
        );
    }

    @Override
    public List<AutorizacaoPagamento> obterAbastecimentosParaNotaFiscal(Long codigoFrota, Long codigoPV, EmpresaAgregada empresaAgregada, Unidade unidade, Date dataInicioPeriodo, Date dataFimPeriodo, Boolean prePago) {
        List<ParametroPesquisa> params = new ArrayList<>();
        params.add(new ParametroPesquisaIgual("status",StatusAutorizacao.AUTORIZADO.getValue()));
        params.add(new ParametroPesquisaMaior("valorTotal", BigDecimal.ZERO));
        params.add(new ParametroPesquisaIgual("transacaoFrota.frota.id", codigoFrota));
        params.add(new ParametroPesquisaNulo("transacaoFrota.frota.numeroJdeInterno", true));
        params.add(new ParametroPesquisaIgual("pontoVenda.id", codigoPV));
        params.add(new ParametroPesquisaDataMaiorOuIgual("dataProcessamento", dataInicioPeriodo));
        params.add(new ParametroPesquisaDataMenorOuIgual("dataProcessamento", dataFimPeriodo));
        params.add(new ParametroPesquisaIgual("transacaoFrota.consumiuCreditoPrePago", prePago));
        if(empresaAgregada != null && empresaAgregada.getId() != null) { //agregada e exige NF
            params.add(new ParametroPesquisaIgual("empresaAgregada.id", empresaAgregada.getId()));
            params.add(new ParametroPesquisaIgual("empresaAgregadaExigeNf", true));
        } else { //Agregada e não exige NF ou Sede
            params.add(new ParametroPesquisaIgual("empresaAgregadaExigeNf", false));
        }
        if(unidade != null && unidade.getId() != null) { //unidade e exige NF
            params.add(new ParametroPesquisaIgual("unidade.id", unidade.getId()));
            params.add(new ParametroPesquisaIgual("unidadeExigeNf", true));
        } else { //unidade e não exige NF ou Sede
            params.add(new ParametroPesquisaIgual("unidadeExigeNf", false));
        }

        return pesquisar((ParametroOrdenacaoColuna) null, params.toArray(new ParametroPesquisa[params.size()]));
    }

    @Override
    public AutorizacaoPagamento obterAutorizacaoPagamentoComMesmoAbastecimento(String uuidAbastecimento) {
        return pesquisarUnico(new ParametroPesquisaIgual("uuidAbastecimento",uuidAbastecimento),
                new ParametroPesquisaIgual("status", StatusAutorizacao.AUTORIZADO.getValue()));
    }


    @Override
    public ResultadoPaginadoFrtVo<AutorizacaoPagamento> pesquisar(FiltroPesquisaAbastecimentoFrtVo filtro) {
        List<ParametroPesquisa> parametros = criarParametrosPesquisa(filtro);
        InformacaoPaginacaoFrtVo paginacao = new InformacaoPaginacaoFrtVo(
                filtro.getPagina(),
                new ParametroOrdenacaoColuna("dataRequisicao"),
                new ParametroOrdenacaoColuna("id")
        );
        ResultadoPaginado<AutorizacaoPagamento> resultadoPaginado = pesquisar(paginacao, parametros.toArray(new ParametroPesquisa[parametros.size()]));
        return new ResultadoPaginadoFrtVo<>(resultadoPaginado, paginacao.getPagina(), paginacao.getTamanhoPagina());
    }

    @Override
    public List<AutorizacaoPagamento> obterPorIds(List<Long> idsAutorizacaoPagamento) {
        return pesquisar((ParametroOrdenacaoColuna) null,new ParametroPesquisaIn("id",idsAutorizacaoPagamento));
    }

    @Override
    public List<AutorizacaoPagamento> pesquisarAutorizacoesSemNota(FiltroPesquisaAbastecimentoVo filtro) {
        List<ParametroPesquisa> params =  montarParametroPesquisa(filtro);
        params.add(new ParametroPesquisaEmpty("notasFiscais"));
        return pesquisar((InformacaoPaginacao) null, params.toArray(new ParametroPesquisa[params.size()])).getRegistros();
    }

    @Override
    public List<AutorizacaoPagamento> obterAutorizacoesPorCobranca(Long idCobranca) {
        return pesquisar(null, QUERY_AUTORIZACOES_AVISO_DEBITO, new ParametroPesquisaIgual("idCobranca", idCobranca) ).getRegistros();
    }

    @Override
    public AutorizacaoPagamento obterEstornoPorIdAutorizacaoCancelada(Long idAutorizacaoPagamentoCancelada){
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("idAutorizacaoEstorno", idAutorizacaoPagamentoCancelada));
        parametros.add(new ParametroPesquisaMenor("valorTotal", BigDecimal.ZERO));
        parametros.add(new ParametroPesquisaIgual("status",StatusAutorizacao.AUTORIZADO.getValue()));
        return pesquisarUnico(parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<AutorizacaoPagamento> obterEstornosPorIdAutorizacaoCancelada(Long idAutorizacaoPagamentoCancelada){
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("idAutorizacaoEstorno", idAutorizacaoPagamentoCancelada));
        parametros.add(new ParametroPesquisaIgual("status",StatusAutorizacao.AUTORIZADO.getValue()));
        return pesquisar((ParametroOrdenacaoColuna) null, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<AutorizacaoPagamento> obterAbastecimentosPendentesSimilares(Long idAutorizacaoAtual, Long idMotorista, Long idVeiculo, Long idPontoVenda){
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaDiferente("id", idAutorizacaoAtual));
        parametros.add(new ParametroPesquisaIgual("status", StatusAutorizacao.PENDENTE_AUTORIZACAO.getValue()));
        parametros.add(new ParametroPesquisaIgual("motorista.id", idMotorista));
        parametros.add(new ParametroPesquisaIgual("veiculo.id", idVeiculo));
        if (idPontoVenda != null) {
            parametros.add(new ParametroPesquisaIgual("pontoVenda.id", idPontoVenda));
        } else {
            parametros.add(new ParametroPesquisaNulo("pontoVenda"));
        }
        return pesquisar((ParametroOrdenacaoColuna) null, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public AutorizacaoPagamento buscaPorCdCTA(Long cdCTA){
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("codigoAbastecimentoCTA",cdCTA));
        parametros.add( new ParametroPesquisaFetch("items"));
        return pesquisarUnico(parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public AutorizacaoPagamento obterAbastecimentoPosPorPedido(Long idPedido) {
        return pesquisarUnico(
                new ParametroPesquisaIgual("pedido.id", idPedido),
                new ParametroPesquisaIgual("tipoAutorizacaoPagamento", TipoAutorizacaoPagamento.POS_FL.getValue()),
                new ParametroPesquisaIgual("status", StatusAutorizacao.AUTORIZADO.getValue()),
                new ParametroPesquisaMaior("valorTotal", BigDecimal.ZERO),
                new ParametroPesquisaNulo("idAutorizacaoEstorno")
        );
    }

    @Override
    public List<TransacaoPendenteVo> obterAbastecimentosAutorizadosPendentesDeConciliacao() {
        Date dataAtual = new Date();
        Date limiteSuperior = UtilitarioCalculoData.adicionarHorasData(dataAtual, LIMITE_SUPERIOR_CONCILIACAO);
        Date limiteInferior = UtilitarioCalculoData.adicionarDiasData(dataAtual, LIMITE_INFERIOR_CONCILIACAO);
        return pesquisar(null, QUERY_ABASTECIMENTOS_AUTORIZADOS_PENDENTES_CONFIRMACAO, TransacaoPendenteVo.class,
                new ParametroPesquisaIgual("limiteInferiorData", limiteInferior),
                new ParametroPesquisaIgual("limiteSuperiorData", limiteSuperior)
        ).getRegistros();
    }

    @Override
    public BigDecimal obterLitragemDoAbastecimentoAnterior(Long idAbastecimento, Date dataAbastecimento, String placaVeiculo, Long cnpjFrota) {

        List<BigDecimal> litragem = this.pesquisar(new InformacaoPaginacao(1,1),
                QUERY_LITRAGEM_ULTIMO_ABASTECIMENTO,
                BigDecimal.class,
                new ParametroPesquisaIgual("idAbastecimento", idAbastecimento),
                new ParametroPesquisaIgual("dataAbastecimento", dataAbastecimento),
                new ParametroPesquisaIgual("placaVeiculo", placaVeiculo),
                new ParametroPesquisaIgual("cnpjFrota", cnpjFrota)
        ).getRegistros();

        return litragem.isEmpty() ? null : litragem.get(0);
    }

    @Override
    public List<AutorizacaoPagamento> obterAbastecimentoPorNota(Long cnpjDest, Long cnpjEmit, Date dataEmissao, BigDecimal valorTotalNota) {
        final BigDecimal toleranciaDeValorNota = BigDecimal.valueOf(.05);
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("dataEmissao", obterUltimoInstanteDia(dataEmissao)));
        parametros.add(new ParametroPesquisaIgual("cnpjEmit", cnpjEmit));
        parametros.add(new ParametroPesquisaIgual("cnpjDest", cnpjDest));
        parametros.add(new ParametroPesquisaIgual("limiteSuperiorTotalNf", valorTotalNota.add(toleranciaDeValorNota)));
        parametros.add(new ParametroPesquisaIgual("limiteInferiorTotalNf", valorTotalNota.subtract(toleranciaDeValorNota)));

        return pesquisar((InformacaoPaginacao) null, CONSULTA_ABASTECIMENTOS_POR_NFE, AutorizacaoPagamento.class,
                parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }

    /**
     * Cria a lista de parametros para a pesquisa da tela de listagem de autorizacoes de pagamento
     * @param filtro O filtro de pesquisa
     * @return Os parametros montados
     */
    private List<ParametroPesquisa> criarParametrosPesquisa(FiltroPesquisaAbastecimentoFrtVo filtro) {
        return new ParametrosPesquisaBuilder()
                .adicionarParametros(
                        new ParametroPesquisaIgual("id", filtro.getIdentificador())
                )
                .adicionarParametros(
                        new ParametroPesquisaDataMaiorOuIgual("dataProcessamento", obterPrimeiroInstanteDia(filtro.getDataInicial())),
                        new ParametroPesquisaDataMenorOuIgual("dataProcessamento", obterUltimoInstanteDia(filtro.getDataFinal()))
                )
                .adicionarParametros(
                        filtro.getCnpjRevenda(),
                        this::criarParametroFiltroFrotistaCnpjRevendaPV,
                        this::criarParametroFiltroFrotistaCnpjRevendaUnidade,
                        this::criarParametroFiltroFrotistaCnpjRevendaFrota
                )
                .adicionarParametros(filtro.getPostoInterno(), valor -> new ParametroPesquisaNulo("pontoVenda", !((Boolean) valor)))
                .adicionarParametros(filtro.getPlacaVeiculo(), valor -> new ParametroPesquisaIgual("veiculo.placa", valor))
                .adicionarParametros(
                        filtro.getTipoVeiculo(),
                        valor -> new ParametroPesquisaIgual("veiculo.subtipoVeiculo.tipoVeiculo.id", valor)
                )
                .adicionarParametros(filtro.getSubtipoVeiculo(), valor -> new ParametroPesquisaIgual("veiculo.subtipoVeiculo.id", valor))
                .adicionarParametros(
                        filtro.getCpfMotorista(),
                        valor -> new ParametroPesquisaIgual("motorista.cpf", valor)
                )
                .adicionarParametros(filtro.getCnpjUnidade(), this::criarParametroFiltroFrotistaCnpjUnidade)
                .adicionarParametros(filtro.getCnpjEmpresaAgregada(), this::criarParametroFiltroFrotistaCnpjEmpresaAgregada)
                .adicionarParametros(filtro.getNumeroNotaFiscal(), valor -> new ParametroPesquisaIgual("notasFiscais.numero", valor))
                .adicionarParametros(filtro.getStatusEmissaoNotaFiscal(), valor ->  new ParametroPesquisaIgual("statusNotaFiscal", valor))
                .adicionarParametros( filtro.getStatusAutorizacaoPagamento() != null ?
                        criarParametroFiltroFrotistaStatusPagamentoIgual(filtro.getStatusAutorizacaoPagamento()) :
                        criarParametroFiltroFrotistaStatusPagamentoTodos()
                )
                .adicionarParametros(filtro.getPagamentoEmContingencia(), this::criarParametroFiltroFrotistaPagementoContigencia)
                .adicionarParametros(
                        filtro.getPagamentoSemEstorno(),
                        valor -> new ParametroPesquisaNulo("idAutorizacaoEstorno", !((Boolean) valor))
                )
                .build();
    }

    /**
     * Cria o parametro de pesquisa relativo ao CNPJ da revenda para o contexto do frotista
     * @param cnpjRevenda O cnpj
     * @param <T> O tipo do parametro
     * @return O parametro
     */
    private <T> ParametroPesquisa criarParametroFiltroFrotistaCnpjRevendaPV(T cnpjRevenda) {
        return new ParametroPesquisaOr(
                new ParametroPesquisaAnd(
                        new ParametroPesquisaIgual("pontoVenda.componentes.codigoPessoa", cnpjRevenda),
                        new ParametroPesquisaIn("pontoVenda.componentes.atividadeComponente.codigoCorporativo", AtividadeComponente.obterCodigosAreaAbastecimento())
                ),
                new ParametroPesquisaNulo("pontoVenda", false)
        );
    }

    /**
     * Cria o parametro de pesquisa relativo ao CNPJ da revenda
     * @param cnpjRevenda O cnpj
     * @param <T> O tipo do parametro
     * @return O parametro
     */
    private <T> ParametroPesquisa criarParametroFiltroFrotistaCnpjRevendaUnidade(T cnpjRevenda) {
        return new ParametroPesquisaOr(
                new ParametroPesquisaNulo("pontoVenda", true),
                new ParametroPesquisaAnd(
                        new ParametroPesquisaOr(
                                new ParametroPesquisaIgual("unidade.cnpj", cnpjRevenda),
                                new ParametroPesquisaNulo("unidade", false)
                        ),
                        new ParametroPesquisaNulo("pontoVenda", false)
                )
        );
    }

    /**
     * Cria o parametro de pesquisa relativo ao CNPJ da revenda para o contexto do frotista
     * @param cnpjRevenda O cnpj
     * @return O parametro
     */
    private ParametroPesquisa criarParametroFiltroFrotistaCnpjRevendaFrota(Object cnpjRevenda) {
        return new ParametroPesquisaOr(
                new ParametroPesquisaAnd(
                        new ParametroPesquisaIgual("frota.cnpj", cnpjRevenda),
                        new ParametroPesquisaNulo("unidade", false),
                        new ParametroPesquisaNulo("pontoVenda", false)
                ),
                new ParametroPesquisaNulo("unidade", true),
                new ParametroPesquisaNulo("pontoVenda", true)
        );
    }

    /**
     * Cria o parametro de pesquisa relativo ao CNPJ da unidade da frota
     * @param valor O cnpj da unidade
     * @return O parametro
     */
    private ParametroPesquisa criarParametroFiltroFrotistaCnpjUnidade(Object valor) {
        return new ParametroPesquisaOr(
                new ParametroPesquisaIgual("motorista.unidade.cnpj", valor),
                new ParametroPesquisaIgual("veiculo.unidade.cnpj", valor)
        );
    }

    /**
     * Cria o parametro de pesquisa relativo ao CNPJ da empresa agregada da frota
     * @param valor O cnpj
     * @return O parametro
     */
    private ParametroPesquisa criarParametroFiltroFrotistaCnpjEmpresaAgregada(Object valor) {
        return new ParametroPesquisaOr(
                new ParametroPesquisaIgual("motorista.empresaAgregada.cnpj", valor),
                new ParametroPesquisaIgual("empresaAgregada.cnpj", valor)
        );
    }

    /**
     * Cria o parametro de pesquisa relativo ao status do pagamento
     * @param valor O status
     * @return O parametro
     */
    private ParametroPesquisa criarParametroFiltroFrotistaStatusPagamentoIgual(Integer valor) {
        return  new ParametroPesquisaIgual("status", valor);
    }

    /**
     * Cria o parametro de pesquisa relativo para todos os status possiveis das autorizacoes
     * @return O parametro
     */
    private ParametroPesquisa criarParametroFiltroFrotistaStatusPagamentoTodos() {
        return new ParametroPesquisaIn("status", Arrays.asList(
                StatusAutorizacao.CANCELADO.getValue(),
                StatusAutorizacao.NAO_AUTORIZADO.getValue(),
                StatusAutorizacao.AUTORIZADO.getValue(),
                StatusAutorizacao.PENDENTE_AUTORIZACAO.getValue()
        ));
    }

    /**
     * Cria o parametro de pesquisa relativo ao tipo de autorizacao de pagamento
     * @param valor O tipo de autorizacao de pagamento
     * @return O parametro
     */
    private ParametroPesquisa criarParametroFiltroFrotistaPagementoContigencia(Object valor) {
        if((Boolean) valor) {
            return new ParametroPesquisaIgual("tipoAutorizacaoPagamento", TipoAutorizacaoPagamento.PCC.getValue());
        }
        return new ParametroPesquisaOr(
                new ParametroPesquisaNulo("tipoAutorizacaoPagamento", false),
                new ParametroPesquisaDiferente("tipoAutorizacaoPagamento", TipoAutorizacaoPagamento.PCC.getValue())
        );
    }

    /**
     * Povoa o parametro de ordenacao das colunas
     * @param filtro filtro de pesquisa de abastecimento
     */
    private void povoarParametroOrdenacaoColuna(FiltroPesquisaAbastecimentoVo filtro) {
        if(filtro.getPaginacao() != null) {
            for (ParametroOrdenacaoColuna p : filtro.getPaginacao().getParametrosOrdenacaoColuna()) {
                if ("statusAutorizacao.label".equals(p.getNome())) {
                    p.setNome("statusAutorizacao");
                }
            }
        }
    }

    /**
     * Povoa parametro da empresa agregada
     * @param filtro filtro de pesquisa do abastecimento
     * @param parametros lista de parametros de pesquisa
     */
    private void povoarParametroEmpresaAgregada(FiltroPesquisaAbastecimentoVo filtro, List<ParametroPesquisa> parametros) {
        if(filtro.getEmpresaAgregada() != null) {
            parametros.add(new ParametroPesquisaIgual("cnpjEmpresaVeiculo", UtilitarioFormatacao.obterLongMascara(filtro.getEmpresaAgregada().getCnpj())));
            parametros.add(new ParametroPesquisaIgual("agregadoVeiculo", ClassificacaoAgregado.AGREGADO.getValue()));
        }
    }

    /**
     * Povoa parametros de pesquisa para abastecimentos na modalidade de pagamento pre
     * @param filtro filtro de pesquisa de abastecimetno
     * @param parametros lista de parametros de pesquisa
     */
    private void povoarParametroModalidadePagementoPre(FiltroPesquisaAbastecimentoVo filtro, List<ParametroPesquisa> parametros) {
        if(filtro.getIdConsolidado() != null) {
            TransacaoConsolidada transacaoConsolidada = getGerenciadorDeEntidade().find(TransacaoConsolidada.class, filtro.getIdConsolidado());
            if (transacaoConsolidada != null) {
                boolean pre = ModalidadePagamento.PRE_PAGO.getValue().equals(transacaoConsolidada.getModalidadePagamento());
                parametros.add(new ParametroPesquisaIgual("transacaoFrota.consumiuCreditoPrePago", pre));
            }
        }
    }

    /**
     * Povoa parametros de pesquisa de unidade
     * @param filtro filtro de pesquisa de abastecimento
     * @param parametros lista de parametros de pesquisa de abastecimento
     */
    private void povoarParametroPesquisaUnidade(FiltroPesquisaAbastecimentoVo filtro, List<ParametroPesquisa> parametros) {
        if (filtro.getUnidade() != null && filtro.getUnidade().getId() != null) {
            if (filtro.getUnidade().getId() > 0) {
                parametros.add(new ParametroPesquisaIgual("unidade", filtro.getUnidade().getId()));
            } else {
                parametros.add(new ParametroPesquisaNulo("unidade"));
            }
        }
    }

    /**
     * Povoa os parametros de status e statusNotaFiscal da consulta de abastecimentos
     * @param filtro O filtro de pesquisa
     * @param parametros Lista de parametros de pesquisa de abastecimento
     */
    private void povoarParametrosStatus(FiltroPesquisaAbastecimentoVo filtro, List<ParametroPesquisa> parametros) {
        if(filtro.getStatusAutorizacao() != null && filtro.getStatusAutorizacao().getName() != null) {
            Integer status = StatusAutorizacao.valueOf(filtro.getStatusAutorizacao().getName()).getValue();
            if(StatusAutorizacao.AGUARDANDO_APROVACAO_SOLUCAO.getValue().equals(status)) {
                parametros.add(new ParametroPesquisaOr(
                        new ParametroPesquisaIgual("status", StatusAutorizacao.AGUARDANDO_APROVACAO_SOLUCAO.getValue()),
                        new ParametroPesquisaIgual("statusEdicao", StatusEdicao.PENDENTE.getValue())
                ));
            } else {
                parametros.add(new ParametroPesquisaIgual("status", status));
            }
        }
        if(filtro.getStatusEmissaoNf() != null && filtro.getStatusEmissaoNf().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("statusNotaFiscal", StatusNotaFiscalAbastecimento.valueOf(filtro.getStatusEmissaoNf().getName()).getValue()));
        }
    }

    /**
     * Povoa os parametros de pesquisa pertinentes ao filtro de estorno da consulta
     * @param filtro O filtro de pesquisa
     * @param parametros Lista de parametros de pesquisa de abastecimento
     */
    private void povoarParametrosEstorno(FiltroPesquisaAbastecimentoVo filtro, List<ParametroPesquisa> parametros) {
        if(filtro.getSemEstorno() != null && filtro.getSemEstorno()){
            parametros.add(new ParametroPesquisaMaior("valorTotal", BigDecimal.ZERO));
            parametros.add(new ParametroPesquisaIgual("status", StatusAutorizacao.AUTORIZADO.getValue()));
        }

        if(null != filtro.isApenasEstornos() && filtro.isApenasEstornos()) {
            parametros.add(new ParametroPesquisaNulo("idAutorizacaoEstorno", true));
            parametros.add(new ParametroPesquisaMenor("valorTotal", BigDecimal.ZERO));
        }
    }

    /**
     * Verifica se a consulta foi para abastecimentos ajustados e insere as restrições na query
     * @param filtro Filtro de pesquisa usado na consulta dos abastecimentos
     * @param parametros Lista de parametros de pesquisa de abastecimento
     */
    private void povoarParametrosAjustados(FiltroPesquisaAbastecimentoVo filtro, List<ParametroPesquisa> parametros) {
        if(null != filtro.isApenasAjustados() && filtro.isApenasAjustados()) {
            parametros.add(new ParametroPesquisaIgual("statusEdicao", StatusEdicao.EDITADO.getValue()));
        }
    }

    @Override
    public List<InformacoesVolumeVo> obterInformacoesVendasProfrotasAPCO(Date dataInicial, Date dataFinal) {
        return pesquisar(null, CONSULTA_VENDA_PROFROTAS, InformacoesVolumeVo.class, new ParametroPesquisaIgual("dataInicial", dataInicial), new ParametroPesquisaIgual("dataFinal", dataFinal)).getRegistros();
    }

    @Override
    public List<AutorizacaoPagamento> obterAbastecimentosComJustificativaAssociadaPorAbastecimentos(List<Long> idsAutorizacoes) {

        if(CollectionUtils.isNotEmpty(idsAutorizacoes)) {
            return pesquisar(null, QUERY_AUTORIZACOES_EM_LISTA_DE_ABASTECIMENTOS_POR_FLAG_JUSTIFICATIVA,
                    new ParametroPesquisaIgual("idsAutorizacoes", idsAutorizacoes),
                    new ParametroPesquisaIgual("isJustificativa", true)).getRegistros();
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public List<AutorizacaoPagamento> obterAbastecimentosCicloParaNotaFiscal(TransacaoConsolidada transacaoConsolidada) {
        List<ParametroPesquisa> params = new ArrayList<>();
        params.add(new ParametroPesquisaIgual("status", StatusAutorizacao.AUTORIZADO.getValue()));
        params.add(new ParametroPesquisaMaior("valorTotal", BigDecimal.ZERO));

        params.add(new ParametroPesquisaOr(
                new ParametroPesquisaIgual("transacaoConsolidadaPostergada.id", transacaoConsolidada.getId()),
                new ParametroPesquisaAnd(
                        new ParametroPesquisaIgual("transacaoConsolidada.id", transacaoConsolidada.getId()),
                        new ParametroPesquisaNulo("dataPostergacao"))
        ));

        //Validando exigência de NF para ciclos de empresas agregadas e unidades.
        if(transacaoConsolidada.getEmpresaAgregada() != null && transacaoConsolidada.getEmpresaAgregada().getId() != null) {
            params.add(new ParametroPesquisaIgual("empresaAgregada.id", transacaoConsolidada.getEmpresaAgregada().getId()));
            params.add(new ParametroPesquisaIgual("empresaAgregadaExigeNf", true));
        }
        if(transacaoConsolidada.getUnidade() != null && transacaoConsolidada.getUnidade().getId() != null) {
            params.add(new ParametroPesquisaIgual("unidade.id", transacaoConsolidada.getUnidade().getId()));
            params.add(new ParametroPesquisaIgual("unidadeExigeNf", true));
        }

        return pesquisar((ParametroOrdenacaoColuna) null, params.toArray(new ParametroPesquisa[params.size()]));
    }

    @Override
    public Integer obterNumeroAbastecimentosPostergados(FiltroPesquisaAbastecimentoVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaIgual("idConsolidado", filtro.getIdConsolidado()));

        parametros.add(new ParametroPesquisaIgual("dataRequisicaoDe", obterPrimeiroInstanteDia(filtro.getDataAbastecimento())));
        parametros.add(new ParametroPesquisaIgual("dataRequisicaoAte", obterUltimoInstanteDia(filtro.getDataAbastecimento())));

        parametros.add(new ParametroPesquisaIgual("placaVeiculo", filtro.getPlaca() != null ? filtro.getPlaca().toLowerCase() : null));

        Long quantidadePostergados = pesquisarUnicoSemIsolamentoDados(CONSULTA_QUANTIDADE_ABASTECIMENTOS_POSTERGADOS, parametros.toArray(new ParametroPesquisa[parametros.size()]));
        return quantidadePostergados.intValue();
    }

    @Override
    public Long obterTotalAbastecimentosAutorizadosPorMotorista(Long cpfMotorista) {
        Long totalAbastecimentos = pesquisarTotalRegistros(
                new ParametroPesquisaIgual("motorista.cpf", cpfMotorista),
                new ParametroPesquisaIgual("status", StatusAutorizacao.AUTORIZADO.getValue()),
                new ParametroPesquisaOr(
                        new ParametroPesquisaMaior("valorTotal", BigDecimal.ZERO),
                        new ParametroPesquisaNulo("valorTotal"))
        );

        return totalAbastecimentos != null ? totalAbastecimentos : 0;
    }

    @Override
    public Date obterDataUltimoAbastecimentoAutorizadoMotorista(Long cpfMotorista) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("motorista.cpf", cpfMotorista));
        parametros.add(new ParametroPesquisaIgual("status", StatusAutorizacao.AUTORIZADO.getValue()));
        parametros.add(new ParametroPesquisaOr(
                new ParametroPesquisaMaior("valorTotal", BigDecimal.ZERO),
                new ParametroPesquisaNulo("valorTotal"))
        );

        AutorizacaoPagamento autorizacaoPagamento = pesquisar(new InformacaoPaginacao(1,1,"dataRequisicao",Ordenacao.DECRESCENTE),
                parametros.toArray(new ParametroPesquisa[parametros.size()]))
                .getRegistros().stream().findFirst().orElse(null);

        return autorizacaoPagamento == null ? null : autorizacaoPagamento.getDataRequisicao();
    }

    @Override
    public AutorizacaoPagamento obterTransacaoAjustadaOriundaDeEstorno(AutorizacaoPagamento transacaoEstornada) {

        List<ParametroPesquisa> params = new ArrayList<>();

        params.add(new ParametroPesquisaIgual("idAutorizacaoEstorno", transacaoEstornada.getId()));
        params.add(new ParametroPesquisaMaior("valorTotal", BigDecimal.ZERO));

        return pesquisarUnicoSemIsolamentoDados(params.toArray(new ParametroPesquisa[params.size()]));

    }

    @Override
    public AutorizacaoPagamento obterTransacaoNegativaOriundaDeEstorno(AutorizacaoPagamento transacaoEstornada) {

        List<ParametroPesquisa> params = new ArrayList<>();

        params.add(new ParametroPesquisaIgual("idAutorizacaoEstorno", transacaoEstornada.getId()));
        params.add(new ParametroPesquisaMenor("valorTotal", BigDecimal.ZERO));

        return pesquisarUnicoSemIsolamentoDados(params.toArray(new ParametroPesquisa[params.size()]));

    }

    @Override
    public List<AutorizacaoPagamento> obterAutorizacoesDoCiclo(FiltroPesquisaAbastecimentoVo filtro) {
        List<ParametroPesquisa> parametros = montarParametroPesquisa(filtro);

        return pesquisar((ParametroOrdenacaoColuna)null, (ParametroPesquisa[])parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<AutorizacaoPagamento> obterPorTransacoesConsolidadas(List<Long> idsTransacoesConsolidadas) {
        List<ParametroPesquisa> params = new ArrayList<>();
        return pesquisar((InformacaoPaginacao) null, CONSULTA_VIGENTES_POR_CICLOS, new ParametroPesquisaIgual("idsTransacoesConsolidadas", idsTransacoesConsolidadas)).getRegistros();
    }

    @Override
    public ResultadoPaginado<AutorizacaoPagamento> pesquisaPaginadaDetalheCobranca(FiltroPesquisaDetalheCobrancaVo filtro) {
        List<ParametroPesquisa> parametros = montarParametrosPesquisaDetalheCobranca(filtro);
        String ordenacao = montarOrdenacaoDetalheCobranca(filtro);
        String filtroOutrosServicos = montarFiltroOutroServicosDetalheCobranca(filtro);

        String consultaPesquisa = String.format(CONSULTA_ABASTECIMENTOS_COBRANCA, filtroOutrosServicos, ordenacao);

        return pesquisar(filtro.getPaginacao(), consultaPesquisa, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public ResultadoPaginado<AutorizacaoPagamento> pesquisaPaginadaDetalheReembolso(FiltroPesquisaDetalheReembolsoVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaOr(
           new ParametroPesquisaIgual("transacaoConsolidada.id", filtro.getIdConsolidado()),
           new ParametroPesquisaIgual("transacaoConsolidadaPostergada.id", filtro.getIdConsolidado())
        ));
        if(filtro.getDataAbastecimento() != null) {
            parametros.add(new ParametroPesquisaDataMenorOuIgual("dataRequisicao", obterUltimoInstanteDia(filtro.getDataAbastecimento())));
            parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataRequisicao", obterPrimeiroInstanteDia(filtro.getDataAbastecimento())));
        }
        if(filtro.getPlacaVeiculo() != null) {
            parametros.add(new ParametroPesquisaIgualIgnoreCase("placaVeiculo", filtro.getPlacaVeiculo().toUpperCase()));
        }
        if(filtro.getOutrosServicos() != null && filtro.getOutrosServicos().size() > 0) {
            ParametroPesquisaOr parametrosOutrosServicos = new ParametroPesquisaOr();
            filtro.getOutrosServicos().forEach(s -> parametrosOutrosServicos.addParametro(new ParametroPesquisaIgual("itens.produto.id", s.getId())));
            parametros.add(parametrosOutrosServicos);
        }
        if(filtro.getNumeroNf() != null) {
            parametros.add(new ParametroPesquisaLike("notasFiscais.numero", filtro.getNumeroNf()));
        }
        if(filtro.getNumeroSerieNf() != null) {
            parametros.add(new ParametroPesquisaLike("notasFiscais.numeroSerie", filtro.getNumeroSerieNf()));
        }
        if(filtro.getTransacaoEmitida() != null){
            parametros.add(new ParametroPesquisaIgual("statusNotaFiscal", filtro.getTransacaoEmitida() ? EMITIDA.getValue() : PENDENTE.getValue()));
        }
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<AutorizacaoPagamento> pesquisaDetalheCobrancaSemPaginacao(FiltroPesquisaDetalheCobrancaVo filtro) {
        List<ParametroPesquisa> parametros = montarParametrosPesquisaDetalheCobranca(filtro);
        String ordenacao = montarOrdenacaoDetalheCobranca(filtro);
        String filtroOutrosServicos = montarFiltroOutroServicosDetalheCobranca(filtro);

        String consultaPesquisa = String.format(CONSULTA_ABASTECIMENTOS_COBRANCA, filtroOutrosServicos, ordenacao);
        return pesquisar(null, consultaPesquisa, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }

    /**
     * Monta string de ordenação da consulta de detalhe da cobrança
     * @param filtro O filtro fornecido
     * @return A string de ordenação montada
     */
    private String montarOrdenacaoDetalheCobranca(FiltroPesquisaDetalheCobrancaVo filtro) {
        String ordenacao = "";
        if((filtro.getPaginacao() == null || filtro.getPaginacao().getParametrosOrdenacaoColuna().isEmpty())) {
            ordenacao = " A.chaveOrdenacaoFinanceiro, A.dataRequisicao ";
        } else {
            String nomeCampoPv = "pontoDeVenda";
            String nomeCampoChaveOrdenacao = "chaveOrdenacaoFinanceiro";
            String sentido = filtro.getPaginacao().getParametrosOrdenacaoColuna().get(0).getSentidoOrdenacao().equals(Ordenacao.DECRESCENTE) ? " DESC" : " ";
            if(nomeCampoPv.equals(filtro.getPaginacao().getParametrosOrdenacaoColuna().get(0).getNome())) {
                ordenacao = " A.nomePv " + sentido;
            } else if(nomeCampoChaveOrdenacao.equals(filtro.getPaginacao().getParametrosOrdenacaoColuna().get(0).getNome())) {
                ordenacao = " A.chaveOrdenacaoFinanceiro, A.dataRequisicao";
            } else {
                ordenacao = " A.dataRequisicao " + sentido;
            }
        }
        return ordenacao;
    }

    /**
     * Monta lista de parâmetros de pesquisa para a consulta de detalhe da cobrança
     * @param filtro O filtro fornecido
     * @return A lista de parâmetros
     */
    private List<ParametroPesquisa> montarParametrosPesquisaDetalheCobranca(FiltroPesquisaDetalheCobrancaVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        if(filtro.getIdConsolidado() != null) {
            parametros.add(new ParametroPesquisaIgual("idConsolidado", filtro.getIdConsolidado()));
        } else {
            parametros.add(new ParametroPesquisaIgual("idConsolidado", null));
        }
        if(filtro.getDataAbastecimento() != null) {
            parametros.add(new ParametroPesquisaIgual("dataRequisicao", filtro.getDataAbastecimento()));
        } else {
            parametros.add(new ParametroPesquisaIgual("dataRequisicao", null));
        }

        if(filtro.getPlacaVeiculo() != null) {
            parametros.add(new ParametroPesquisaIgual("placaVeiculo", filtro.getPlacaVeiculo().toLowerCase()));
        } else {
            parametros.add(new ParametroPesquisaIgual("placaVeiculo", null));
        }
        if(filtro.getNotaFiscalEmitida() != null && filtro.getNotaFiscalEmitida()){
            parametros.add(new ParametroPesquisaIgual("statusNotaFiscal", EMITIDA.getValue()));
        } else if (filtro.getNotaFiscalEmitida() != null && !filtro.getNotaFiscalEmitida()) {
            parametros.add(new ParametroPesquisaIgual("statusNotaFiscal", PENDENTE.getValue()));
        } else {
            parametros.add(new ParametroPesquisaIgual("statusNotaFiscal", null));
        }

        if (filtro.getMotorista() != null ) {
            parametros.add(new ParametroPesquisaIgual("idMotorista", filtro.getMotorista().getId()));
        } else {
            parametros.add(new ParametroPesquisaIgual("idMotorista", null));
        }

        if(filtro.getPontoVenda() != null) {
            parametros.add(new ParametroPesquisaIgual("idPv", filtro.getPontoVenda().getId()));
        } else {
            parametros.add(new ParametroPesquisaIgual("idPv", null));
        }

        if(filtro.getNumeroNf() != null) {
            parametros.add(new ParametroPesquisaIgual("numeroNf", "%" + filtro.getNumeroNf() + "%"));
        } else {
            parametros.add(new ParametroPesquisaIgual("numeroNf", null));
        }
        if(filtro.getNumeroSerieNf() != null) {
            parametros.add(new ParametroPesquisaIgual("serieNf", "%" + filtro.getNumeroSerieNf() + "%"));
        } else {
            parametros.add(new ParametroPesquisaIgual("serieNf", null));
        }

        if(filtro.getDataInicioPeriodo() != null) {
            parametros.add(new ParametroPesquisaIgual("dataInicioPeriodo", UtilitarioFormatacaoData.formatarDataCurta(filtro.getDataInicioPeriodo())));
        } else {
            parametros.add(new ParametroPesquisaIgual("dataInicioPeriodo", null));
        }
        if(filtro.getDataFimPeriodo() != null) {
            parametros.add(new ParametroPesquisaIgual("dataFimPeriodo", UtilitarioFormatacaoData.formatarDataCurta(filtro.getDataFimPeriodo())));
        } else {
            parametros.add(new ParametroPesquisaIgual("dataFimPeriodo", null));
        }

        if(filtro.getIdFrota() != null) {
            parametros.add(new ParametroPesquisaIgual("idFrota", filtro.getIdFrota()));
        } else {
            parametros.add(new ParametroPesquisaIgual("idFrota", null));
        }


        return parametros;
    }

    /**
     * Monta parte do filtro relativa a outros serviços na consulta de detalhe de cobrança
     * @param filtro O filtro fornecido
     * @return A string de filtro montada
     */
    private String montarFiltroOutroServicosDetalheCobranca(FiltroPesquisaDetalheCobrancaVo filtro) {
        String filtroOutrosServicos = " ";
        StringBuffer strBufferFiltroOutrosServicos = new StringBuffer(filtroOutrosServicos);
        if(filtro.getOutrosServicos() != null && !filtro.getOutrosServicos().isEmpty()) {
            String listaProdutos = " (SELECT I.produto FROM ItemAutorizacaoPagamento I WHERE I.autorizacaoPagamento.id = A.id) ";
            for(EntidadeVo servico : filtro.getOutrosServicos()) {
                strBufferFiltroOutrosServicos.append(" AND " + servico.getId() + " IN " + listaProdutos );
            }
        }
        return strBufferFiltroOutrosServicos.toString();
    }

    @Override
    public Long obterQuantidadeNotasAgrupamento(FiltroPesquisaQtdTransacoesFrotaVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaIgual("idFrota", filtro.getIdFrota()));
        parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataInicioPeriodo", filtro.getDataInicioPeriodo()));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataFimPeriodo", filtro.getDataFimPeriodo()));
        parametros.add(new ParametroPesquisaIgual("statusConsolidacao", filtro.getStatusConsolidacao()));
        parametros.add(new ParametroPesquisaIgual("idCobranca", filtro.getIdCobranca()));
        parametros.add(new ParametroPesquisaIgual("dataLimiteEmissao", filtro.getDataLimiteEmissao()));
        parametros.add(new ParametroPesquisaIgual("dataVencimento", filtro.getDataVencimento()));

        return pesquisarUnicoSemIsolamentoDados(CONSULTA_QUANTIDADE_NOTAS, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

}