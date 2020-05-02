package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoDados;
import ipp.aci.boleia.dados.IBoletoDados;
import ipp.aci.boleia.dados.ICobrancaDados;
import ipp.aci.boleia.dados.IConfiguracaoSistemaDados;
import ipp.aci.boleia.dados.IFaturaDados;
import ipp.aci.boleia.dominio.AjusteCobranca;
import ipp.aci.boleia.dominio.Cobranca;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusPagamentoCobranca;
import ipp.aci.boleia.dominio.vo.AjusteCobrancaVo;
import ipp.aci.boleia.dominio.vo.AvisoDebitoVo;
import ipp.aci.boleia.dominio.vo.BoletoVo;
import ipp.aci.boleia.dominio.vo.DetalheAbastecimentoVo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.UtilitarioLambda;
import ipp.aci.boleia.util.UtilitarioParse;
import ipp.aci.boleia.util.UtilitarioValorMonetarioPorExtenso;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.boleia.util.pdf.UtilitarioRelatorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema.ROTINA_AVISO_DEBITO_DEZ;
import static ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema.ROTINA_AVISO_DEBITO_DEZESSEIS;
import static ipp.aci.boleia.util.UtilitarioCalculo.somarValoresLista;
import static ipp.aci.boleia.util.UtilitarioLambda.concatenarListas;

/**
 * Concentra a lógica de obtenção de faturas
 */
@Component
public class CobrancaSd {
    private static final String TEMPLATE_BOLETO = "boleto";
    private static final Integer QUANTIDADE_PARCELAS = 1;
    private static final Integer NUMERO_MAXIMO_TENTATIVAS_ENVIO = 5;
    private static final Integer DEZ_DA_MANHA = 10;
    private static final Integer QUATRO_DA_TARDE = 16;

    @Autowired
    private IBoletoDados repositorioBoleto;

    @Autowired
    private IAutorizacaoPagamentoDados repositorioAutorizacaoPgto;

    @Autowired
    private ICobrancaDados repositorioCobranca;

    @Autowired
    private IFaturaDados repositorioFatura;

    @Autowired
    private UtilitarioRelatorio utilitarioRelatorio;

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Autowired
    private IConfiguracaoSistemaDados repositorio;

    @Value("${frota.controle.cnpj}")
    private Long cnpjFrotaControle;

    /**
     * Método gera um boleto em PDF para uma cobrança.
     *
     * @param cobranca o objeto com informações da cobrança
     * @return Boleto gerado
     * @throws ExcecaoValidacao excecao de validacao do boleto
     */
    public byte[] gerarBoletoPDFPorCobranca(Cobranca cobranca) throws ExcecaoValidacao {
        BoletoVo boleto = gerarBoletoPorCobranca(cobranca);
        return gerarBoletoPDF(boleto);
    }

    /**
     * Método gera um boleto para uma cobrança.
     *
     * @param cobranca o objeto com informações da cobrança
     * @return Boleto gerado
     * @throws ExcecaoValidacao excecao de validacao do boleto
     */
    public BoletoVo gerarBoletoPorCobranca(Cobranca cobranca) throws ExcecaoValidacao {
        BoletoVo boleto = repositorioBoleto.recuperar(cobranca);
        validarBoleto(boleto);
        boleto.setAvisoDebito(construirAvisoDebito(cobranca));
        boleto.setAjustes(construirAjustesParaBoleto(cobranca));
        adaptarDadosNaoRetornados(boleto);
        return boleto;
    }

    /**
     * Método que cria uma cobrança a partir de uma lista de transações.
     *
     * @param listaTransacoes Lista de transações.
     * @return Nova cobrança para lista de transações.
     */
    public Cobranca gerarCobranca(List<TransacaoConsolidada> listaTransacoes) {
        BigDecimal valorTotalCobranca = somarValoresLista(listaTransacoes, TransacaoConsolidada::getValorTotal);
        Optional<TransacaoConsolidada> transacao = listaTransacoes.stream().findFirst();
        Cobranca cobranca = new Cobranca();

        BigDecimal valorDescontoCredito;
        BigDecimal valorDescontoAbastecimentos;
        if (transacao.isPresent()) {
            valorDescontoAbastecimentos = somarValoresLista(listaTransacoes, TransacaoConsolidada::getValorDescontoAbastecimentos);
            valorDescontoCredito = obterCreditoCobrancaAnterior(transacao.get().getFrota().getId());
            cobranca.setValorTotal(valorTotalCobranca.add(valorDescontoCredito));
            cobranca.setValorDescontoAbastecimentos(valorDescontoAbastecimentos);
            cobranca.setValorDescontoCredito(valorDescontoCredito);
            cobranca.setDataInicioPeriodo(transacao.get().getDataInicioPeriodo());
            cobranca.setDataFimPeriodo(transacao.get().getDataFimPeriodo());
        }

        cobranca.setQuantidadeParcelas(QUANTIDADE_PARCELAS);
        cobranca.setStatus(StatusPagamentoCobranca.EM_ABERTO.getValue());
        cobranca.setTransacoesConsolidadas(listaTransacoes);

        //Atualiza o valor total com base nos descontos/acréscimos concedidos no ciclo anterior.
        List<AjusteCobranca> ajustesCicloAnterior = obterAjustesCicloAnterior(cobranca);
        if (ajustesCicloAnterior != null) {
            ajustesCicloAnterior.forEach(a -> {
                BigDecimal valorTotal = cobranca.getValorTotal().add(a.getValorAjuste().negate());
                cobranca.setValorTotal(valorTotal);
            });
        }
        return cobranca;
    }

    /**
     * Retorna uma lista de cobranças disponíveis para tentativa de reenvio ao JDE.
     *
     * @return lista de cobranças para reenvio.
     */
    public List<Cobranca> obterCobrancasParaReenvio() {
        return repositorioCobranca.obterCobrancasErroEnvio(NUMERO_MAXIMO_TENTATIVAS_ENVIO);
    }

    /**
     * Método que gera fatura para uma cobrança.
     *
     * @param cobranca Cobranca
     * @return Cobranca faturada.
     */
    public Cobranca gerarFatura(Cobranca cobranca) {
        Cobranca cobrancaAnterior = repositorioCobranca.obterCobrancaAnterior(cobranca);
        Cobranca cobrancaComFatura = repositorioFatura.incluir(cobranca, cobrancaAnterior);
        incrementarNumeroTentativasEnvio(cobrancaComFatura);
        return repositorioCobranca.armazenar(cobrancaComFatura);
    }

    /**
     * Incrementa o número de tentativas de envio de uma Cobrança para o JDE.
     *
     * @param cobranca Cobrança a ter o numero de tentativas incrementado.
     */
    private void incrementarNumeroTentativasEnvio(Cobranca cobranca) {
        Integer numeroTentativas = cobranca.getNumeroTentativasEnvio();
        if (numeroTentativas != null) {
            cobranca.setNumeroTentativasEnvio(numeroTentativas + 1);
        } else {
            cobranca.setNumeroTentativasEnvio(1);
        }
    }

    /**
     * Obtém a lista de ajustes realizados no ciclo anterior de uma cobrança.
     *
     * @param cobranca Cobrança que será utilizada na busca.
     * @return Lista de ajustes realizados no ciclo anterior.
     */
    private List<AjusteCobranca> obterAjustesCicloAnterior(Cobranca cobranca) {
        Cobranca cobrancaAnterior = repositorioCobranca.obterCobrancaAnterior(cobranca);
        return cobrancaAnterior != null ? cobrancaAnterior.getAjustes() : null;
    }

    /**
     * Constrói uma lista de {@link AjusteCobrancaVo} para incluir no boleto.
     *
     * @return Lista de VOs.
     */
    private List<AjusteCobrancaVo> construirAjustesParaBoleto(Cobranca cobranca) {
        List<AjusteCobranca> ajustesAnteriores = obterAjustesCicloAnterior(cobranca);

        List<AjusteCobrancaVo> ajustesCiclo = extrairAjustesCobrancaVoParaBoleto(cobranca.getAjustes(), false, null, mensagens.obterMensagem("boleto.ajustecobranca.descricaoajusteciclo"));

        List<AjusteCobrancaVo> debitosConcedido = new ArrayList<>();
        List<AjusteCobrancaVo> creditosConcedido = new ArrayList<>();
        if (ajustesAnteriores != null) {
            debitosConcedido = extrairAjustesCobrancaVoParaBoleto(ajustesAnteriores, true, AjusteCobranca::isAcrescimo, mensagens.obterMensagem("boleto.ajustecobranca.descricaodebitoconcedido"));
            creditosConcedido = extrairAjustesCobrancaVoParaBoleto(ajustesAnteriores, true, AjusteCobranca::isDesconto, mensagens.obterMensagem("boleto.ajustecobranca.descricaocreditoconcedido"));
        }

        return concatenarListas(ajustesCiclo, debitosConcedido, creditosConcedido);
    }

    /**
     * Extrai uma lista de VOs a partir de uma lista de ajustes e os critérios específicos.
     *
     * @param ajustes             Lista de ajustes.
     * @param inverterSinalAjuste Indica se deverá ser invertido o sinal do valor de ajuste.
     * @param filtro              Filtro a ser aplicado na lista de ajustes.
     * @param descricaoAjuste     Descricao dos ajustes que serão extraídos.
     * @return Lista de VOs
     */
    private List<AjusteCobrancaVo> extrairAjustesCobrancaVoParaBoleto(List<AjusteCobranca> ajustes, boolean inverterSinalAjuste, Predicate<AjusteCobranca> filtro, String descricaoAjuste) {
        if (ajustes == null) {
            return Collections.emptyList();
        }

        return ajustes.stream()
                .filter(a -> a.getValorAjuste() != null && (filtro == null || filtro.test(a)))
                .map(a -> new AjusteCobrancaVo(a.getDataAjusteFormatada(), descricaoAjuste, inverterSinalAjuste ? a.getValorAjuste().negate() : a.getValorAjuste()))
                .sorted(Comparator.comparing(AjusteCobrancaVo::getDataAjuste))
                .collect(Collectors.toList());
    }

    /**
     * Método que gera boleto em PDF.
     *
     * @param boleto Dados do boleto a ser gerado.
     * @return representação binária do boleto em PDF.
     */
    public byte[] gerarBoletoPDF(BoletoVo boleto) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            utilitarioRelatorio.gerarPdf(boleto, TEMPLATE_BOLETO, outputStream);
            return outputStream.toByteArray();
        } catch (IOException ex) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_GENERICO, ex);
        }
    }

    /**
     * Controi um aviso de debito a partir de uma cobranca
     *
     * @param cobranca cobraca
     * @return Aviso de Debito
     */
    private AvisoDebitoVo construirAvisoDebito(Cobranca cobranca) {
        Frota frota = cobranca.getFrota();
        AvisoDebitoVo aviso = new AvisoDebitoVo();
        aviso.setAviso(cobranca.getId().toString());
        aviso.setDetalhes(UtilitarioLambda.converterLista(repositorioAutorizacaoPgto.obterAutorizacoesPorCobranca(cobranca.getId()), DetalheAbastecimentoVo::new));
        aviso.setCepFrota(UtilitarioFormatacao.formatarCepApresentacao(frota.getCep()));
        aviso.setCnpjFrota(UtilitarioFormatacao.formatarCnpjApresentacao(frota.getCnpj()));
        aviso.setDocumentoReferencia(mensagens.obterMensagem("boleto.avisodebito.documentoreferencia"));
        aviso.setValorAviso(UtilitarioFormatacao.formatarDecimal(cobranca.getValorTotal().setScale(2, RoundingMode.HALF_UP)));
        BigDecimal valorDevido = cobranca.getValorTotal().subtract(cobranca.getValorDescontoAbastecimentos()).setScale(2, RoundingMode.HALF_UP);
        aviso.setValorDevido(UtilitarioFormatacao.formatarDecimal(valorDevido));
        aviso.setEnderecoFrota(frota.getLogradouroENumero());
        TransacaoConsolidada transacaoConsolidada = cobranca.getTransacoesConsolidadas().get(0);
        aviso.setDataLancamento(UtilitarioFormatacaoData.formatarDataCurta(transacaoConsolidada.getDataFimPeriodo()));
        aviso.setDataEmissao(UtilitarioFormatacaoData.formatarDataCurta(new Date()));
        aviso.setEmitente(mensagens.obterMensagem("boleto.avisodebito.emitente"));
        aviso.setLocalEmitente(mensagens.obterMensagem("boleto.avisodebito.localemitente"));
        aviso.setNumeroLocalEmitente(mensagens.obterMensagem("boleto.avisodebito.numerolocalemitente"));
        aviso.setCnpjEmitente(mensagens.obterMensagem("boleto.avisodebito.cnpjemitente"));
        String valorExtenso = UtilitarioValorMonetarioPorExtenso.valorExtenso(mensagens, UtilitarioFormatacao.formatarDecimalMoedaReal(cobranca.getValorTotal())).toUpperCase();
        aviso.setValorPorExtenso(UtilitarioFormatacao.preencherValorEmExtensoApresentacao(valorExtenso));
        aviso.setRazaoSocialFrota(frota.getNomeRazaoFrota());
        aviso.setMunicipioFrota(frota.getMunicipio());
        aviso.setUfFrota(frota.getUnidadeFederativa());
        aviso.setCodigoCliente(frota.getNumeroJdeInterno().toString());
        aviso.setCiclo(mensagens.obterMensagem("boleto.avisodebito.ciclo", UtilitarioFormatacaoData.formatarPeriodoDias(transacaoConsolidada.getDataInicioPeriodo(), transacaoConsolidada.getDataFimPeriodo(), false)));

        if (cobranca.getValorDescontoAbastecimentos() != null && BigDecimal.ZERO.compareTo(cobranca.getValorDescontoAbastecimentos()) != 0) {
            aviso.setValorAbastecimentosTotalComDesconto(UtilitarioFormatacao.formatarDecimal(cobranca.getValorTotal().subtract(cobranca.getValorDescontoAbastecimentos().setScale(2, RoundingMode.HALF_UP))));
            aviso.setValorDescontoAbastecimentos(UtilitarioFormatacao.formatarDecimal(cobranca.getValorDescontoAbastecimentos().setScale(2, RoundingMode.HALF_UP)));
        } else {
            aviso.setValorDescontoAbastecimentos("");
            aviso.setValorAbastecimentosTotalComDesconto(UtilitarioFormatacao.formatarDecimal(cobranca.getValorTotal().setScale(2, RoundingMode.HALF_UP)));
        }
        return aviso;
    }

    /**
     * Método que verifica a validade do boleto
     *
     * @param boleto Boleto a ser validado.
     * @throws ExcecaoValidacao envia a mensagem caso o boleto não esteja disponivel
     */
    private void validarBoleto(BoletoVo boleto) throws ExcecaoValidacao {
        if (!boleto.isBoletoGerado()) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("servico.boleto.erro.naodisponivel"));
        }
    }

    /**
     * Método que ajusta dados inconsistentes retornados do JDE.
     *
     * @param boleto Dados do boleto a ser tratado.
     */
    private void adaptarDadosNaoRetornados(BoletoVo boleto) {
        BigDecimal valorAviso = new BigDecimal(boleto.getAvisoDebito().getValorAviso().replace(".", "").replace(',', '.'));
        if (boleto.getValorDesconto() == null && !boleto.getValorDocumento().equals(valorAviso)) {
            boleto.setValorDesconto(boleto.getValorDocumento().subtract(valorAviso));
        }
    }

    /**
     * Método que verifica se existe algum crédito da última cobrança <br>
     * para uma determinada frota
     *
     * @param idFrota A frota desejada
     * @return O valor do crédito
     */
    private BigDecimal obterCreditoCobrancaAnterior(Long idFrota) {
        BigDecimal valorCredito = BigDecimal.ZERO;
        Cobranca ultimaCobranca = repositorioCobranca.obterUltimaCobranca(idFrota);
        if (ultimaCobranca != null && ultimaCobranca.getValorTotal().doubleValue() < 0) {
            valorCredito = ultimaCobranca.getValorTotal();
        }
        return valorCredito;
    }

    /**
     * Verifica se cobranças para a frota controle devem ser excluídas dos resultados.
     *
     * @param usuarioLogado o usuário logado.
     * @return true se a frota controle deve ser ignorada, false caso contrário.
     */
    public boolean verificarFrotaControle(Usuario usuarioLogado) {
        return usuarioLogado.getFrota() == null || !usuarioLogado.getFrota().getCnpj().equals(cnpjFrotaControle);
    }

    /**
     * Dado o horario atual, verifica se a inativação de frotas está habilitada,
     * para inativar frotas conforme rotina de verificação de debido.
     *
     * @return true caso o parametro do horário seja 1 false caso contrário
     */
    public Boolean deveInativarFrota() {
        Date dataAtual = utilitarioAmbiente.buscarDataAmbiente();
        Integer horaAtual = UtilitarioCalculoData.obterCampoData(dataAtual, Calendar.HOUR_OF_DAY);
        if (horaAtual.equals(DEZ_DA_MANHA)) {
            return UtilitarioParse
                    .numericoStringParaBooleano(repositorio.buscarConfiguracoes(ROTINA_AVISO_DEBITO_DEZ).getParametro());
        } else if (horaAtual.equals(QUATRO_DA_TARDE)) {
            return UtilitarioParse
                    .numericoStringParaBooleano(repositorio.buscarConfiguracoes(ROTINA_AVISO_DEBITO_DEZESSEIS).getParametro());
        }
        return false;
    }

}