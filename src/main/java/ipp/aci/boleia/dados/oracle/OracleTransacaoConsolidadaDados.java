package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPontoDeVendaDados;
import ipp.aci.boleia.dados.ITransacaoConsolidadaDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.EmpresaAgregada;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.Unidade;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.ModalidadePagamento;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoReembolsoJde;
import ipp.aci.boleia.dominio.enums.StatusNotaFiscal;
import ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso;
import ipp.aci.boleia.dominio.enums.StatusTransacaoConsolidada;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaAnd;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.AgrupamentoTransacaoConsolidadaPvVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaDetalheCicloVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaFinanceiroVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaReembolsoGraficoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaReembolsoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaTransacaoConsolidadaVo;
import ipp.aci.boleia.dominio.vo.PontosGraficoFinanceiroVo;
import ipp.aci.boleia.dominio.vo.frotista.FiltroPesquisaNotaFiscalFrtVo;
import ipp.aci.boleia.dominio.vo.frotista.InformacaoPaginacaoFrtVo;
import ipp.aci.boleia.dominio.vo.frotista.ResultadoPaginadoFrtVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.negocio.ParametrosPesquisaBuilder;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static ipp.aci.boleia.util.UtilitarioCalculoData.adicionarMesesData;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterPrimeiroDiaMes;

/**
 * Respositorio de entidades AutorizacaoPagamento Consolidada
 */
@Repository
public class OracleTransacaoConsolidadaDados extends OracleRepositorioBoleiaDados<TransacaoConsolidada>
        implements ITransacaoConsolidadaDados {

    private static final String CLAUSULA_FROTA = "( F.id = :frotaId ) AND ";
    private static final String CLAUSULA_EXIGE_NOTA = "( ( F.semNotaFiscal is null or F.semNotaFiscal = 0 ) or TC.unidade is not null or TC.empresaAgregada is not null ) ";
    
    private static final String CLAUSULA_DATA_REEMB_GERADO = 
        " tc.reembolso IS NOT NULL AND " +
        " ((r.dataPagamento is null AND (r.dataVencimentoPgto >= :dataInicioPeriodo AND r.dataVencimentoPgto <= :dataFimPeriodo)) " +
        " OR (r.dataPagamento >= :dataInicioPeriodo AND r.dataPagamento <= :dataFimPeriodo)) ";

    private static final String CLAUSULA_DATA_REEMB_NAO_GERADO = 
        " tc.reembolso IS NULL AND " +
        " (fpv.frota.modoPagamento = " + ModalidadePagamento.POS_PAGO.getValue() +
        " AND (tc.dataFimPeriodo + p.prazoReembolso >= :dataInicioPeriodo AND tc.dataFimPeriodo + p.prazoReembolso <= :dataFimPeriodo)) " +
        " OR ((fpv.frota.modoPagamento = " + ModalidadePagamento.PRE_PAGO.getValue() +
        " AND (tc.dataFimPeriodo + 2 >= :dataInicioPeriodo AND tc.dataFimPeriodo + 2 <= :dataFimPeriodo))) ";

    private static final String CLAUSULA_CALCULO_PRAZO_REEMB_PRE_PAGO = 
        " fpv.frota.modoPagamento = " + ModalidadePagamento.PRE_PAGO.getValue() +
        " THEN TRUNC(tc.dataFimPeriodo + 2 ";

    private static final String CLAUSULA_CONSTRUTOR_AGRUPAMENTO_CONSOLIDADO_PV =
            "       new ipp.aci.boleia.dominio.vo.AgrupamentoTransacaoConsolidadaPvVo(" +
                    "TC.dataInicioPeriodo, " +
                    "TC.dataFimPeriodo, " +
                    "MIN(TCP.dataLimiteEmissaoNfe), " +
                    "TC.statusConsolidacao, " +
                    "SUM(TC.valorFaturamento), " +
                    "SUM(CASE WHEN RM.valorReembolso IS NULL THEN TC.valorReembolso ELSE RM.valorReembolso END), " +
                    "SUM(CASE WHEN RM.valorDesconto IS NULL THEN TC.valorDesconto ELSE RM.valorDesconto END), " +
                    "SUM(CASE WHEN TC.empresaAgregada IS NOT NULL OR TC.unidade IS NOT NULL OR F.semNotaFiscal IS NULL OR F.semNotaFiscal = false THEN TC.valorTotalNotaFiscal ELSE 0 END), " +
                    "SUM(CASE WHEN TC.empresaAgregada IS NOT NULL OR TC.unidade IS NOT NULL OR F.semNotaFiscal IS NULL OR F.semNotaFiscal = false THEN TC.valorEmitidoNotaFiscal ELSE 0 END), " +
                    "SUM(TC.quantidadeAbastecimentos)) ";

    private static final String CLAUSULA_NOTA_ATRASADA =
            " TRUNC(TC.prazos.dataLimiteEmissaoNfe) <  TRUNC(SYSDATE) " +
                    " AND TC.statusNotaFiscal <> " + StatusNotaFiscal.EMITIDA.getValue() +
                    " AND TC.statusNotaFiscal <> " + StatusNotaFiscal.SEM_EMISSAO.getValue() + " ";


    private static final String CLAUSULA_NOTA_PENDENTE =
            " TRUNC(TC.prazos.dataLimiteEmissaoNfe) >= TRUNC(SYSDATE) " +
                    " AND TC.statusNotaFiscal <> " + StatusNotaFiscal.EMITIDA.getValue() +
                    " AND TC.statusNotaFiscal <> " + StatusNotaFiscal.SEM_EMISSAO.getValue() + " ";

    private static final String CLAUSULA_NOTA_EMITIDA =
            " TC.statusNotaFiscal = " + StatusNotaFiscal.EMITIDA.getValue() + " ";

    private static final String CLAUSULA_NOTA_SEM_EMISSAO =
            " TC.statusNotaFiscal = " + StatusNotaFiscal.SEM_EMISSAO.getValue() + " ";

    private static final String CONSULTA_CONSOLIDADO_POR_FROTA_PV_DATA =
            " select t " +
                    " from TransacaoConsolidada t" +
                    "     join t.frotaPtov fpv  " +
                    "     join fpv.frota f  " +
                    "     join fpv.pontoVenda pv  " +
                    " where " +
                    "     f.id = :idFrota  " +
                    "     and pv.id = :idPontoVenda  " +
                    "     and t.dataFimPeriodo = " +
                    "     ( " +
                    "     	  select MAX(tc.dataFimPeriodo) " +
                    "         from TransacaoConsolidada tc " +
                    "    	      join tc.frotaPtov fptov  " +
                    "    	      join fptov.frota fr  " +
                    "    	      join fptov.pontoVenda ptov  " +
                    "         where " +
                    "    	     fr.id = :idFrota  " +
                    "    	     and ptov.id = :idPontoVenda  " +
                    "    	     and tc.dataFimPeriodo <= :data" +
                    "     )";

    private static final String CONSULTA_CONSOLIDADO_SEM_COBRANCA =
            " select t " +
                    " from TransacaoConsolidada t" +
                    "     join FETCH t.frotaPtov fpv  " +
                    "     join fpv.frota ft  " +
                    "     left join t.cobranca c  " +
                    " where " +
                    "     c.id is null  " +
                    "     and t.valorTotal is not null " +
                    "     and ft.numeroJdeInterno is not null" +
                    "     and t.modalidadePagamento = " + ModalidadePagamento.POS_PAGO.getValue() +
                    "     and t.dataFimPeriodo < :hoje " +
                    "     and t.statusConsolidacao = " + StatusTransacaoConsolidada.FECHADA.getValue() +
                    " order by t.dataFimPeriodo";

    private static final String CONSULTA_CONSOLIDADO_SEM_REEMBOLSO =
            " SELECT t " +
                    " FROM TransacaoConsolidada t " +
                    "     JOIN FETCH t.frotaPtov fpv " +
                    "     join fpv.pontoVenda pv  " +
                    "     join fpv.frota fr  " +
                    " WHERE " +
                    "     t.reembolso IS NULL " +
                    "     AND t.valorReembolso IS NOT NULL " +
                    "     AND t.valorDesconto IS NOT NULL " +
                    "     AND pv.numeroJdeInterno IS NOT NULL " +
                    "     AND fr.numeroJdeInterno IS NOT NULL " +
                    "     AND t.valorTotal IS NOT NULL " +
                    "     AND t.dataFimPeriodo < :hoje " +
                    "     AND t.statusConsolidacao = " + StatusTransacaoConsolidada.FECHADA.getValue();

    private static final String CONSULTA_CONSOLIDADO_ABERTO =
            " SELECT distinct(t) " +
                    " FROM TransacaoConsolidada t " +
                    "     JOIN FETCH t.autorizacaoPagamentos a " +
                    "     JOIN FETCH t.frotaPtov fpv " +
                    "     JOIN FETCH fpv.frota f " +
                    " WHERE " +
                    "     t.dataFimPeriodo < :hoje " +
                    "     AND t.statusConsolidacao = " + StatusTransacaoConsolidada.EM_ABERTO.getValue();

    private static final String CONSULTA_PESQUISA_GRID =
            "SELECT TC " +
                    " FROM TransacaoConsolidada TC " +
                    "   LEFT JOIN FETCH TC.prazos TCP " +
                    "   LEFT JOIN TC.frotaPtov FR " +
                    "   LEFT JOIN FR.frota F " +
                    "   LEFT JOIN FR.pontoVenda PV " +
                    " WHERE " +
                    "   ( " + CLAUSULA_NOTA_SEM_EMISSAO +
                    "       OR (TC.valorTotalNotaFiscal is not null AND TC.valorTotalNotaFiscal > 0))" +
                    "   AND (TC.dataInicioPeriodo <= :dataFimPeriodo OR :dataFimPeriodo is null) " +
                    "   AND (TC.dataFimPeriodo >= :dataInicioPeriodo OR :dataInicioPeriodo is null) " +
                    "   AND (F.id = :idFrota OR :idFrota is null) " +
                    "   AND (PV.id = :idPontoVenda OR :idPontoVenda is null) " +
                    "   AND (" +
                    "           (F.semNotaFiscal IS NULL OR F.semNotaFiscal = false) " +
                    "           OR (TC.empresaAgregada.id IS NOT NULL) " +
                    "           OR (TC.unidade.id IS NOT NULL)" +
                    "       ) " +
                    "   AND (TC.empresaAgregada.id = :idEmpresaAgregada OR :idEmpresaAgregada is null) " +
                    "   AND (TC.unidade.id = :idUnidade OR :idUnidade is null) " +
                    "   AND (:idInvalido IN (:idsPvs) OR PV.id IN (:idsPvs)) " +
                    "   %s " +
                    "   %s " +
                    "   %s " +
                    " ORDER BY " +
                    "   (CASE WHEN TC.statusNotaFiscal = 0 THEN (CASE WHEN TRUNC(TCP.dataLimiteEmissaoNfe) < SYSDATE THEN 0 ELSE 1 END) ELSE 2 END), TC.dataFimPeriodo";


    private static final String CONSULTA_PESQUISA_FROTISTA =
            "SELECT TC " +
                    " FROM TransacaoConsolidada TC " +
                    "   LEFT JOIN TC.frotaPtov FR " +
                    " WHERE " +
                    "   ( " + CLAUSULA_NOTA_SEM_EMISSAO +
                    "       OR (TC.valorTotalNotaFiscal is not null AND TC.valorTotalNotaFiscal > 0))" +
                    "   AND (TC.dataInicioPeriodo >= :dataInicioPeriodo OR :dataInicioPeriodo is null) " +
                    "   AND (TC.dataFimPeriodo <= :dataFimPeriodo OR :dataFimPeriodo is null) " +
                    "   AND (FR.frota.id = :idFrota OR :idFrota is null) " +
                    "   AND (FR.pontoVenda.id = :idPtov OR :idPtov is null) " +
                    "   %s " +
                    " ORDER BY " +
                    "   TC.dataInicioPeriodo, TC.id";

    private static final String FILTRO_NOTAFISCAL_PESQUISA_GRID =
            " AND EXISTS ( " +
                    " SELECT 1 " +
                    "    FROM FrotaPontoVenda FR2, " +
                    "    AutorizacaoPagamento AP, " +
                    "    NotaFiscal NF " +
                    "    join NF.autorizacoesPagamento autorizacaoPagamento " +
                    "WHERE " +
                    "    FR2.frota.id=AP.frota.id " +
                    "    AND FR2.pontoVenda.id=AP.pontoVenda.id " +
                    "    AND autorizacaoPagamento.id = AP.id " +
                    "    AND FR2.id=FR.id " +
                    "    AND AP.status = 1 " +
                    "    AND (AP.dataProcessamento BETWEEN TC.dataInicioPeriodo AND TC.dataFimPeriodo) " +
                    "    AND LOWER(NF.numero) LIKE '%%'||:notaFiscal||'%%' " +
                    "    AND (LOWER(NF.numeroSerie) LIKE '%%'||:numeroSerie||'%%' OR :numeroSerie is null))";


    private static final String CONSULTA_REEMBOLSO_PENDENTE_GRID =
            "SELECT TC " +
                    " FROM TransacaoConsolidada TC " +
                    "   LEFT JOIN FETCH TC.reembolso RE " +
                    "   LEFT JOIN TC.frotaPtov FR " +
                    "   LEFT JOIN FR.frota FT " +
                    "   LEFT JOIN FR.pontoVenda PV " +
                    " WHERE " +
                    "   TC.dataFimPeriodo < :hoje " +
                    "   AND TC.statusConsolidacao = " + StatusTransacaoConsolidada.FECHADA.getValue() +
                    "   AND TC.valorTotal is not null and TC.valorTotal <> 0 " +
                    "   AND RE.dataPagamento is null " +
                    "   AND (TC.dataInicioPeriodo >= :dataInicioPeriodo OR :dataInicioPeriodo is null) " +
                    "   AND (TC.dataFimPeriodo <= :dataFimPeriodo OR :dataFimPeriodo is null) " +
                    "   AND (TO_CHAR(RE.numeroDocumento) LIKE '%%'||:numeroDocumento||'%%' OR :numeroDocumento is null) " +
                    "   AND (FR.frota.id = :idFrota OR :idFrota is null) " +
                    "   AND (FR.pontoVenda.id = :idPontoVenda OR :idPontoVenda is null) " +
                    "   AND (TC.empresaAgregada.id = :idEmpresaAgregada OR :idEmpresaAgregada is null) " +
                    "   AND (TC.unidade.id = :idUnidade OR :idUnidade is null) " +
                    "   AND (:filtrarPvsusuario = 0 OR FR.pontoVenda.id IN (:idPontosVendaUsuario)) " +
                    "   %s " +
                    "   %s " +
                    "   %s " +
                    " ORDER BY " +
                    "   %s ";

    private static final String CONSULTA_TRANSACOES_PARA_CONSOLIDACAO =
            " SELECT tc " +
                    " FROM TransacaoConsolidada tc " +
                    "     JOIN FETCH tc.prazos tp " +
                    " WHERE " +
                    "     (tc.dataFimPeriodo < :hoje " +
                    "     AND tc.statusConsolidacao = " + StatusTransacaoConsolidada.EM_ABERTO.getValue() +
                    "     ) " +
                    "     OR (tp.dataLimiteEmissaoNfe < :hoje " +
                    "     AND tc.statusConsolidacao = " + StatusTransacaoConsolidada.EM_AJUSTE.getValue() +
                    "     ) " ;

    private static final String CONSULTA_TOTAL_REEMBOLSO_PERIODO =
            " SELECT SUM(CASE WHEN r.valorReembolso IS NULL THEN tc.valorReembolso "+
                    "ELSE r.valorReembolso END) " +
                    "FROM TransacaoConsolidada tc " +
                    "LEFT JOIN tc.frotaPtov fpv " +
                    "LEFT JOIN tc.reembolso r " +
                    "WHERE tc.dataInicioPeriodo >= :dataInicioPeriodo AND tc.dataFimPeriodo <= :dataFimPeriodo " +
                    "AND (fpv.pontoVenda.id IN :idsPvs) " +
                    "AND (fpv.frota.id = :idFrota OR :idFrota is null) " +
                    "AND (tc.statusConsolidacao = :statusConsolidacao or :statusConsolidacao is null) " +
                    "AND (tc.valorTotal <> 0 OR tc.valorTotalNotaFiscal <> 0) " +
                    "AND r.status = 1";

    private static final String CONSULTA_NUMERO_REEMBOLSOS_POR_STATUS =
            "SELECT count(*) " +
                    "FROM TransacaoConsolidada tc " +
                    "LEFT JOIN tc.frotaPtov fpv " +
                    "LEFT JOIN tc.reembolso r " +
                    "WHERE tc.dataInicioPeriodo >= :dataInicioPeriodo AND tc.dataFimPeriodo <= :dataFimPeriodo " +
                    "AND (fpv.pontoVenda.id IN :idsPvs) " +
                    "AND (fpv.frota.id = :idFrota OR :idFrota is null) " +
                    "AND (tc.statusConsolidacao = :statusConsolidacao or :statusConsolidacao is null) " +
                    "AND (tc.valorTotal <> 0 OR tc.valorTotalNotaFiscal <> 0) " +
                    "AND (trunc(r.dataVencimentoPgto) < trunc(SYSDATE) AND r.valorReembolso > 0 AND r.status <> " + StatusPagamentoReembolso.PAGO.getValue() + ")";


    private static final String CONSULTA_CONSOLIDADOS_GRID_FINANCEIRO =
            "SELECT tc " +
                    "FROM TransacaoConsolidada tc " +
                    "LEFT JOIN tc.frotaPtov fpv " +
                    "JOIN tc.reembolso r " +
                    "WHERE ((r.dataPagamento is null AND (r.dataVencimentoPgto >= :dataInicioPeriodo AND r.dataVencimentoPgto <= :dataFimPeriodo)) " +
                    "OR (r.dataPagamento >= :dataInicioPeriodo AND r.dataPagamento <= :dataFimPeriodo)) " +
                    "AND (fpv.pontoVenda.id IN :idsPvs) " +
                    "AND (fpv.frota.id = :idFrota OR :idFrota is null) " +
                    "AND (tc.statusConsolidacao = :statusConsolidacao or :statusConsolidacao is null) " +
                    "AND (tc.valorFaturamento <> 0 OR tc.valorReembolso <> 0 OR tc.valorTotalNotaFiscal <> 0 OR tc.quantidadeAbastecimentos <> 0) " +
                    "AND (r.status in (" + StatusPagamentoReembolso.PAGO.getValue() + ", " + StatusPagamentoReembolso.ATRASADO.getValue() + ", " + StatusPagamentoReembolso.NF_ATRASADA.getValue() + ", " + StatusPagamentoReembolso.A_DESCONTAR.getValue() + ")) " +
                    "ORDER BY %s ";

    private static final String CONSULTA_PONTOS_GRAFICO =
            "SELECT new ipp.aci.boleia.dominio.vo.PontosGraficoFinanceiroVo( " +
                "(CASE " +
                    "WHEN rm.status=1 THEN trunc(rm.dataPagamento) " +
                    "WHEN rm.dataVencimentoPgto IS NOT NULL THEN trunc(rm.dataVencimentoPgto) " +
                    "WHEN " + CLAUSULA_CALCULO_PRAZO_REEMB_PRE_PAGO +
                    "ELSE trunc(tc.dataFimPeriodo + p.prazoReembolso) " +
                "END) AS dataPagamento, " +
                "SUM(CASE WHEN rm.valorReembolso IS NULL THEN tc.valorReembolso "+
                    "ELSE rm.valorReembolso END), " +
                "CASE " +
                    "WHEN rm.status=0 THEN 'PREVISTO' " +
                    "WHEN rm.status=1 THEN 'PAGO' " +
                    "WHEN rm.status=2 THEN 'PREVISTO' " +
                    "WHEN rm.status=3 THEN 'PREVISTO' " +
                    "WHEN rm.status IS NULL THEN 'PREVISTO' " +
                "END) " +
                "FROM " +
                    "TransacaoConsolidada tc " +
                    "LEFT JOIN tc.prazos prz " +
                    "LEFT JOIN tc.reembolso rm	" +
                    "LEFT JOIN tc.frotaPtov f_ptov	" +
                    "LEFT JOIN tc.prazos p	" +
                "WHERE " +
                    "(rm.status is null or rm.status NOT IN (4,5)) " +
                    "( " + CLAUSULA_DATA_REEMB_GERADO + ") " +
                    "OR ( " + CLAUSULA_DATA_REEMB_NAO_GERADO + ") " +
                    "AND (f_ptov.pontoVenda.id IN :idsPvs) " +
                    "AND (f_ptov.frota.id = :idFrota OR :idFrota is null) " +
                    "AND (tc.valorReembolso <> 0 or rm.valorReembolso <> 0) " +
                    "AND tc.valorReembolso is not null " +
                    "AND (tc.statusConsolidacao = :statusConsolidacao or :statusConsolidacao is null) " +
                "GROUP BY " +
                    "CASE " +
                        "WHEN rm.status=1 THEN trunc(rm.dataPagamento) " +
                        "WHEN rm.dataVencimentoPgto IS NOT NULL THEN trunc(rm.dataVencimentoPgto) " +
                        "ELSE trunc(prz.dataLimiteEmissaoNfe+ 2) " +
                    "END, " +
                    "CASE " +
                        "WHEN rm.status=0 THEN 'PREVISTO' " +
                        "WHEN rm.status=1 THEN 'PAGO' " +
                        "WHEN rm.status=2 THEN 'PREVISTO' " +
                        "WHEN rm.status=3 THEN 'PREVISTO' " +
                        "WHEN rm.status IS NULL THEN 'PREVISTO' " +
                    "END " +
                "ORDER BY dataPagamento ASC ";

    /**
     * Busca uma lista de transações consolidadas de um ponto de venda pertencentes a um agrupamento.
     */
    private static final String CONSULTA_TRANSACOES_CONSOLIDADAS_DE_AGRUPAMENTO =
    "SELECT TC " +
            "FROM TransacaoConsolidada TC " +
            "JOIN TC.frotaPtov FP " +
            "JOIN FP.frota F " +
            "JOIN TC.prazos TCP " +
            "LEFT JOIN TC.reembolso RM	" +
            "LEFT JOIN TC.empresaAgregada EA " +
            "LEFT JOIN TC.unidade U	" +
            "WHERE FP.pontoVenda.id IN :idsPvs AND " +
            "      TRUNC(TC.dataInicioPeriodo) = TRUNC(:dataInicio) AND " +
            "      TRUNC(TC.dataFimPeriodo) = TRUNC(:dataFim) AND " +
            "      TC.statusConsolidacao = :statusCiclo AND " +
            CLAUSULA_FROTA +
            "(:statusNf IS NULL OR (TRUNC(TC.dataInicioPeriodo) = TRUNC(:dataInicioCicloAtual) AND (TC.statusNotaFiscal = :statusNf)) OR (TRUNC(TC.dataInicioPeriodo) < TRUNC(:dataInicioCicloAtual))) AND " +
            "      (TC.reembolso is NULL OR (RM.dataPagamento IS NULL AND TRUNC(RM.dataVencimentoPgto) >= TRUNC(SYSDATE) AND RM.valorReembolso >= 0)) ";

    /**
     * Busca uma lista de transações consolidadas de um ponto de venda pertencentes a um agrupamento com a seguinte ordenação:
     *
     * 1. Ordem crescente da porcentagem de nota fiscal
     * 2. Isentos de nota fiscal
     * 3. Demais casos sem emissão de nota fiscal
     */
    private static final String CONSULTA_TRANSACOES_CONSOLIDADAS_DE_AGRUPAMENTO_GRID =
    "SELECT TC " +
            "FROM TransacaoConsolidada TC " +
            "JOIN TC.frotaPtov FP " +
            "JOIN FP.frota F " +
            "JOIN TC.prazos TCP " +
            "LEFT JOIN TC.reembolso RM	" +
            "WHERE FP.pontoVenda.id IN :idsPvs AND " +
            "      TRUNC(TC.dataInicioPeriodo) = TRUNC(:dataInicio) AND " +
            "      TRUNC(TC.dataFimPeriodo) = TRUNC(:dataFim) AND " +
            "      TC.statusConsolidacao = :statusCiclo AND " +
            CLAUSULA_FROTA +
            "(:statusNf IS NULL OR (TRUNC(TC.dataInicioPeriodo) = TRUNC(:dataInicioCicloAtual) AND (TC.statusNotaFiscal = :statusNf)) OR (TRUNC(TC.dataInicioPeriodo) < TRUNC(:dataInicioCicloAtual))) AND " +
            "      (TC.reembolso is NULL OR (RM.dataPagamento IS NULL AND TRUNC(RM.dataVencimentoPgto) >= TRUNC(SYSDATE))) " +
            "ORDER BY " +
                "CASE WHEN (" + CLAUSULA_EXIGE_NOTA + " AND TC.valorTotalNotaFiscal > 0) THEN (TC.valorEmitidoNotaFiscal / TC.valorTotalNotaFiscal) " +
                    "WHEN (F.semNotaFiscal = 1 AND TC.unidade IS NULL AND TC.empresaAgregada IS NULL) THEN 2 " +
                    "ELSE 3 " +
                "END ";

    /**
     * Busca uma lista de transações consolidadas de um ponto de venda agrupadas por data e status.
     */
    private static final String CONSULTA_TRANSACOES_CONSOLIDADAS_AGRUPADAS_POR_PV =
            "SELECT " + CLAUSULA_CONSTRUTOR_AGRUPAMENTO_CONSOLIDADO_PV +
                    "FROM TransacaoConsolidada TC " +
                    "JOIN TC.frotaPtov FP " +
                    "JOIN FP.frota F " +
                    "JOIN TC.prazos TCP " +
                    "LEFT JOIN TC.reembolso RM	" +
                    "LEFT JOIN TC.empresaAgregada EA " +
                    "LEFT JOIN TC.unidade U	" +
                    "WHERE FP.pontoVenda.id IN :idsPvs AND " +
                    "      TRUNC(TC.dataInicioPeriodo) >= TRUNC(:dataInicio) AND " +
                    "      TRUNC(TC.dataFimPeriodo) <= TRUNC(:dataFim) AND " +
                    "      (TC.reembolso is NULL OR (RM.dataPagamento IS NULL AND TRUNC(RM.dataVencimentoPgto) >= TRUNC(SYSDATE) AND RM.valorReembolso >= 0)) " +
                    "GROUP BY TC.dataInicioPeriodo, TC.dataFimPeriodo, TC.statusConsolidacao " +
                    "ORDER BY CASE WHEN TC.statusConsolidacao = " + StatusTransacaoConsolidada.EM_AJUSTE.getValue() + " THEN 1 " +
                    "              WHEN TC.statusConsolidacao = " + StatusTransacaoConsolidada.EM_ABERTO.getValue() + " THEN 2 " +
                    "              ELSE 3 " +
                    "         END, " +
                    "         TC.dataInicioPeriodo, TC.dataFimPeriodo";

    /**
     * Busca uma lista de transações consolidadas de um ponto de venda agrupadas por data e status.
     */
    private static final String CONSULTA_DETALHAMENTO_CICLO_PV =
            "SELECT " + CLAUSULA_CONSTRUTOR_AGRUPAMENTO_CONSOLIDADO_PV +
                    "FROM TransacaoConsolidada TC " +
                    "JOIN TC.frotaPtov FP " +
                    "JOIN FP.frota F " +
                    "JOIN TC.prazos TCP " +
                    "LEFT JOIN TC.reembolso RM	" +
                    "LEFT JOIN TC.empresaAgregada EA " +
                    "LEFT JOIN TC.unidade U	" +
                    "WHERE FP.pontoVenda.id IN :idsPvs AND " +
                    "      TRUNC(TC.dataInicioPeriodo) >= TRUNC(:dataInicio) AND " +
                    "      TRUNC(TC.dataFimPeriodo) <= TRUNC(:dataFim) AND " +
                    "      (:frotaId IS NULL OR F.id = :frotaId) AND " +
                    "      (:statusNf IS NULL OR (TRUNC(TC.dataInicioPeriodo) = TRUNC(:dataInicioCicloAtual) AND (TC.statusNotaFiscal = :statusNf)) OR (TRUNC(TC.dataInicioPeriodo) < TRUNC(:dataInicioCicloAtual))) AND " +
                    "      (TC.reembolso is NULL OR (RM.dataPagamento IS NULL AND TRUNC(RM.dataVencimentoPgto) >= TRUNC(SYSDATE) AND RM.valorReembolso >= 0)) " +
                    "GROUP BY TC.dataInicioPeriodo, TC.dataFimPeriodo, TC.statusConsolidacao ";

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private IPontoDeVendaDados repositorioPv;

    @Value("${frota.controle.cnpj}")
    private Long cnpjFrotaControle;

    /**
     * Instancia o repositorio
     */
    public OracleTransacaoConsolidadaDados() {
        super(TransacaoConsolidada.class);
    }

    /**
     * Realiza a pesquisa de Transacoes Consolidadas através de um filtor
     *
     * @param filtro parâmetros utilizados na consulta
     * @param usuarioLogado usuário que está realizando a consulta
     * @return retorna o resultado paginado da consulta
     */
    @Override
    public ResultadoPaginado<TransacaoConsolidada> pesquisar(FiltroPesquisaTransacaoConsolidadaVo filtro, Usuario usuarioLogado) {
        List<ParametroPesquisa> parametros = criarParametrosPesquisaGrid(filtro, usuarioLogado);
        String filtroStatus = "";
        if (filtro.getStatusEmissaoNF() != null && !filtro.getStatusEmissaoNF().isEmpty()
                && (StatusNotaFiscal.values().length != filtro.getStatusEmissaoNF().size())) {
            boolean atrasada = filtro.getStatusEmissaoNF().stream().anyMatch(x -> StatusNotaFiscal.valueOf(x.getName()).equals(StatusNotaFiscal.ATRASADA));
            boolean pendente = filtro.getStatusEmissaoNF().stream().anyMatch(x -> StatusNotaFiscal.valueOf(x.getName()).equals(StatusNotaFiscal.PENDENTE));
            boolean emitida = filtro.getStatusEmissaoNF().stream().anyMatch(x -> StatusNotaFiscal.valueOf(x.getName()).equals(StatusNotaFiscal.EMITIDA));
            boolean semEmissao = filtro.getStatusEmissaoNF().stream().anyMatch(x -> StatusNotaFiscal.valueOf(x.getName()).equals(StatusNotaFiscal.SEM_EMISSAO));
            filtroStatus = montarFiltroStatus(atrasada, pendente, emitida, semEmissao);
        }
        String filtroNotaFiscal = "";
        if(filtro.getNotaFiscal() != null && filtro.getNotaFiscal().trim().length() > 0){
            filtroNotaFiscal = FILTRO_NOTAFISCAL_PESQUISA_GRID;
        }

        String filtroFrotaControle = "";

        if (usuarioLogado.getFrota() == null || !usuarioLogado.getFrota().getCnpj().equals(cnpjFrotaControle)){
            filtroFrotaControle = "AND FR.frota.cnpj != " + cnpjFrotaControle + " ";
        }

        String consultaPesquisa = String.format(CONSULTA_PESQUISA_GRID, filtroFrotaControle, filtroStatus, filtroNotaFiscal);
        return pesquisar(filtro.getPaginacao(), consultaPesquisa, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    /**
     * Cria uma lista de parametros para a montagem da consulta de transacoes a ser exibida no grid
     * @param filtro O filtro informado pelo usuario
     * @param usuarioLogado usuário que está realizando a consulta
     * @return Uma lista de parametros
     */
    private List<ParametroPesquisa> criarParametrosPesquisaGrid(FiltroPesquisaTransacaoConsolidadaVo filtro, Usuario usuarioLogado) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        ParametroPesquisaIgual parametroDe = new ParametroPesquisaIgual("dataInicioPeriodo", null);
        ParametroPesquisaIgual parametroAte = new ParametroPesquisaIgual("dataFimPeriodo", null);
        ParametroPesquisaIgual parametroPV = new ParametroPesquisaIgual("idPontoVenda", null);
        ParametroPesquisaIgual parametroEmpresaAgregada = new ParametroPesquisaIgual("idEmpresaAgregada", null);
        ParametroPesquisaIgual parametroUnidade = new ParametroPesquisaIgual("idUnidade", null);
        ParametroPesquisaIgual parametroRede = new ParametroPesquisaIgual("idsPvs", Collections.singletonList(-1L));
        ParametroPesquisaIgual parametroIdInvalido = new ParametroPesquisaIgual("idInvalido", -1L);
        ParametroPesquisaIgual parametroFrota = criarParametroFrota(filtro, usuarioLogado);

        if (filtro.getDe() != null) {
            parametroDe = new ParametroPesquisaIgual("dataInicioPeriodo", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDe()));
        }

        if (filtro.getAte() != null) {
            parametroAte = new ParametroPesquisaIgual("dataFimPeriodo", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getAte()));
        }

        if (filtro.getPontoDeVenda() != null && filtro.getPontoDeVenda().getId() != null) {
            parametroPV = new ParametroPesquisaIgual("idPontoVenda", filtro.getPontoDeVenda().getId());
        } else if (usuarioLogado.isRevendedor()) {
            parametroRede = new ParametroPesquisaIgual("idsPvs", usuarioLogado.getPontosDeVenda().stream().map(PontoDeVenda::getId).collect(Collectors.toList()));
        }

        if(filtro.getEmpresaAgregada() != null && filtro.getEmpresaAgregada().getId() != null){
            parametroEmpresaAgregada = new ParametroPesquisaIgual("idEmpresaAgregada", filtro.getEmpresaAgregada().getId());
        }

        if(filtro.getUnidade() != null && filtro.getUnidade().getId() != null) {
            parametroUnidade = new ParametroPesquisaIgual("idUnidade", filtro.getUnidade().getId());
        }

        parametros.add(parametroDe);
        parametros.add(parametroAte);
        parametros.add(parametroFrota);
        parametros.add(parametroPV);
        parametros.add(parametroEmpresaAgregada);
        parametros.add(parametroUnidade);
        parametros.add(parametroRede);
        parametros.add(parametroIdInvalido);

        if(filtro.getNotaFiscal() != null && filtro.getNotaFiscal().trim().length() > 0) {
            povoarParametroIgual("notaFiscal", filtro.getNotaFiscal().toLowerCase(), parametros);

            ParametroPesquisaIgual parametroNumeroSerie = new ParametroPesquisaIgual("numeroSerie", null);
            if(filtro.getNumeroSerie() != null && filtro.getNumeroSerie().trim().length() > 0) {
                parametroNumeroSerie = new ParametroPesquisaIgual("numeroSerie", filtro.getNumeroSerie().toLowerCase());
            }
            parametros.add(parametroNumeroSerie);
        }

        return parametros;
    }

    /**
     * Cria um parâmetro para comparação de frota a ser injetado na consulta
     * @param filtro O filtro de pesquisa
     * @param usuarioLogado O usuário que está realizando a consulta
     * @return O parâmetro para a consulta
     */
    private ParametroPesquisaIgual criarParametroFrota(FiltroPesquisaTransacaoConsolidadaVo filtro, Usuario usuarioLogado) {
        ParametroPesquisaIgual parametroFrota = new ParametroPesquisaIgual("idFrota", null);
        if (usuarioLogado.isFrotista()) {
            parametroFrota = new ParametroPesquisaIgual("idFrota", usuarioLogado.getFrota().getId());
        } else if (filtro.getFrota() != null && filtro.getFrota().getId() > 0) {
            parametroFrota = new ParametroPesquisaIgual("idFrota", filtro.getFrota().getId());
        }
        return parametroFrota;
    }

    /**
     * Povoa o filtro de status da consulta de transacoes para a tela de pesquisa
     *
     * @param atrasada Se necessario exibir as atrasadas
     * @param pendente Se necessario exibir as pendentes
     * @param emitida Se necessario exibir as emitidas
     * @param semEmissao Se necessario exibir as sem emissão
     * @return Ums string contendo as clausulas de filtro por status
     */
    private String montarFiltroStatus(boolean atrasada, boolean pendente, boolean emitida, boolean semEmissao) {
        StringBuilder filtro = new StringBuilder();
        filtro.append(" AND (");
        List<String> clausulas = new ArrayList<>();

        if(atrasada) {
            clausulas.add(CLAUSULA_NOTA_ATRASADA);
        }

        if (pendente) {
            clausulas.add(CLAUSULA_NOTA_PENDENTE);
        }

        if(emitida) {
            clausulas.add(CLAUSULA_NOTA_EMITIDA);
        }

        if (semEmissao) {
            clausulas.add(CLAUSULA_NOTA_SEM_EMISSAO);
        }

        if(clausulas.isEmpty()) {
            return "";
        }

        filtro.append(String.join("OR", clausulas));
        filtro.append(")");
        return filtro.toString();
    }

    @Override
    public List<TransacaoConsolidada> obterTransacoesParaConsolidacao() {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("statusConsolidacao", StatusTransacaoConsolidada.EM_ABERTO.getValue()));
        ParametroOrdenacaoColuna parametroOrdenacao = new ParametroOrdenacaoColuna("dataFimPeriodo");
        return pesquisar(parametroOrdenacao, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<TransacaoConsolidada> obterConsolidacoesSemNotaFiscalEntreDatas(
            Date dataIntervaloMin, Date dataIntervaloMax) {
        ParametroPesquisa[] parametros = new ParametroPesquisa[] {
                new ParametroPesquisaIgual("statusNotaFiscal",StatusNotaFiscal.PENDENTE.getValue()),
                new ParametroPesquisaDataMaiorOuIgual("prazos.dataLimiteEmissaoNfe", dataIntervaloMin),
                new ParametroPesquisaDataMenorOuIgual("prazos.dataLimiteEmissaoNfe", dataIntervaloMax)
        };
        return pesquisar((ParametroOrdenacaoColuna) null, parametros);
    }

    @Override
    public List<TransacaoConsolidada> obterConsolidacoesComCicloAbastecimentoEncerrado(
            Date dataIntervaloMin, Date dataIntervaloMax) {
        ParametroPesquisa[] parametros = new ParametroPesquisa[] {
                new ParametroPesquisaOr(
                        new ParametroPesquisaAnd(
                                new ParametroPesquisaIgual("prazos.possuiPrazoAjuste", true),
                                new ParametroPesquisaDataMaiorOuIgual("prazos.dataLimiteEmissaoNfe", dataIntervaloMin),
                                new ParametroPesquisaDataMenorOuIgual("prazos.dataLimiteEmissaoNfe", dataIntervaloMax)
                        ),
                        new ParametroPesquisaAnd(
                                new ParametroPesquisaIgual("prazos.possuiPrazoAjuste", false),
                                new ParametroPesquisaDataMaiorOuIgual("dataFimPeriodo", dataIntervaloMin),
                                new ParametroPesquisaDataMenorOuIgual("dataFimPeriodo", dataIntervaloMax)
                        )
                )
        };
        return pesquisar((ParametroOrdenacaoColuna) null, parametros);
    }

    @Override
    public List<TransacaoConsolidada> obterUltimasTransacoesPorFrotaPtovData(Long idFrota, Long idPontoVenda, Date data) {
        return pesquisarSemIsolamentoDados(null, CONSULTA_CONSOLIDADO_POR_FROTA_PV_DATA,
                new ParametroPesquisaIgual("idFrota", idFrota),
                new ParametroPesquisaIgual("idPontoVenda", idPontoVenda),
                new ParametroPesquisaIgual("data", data)
        ).getRegistros();
    }

    @Override
    public List<TransacaoConsolidada> obterTransacoesSemCobranca(){
        return pesquisarSemIsolamentoDados(null,CONSULTA_CONSOLIDADO_SEM_COBRANCA, new ParametroPesquisaIgual("hoje", obterDataHoje())).getRegistros();
    }

    @Override
    public List<TransacaoConsolidada> obterTransacoesSemReembolso() {
        return pesquisarSemIsolamentoDados(null, CONSULTA_CONSOLIDADO_SEM_REEMBOLSO, new ParametroPesquisaIgual("hoje", obterDataHoje())).getRegistros();
    }

    @Override
    public List<TransacaoConsolidada> obterTransacoesAbertasParaFechamento(){
        return pesquisarSemIsolamentoDados(null,CONSULTA_CONSOLIDADO_ABERTO, new ParametroPesquisaIgual("hoje", obterDataHoje())).getRegistros();
    }

    @Override
    public ResultadoPaginado<TransacaoConsolidada> pesquisarReembolsosPendentes(FiltroPesquisaReembolsoVo filtro) {
        List<ParametroPesquisa> parametros = criarParametrosPesquisaReembolso(filtro);
        parametros.add(new ParametroPesquisaIgual("hoje", obterDataHoje()));

        String pesquisaFrotaControle = "";
        if(ambiente.getUsuarioLogado().getFrota() == null || !ambiente.getUsuarioLogado().getFrota().getCnpj().equals(cnpjFrotaControle)){
            pesquisaFrotaControle = "AND TC.frotaPtov.frota.cnpj != " + cnpjFrotaControle + " ";
        }
        String pesquisaStatusReembolso = "";

        if ((filtro.getStatusPagamento() != null) && (!filtro.getStatusPagamento().isEmpty())) {

            String exigeNF = "((TC.frotaPtov.frota.semNotaFiscal IS NULL OR TC.frotaPtov.frota.semNotaFiscal = false) OR TC.unidade IS NOT NULL OR TC.empresaAgregada IS NOT NULL)";
            String naoExigeNF = "(TC.frotaPtov.frota.semNotaFiscal = true AND TC.unidade IS NULL OR TC.empresaAgregada IS NULL)";
            pesquisaStatusReembolso = "" +
                    "    AND CASE " +
                    "        WHEN TC.statusNotaFiscal = " + StatusNotaFiscal.EMITIDA.getValue() + " AND RE.status = " + StatusPagamentoReembolso.PAGO.getValue() + " THEN  " + StatusPagamentoReembolso.PAGO.getValue() +

                    "        WHEN " + exigeNF + " AND TC.statusNotaFiscal = 0 AND TC.valorTotal > 0 AND TC.prazos.dataLimiteEmissaoNfe >= TRUNC(SYSDATE) THEN " + StatusPagamentoReembolso.AGUARDANDO_NF.getValue() +
                    "        WHEN " + exigeNF + " AND TC.statusNotaFiscal = 0 AND TC.valorTotal < 0 THEN " + StatusPagamentoReembolso.A_DESCONTAR.getValue() +
                    "        WHEN " + exigeNF + " AND TC.statusNotaFiscal = 0 AND TC.valorTotal > 0 AND TC.prazos.dataLimiteEmissaoNfe < TRUNC(SYSDATE) THEN " + StatusPagamentoReembolso.NF_ATRASADA.getValue() +

                    "        WHEN " + naoExigeNF + " AND TC.statusConsolidacao = 1 AND TC.valorTotal > 0 AND (TC.prazos.dataLimiteEmissaoNfe + 2) < TRUNC(SYSDATE) THEN " + StatusPagamentoReembolso.ATRASADO.getValue() +
                    "        WHEN " + naoExigeNF + " AND TC.statusConsolidacao = 1 AND TC.valorTotal > 0 AND (TC.prazos.dataLimiteEmissaoNfe + 2) >= TRUNC(SYSDATE) THEN " + StatusPagamentoReembolso.EM_ABERTO.getValue() +

                    "        WHEN TC.statusNotaFiscal = 1 AND TC.valorTotal < 0 THEN " + StatusPagamentoReembolso.A_DESCONTAR.getValue() +
                    "        WHEN TC.statusNotaFiscal = 1 AND TC.valorTotal > 0 AND (TC.prazos.dataLimiteEmissaoNfe + 2) < TRUNC(SYSDATE) THEN " + StatusPagamentoReembolso.ATRASADO.getValue() +
                    "        WHEN TC.statusNotaFiscal = 1 AND TC.valorTotal > 0 AND (TC.prazos.dataLimiteEmissaoNfe + 2) >= TRUNC(SYSDATE) THEN " + StatusPagamentoReembolso.EM_ABERTO.getValue() +
                    "    END IN :filtroStatusReembolso ";

            parametros.add(new ParametroPesquisaIn("filtroStatusReembolso", filtro.getStatusPagamento().stream().map(f -> StatusPagamentoReembolso.valueOf(f.getName()).getValue()).collect(Collectors.toList())));
        }

        String filtroStatusIntegracao = filtro.getStatusIntegracao() != null ? montarFiltroStatusIntegracao(filtro) : "";
        String ordenacao = criarParametroOrdenacao(filtro.getPaginacao().getParametrosOrdenacaoColuna());

        String consultaPesquisa = String.format(CONSULTA_REEMBOLSO_PENDENTE_GRID, pesquisaFrotaControle, filtroStatusIntegracao, pesquisaStatusReembolso, ordenacao);

        return pesquisar(filtro.getPaginacao(), consultaPesquisa, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    /**
     * Cria o parâmetro de ordenação para  consulta de reembolsos pendentes
     *
     * @param parametrosOrdenacaoColuna Os critérios de ordenação do resultado definidos pelo usuário
     * @return O parametro de ordenacao criado.
     */
    private String criarParametroOrdenacao(List<ParametroOrdenacaoColuna> parametrosOrdenacaoColuna) {
        if (parametrosOrdenacaoColuna != null && !parametrosOrdenacaoColuna.isEmpty()) {
            return parametrosOrdenacaoColuna.get(0).getNome() + " " + (parametrosOrdenacaoColuna.get(0).isDecrescente() ? "DESC" : "ASC");
        }
        return "TC.dataInicioPeriodo ASC";
    }

    /**
     * Cria os parametros de pesquisa de reembolsos pendentes.
     *
     * @param filtro Filtro de pesquisa de reembolsos.
     * @return A lista com os parametros da consulta.
     */
    private List<ParametroPesquisa> criarParametrosPesquisaReembolso(FiltroPesquisaReembolsoVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        ParametroPesquisaDataMaiorOuIgual parametroDataInicioPeriodo = new ParametroPesquisaDataMaiorOuIgual("dataInicioPeriodo",null);
        ParametroPesquisaDataMenorOuIgual parametroDataFimPeriodo = new ParametroPesquisaDataMenorOuIgual("dataFimPeriodo",null);
        ParametroPesquisaIgual parametroNumeroDocumento = new ParametroPesquisaIgual("numeroDocumento",null);
        ParametroPesquisaIgual parametroIdFrota = new ParametroPesquisaIgual("idFrota",null);
        ParametroPesquisaIgual parametroIdPontoVenda = new ParametroPesquisaIgual("idPontoVenda",null);
        ParametroPesquisaIgual parametroIdEmpresaAgregada = new ParametroPesquisaIgual("idEmpresaAgregada",null);
        ParametroPesquisaIgual parametroIdUnidade = new ParametroPesquisaIgual("idUnidade",null);

        if (filtro.getDe() != null) {
            Date dataInicioPeriodo = obterPrimeiroDiaMes(UtilitarioFormatacaoData.lerDataMesAno(filtro.getDe()));
            parametroDataInicioPeriodo = new ParametroPesquisaDataMaiorOuIgual("dataInicioPeriodo", dataInicioPeriodo);
        }
        if (filtro.getAte() != null) {
            Date dataFimPeriodo = UtilitarioCalculoData.obterUltimoDiaMes(UtilitarioFormatacaoData.lerDataMesAno(filtro.getAte()));
            parametroDataFimPeriodo = new ParametroPesquisaDataMenorOuIgual("dataFimPeriodo", UtilitarioCalculoData.obterUltimoDiaMes(dataFimPeriodo));
        }
        if (StringUtils.isNotBlank(filtro.getNumeroDocumento())){
            parametroNumeroDocumento = new ParametroPesquisaIgual("numeroDocumento", filtro.getNumeroDocumento());
        }
        if(filtro.getFrota() != null && filtro.getFrota().getId() != null) {
            parametroIdFrota = new ParametroPesquisaIgual("idFrota", filtro.getFrota().getId());
        }
        if (filtro.getPontoDeVenda() != null && filtro.getPontoDeVenda().getId() != null){
            parametroIdPontoVenda = new ParametroPesquisaIgual("idPontoVenda", filtro.getPontoDeVenda().getId());
        }
        if (filtro.getEmpresaAgregada() != null && filtro.getEmpresaAgregada().getId() != null) {
            parametroIdEmpresaAgregada = new ParametroPesquisaIgual("idEmpresaAgregada", filtro.getEmpresaAgregada().getId());
        }
        if (filtro.getUnidade() != null && filtro.getUnidade().getId() != null) {
            parametroIdUnidade = new ParametroPesquisaIgual("idUnidade", filtro.getUnidade().getId());
        }

        parametros.add(parametroDataInicioPeriodo);
        parametros.add(parametroDataFimPeriodo);
        parametros.add(parametroNumeroDocumento);
        parametros.add(parametroIdFrota);
        parametros.add(parametroIdPontoVenda);
        parametros.add(parametroIdEmpresaAgregada);
        parametros.add(parametroIdUnidade);
        return adicionarParametrosIsolamentoDados(parametros);
    }

    /**
     * Adiciona parametros de isolamento de dados na lista de parametros informada
     *
     * @param parametros A lista de parametros original
     * @return A lista contendo os parametros originais mais os parametros de isolamento de dados
     */
    private List<ParametroPesquisa> adicionarParametrosIsolamentoDados(List<ParametroPesquisa> parametros) {

        Usuario usuarioLogado = ambiente.getUsuarioLogado();
        Integer filtrarPvsusuario;
        List<Long> idsPvsUsuario;

        if(usuarioLogado.isInterno()) {
            filtrarPvsusuario = 0;
            idsPvsUsuario = new ArrayList<>();
            idsPvsUsuario.add(-1L);
        } else {
            filtrarPvsusuario = 1;
            idsPvsUsuario = usuarioLogado.getPontosDeVenda().stream().map(PontoDeVenda::getId).collect(Collectors.toList());
        }

        parametros.add(new ParametroPesquisaIgual("idPontosVendaUsuario", idsPvsUsuario));
        parametros.add(new ParametroPesquisaIgual("filtrarPvsusuario", filtrarPvsusuario));

        return parametros;
    }

    /**
     * Monta um trecho de query com as clausulas de consulta a partir do filtro.
     *
     * @param filtro Filtro de pesquisa de reembolsos
     * @return O trecho da query de consulta.
     */
    private String montarFiltroStatusIntegracao(FiltroPesquisaReembolsoVo filtro) {
        StringBuilder query = new StringBuilder();
        boolean pendente = filtro.getStatusIntegracao().stream().anyMatch(x -> StatusIntegracaoReembolsoJde.valueOf(x.getName()).equals(StatusIntegracaoReembolsoJde.PENDENTE));
        boolean erroEnvio = filtro.getStatusIntegracao().stream().anyMatch(x -> StatusIntegracaoReembolsoJde.valueOf(x.getName()).equals(StatusIntegracaoReembolsoJde.ERRO_ENVIO));
        boolean realizado = filtro.getStatusIntegracao().stream().anyMatch(x -> StatusIntegracaoReembolsoJde.valueOf(x.getName()).equals(StatusIntegracaoReembolsoJde.REALIZADO));
        boolean aguardandoLiberacao = filtro.getStatusIntegracao().stream().anyMatch(x -> StatusIntegracaoReembolsoJde.valueOf(x.getName()).equals(StatusIntegracaoReembolsoJde.AGUARDANDO_LIBERACAO));
        boolean erroLiberacao = filtro.getStatusIntegracao().stream().anyMatch(x -> StatusIntegracaoReembolsoJde.valueOf(x.getName()).equals(StatusIntegracaoReembolsoJde.ERRO_LIBERACAO));

        if (pendente || erroEnvio || realizado || aguardandoLiberacao || erroLiberacao) {
            String listaStatus = filtro.getStatusIntegracao().stream().map(x -> StatusIntegracaoReembolsoJde.valueOf(x.getName()).getValue().toString()).collect(Collectors.joining(","));
            query.append(" AND (RE.statusIntegracao IN ( ").append(listaStatus).append(") ");

            if (pendente){
                query.append(" OR (RE.statusIntegracao is null)");
            }
            query.append(")");
        }
        return query.toString();
    }

    @Override
    public TransacaoConsolidada obterConsolidadoParaAbastecimento(Long idAbastecimento) {
        AutorizacaoPagamento abastecimento = getGerenciadorDeEntidade().find(AutorizacaoPagamento.class, idAbastecimento);
        Date dataReferencia = abastecimento.getDataProcessamento();
        if(abastecimento.getDataPostergacao() != null) {
            dataReferencia = abastecimento.getDataPostergacao();
        }
        return obterConsolidadoParaAbastecimentoEData(abastecimento, dataReferencia);
    }

    @Override
    public TransacaoConsolidada obterConsolidadoParaAbastecimentoEData(AutorizacaoPagamento abastecimento, Date dataReferencia) {
        Frota frota = abastecimento.getFrota();
        PontoDeVenda pv = abastecimento.getPontoVenda();
        ModalidadePagamento modalidadePagamento = abastecimento.getModalidadePagamento();
        EmpresaAgregada empresaAgregada = abastecimento.empresaAgregadaExigeNf() ? abastecimento.getEmpresaAgregada() : null;
        Unidade unidade = abastecimento.unidadeExigeNf() ? abastecimento.getUnidade() : null;

        return obterConsolidado(frota, pv, modalidadePagamento, empresaAgregada, unidade, dataReferencia);
    }

    @Override
    public TransacaoConsolidada obterConsolidado(Frota frota, PontoDeVenda pv, ModalidadePagamento modalidadePagamento, EmpresaAgregada empresaAgregada, Unidade unidade, Date dataReferencia) {
        ParametroPesquisa parametroEmpresaAgregadaExigeNf = new ParametroPesquisaNulo("empresaAgregada.id");
        ParametroPesquisa parametroUnidadeExigeNf = new ParametroPesquisaNulo("unidade.id");
        if(empresaAgregada != null) {
            parametroEmpresaAgregadaExigeNf = new ParametroPesquisaIgual("empresaAgregada.id", empresaAgregada.getId());
        } else if(unidade != null) {
            parametroUnidadeExigeNf = new ParametroPesquisaIgual("unidade.id", unidade.getId());
        }

        return pesquisarUnico (
                new ParametroPesquisaDataMenorOuIgual("dataInicioPeriodo", dataReferencia),
                new ParametroPesquisaDataMaiorOuIgual("dataFimPeriodo", UtilitarioCalculoData.obterPrimeiroInstanteDia(dataReferencia)),
                new ParametroPesquisaIgual("frotaPtov.pontoVenda.id", pv.getId()),
                new ParametroPesquisaIgual("frotaPtov.frota.id", frota.getId()),
                new ParametroPesquisaIgual("modalidadePagamento", modalidadePagamento.getValue()),
                parametroEmpresaAgregadaExigeNf,
                parametroUnidadeExigeNf
        );
    }

    @Override
    public ResultadoPaginadoFrtVo<TransacaoConsolidada> pesquisar(FiltroPesquisaNotaFiscalFrtVo filtro) {
        Long idFrota = ambiente.getUsuarioLogado().getFrota().getId();
        Long idptov = null;
        if(filtro.getCnpjRevenda()!= null){
            PontoDeVenda ptov = repositorioPv.obterPorCnpjAreaAbastecimento(filtro.getCnpjRevenda());
            idptov = ptov!=null ? ptov.getId() :null;
        }
        ParametroPesquisa[] params = new ParametrosPesquisaBuilder()
                .adicionarParametros(
                        new ParametroPesquisaIgual("dataInicioPeriodo", obterPrimeiroDiaMes(filtro.getMesAnoPeriodo())),
                        new ParametroPesquisaIgual("dataFimPeriodo", UtilitarioCalculoData.obterUltimoDiaMes(filtro.getMesAnoPeriodo())),
                        new ParametroPesquisaIgual("idFrota", idFrota),
                        new ParametroPesquisaIgual("idPtov",idptov)
                )
                .buildArray();

        String filtroStatus = "";
        if(filtro.getStatusEmissaoNotaFiscal() != null) {
            boolean atrasada = StatusNotaFiscal.ATRASADA.getValue().equals(filtro.getStatusEmissaoNotaFiscal());
            boolean pendente = StatusNotaFiscal.PENDENTE.getValue().equals(filtro.getStatusEmissaoNotaFiscal());
            boolean emitida = StatusNotaFiscal.EMITIDA.getValue().equals(filtro.getStatusEmissaoNotaFiscal());
            boolean semEmissao = StatusNotaFiscal.SEM_EMISSAO.getValue().equals(filtro.getStatusEmissaoNotaFiscal());
            filtroStatus = montarFiltroStatus(atrasada, pendente, emitida, semEmissao);
        }

        InformacaoPaginacaoFrtVo paginacao =
                new InformacaoPaginacaoFrtVo(filtro.getPagina(), new ParametroOrdenacaoColuna("id"));

        paginacao.setTamanhoPagina(5);
        String consulta = String.format(CONSULTA_PESQUISA_FROTISTA, filtroStatus);
        ResultadoPaginado<TransacaoConsolidada> resultado = pesquisar(paginacao, consulta, params);
        return new ResultadoPaginadoFrtVo<>(resultado, paginacao.getPagina(), paginacao.getTamanhoPagina());
    }

    /**
     * Obtem a primeira hora do dia de hoje
     *
     * @return O dia de hoje, as 0h da manha
     */
    private Date obterDataHoje() {
        return UtilitarioCalculoData.obterPrimeiroInstanteDia(ambiente.buscarDataAmbiente());
    }

    @Override
    public TransacaoConsolidada obterUltimoConsolidadoAnteriorAoAbastecimentoSemCiclo(Long idFrota, Long idPtov, Long idEmpresaAgregada, Long idUnidade, Date dataInicio, Date dataAbastecimentoSemCiclo) {
        ParametroPesquisa[] parametros = new ParametroPesquisa[] {
                new ParametroPesquisaDataMenor("dataFimPeriodo", dataAbastecimentoSemCiclo),
                new ParametroPesquisaDataMaiorOuIgual("dataFimPeriodo", dataInicio),
                new ParametroPesquisaIgual("frotaPtov.frota.id",idFrota),
                new ParametroPesquisaIgual("frotaPtov.pontoVenda.id",idPtov),
                idEmpresaAgregada != null ? new ParametroPesquisaIgual("empresaAgregada.id",idEmpresaAgregada) :  new ParametroPesquisaNulo("empresaAgregada.id"),
                idUnidade != null ? new ParametroPesquisaIgual("unidade.id",idUnidade) :  new ParametroPesquisaNulo("unidade.id")
        };
        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.setPagina(1);
        paginacao.setTamanhoPagina(1);
        paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("dataFimPeriodo", Ordenacao.DECRESCENTE));
        return pesquisar(paginacao,parametros).getRegistros().stream().findFirst().orElse(null);
    }

    @Override
    public List<TransacaoConsolidada> buscarCiclosEmAberto(Long idFrota){
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("frotaPtov.frota", idFrota));
        parametros.add(new ParametroPesquisaIgual("statusConsolidacao", StatusTransacaoConsolidada.EM_ABERTO.getValue()));

        return pesquisar(
                new ParametroOrdenacaoColuna("id", Ordenacao.DECRESCENTE), parametros.toArray(new ParametroPesquisa[parametros.size()])
        );
    }

    @Override
    public List<TransacaoConsolidada> obterCiclosParaAtualizarStatus() {
        return pesquisarSemIsolamentoDados(null,CONSULTA_TRANSACOES_PARA_CONSOLIDACAO, new ParametroPesquisaIgual("hoje", obterDataHoje())).getRegistros();
    }

    @Override
    public List<TransacaoConsolidada> pesquisarTransacoesPorPvEData(Long pv, Date de, Date ate) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("frotaPtov.pontoVenda.id", pv));
        parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataInicioPeriodo", UtilitarioCalculoData.obterPrimeiroInstanteDia(de)));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataFimPeriodo", UtilitarioCalculoData.obterUltimoInstanteDia(ate)));
        
        return pesquisar((ParametroOrdenacaoColuna) null, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<TransacaoConsolidada> pesquisarTransacoesDeAgrupamento(FiltroPesquisaDetalheCicloVo filtro) {
        String consulta = CONSULTA_TRANSACOES_CONSOLIDADAS_DE_AGRUPAMENTO;

        List<ParametroPesquisa> parametros = new ArrayList<>();

        popularParametrosDataEPvBoxFinanceiro(parametros, filtro.getIdPv(), filtro.getInicio(), filtro.getFim());
        parametros.add(new ParametroPesquisaIgual("dataInicioCicloAtual", filtro.getInicio()));

        if(filtro.getStatusNf() != null && filtro.getStatusNf().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("statusNf", StatusNotaFiscal.valueOf(filtro.getStatusNf().getName()).getValue()));
        } else {
            parametros.add(new ParametroPesquisaIgual("statusNf", null));
        }

        if(filtro.getFrota()!= null && filtro.getFrota().getId() != null){
            parametros.add(new ParametroPesquisaIgual("frotaId", filtro.getFrota().getId()));
        }else {
            consulta = consulta.replace(CLAUSULA_FROTA, "");
        }

        parametros.add(new ParametroPesquisaIgual("statusCiclo", filtro.getStatusCiclo().getValue()));

        return pesquisar(null, consulta, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }

    @Override
    public ResultadoPaginado<TransacaoConsolidada> pesquisarTransacoesDetalhamentoDeCiclo(FiltroPesquisaDetalheCicloVo filtro) {
        String consulta = CONSULTA_TRANSACOES_CONSOLIDADAS_DE_AGRUPAMENTO_GRID;

        List<ParametroPesquisa> parametros = new ArrayList<>();

        popularParametrosDataEPvBoxFinanceiro(parametros, filtro.getIdPv(), filtro.getInicio(), filtro.getFim());
        parametros.add(new ParametroPesquisaIgual("dataInicioCicloAtual", filtro.getInicio()));

        if(filtro.getStatusNf() != null && filtro.getStatusNf().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("statusNf", StatusNotaFiscal.valueOf(filtro.getStatusNf().getName()).getValue()));
        } else {
            parametros.add(new ParametroPesquisaIgual("statusNf", null));
        }

        if(filtro.getFrota()!= null && filtro.getFrota().getId() != null){
            parametros.add(new ParametroPesquisaIgual("frotaId", filtro.getFrota().getId()));
        }else {
            consulta = consulta.replace(CLAUSULA_FROTA, "");
        }

        parametros.add(new ParametroPesquisaIgual("statusCiclo", filtro.getStatusCiclo().getValue()));

        return pesquisar(filtro.getPaginacao(), consulta, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public ResultadoPaginado<TransacaoConsolidada> pesquisarTransacoesFinanceiro(FiltroPesquisaFinanceiroVo filtro, Usuario usuarioLogado) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataInicioPeriodo", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDe())));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataFimPeriodo", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getAte())));

        if(filtro.getPontoDeVenda() != null) {
            parametros.add(new ParametroPesquisaIn("idsPvs", Collections.singletonList(filtro.getPontoDeVenda().getId())));
        } else {
            parametros.add(new ParametroPesquisaIn("idsPvs", usuarioLogado.getPontosDeVenda().stream().map(PontoDeVenda::getId).collect(Collectors.toList())));
        }

        if(filtro.getFrota() != null){
            parametros.add(new ParametroPesquisaIgual("idFrota", filtro.getFrota().getId()));
        }  else {
            parametros.add(new ParametroPesquisaIgual("idFrota", null));
        }

        if(filtro.getStatusCiclo() != null && filtro.getStatusCiclo().getName() != null){
            parametros.add(new ParametroPesquisaIgual("statusConsolidacao", StatusTransacaoConsolidada.valueOf(filtro.getStatusCiclo().getName()).getValue()));
        }else{
            parametros.add(new ParametroPesquisaIgual("statusConsolidacao", null));
        }

        String ordenacao = " ";
        if(filtro.getPaginacao().getParametrosOrdenacaoColuna().isEmpty()) {
            ordenacao = "CASE WHEN r.status = " + StatusPagamentoReembolso.NF_ATRASADA.getValue() + " THEN 0 " +
                    "WHEN r.valorReembolso > 0 AND trunc(r.dataVencimentoPgto) < trunc(sysdate()) AND r.status <> " + StatusPagamentoReembolso.PAGO.getValue() + " THEN 1 " + //ATRASADO
                    "ELSE 2 END, r.dataPagamento, r.dataVencimentoPgto ";
        } else {
            String campoOrdenacao= "r.dataPagamento %s, r.dataVencimentoPgto ";
            String direcaoOrdenacao = filtro.getPaginacao().getParametrosOrdenacaoColuna().get(0).isDecrescente() ? " DESC" : " ";
            ordenacao = String.format(campoOrdenacao, direcaoOrdenacao);
        }

        //Monta a consulta completa da grid do financeiro
        String consultaPesquisaGridFinanceiro = String.format(CONSULTA_CONSOLIDADOS_GRID_FINANCEIRO, ordenacao);

        return pesquisar(filtro.getPaginacao(), consultaPesquisaGridFinanceiro, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public BigDecimal obterTotalReembolsoPeriodo(FiltroPesquisaFinanceiroVo filtro, Usuario usuarioLogado) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataInicioPeriodo", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDe())));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataFimPeriodo", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getAte())));

        if(filtro.getPontoDeVenda() != null) {
            parametros.add(new ParametroPesquisaIn("idsPvs", Collections.singletonList(filtro.getPontoDeVenda().getId())));
        } else {
            parametros.add(new ParametroPesquisaIn("idsPvs", usuarioLogado.getPontosDeVenda().stream().map(PontoDeVenda::getId).collect(Collectors.toList())));
        }
        if(filtro.getFrota() != null){
            parametros.add(new ParametroPesquisaIgual("idFrota", filtro.getFrota().getId()));
        }

        if(filtro.getStatusCiclo() != null && filtro.getStatusCiclo().getName() != null){
            parametros.add(new ParametroPesquisaIgual("statusConsolidacao", StatusTransacaoConsolidada.valueOf(filtro.getStatusCiclo().getName()).getValue()));
        }else{
            parametros.add(new ParametroPesquisaIgual("statusConsolidacao", null));
        }

        BigDecimal totalReembolso = pesquisarUnicoSemIsolamentoDados(CONSULTA_TOTAL_REEMBOLSO_PERIODO, parametros.toArray(new ParametroPesquisa[parametros.size()]));
        return totalReembolso != null ? totalReembolso : BigDecimal.ZERO;
    }

    /**
     * Retorna uma lista com os parametros comuns para busca de consolidados associados a reembolsos pagos, previstos existentes e previstos nao gerados
     * @param filtro filtro contendo as informações que devem ser consideradas na consulta.
     * @param usuarioLogado Usuario logado no sistema que fez a requisição.
     * @return lista com os parametros comuns para busca de consolidados associados a reembolsos pagos, previstos existentes e previstos nao gerados
     */
    private  List<ParametroPesquisa> obterParametrosDePesquisaBuscaDeReembolsosParaGrafico(FiltroPesquisaReembolsoGraficoVo filtro, Usuario usuarioLogado){

        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataInicioPeriodo", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDataInicioPesquisa())));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataFimPeriodo", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getDataFimPesquisa())));

        if(filtro.getIdPontoDeVendaSelecionado()  != null) {
            parametros.add(new ParametroPesquisaIn("idsPvs", Collections.singletonList(filtro.getIdPontoDeVendaSelecionado())));
        } else if(usuarioLogado.isRevendedor()) {
            parametros.add(new ParametroPesquisaIn("idsPvs", usuarioLogado.getPontosDeVenda().stream().map(PontoDeVenda::getId).collect(Collectors.toList())));
        }

        parametros.add(new ParametroPesquisaIgual("idFrota", filtro.getIdFrotaSelecionada()));

        if(filtro.getStatusConsolidacaoSelecionado() != null && filtro.getStatusConsolidacaoSelecionado().getName() != null){
            parametros.add(new ParametroPesquisaIgual("statusConsolidacao", StatusTransacaoConsolidada.valueOf(filtro.getStatusConsolidacaoSelecionado().getName()).getValue()));
        }else{
            parametros.add(new ParametroPesquisaIgual("statusConsolidacao", null));
        }

        return parametros;
    }

    @Override
    public List<PontosGraficoFinanceiroVo> obterPontosGraficoReembolsos( FiltroPesquisaReembolsoGraficoVo filtro, Usuario usuarioLogado){
        filtro.setPaginacao(null);
        List<ParametroPesquisa> parametros = obterParametrosDePesquisaBuscaDeReembolsosParaGrafico(filtro, usuarioLogado);

        return pesquisar(filtro.getPaginacao(), CONSULTA_PONTOS_GRAFICO, PontosGraficoFinanceiroVo.class, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }

    @Override
    public Integer obterNumeroReembolsosAtrasados(FiltroPesquisaFinanceiroVo filtro, Usuario usuarioLogado){
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataInicioPeriodo", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDe())));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataFimPeriodo", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getAte())));

        if(filtro.getPontoDeVenda() != null && filtro.getPontoDeVenda().getId() != null) {
            parametros.add(new ParametroPesquisaIn("idsPvs", Collections.singletonList(filtro.getPontoDeVenda().getId())));
        } else if(usuarioLogado.isRevendedor()) {
            parametros.add(new ParametroPesquisaIn("idsPvs", usuarioLogado.getPontosDeVenda().stream().map(PontoDeVenda::getId).collect(Collectors.toList())));
        }
        if(filtro.getFrota() != null){
            parametros.add(new ParametroPesquisaIgual("idFrota", filtro.getFrota().getId()));
        }

        if(filtro.getStatusCiclo() != null && filtro.getStatusCiclo().getName() != null){
            parametros.add(new ParametroPesquisaIgual("statusConsolidacao", StatusTransacaoConsolidada.valueOf(filtro.getStatusCiclo().getName()).getValue()));
        }else{
            parametros.add(new ParametroPesquisaIgual("statusConsolidacao", null));
        }

        Long numeroCiclosAtrasados = pesquisarUnicoSemIsolamentoDados(CONSULTA_NUMERO_REEMBOLSOS_POR_STATUS, parametros.toArray(new ParametroPesquisa[parametros.size()]));
        return numeroCiclosAtrasados.intValue();
    }

    @Override
    public List<AgrupamentoTransacaoConsolidadaPvVo> pesquisarDetalheCicloParaPv(FiltroPesquisaDetalheCicloVo filtro){
        Date agora = ambiente.buscarDataAmbiente();
        Date dataInicioPeriodo = UtilitarioCalculoData.obterPrimeiroDiaMes(adicionarMesesData(agora, -1));
        Date dataFimPeriodo = UtilitarioCalculoData.obterUltimoDiaMes(agora);

        List<ParametroPesquisa> parametrosPesquisa = new ArrayList<>();
        popularParametrosDataEPvBoxFinanceiro(parametrosPesquisa, filtro.getIdPv(), dataInicioPeriodo, dataFimPeriodo);

        parametrosPesquisa.add(new ParametroPesquisaIgual("frotaId", filtro.getFrota() != null ? filtro.getFrota().getId() : null));
        parametrosPesquisa.add(new ParametroPesquisaIgual("dataInicioCicloAtual", filtro.getInicio()));
        if(filtro.getStatusNf() != null && filtro.getStatusNf().getName() != null) {
            parametrosPesquisa.add(new ParametroPesquisaIgual("statusNf", StatusNotaFiscal.valueOf(filtro.getStatusNf().getName()).getValue()));
        } else {
            parametrosPesquisa.add(new ParametroPesquisaIgual("statusNf", null));
        }

        return pesquisar(null, CONSULTA_DETALHAMENTO_CICLO_PV, AgrupamentoTransacaoConsolidadaPvVo.class, parametrosPesquisa.toArray(new ParametroPesquisa[parametrosPesquisa.size()])).getRegistros();
    }

    @Override
    public List<AgrupamentoTransacaoConsolidadaPvVo> pesquisarTransacoesConsolidadasAgrupadasParaPv(Long idPv, Date dataInicioPeriodo, Date dataFimPeriodo) {

        List<ParametroPesquisa> parametrosPesquisa = new ArrayList<>();
        popularParametrosDataEPvBoxFinanceiro(parametrosPesquisa, idPv, dataInicioPeriodo, dataFimPeriodo);

        return pesquisar(null, CONSULTA_TRANSACOES_CONSOLIDADAS_AGRUPADAS_POR_PV, AgrupamentoTransacaoConsolidadaPvVo.class, parametrosPesquisa.toArray(new ParametroPesquisa[parametrosPesquisa.size()])).getRegistros();
    }

    @Override
    public TransacaoConsolidada desanexar(TransacaoConsolidada transacaoConsolidada) {
        return super.desanexar(transacaoConsolidada);
    }

    @Override
    public List<TransacaoConsolidada> obterCiclosFechadosQueNaoPassaramPorPostergacao(){
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("processouPostergacao", 0));
        parametros.add(new ParametroPesquisaIgual("statusConsolidacao", StatusTransacaoConsolidada.FECHADA.getValue()));

        return pesquisar((InformacaoPaginacao) null, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }

    /**
     *  Popula os parâmetros de pesquisa em comum entre as duas consultas de box do financeiro
     * @param parametrosPesquisa A lista de parâmetros
     * @param idPv Id do ponto de venda selecionado
     * @param dataInicioPeriodo Data de inicio do filtro de pesquisa
     * @param dataFimPeriodo Data de fim do filtro de pesquisa
     */
    private void popularParametrosDataEPvBoxFinanceiro(List<ParametroPesquisa> parametrosPesquisa, Long idPv, Date dataInicioPeriodo, Date dataFimPeriodo){
        Usuario usuarioLogado = ambiente.getUsuarioLogado();

        parametrosPesquisa.add(new ParametroPesquisaIgual("dataInicio", dataInicioPeriodo));
        parametrosPesquisa.add(new ParametroPesquisaIgual("dataFim", dataFimPeriodo));
        if(idPv != null) {
            parametrosPesquisa.add(new ParametroPesquisaIn("idsPvs", Collections.singletonList(idPv)));
        } else if(usuarioLogado.isRevendedor()) {
            parametrosPesquisa.add(new ParametroPesquisaIn("idsPvs", usuarioLogado.getPontosDeVenda().stream().map(PontoDeVenda::getId).collect(Collectors.toList())));
        }

    }
}
