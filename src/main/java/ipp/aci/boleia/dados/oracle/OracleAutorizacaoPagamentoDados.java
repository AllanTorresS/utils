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
import ipp.aci.boleia.dominio.enums.TipoAtividadeComponente;
import ipp.aci.boleia.dominio.enums.TipoAutorizacaoPagamento;
import ipp.aci.boleia.dominio.enums.TiposBandeiras;
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
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaAbastecimentoVo;
import ipp.aci.boleia.dominio.vo.QuantidadeAbastecidaVeiculoVo;
import ipp.aci.boleia.dominio.vo.TransacaoPendenteVo;
import ipp.aci.boleia.dominio.vo.apco.VolumeVendasClienteProFrotaVo;
import ipp.aci.boleia.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo;
import ipp.aci.boleia.dominio.vo.frotista.InformacaoPaginacaoFrtVo;
import ipp.aci.boleia.dominio.vo.frotista.ResultadoPaginadoFrtVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacao;
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
                    " AND tfl.statusConfirmacao = " + StatusConfirmacaoTransacao.NAO_CONFIRMADO.getValue() +
                    " AND tfl.dataRequisicao BETWEEN :limiteInferiorData AND :limiteSuperiorData";


    private static final String CONSULTA_VENDA_PROFROTAS = "SELECT new ipp.aci.boleia.dominio.vo.apco.VolumeVendasClienteProFrotaVo(" +
            "c.codigoCorporativo, " +
            "a.frota.id, " +
            "i.combustivel.codigoCombustivelCorporativo, " +
            "TRUNC(a.dataRequisicao), SUM(a.totalLitrosAbastecimento)) " +
            "FROM AutorizacaoPagamento AS a " +
            "INNER JOIN  a.pontoVenda AS p " +
            "INNER JOIN p.componentes AS c " +
            "INNER JOIN a.items AS i " +
            "INNER JOIN c.atividadeComponente ac " +
            "WHERE i.combustivel IS NOT NULL AND i.combustivel.codigoCombustivelCorporativo IS NOT NULL AND " +
            "(ac.codigoCorporativo = " + TipoAtividadeComponente.AREA_ABASTECIMENTO.getCodigoCorporativo() + " OR ac.codigoCorporativo = "
            + TipoAtividadeComponente.AREA_ABASTECIMENTO_OUTRA.getCodigoCorporativo() + " ) AND " +
            "a.status = "+ StatusAutorizacao.AUTORIZADO.getValue() + " AND c.bandeira.codigoCorporativo =  " + TiposBandeiras.IPIRANGA.getCodigoCorporativo() +
            " AND a.dataRequisicao BETWEEN :dataInicial AND :dataFinal" + " AND a.frota.status != " + StatusFrota.PRE_CADASTRO.getValue() +
            " GROUP BY c.codigoCorporativo, a.frota.id, i.combustivel.codigoCombustivelCorporativo, TRUNC(a.dataRequisicao)";

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
        if(filtro.getDe() != null) {
            ParametroPesquisaDataMaiorOuIgual dataProcessamentoDe = new ParametroPesquisaDataMaiorOuIgual("dataProcessamento", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDe()));
            ParametroPesquisaDataMaiorOuIgual dataPostergacaoDe = new ParametroPesquisaDataMaiorOuIgual("dataPostergacao", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDe()));
            parametros.add(new ParametroPesquisaOr(dataProcessamentoDe, dataPostergacaoDe));
        }
        if(filtro.getAte() != null) {
            ParametroPesquisaDataMenorOuIgual dataProcessamentoAte = new ParametroPesquisaDataMenorOuIgual("dataProcessamento", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getAte()));
            ParametroPesquisaDataMenorOuIgual dataPostergacaoAte = new ParametroPesquisaDataMenorOuIgual("dataPostergacao", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getAte()));
            parametros.add(new ParametroPesquisaOr(dataProcessamentoAte, dataPostergacaoAte));
        }
        povoarParametroDataMaiorIgual("dataRequisicao", filtro.getRequisicaoDe(), parametros);
        povoarParametroDataMenorIgual("dataRequisicao", filtro.getRequisicaoAte(), parametros);
        if(filtro.getProcessamentoDe() != null) {
            ParametroPesquisaDataMaiorOuIgual dataProcessamentoDe = new ParametroPesquisaDataMaiorOuIgual("dataProcessamento", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getProcessamentoDe()));
            ParametroPesquisaDataMaiorOuIgual dataPostergacaoDe = new ParametroPesquisaDataMaiorOuIgual("dataPostergacao", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getProcessamentoDe()));
            parametros.add(new ParametroPesquisaOr(dataProcessamentoDe, dataPostergacaoDe));
        }
        if(filtro.getProcessamentoAte() != null) {
            ParametroPesquisaDataMenorOuIgual dataProcessamentoAte = new ParametroPesquisaDataMenorOuIgual("dataProcessamento", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getProcessamentoAte()));
            ParametroPesquisaDataMenorOuIgual dataPostergacaoAte = new ParametroPesquisaDataMenorOuIgual("dataPostergacao", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getProcessamentoAte()));
            parametros.add(new ParametroPesquisaOr(dataProcessamentoAte, dataPostergacaoAte));
        }
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
        if(filtro.getDataHoraProcessamentoDe() != null) {
            ParametroPesquisaDataMaiorOuIgual dataProcessamentoDe = new ParametroPesquisaDataMaiorOuIgual("dataProcessamento", filtro.getDataHoraProcessamentoDe());
            ParametroPesquisaDataMaiorOuIgual dataPostergacaoDe = new ParametroPesquisaDataMaiorOuIgual("dataProcessamento", filtro.getDataHoraProcessamentoDe());
            parametros.add(new ParametroPesquisaOr(dataProcessamentoDe, dataPostergacaoDe));
        }
        if(filtro.getDataHoraProcessamentoAte() != null) {
            ParametroPesquisaDataMenorOuIgual dataProcessamentoAte = new ParametroPesquisaDataMenorOuIgual("dataProcessamento", filtro.getDataHoraProcessamentoAte());
            ParametroPesquisaDataMenorOuIgual dataPostergacaoAte = new ParametroPesquisaDataMenorOuIgual("dataPostergacao", filtro.getDataHoraProcessamentoAte());
            parametros.add(new ParametroPesquisaOr(dataProcessamentoAte, dataPostergacaoAte));
        }
        povoarParametrosAjustados(filtro, parametros);

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
        return parametros;
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
    public AutorizacaoPagamento obterAbastecimentoPorCdPedido(Long idPedido) {
        return pesquisarUnico(
                new ParametroPesquisaIgual("pedido.id", idPedido),
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
        parametros.add(new ParametroPesquisaIgual("status", StatusAutorizacao.AUTORIZADO.getValue()));
        parametros.add(new ParametroPesquisaIgual("pontoVenda.componentes.codigoPessoa", cnpjEmit));
        parametros.add(
                new ParametroPesquisaOr(
                        new ParametroPesquisaIgual("frota.cnpj", cnpjDest),
                        new ParametroPesquisaIgual("unidade.cnpj", cnpjDest),
                        new ParametroPesquisaIgual("empresaAgregada.cnpj", cnpjDest)
                )
        );
        parametros.add(new ParametroPesquisaIgual("statusNotaFiscal", StatusNotaFiscalAbastecimento.PENDENTE.getValue()));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataProcessamento", UtilitarioCalculoData.obterUltimoInstanteDia(dataEmissao)));
        parametros.add(new ParametroPesquisaMenorOuIgual("valorTotal", valorTotalNota.add(toleranciaDeValorNota)));
        parametros.add(new ParametroPesquisaMaiorOuIgual("valorTotal", valorTotalNota.subtract(toleranciaDeValorNota)));

        return pesquisar((InformacaoPaginacao) null, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
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
                        new ParametroPesquisaDataMaiorOuIgual("dataProcessamento", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDataInicial())),
                        new ParametroPesquisaDataMenorOuIgual("dataProcessamento", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getDataFinal()))
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
            parametros.add(new ParametroPesquisaNulo("idAutorizacaoEstorno", true));
            parametros.add(new ParametroPesquisaMaior("valorTotal", BigDecimal.ZERO));
        }
    }

    @Override
    public List<VolumeVendasClienteProFrotaVo> obterVendasProfrotasAPCO(Date dataInicial, Date dataFinal) {
        return pesquisar(null, CONSULTA_VENDA_PROFROTAS, VolumeVendasClienteProFrotaVo.class, new ParametroPesquisaIgual("dataInicial", dataInicial), new ParametroPesquisaIgual("dataFinal", dataFinal)).getRegistros();
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

        parametros.add(new ParametroPesquisaIgual("dataRequisicaoDe", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDataAbastecimento())));
        parametros.add(new ParametroPesquisaIgual("dataRequisicaoAte", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getDataAbastecimento())));

        parametros.add(new ParametroPesquisaIgual("placaVeiculo", filtro.getPlaca()));

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
}