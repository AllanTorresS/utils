package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ICobrancaDados;
import ipp.aci.boleia.dominio.Cobranca;
import ipp.aci.boleia.dominio.enums.PeriodoCalculoImpontualidadeFrota;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoJde;
import ipp.aci.boleia.dominio.enums.StatusPagamentoCobranca;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaAnd;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaFetch;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.EnumVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaCobrancaVo;
import ipp.aci.boleia.dominio.vo.frotista.FiltroPesquisaCobrancaFrtVo;
import ipp.aci.boleia.dominio.vo.frotista.InformacaoPaginacaoFrtVo;
import ipp.aci.boleia.dominio.vo.frotista.ResultadoPaginadoFrtVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.negocio.ParametrosPesquisaBuilder;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Respositorio de entidades de Cobranca
 */
@Repository
public class OracleCobrancaDados extends OracleRepositorioBoleiaDados<Cobranca> implements ICobrancaDados {

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Value("${frota.controle.cnpj}")
    private Long cnpjFrotaControle;

    private static final String CLAUSULA_PAGAMENTO_ATRASADO =
            "AND TRUNC(C.dataVencimentoVigente) < TRUNC(:dataAtual) " +
            "AND (" +
                    "(" +
                        "(TRUNC(C.dataPagamento) > TRUNC(C.dataVencimentoVigente)) " +
                        "OR " +
                        "(C.dataPagamento IS NULL AND TRUNC(C.dataVencimentoVigente) < TRUNC(:dataAtual))" +
                    ")" +
            ") ";

    private static final String CONSULTA_COBRANCAS_EM_UM_PERIODO =
            "SELECT COUNT(0) " +
                    "FROM Cobranca C " +
                    "JOIN C.transacoesConsolidadas TC " +
                    "JOIN TC.frotaPtov FPV " +
                    "JOIN FPV.frota F " +
                    "WHERE F.id = :idFrota " +
                    "AND TRUNC(C.dataVencimentoVigente) >= TRUNC(:limiteInferiorCalculo) " +
                    "AND TRUNC(C.dataVencimentoVigente) <= TRUNC(:limiteSuperiorCalculo) ";

    private static final String CONSULTA_COBRANCAS_ATRASADAS_EM_UM_PERIODO =
            CONSULTA_COBRANCAS_EM_UM_PERIODO +
            CLAUSULA_PAGAMENTO_ATRASADO;


    /**
     * Instancia o repositorio
     */
    public OracleCobrancaDados() {
        super(Cobranca.class);
    }

    @Override
    public ResultadoPaginado<Cobranca> pesquisar(FiltroPesquisaCobrancaVo filtro) {
        List<ParametroPesquisa> parametros = criarParametrosPesquisa(filtro);
        filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("dataInicioPeriodo"));
        filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("id"));
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    /**
     * Cria uma lista de parametros para a montagem da consulta de cobranças a ser exibida no grid
     *
     * @param filtro O filtro informado pelo usuario
     * @return Uma lista de parametros
     */
    private List<ParametroPesquisa> criarParametrosPesquisa(FiltroPesquisaCobrancaVo filtro) {

        List<ParametroPesquisa> parametros = new ArrayList<>();

        povoarParametroDataMaiorIgual("transacoesConsolidadas.dataInicioPeriodo", UtilitarioCalculoData.obterPrimeiroDiaMes(filtro.getDe()), parametros);
        povoarParametroDataMenorIgual("transacoesConsolidadas.dataFimPeriodo", UtilitarioCalculoData.obterUltimoDiaMes(filtro.getAte()), parametros);
        povoarParametrosStatusPagamento(filtro, parametros);

        if (filtro.getStatusIntegracao() != null && filtro.getStatusIntegracao().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("statusIntegracaoJDE", StatusIntegracaoJde.valueOf(filtro.getStatusIntegracao().getName()).getValue()));
        }

        if (filtro.getFrota() != null && filtro.getFrota().getId() != null) {
            parametros.add(new ParametroPesquisaIgual("transacoesConsolidadas.frotaPtov.frota.id", filtro.getFrota().getId()));
        }

        if (StringUtils.isNotBlank(filtro.getNumeroDocumento())) {
            parametros.add(new ParametroPesquisaLike("numeroDocumento", filtro.getNumeroDocumento()));
        }

        parametros.add(new ParametroPesquisaMaior("valorTotal", BigDecimal.ZERO));

        if (filtro.getIgnorarFrotaControle()) {
            parametros.add(
                    new ParametroPesquisaDiferente(
                            "transacoesConsolidadas.frotaPtov.frota.cnpj", cnpjFrotaControle
                    ));
        }

        return parametros;
    }

    @Override
    public List<Cobranca> obterCobrancasEmAberto(Long idFrota) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("transacoesConsolidadas.frotaPtov.frota.id", idFrota));
        parametros.add(new ParametroPesquisaDiferente("status", StatusPagamentoCobranca.PAGO.getValue()));

        return pesquisar(new ParametroOrdenacaoColuna("dataVencimentoPagto",Ordenacao.DECRESCENTE), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public boolean verificarCobrancasEmAberto(Long idFrota){
        List<ParametroPesquisa> parametros = new ArrayList<>();
        if (idFrota != null) {
            parametros.add(new ParametroPesquisaIgual("transacoesConsolidadas.frotaPtov.frota.id", idFrota));
            parametros.add(new ParametroPesquisaMaior("valorTotal", BigDecimal.ZERO));
            parametros.add(new ParametroPesquisaDiferente("status", StatusPagamentoCobranca.PAGO.getValue()));

            List<Cobranca> cobrancas = pesquisar(new ParametroOrdenacaoColuna("dataVencimentoPagto",Ordenacao.DECRESCENTE), parametros.toArray(new ParametroPesquisa[parametros.size()]));
            return !cobrancas.isEmpty();
        }

        return false;
    }

    @Override
    public boolean verificarCobrancasVencidas(Long idFrota){
        List<ParametroPesquisa> parametros = new ArrayList<>();
        if (idFrota != null) {
            parametros.add(new ParametroPesquisaIgual("transacoesConsolidadas.frotaPtov.frota.id", idFrota));
            parametros.add(new ParametroPesquisaMaior("valorTotal", BigDecimal.ZERO));
            parametros.add(new ParametroPesquisaIgual("status", StatusPagamentoCobranca.VENCIDO.getValue()));

            List<Cobranca> cobrancas = pesquisar(new ParametroOrdenacaoColuna("dataVencimentoPagto",Ordenacao.DECRESCENTE), parametros.toArray(new ParametroPesquisa[parametros.size()]));
            return !cobrancas.isEmpty();
        }

        return false;
    }

    @Override
    public List<Cobranca> buscarCobrancasParaConsultarAvisoDebito() {
        return pesquisar(new ParametroOrdenacaoColuna("dataVencimentoPagto",Ordenacao.DECRESCENTE),
                new ParametroPesquisaNulo("numeroDocumento", true),
                new ParametroPesquisaNulo("tipoDocumento", true),
                new ParametroPesquisaNulo("ciaDocumento", true),
                new ParametroPesquisaDiferente("status", StatusPagamentoCobranca.PAGO.getValue()));
    }

    @Override
    public Cobranca obterUltimaCobranca(Long idFrota) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("transacoesConsolidadas.frotaPtov.frota.id", idFrota));
        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.setPagina(1);
        paginacao.setTamanhoPagina(1);
        paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("id",Ordenacao.DECRESCENTE));
        List<Cobranca> cobrancas = pesquisar(paginacao, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
        if(cobrancas != null && !cobrancas.isEmpty()){
            return cobrancas.get(0);
        }
        return null;
    }

    @Override
    public Cobranca obterPorAutorizacaoPagamentoId(Long idAutorizacaoPagamento) {
        return pesquisarUnico(new ParametroPesquisaIgual("transacoesConsolidadas.autorizacaoPagamentos.id", idAutorizacaoPagamento));
    }

    @Override
    public ResultadoPaginadoFrtVo<Cobranca> pesquisar(FiltroPesquisaCobrancaFrtVo filtro) {
        ParametroPesquisa[] parametros = new ParametrosPesquisaBuilder()
                .adicionarParametros(
                        new ParametroPesquisaDataMaiorOuIgual(
                                "transacoesConsolidadas.dataInicioPeriodo",
                                UtilitarioCalculoData.obterPrimeiroDiaMes(filtro.getMesInicial())
                        ),
                        new ParametroPesquisaDataMenorOuIgual(
                                "transacoesConsolidadas.dataFimPeriodo",
                                UtilitarioCalculoData.obterUltimoDiaMes(filtro.getMesFinal())
                        ),
                        new ParametroPesquisaMaior("valorTotal", BigDecimal.ZERO)
                )
                .adicionarParametros(filtro.getStatusPagamento(), this::construirParametrosStatusPagamento)
                .buildArray();

        InformacaoPaginacao paginacao = new InformacaoPaginacaoFrtVo(
                filtro.getPagina(),
                new ParametroOrdenacaoColuna("dataVencimentoPagto"),
                new ParametroOrdenacaoColuna("id")
        );

        ResultadoPaginado<Cobranca> resultadoPaginado = pesquisar(paginacao, parametros);
        return new ResultadoPaginadoFrtVo<>(resultadoPaginado, paginacao.getPagina(), paginacao.getTamanhoPagina());
    }

    @Override
    public List<Cobranca> pesquisarParaConciliacao(FiltroPesquisaCobrancaVo filtro) {
        List<ParametroPesquisa> parametros = criarParametrosPesquisa(filtro);
        parametros.add(new ParametroPesquisaNulo("numeroDocumento", true));
        return pesquisar(new ParametroOrdenacaoColuna("dataVencimentoPagto", Ordenacao.CRESCENTE), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public Cobranca obterCobrancaAnterior(Cobranca cobranca) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("transacoesConsolidadas.frotaPtov.frota.id", cobranca.getFrota().getId()));
        parametros.add(new ParametroPesquisaDataMenor("dataInicioPeriodo", cobranca.getDataInicioPeriodo()));

        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.setPagina(1);
        paginacao.setTamanhoPagina(1);
        paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("id", Ordenacao.DECRESCENTE));

        List<Cobranca> cobrancas = pesquisar(paginacao, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
        if (cobrancas != null && !cobrancas.isEmpty()) {
            return cobrancas.stream().findFirst().get();
        }
        return null;
    }

    @Override
    public List<Cobranca> obterCobrancasErroEnvio(Integer numeroTentativas) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaOr(
                new ParametroPesquisaIgual("statusIntegracaoJDE", StatusIntegracaoJde.ERRO_ENVIO.getValue()),
                new ParametroPesquisaNulo("statusIntegracaoJDE")
        ));
        parametros.add(new ParametroPesquisaMenor("numeroTentativasEnvio", new BigDecimal(numeroTentativas)));
        return pesquisar(new ParametroOrdenacaoColuna("dataFimPeriodo", Ordenacao.CRESCENTE), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public Cobranca desanexar(Cobranca cobranca) {
        return super.desanexar(cobranca);
    }

    /**
     * Constroi um {@link ParametroPesquisa} para status de cobrança
     * @param valor O valor correspontente ao status desejado
     * @return O filtro a ser enviado à consulta
     */
    private ParametroPesquisa construirParametrosStatusPagamento(Object valor) {
        StatusPagamentoCobranca statusPagamento = StatusPagamentoCobranca.obterPorValor((Integer) valor);
        if(StatusPagamentoCobranca.VENCIDO.equals(statusPagamento)) {
            return new ParametroPesquisaAnd(
                    new ParametroPesquisaOr(
                            new ParametroPesquisaIgual("status", StatusPagamentoCobranca.A_VENCER.getValue()),
                            new ParametroPesquisaIgual("status", StatusPagamentoCobranca.VENCIDO.getValue())
                    ),
                    new ParametroPesquisaDataMenor(
                            "dataVencimentoPagto",
                            UtilitarioCalculoData.obterPrimeiroInstanteDia(ambiente.buscarDataAmbiente())
                    )
            );

        }

        ParametroPesquisa parametroStatus =  new ParametroPesquisaIgual("status", valor);

        if(statusPagamento.equals(StatusPagamentoCobranca.A_VENCER)) {
            return new ParametroPesquisaAnd(
                    parametroStatus,
                    new ParametroPesquisaOr(
                            new ParametroPesquisaDataMaiorOuIgual("dataVencimentoPagto", ambiente.buscarDataAmbiente()),
                            new ParametroPesquisaNulo("dataVencimentoPagto")
                    )
            );
        }

        return parametroStatus;

    }

    /**
     * Povoa os parametros de consulta pertinentes ao status de pagamento da cobranca
     * @param filtro O filtro de pesquisa
     * @param parametros A lista corrente de parametros
     */
    private void povoarParametrosStatusPagamento(FiltroPesquisaCobrancaVo filtro, List<ParametroPesquisa> parametros) {
        if (CollectionUtils.isNotEmpty(filtro.getStatusPagamento())) {
            ParametroPesquisaOr parametrosStatusOr = new ParametroPesquisaOr();

            for (EnumVo statusPagamento : filtro.getStatusPagamento()) {
                if(statusPagamento.getName() != null) {
                    if (StatusPagamentoCobranca.valueOf(statusPagamento.getName()).getValue().equals(StatusPagamentoCobranca.VENCIDO.getValue())){
                        parametrosStatusOr.addParametro(new ParametroPesquisaAnd(
                                new ParametroPesquisaOr(new ParametroPesquisaIgual("status", StatusPagamentoCobranca.A_VENCER.getValue()),new ParametroPesquisaIgual("status", StatusPagamentoCobranca.VENCIDO.getValue())),
                                new ParametroPesquisaDataMenor("dataVencimentoPagto", UtilitarioCalculoData.obterPrimeiroInstanteDia(ambiente.buscarDataAmbiente()))
                        ));
                    } else {
                        ParametroPesquisaAnd parametrosPesquisaAnd = new ParametroPesquisaAnd();

                        parametrosPesquisaAnd.addParametro(new ParametroPesquisaIgual("status", StatusPagamentoCobranca.valueOf(statusPagamento.getName()).getValue()));

                        if (StatusPagamentoCobranca.valueOf(statusPagamento.getName()).getValue().equals(StatusPagamentoCobranca.A_VENCER.getValue())){
                            parametrosPesquisaAnd.addParametro(new ParametroPesquisaOr(
                                    new ParametroPesquisaDataMaiorOuIgual("dataVencimentoPagto", ambiente.buscarDataAmbiente()),
                                    new ParametroPesquisaNulo("dataVencimentoPagto"))
                            );
                        }

                        parametrosStatusOr.addParametro(parametrosPesquisaAnd);
                    }
                }
            }

            parametros.add(parametrosStatusOr);
        }
    }

    @Override
    public Long obterQuantidadeCobrancaPagasEmAtraso(Long idFrota, PeriodoCalculoImpontualidadeFrota periodoCalculo) {
        List<ParametroPesquisa> parametros = montarParametrosPesquisaImpontualidade(idFrota, periodoCalculo, true);
        return pesquisarUnicoSemIsolamentoDados(CONSULTA_COBRANCAS_ATRASADAS_EM_UM_PERIODO, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public Long obterQuantidadeTotalCobrancas(Long idFrota, PeriodoCalculoImpontualidadeFrota periodoCalculo) {
        List<ParametroPesquisa> parametros = montarParametrosPesquisaImpontualidade(idFrota, periodoCalculo, false);
        return pesquisarUnicoSemIsolamentoDados(CONSULTA_COBRANCAS_EM_UM_PERIODO, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<Cobranca> buscarCobrancasAtrasadasDoisDiasUteis() {
        Date limiteInferiorData = UtilitarioCalculoData.obterProximoDiaUtilSemFeriado(UtilitarioCalculoData.adicionarDiasData(UtilitarioCalculoData.obterUltimoInstanteDia(ambiente.buscarDataAmbiente()), 2));
        return pesquisarSemIsolamentoDados(
            new ParametroOrdenacaoColuna("dataVencimentoPagto", Ordenacao.CRESCENTE),
            new ParametroPesquisaDataMaiorOuIgual("dataVencimentoPagto", limiteInferiorData),
            new ParametroPesquisaNulo("dataPagamento"),
            new ParametroPesquisaIgual("statusIntegracaoJDE", StatusIntegracaoJde.REALIZADO.getValue()),
            new ParametroPesquisaFetch("frota")
        );
    }

    /**
     * Monta os parâmetros de pesquisa das cobranças em um período
     * @param idFrota O identificador da frota
     * @param periodoCalculo O período do cálculo
     * @return Os parâmetros de pesquisa
     */
    private List<ParametroPesquisa> montarParametrosPesquisaImpontualidade(Long idFrota, PeriodoCalculoImpontualidadeFrota periodoCalculo, Boolean consultaAtrasados) {
        Date dataAtual = ambiente.buscarDataAmbiente();
        Date limiteInferior = UtilitarioCalculoData.diminuirMeses(UtilitarioCalculoData.obterPrimeiroDiaMes(dataAtual), PeriodoCalculoImpontualidadeFrota.TRIMESTRAL.getValue().equals(periodoCalculo.getValue()) ? 3 : 12);
        Date limiteSuperior = UtilitarioCalculoData.obterUltimoDiaMesAnterior(dataAtual);
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("idFrota", idFrota));
        if(consultaAtrasados) {
            parametros.add(new ParametroPesquisaIgual("dataAtual", dataAtual));
        }
        parametros.add(new ParametroPesquisaIgual("limiteInferiorCalculo", limiteInferior));
        parametros.add(new ParametroPesquisaIgual("limiteSuperiorCalculo", limiteSuperior));

        return parametros;
    }


}