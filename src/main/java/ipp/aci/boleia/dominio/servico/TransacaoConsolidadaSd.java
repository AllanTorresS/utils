package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoDados;
import ipp.aci.boleia.dados.IFilaPostergacaoAbastecimentoDados;
import ipp.aci.boleia.dados.IFrotaPontoVendaDados;
import ipp.aci.boleia.dados.INegociacaoDados;
import ipp.aci.boleia.dados.INotaFiscalDados;
import ipp.aci.boleia.dados.IParametroCicloDados;
import ipp.aci.boleia.dados.IPrazoGeracaoCobrancaDados;
import ipp.aci.boleia.dados.ITransacaoConsolidadaDados;
import ipp.aci.boleia.dados.ITransacaoConsolidadaDetalheDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.AutorizacaoPagamentoEdicao;
import ipp.aci.boleia.dominio.EmpresaAgregada;
import ipp.aci.boleia.dominio.FrotaPontoVenda;
import ipp.aci.boleia.dominio.Negociacao;
import ipp.aci.boleia.dominio.NotaFiscal;
import ipp.aci.boleia.dominio.ParametroCiclo;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.PrazoGeracaoCobranca;
import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.TransacaoConsolidadaDetalhe;
import ipp.aci.boleia.dominio.TransacaoConsolidadaPrazos;
import ipp.aci.boleia.dominio.Unidade;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.ModalidadePagamento;
import ipp.aci.boleia.dominio.enums.MotivoEstorno;
import ipp.aci.boleia.dominio.enums.StatusNotaFiscal;
import ipp.aci.boleia.dominio.enums.StatusTransacaoConsolidada;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.AgrupamentoTransacaoConsolidadaCobrancaVo;
import ipp.aci.boleia.dominio.vo.EntidadeVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaFinanceiroVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaTransacaoConsolidadaDetalheVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaTransacaoConsolidadaVo;
import ipp.aci.boleia.dominio.vo.VolumeRealizadoVo;
import ipp.aci.boleia.util.ConstantesFormatacao;
import ipp.aci.boleia.util.UtilitarioCalculo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.concorrencia.MapeadorLock;
import ipp.aci.boleia.util.concorrencia.Sincronizador;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoRecursoBloqueado;
import ipp.aci.boleia.util.excecao.ExcecaoSemConteudo;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.boleia.util.seguranca.UtilitarioCriptografia;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ipp.aci.boleia.dominio.enums.StatusTransacaoConsolidada.FECHADA;
import static ipp.aci.boleia.util.UtilitarioCalculo.calcularPorcentagem;
import static ipp.aci.boleia.util.UtilitarioCalculo.somarValoresLista;
import static ipp.aci.boleia.util.UtilitarioCalculoData.adicionarDiasData;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterPrimeiroInstanteDia;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterUltimoInstanteDia;
import static ipp.aci.boleia.util.UtilitarioLambda.agrupar;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;


/**
 * Implementa as regras de negocio relacionadas a entidade TransacaoConsolidada
 */
@Component
public class TransacaoConsolidadaSd {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransacaoConsolidadaSd.class);
    public static final BigDecimal MDR_PADRAO_SOLUCAO = new BigDecimal("2");

    @Autowired
    private ITransacaoConsolidadaDados repositorio;

    @Autowired
    private INegociacaoDados repositorioNegociacao;

    @Autowired
    private ITransacaoConsolidadaDetalheDados repositorioDetalhe;

    @Autowired
    private IParametroCicloDados parametroCicloDados;

    @Autowired
    private INotaFiscalDados repositorioNF;

    @Autowired
    private IPrazoGeracaoCobrancaDados repositorioPrazoGeracaoCobranca;

    @Autowired
    private NotificacaoUsuarioSd notificacaoUsuarioSd;

    @Autowired
    private ReembolsoBaseSd reembolsoSd;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private IAutorizacaoPagamentoDados repositorioAutorizacaoPgto;

    @Autowired
    private AutorizacaoPagamentoSd autorizacaoPagamentoSd;

    @Autowired
    private EmailSd emailSd;

    @Autowired
    private IFrotaPontoVendaDados repositorioFrotaPontoVenda;

    @Autowired
    private IFilaPostergacaoAbastecimentoDados filaPostergacaoAbastecimentoDados;

    @Autowired
    @Qualifier("Redis")
    private Sincronizador sincronizador;

    @Value("${concorrencia.lock.trans-consol-auto-pgto.prefixo}")
    private String prefixoLockAutorizacaoPagamento;

    @Value("${concorrencia.lock.trans-consol.prefixo}")
    private String prefixoLockTransacaoConsolidada;

    @Value("${email.financeiro}")
    private String destinatarioFinanceiro;

    private MapeadorLock<Long> mapeadorLockAutorizacaoPagamento;
    private MapeadorLock<Long> mapeadorLockTransacaoConsolidada;

    /**
     * Configura o monitor de autorização de pagamento
     */
    @PostConstruct
    public void inicializador() {
        mapeadorLockAutorizacaoPagamento = new MapeadorLock<>(sincronizador, this::construirChaveLockAutorizacaoPagamento);
        mapeadorLockTransacaoConsolidada = new MapeadorLock<>(sincronizador, this::construirChaveLockTransacaoConsolidada);
    }

    /**
     * Retorna a quantidade de notas fiscais que uma transação consolidada possui.
     * Nota: As nfs associadas a autorizacoes pagamento originais do ciclo que foram postergadas para outros ciclos nao sao consideradas
     * @param transacaoConsolidada Transação consolidada utilizada na consulta.
     * @return O numero de notas fiscais.
     */
    public int obterQuantidadeNotasFiscais(TransacaoConsolidada transacaoConsolidada) {
        return transacaoConsolidada.getAutorizacoesPagamentoAssociadas()
                .stream()
                .filter(autorizacaoPagamento -> !autorizacaoPagamentoFoiPostergadaParaOutroCiclo(autorizacaoPagamento, transacaoConsolidada))
                .mapToInt(AutorizacaoPagamento::getQuantidadeNotasFiscais)
                .reduce(0, Integer::sum);
    }

    /**
     * Realiza a pesquisa de transacoes consolidadas para exportação.
     *
     * @param filtro parâmetros utilizados na consulta.
     * @param usuario Usuário responsável pela exportação.
     * @return lista de transações consolidadas.
     */
    public ResultadoPaginado<TransacaoConsolidada> pesquisarTransacoesFinanceiroParaExportacao(FiltroPesquisaFinanceiroVo filtro, Usuario usuario) {
        InformacaoPaginacao informacaoPaginacao = filtro.getPaginacao();
        informacaoPaginacao.setTamanhoPagina(null);
        informacaoPaginacao.setPagina(null);
        filtro.setPaginacao(informacaoPaginacao);
        return repositorio.pesquisarTransacoesFinanceiroRevenda(filtro, usuario);
    }

    /**
     * Obtem todas as transacoes candidatas a consolidacao, cuja a data de fim do ciclo
     * seja anterior a data de ontem e que possuam valor total nulo.
     *
     * @return Transacoes candidatas ao processo de consolidacao
     */
    public List<Long> buscarTransacoesConsolidadasParaProcessar() {
        List<Long> ids = new ArrayList<>();
        // Obtem as transações que estão com status aberto
        List<TransacaoConsolidada> transacoesConsolidadas = repositorio.obterTransacoesParaConsolidacao();
        if(transacoesConsolidadas != null && !transacoesConsolidadas.isEmpty()) {
            ids = transacoesConsolidadas.stream().map(TransacaoConsolidada::getId).collect(Collectors.toList());
        }
        return ids;
    }

    /**
     * Realiza a consolidacao das notas fiscais do periodo da transacao consolidada cadastrada
     *
     * @param idTransacaoConsolidada TransacaoConsolidada a ser processada
     * @throws ExcecaoRecursoBloqueado caso o lock não esteja disponível
     */
    public void processarTransacaoConsolidada(Long idTransacaoConsolidada) throws ExcecaoRecursoBloqueado  {
        Lock lock = getLockTransacaoConsolidada(idTransacaoConsolidada);
        if(lock.tryLock()) {
            try {
                TransacaoConsolidada transacaoConsolidada = repositorio.obterPorId(idTransacaoConsolidada);
                processarValoresTransacaoConsolidada(transacaoConsolidada);
                atualizarStatusCicloConsolidado(transacaoConsolidada);

                if(transacaoConsolidada.esta(StatusTransacaoConsolidada.FECHADA)) {
                    reverterEdicoesPendentes(transacaoConsolidada);
                }
            } finally {
                lock.unlock();
            }
        } else {
            throw new ExcecaoRecursoBloqueado(mensagens.obterMensagem("Erro.TRANS_CONSOL_LOCKADO", idTransacaoConsolidada));
        }
    }

    /**
     * Atualiza a última data de emissão do ciclo a partir da nota sendo adicionada ou removida
     *
     * @param idCiclo id do consolidado associado à nota
     * @param dataEmissao data de emissão da nota
     * @param notaRemovida indica que a nota associada à data de emissão está sendo removida do sistema
     */
    public void atualizarUltimaDataEmissaoNf(Long idCiclo, Date dataEmissao, boolean notaRemovida) {
        TransacaoConsolidada ciclo = repositorio.obterPorId(idCiclo);
        if (notaRemovida) {
            if (dataEmissao != null && dataEmissao.equals(ciclo.getDataUltimaEmissaoNf())) {
                ciclo.setDataUltimaEmissaoNf(emptyIfNull(ciclo.getAutorizacaoPagamentos()).stream().map(a ->
                        a.getNotasFiscais().stream().map(n -> n.getDataEmissao()).max(Date::compareTo)
                                .orElse(null)).filter(Objects::nonNull).max(Date::compareTo).orElse(null));
            }
        } else if (ciclo.getDataUltimaEmissaoNf() == null || (dataEmissao != null && dataEmissao.after(ciclo.getDataUltimaEmissaoNf()))) {
            ciclo.setDataUltimaEmissaoNf(dataEmissao);
        }
    }

    /**
     * Inicia o processo de postergação dos abastecimentos não emitidos
     * de um ciclo que fechou.
     *
     * @param consolidado Transação consolidada do ciclo que fechou.
     */
    public void postergarAbastecimentosNaoEmitidos(TransacaoConsolidada consolidado) {
        if(consolidado.exigeEmissaoNF() && consolidado.esta(FECHADA) && !consolidado.isProcessouPostergacao()) {
            filaPostergacaoAbastecimentoDados.enviarParaPostergarAbastecimentos(consolidado.getId());
            consolidado.setProcessouPostergacao(true);
            repositorio.armazenar(consolidado);
        }
    }

    /**
     * Realiza a consolidacao das notas fiscais de um ciclo após conciliação automática de notas fiscais
     *
     * @param idTransacaoConsolidada TransacaoConsolidada a ser processada
     * @throws ExcecaoRecursoBloqueado caso o lock não esteja disponível
     */
    public void processarTransacaoConsolidadaAposConciliacaoAutomatica(Long idTransacaoConsolidada) throws ExcecaoRecursoBloqueado  {
        Lock lock = getLockTransacaoConsolidada(idTransacaoConsolidada);
        if(lock.tryLock()) {
            try {
                TransacaoConsolidada transacaoConsolidada = repositorio.obterPorId(idTransacaoConsolidada);
                processarValoresTransacaoConsolidada(transacaoConsolidada);
                repositorio.armazenar(transacaoConsolidada);
            } finally {
                lock.unlock();
            }
        } else {
            throw new ExcecaoRecursoBloqueado(mensagens.obterMensagem("Erro.TRANS_CONSOL_LOCKADO", idTransacaoConsolidada));
        }
    }

    /**
     * Obtem o consolidado ao qual esta vinculado um abastecimento
     *
     * @param idAbastecimento O abastecimento
     * @return A transação consolidada
     */
    public TransacaoConsolidada obterConsolidadoPorAbastecimento(Long idAbastecimento) {
        return repositorio.obterConsolidadoParaAbastecimento(idAbastecimento);
    }

    /**
     * Atualiza o cálculo do consolidado de notas fiscais a partir de uma Autorizacao de Pagamento
     *
     * @param idAutorizacaoPagamento Autorizacao de Pagamento a ser processada
     */
    public  void atualizarConsolidadoNotaFiscal(Long idAutorizacaoPagamento) {
        AutorizacaoPagamento autorizacaoPagamento = repositorioAutorizacaoPgto.obterPorId(idAutorizacaoPagamento);
        atualizarConsolidadoNotaFiscal(autorizacaoPagamento.getTransacaoConsolidadaVigente());
    }

    /**
     * Atualiza o cálculo do consolidado de notas fiscais a partir de uma Autorizacao de Pagamento
     *
     * @param transacaoConsolidada Autorizacao de Pagamento a ser processada
     */
    public void atualizarConsolidadoNotaFiscal(TransacaoConsolidada transacaoConsolidada){
        if(transacaoConsolidada !=  null){
            processarValoresTransacaoConsolidada(transacaoConsolidada);
            transacaoConsolidada.setDataAtualizacao(utilitarioAmbiente.buscarDataAmbiente());
            repositorio.armazenar(transacaoConsolidada);
        }
    }

    /**
     * Rotina é executada no começo do dia X <br>
     * <br>
     * 24 horas para o vencimento: prazo de emissão de NF em X+1 00:00:00 até X+1 23:59:59 <br>
     * 72 horas para o vencimento: prazo de emissão de NF em X+3 00:00:00 até X+3 23:59:59 <br>
     */
    public void verificarCiclosAVencer() {
        Date dataAtual = utilitarioAmbiente.buscarDataAmbiente();
        List<TransacaoConsolidada> transacoes24horas;
        List<TransacaoConsolidada> transacoes72horas;

        // 24 horas
        Date dataVencimento24hrMin = obterPrimeiroInstanteDia(adicionarDiasData(dataAtual,1));
        Date dataVencimento24hrMax = obterUltimoInstanteDia(adicionarDiasData(dataAtual,1));
        transacoes24horas = repositorio.obterConsolidacoesSemNotaFiscalEntreDatas(
                dataVencimento24hrMin, dataVencimento24hrMax);

        // 72 horas
        Date dataVencimento72hMin = obterPrimeiroInstanteDia(adicionarDiasData(dataAtual, 3));
        Date dataVencimento72hMax = obterUltimoInstanteDia(adicionarDiasData(dataAtual, 3));
        transacoes72horas = repositorio.obterConsolidacoesSemNotaFiscalEntreDatas(
                dataVencimento72hMin, dataVencimento72hMax);

        if(!transacoes24horas.isEmpty()) {
            notificacaoUsuarioSd.enviarNotificacaoCiclosAVencerSolucao();
            transacoes24horas.forEach(notificacaoUsuarioSd::enviarNotificacaoCiclosAVencerRevenda);
        }

        if (!transacoes72horas.isEmpty()) {
            transacoes72horas.forEach(notificacaoUsuarioSd::enviarNotificacaoCiclosAVencerRevenda);
        }
    }

    /**
     * Rotina é executada no começo do dia X <br>
     * <br>
     * Pelo menos 24 horas de atraso: prazo de emissão de NF em X-2 00:00:00 a X-2 23:59:59 <br>
     * Pelo menos 72 horas de atraso: prazo de emissão de NF em X-4 00:00:00 a X-4 23:59:59 <br>
     * <br>
     * Usamos -2 e -4 ao invés de -1 e -3 para garantir que pelo menos 24 e 72 terão se
     * passado antes da execução da rotina.
     */
    public void verificarCiclosAtrasados() {
        Date dataAtual = utilitarioAmbiente.buscarDataAmbiente();
        List<TransacaoConsolidada> transacoes24horas;
        List<TransacaoConsolidada> transacoes72horas;

        // Vencidas há mais de 24 horas (e menos de 48 horas)
        Date data24HorasAposVencimento = obterPrimeiroInstanteDia(adicionarDiasData(dataAtual,-2));
        Date data48HorasAposVencimento = obterUltimoInstanteDia(adicionarDiasData(dataAtual,-2));
        transacoes24horas = repositorio.obterConsolidacoesSemNotaFiscalEntreDatas(
                data24HorasAposVencimento, data48HorasAposVencimento);
        List<TransacaoConsolidada> transacoes = new ArrayList<>(transacoes24horas);

        // Vencidas há mais de 72 horas (e menos de 96 horas)
        Date data72HorasAposVencimento = obterPrimeiroInstanteDia(adicionarDiasData(dataAtual,-4));
        Date data96HorasAposVencimento = obterUltimoInstanteDia(adicionarDiasData(dataAtual,-4));
        transacoes72horas = repositorio.obterConsolidacoesSemNotaFiscalEntreDatas(
                data72HorasAposVencimento, data96HorasAposVencimento);
        transacoes.addAll(transacoes72horas);

        if (!transacoes.isEmpty()) {
            notificacaoUsuarioSd.enviarEmailCiclosAtrasados(obterRedes(transacoes));
        }
    }

    /**
     * Rotina é executada no começo do dia X <br>
     * <br>
     * Verificar encerramentos do ciclo de abastecimento no dia X-1 <br>
     * Gerar alerta para o dia X <br>
     */
    public void verificarEncerramentoCiclos() {
        Date dataAtual = utilitarioAmbiente.buscarDataAmbiente();

        Date ontemComecoDoDia = obterPrimeiroInstanteDia(adicionarDiasData(dataAtual, -1));
        Date ontemFimDoDia = obterUltimoInstanteDia(adicionarDiasData(dataAtual,-1));
        List<TransacaoConsolidada> transacoes = repositorio.obterConsolidacoesComCicloAbastecimentoEncerrado(
                ontemComecoDoDia, ontemFimDoDia
        );

        if (!transacoes.isEmpty()) {
            transacoes.forEach(this::atualizarConsolidadoNotaFiscal);
            notificacaoUsuarioSd.enviarNotificacaoCiclosEncerrados(ontemComecoDoDia);
        }
    }

    /**
     * Envia uma notificação para o gestor da revenda
     * sobre o início do período de ajuste de um ciclo
     *
     * @param ciclo O ciclo cujo período de ajuste se iniciou
     */
    public void enviarNotificacaoCicloEmAjuste(TransacaoConsolidada ciclo) {
        notificacaoUsuarioSd.enviarNotificacaoCicloEmAjuste(ciclo);
    }

    /**
     * Busca o volume realizado para um dado produto no ultimo ciclo fechado
     *
     * @param idFrota A frota
     * @param idPv O pv
     * @param idTipoCombustivel O produto
     * @return O volume realizado
     */
    public VolumeRealizadoVo obterVolumeRealizadoUltimoCicloEncerrado(Long idFrota, Long idPv, Long idTipoCombustivel) {
        VolumeRealizadoVo vo = new VolumeRealizadoVo();
        BigDecimal volume = BigDecimal.ZERO;
        Set<TransacaoConsolidada> consolidados = new HashSet<>();

        List<TransacaoConsolidadaDetalhe> detalhes = repositorioDetalhe.obterDetalhesTransacaoPorFrotaPvCombustivel(idFrota, idPv, idTipoCombustivel, utilitarioAmbiente.buscarDataAmbiente());
        if (!detalhes.isEmpty()) {
            for (TransacaoConsolidadaDetalhe detalhe : detalhes) {
                volume = volume.add(detalhe.getQuantidade());
                consolidados.add(detalhe.getTransacaoConsolidada());
            }
        }

        if (CollectionUtils.isEmpty(consolidados)) {
            consolidados.addAll(repositorio.obterUltimasTransacoesPorFrotaPtovData(idFrota, idPv, utilitarioAmbiente.buscarDataAmbiente()));
        }

        if (CollectionUtils.isEmpty(consolidados)) {
            return null;
        }

        // um ciclo pos eh sempre mais longo que um ciclo pre
        TransacaoConsolidada maiorConsolidado = consolidados.stream().max(Comparator.comparing(TransacaoConsolidada::getModalidadePagamento)).orElseThrow(ExcecaoSemConteudo::new);
        vo.setDataInicioCiclo(UtilitarioFormatacaoData.formatarDataDiaMes(maiorConsolidado.getDataInicioPeriodo()));
        vo.setDataFimCiclo(UtilitarioFormatacaoData.formatarDataDiaMes(maiorConsolidado.getDataFimPeriodo()));
        vo.setVolumeCiclo(UtilitarioFormatacao.formatarDecimal(volume.setScale(1, BigDecimal.ROUND_HALF_UP)));

        return vo;
    }

    /**
     * Calcula a data de inicio de um novo ciclo de faturamento a ser aberto, baseado na data vigente
     *
     * @param dataProcessamento Data de processamento usada de referência para calculo da data de inicio do período.
     * @param frotaPontoVenda Relacionamento Frota-PontoVenda da transação consolidada.
     * @param idEmpresaAgregada O identificador da empresa agregada.
     * @param idUnidade O identificador da unidade.
     * @param parametroCiclo Os parâmetros de configuração do ciclo.
     * @return A data de início do ciclo.
     */
    public Date calcularDataInicioPeriodo(Date dataProcessamento, FrotaPontoVenda frotaPontoVenda,  Long idEmpresaAgregada, Long idUnidade, ParametroCiclo parametroCiclo) {
        Date diaInicioCiclo = UtilitarioCalculoData.obterPrimeiroDiaMes(dataProcessamento);
        Date ultimaDataMes = UtilitarioCalculoData.obterUltimoDiaMes(dataProcessamento);
        Integer ultimoDiaMes = UtilitarioCalculoData.obterCampoData(ultimaDataMes, Calendar.DAY_OF_MONTH);
        Integer quantidadeDiasCiclo = parametroCiclo.getPrazoCiclo().intValue();

        ArrayList<Date> datasInicioNoMes = new ArrayList<>();
        datasInicioNoMes.add(diaInicioCiclo);

        while (diaInicioCiclo.before(ultimaDataMes)) {
            diaInicioCiclo = adicionarDiasData(diaInicioCiclo, quantidadeDiasCiclo);
            datasInicioNoMes.add(diaInicioCiclo);
        }

        Date dataInicioCandidata = datasInicioNoMes.stream().filter(d -> {
            Integer diasParaFimDoMes = ultimoDiaMes - UtilitarioCalculoData.obterCampoData(d, Calendar.DAY_OF_MONTH) + 1;
            return (d.compareTo(dataProcessamento) <= 0) && (diasParaFimDoMes >= 4 || quantidadeDiasCiclo <= diasParaFimDoMes);
        }).max(Date::compareTo).orElseThrow(ExcecaoSemConteudo::new);

        // Verifica se existe um "salto" entre as datas inicial e final, se houver, a data inicial será imediatamente após à data final do ciclo anterior.
        TransacaoConsolidada consolidadoSobreposto = repositorio.obterUltimoConsolidadoAnteriorAoAbastecimentoSemCiclo(frotaPontoVenda.getFrota().getId(), frotaPontoVenda.getPontoVenda().getId(), idEmpresaAgregada, idUnidade, dataInicioCandidata, dataProcessamento);
        if(consolidadoSobreposto != null){
            dataInicioCandidata = obterPrimeiroInstanteDia(adicionarDiasData(consolidadoSobreposto.getDataFimPeriodo(),1));
        }
        return dataInicioCandidata;
    }

    /**
     * Calcula a data de encerramento de um ciclo de faturamento a partir de sua data de inicio e dos
     * parametros de configuracao do ciclo
     * @param dataInicioPeriodo A data de inicio do ciclo
     * @param parametroCiclo Os parametros de configuracao do ciclo
     * @return A data de encerramento do ciclo
     */
    private Date calcularDataFimPeriodo(Date dataInicioPeriodo, ParametroCiclo parametroCiclo) {
        Integer anoVigente = UtilitarioCalculoData.obterCampoData(dataInicioPeriodo, Calendar.YEAR);
        Integer mesVigente = UtilitarioCalculoData.obterCampoData(dataInicioPeriodo, Calendar.MONTH);
        Integer diaVigente = UtilitarioCalculoData.obterCampoData(dataInicioPeriodo, Calendar.DAY_OF_MONTH);
        Integer prazoCiclo = parametroCiclo.getPrazoCiclo().intValue();

        int diasParaAdicionar = (diaVigente <= prazoCiclo) ? prazoCiclo - diaVigente : prazoCiclo - 1;
        Date dataFimPeriodo = adicionarDiasData(dataInicioPeriodo, diasParaAdicionar);
        Integer diaFimCalculado = UtilitarioCalculoData.obterCampoData(dataFimPeriodo, Calendar.DAY_OF_MONTH);

        //Verifica se a diferença entre a data fim do ciclo e a última data do mês é menor ou igual a 3 dias.
        //Caso seja, a data fim do ciclo é alterada para a última data do mês.
        Date ultimaDataMes = UtilitarioCalculoData.obterUltimoDiaMes(dataFimPeriodo);
        Integer ultimoDiaMes = UtilitarioCalculoData.obterCampoData(ultimaDataMes, Calendar.DAY_OF_MONTH);
        Integer diasParaFimDoMes = ultimoDiaMes - diaFimCalculado;
        dataFimPeriodo = (diasParaFimDoMes <= 3 && prazoCiclo > diasParaFimDoMes) ? ultimaDataMes : dataFimPeriodo;

        if (UtilitarioCalculoData.obterCampoData(dataFimPeriodo, Calendar.MONTH) > mesVigente ||
                UtilitarioCalculoData.obterCampoData(dataFimPeriodo, Calendar.YEAR) > anoVigente) {
            return UtilitarioCalculoData.obterUltimoDiaMes(dataInicioPeriodo);
        }
        return obterUltimoInstanteDia(dataFimPeriodo);
    }

    /**
     * Cria uma transacao consolidada para o VO informado, adidionando-a na lista <code>listaTC</code> informada como parametro
     *
     * @param dataProcessamento A data de processamento usada de referencia para criar a transação consolidada
     * @param frotaPtov A frota ponto venda para associacao
     * @param empresaAgregada A empresa agregada, caso exija nota fiscal
     * @param unidade A Unidade, caso exija nota fiscal
     * @param prePago Se true, indica que o ciclo da transacao consolidada deve seguir as regras da modalidade pre-pago.
     * @return A transacao consolidada
     */
    public TransacaoConsolidada criarTransacaoConsolidada(Date dataProcessamento, FrotaPontoVenda frotaPtov, EmpresaAgregada empresaAgregada, Unidade unidade, boolean prePago) {

        TransacaoConsolidada tc = new TransacaoConsolidada();
        // Nota: Consolidação de autorização de pagamento PRE-PAGO é diária.
        Date dataInicio = UtilitarioCalculoData.obterPrimeiroInstanteDia(dataProcessamento);
        Date dataFim = UtilitarioCalculoData.obterUltimoInstanteDia(dataProcessamento);
        Long idEmpresaAgregada = empresaAgregada != null ? empresaAgregada.getId() : null;
        Long idUnidade = unidade != null ? unidade.getId() : null;

        if(!prePago) {
            // Carrega os ciclos de pagamento, ex.: Ciclos de 7 em 7 dias, de 15 em 15 dias e etc.
            ParametroCiclo parametroCiclo = parametroCicloDados.obterParametroCicloDaFrota(frotaPtov.getFrota().getId());
            // Calcula a data de início dentro dos ciclos criados.
            dataInicio = calcularDataInicioPeriodo(dataProcessamento, frotaPtov, idEmpresaAgregada, idUnidade, parametroCiclo);

            // Calcula a data fim com relação à data de início e o parâmetro de ciclo. Porém, se o mês termina antes de terminar o ciclo, então a dataFinal será o fim do mês.
            dataFim = calcularDataFimPeriodo(dataInicio, parametroCiclo);
        }

        tc.setModalidadePagamento(prePago ? ModalidadePagamento.PRE_PAGO.getValue() : ModalidadePagamento.POS_PAGO.getValue());
        tc.setFrotaPtov(frotaPtov);
        tc.setFrotaExigeNF(frotaPtov.getFrota().exigeNotaFiscal());
        tc.setFrotaGerenciaNf(frotaPtov.getFrota().isGerenciaNf());
        if(empresaAgregada != null) {
            tc.setEmpresaAgregada(empresaAgregada);
        } else if(unidade != null) {
            tc.setUnidade(unidade);
        }
        tc.setDataInicioPeriodo(dataInicio);
        tc.setDataFimPeriodo(dataFim);
        tc.setStatusConsolidacao(StatusTransacaoConsolidada.EM_ABERTO.getValue());
        tc.setVersao(0L);
        tc.preencherChave();
        return tc;
    }

    /**
     * Obtem os itens (detalhe) de uma transacao consolidada
     *
     * @param id O id da transação consolidada
     * @return Lista com o detalhe da transação consolidada
     */
    public List<TransacaoConsolidadaDetalhe> listarDetalhes(Long id) {
        FiltroPesquisaTransacaoConsolidadaDetalheVo filtro = new FiltroPesquisaTransacaoConsolidadaDetalheVo();
        filtro.setIdConsolidado(id);
        return repositorioDetalhe.pesquisar(filtro).getRegistros();
    }

    /**
     * Cria um lock para mutual exclusion de transação consolidada em processamento
     *
     * @param idTransacaoConsolidada Identificador da transação consolidada
     * @return O Lock criado para a transação consolidada.
     */
    public Lock getLockTransacaoConsolidada(Long idTransacaoConsolidada) {
        return mapeadorLockTransacaoConsolidada.getLock(idTransacaoConsolidada);
    }

    /**
     * Cria um lock para mutual exclusion de autorização de pagamento em processamento
     *
     * @param idAutorizacaoPagamento Identificador da autorização de pagamento
     * @return O Lock criado
     */
    public Lock getLockAutorizacaoPagamento(Long idAutorizacaoPagamento) {
        return mapeadorLockAutorizacaoPagamento.getLock(idAutorizacaoPagamento);
    }

    /**
     * Obtém uma lista com os abastecimentos que deverão ser postergados
     * para o próximo ciclo.
     *
     * @param transacaoConsolidada Transação consolidada com os abastecimentos que sofrerão a postergação.
     * @return Lista dos abastecimentos.
     */
    public List<AutorizacaoPagamento> obterAbastecimentosParaPostergacao(TransacaoConsolidada transacaoConsolidada) {
        if(transacaoConsolidada.exigeEmissaoNF() && transacaoConsolidada.esta(FECHADA)) {
            return transacaoConsolidada.getAutorizacoesPagamentoAssociadas()
                    .stream()
                    .filter(a -> a.isPendenteEmissaoNF(false))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * Cria a chave do lock de autorização de pagamento para criação de ciclos
     *
     * @param idAutorizacaoPagamento id do abastecimento
     * @return chave do lock criada
     */
    private String construirChaveLockAutorizacaoPagamento(Long idAutorizacaoPagamento) {
        return String.format("%s:%s", prefixoLockAutorizacaoPagamento, idAutorizacaoPagamento.toString());
    }

    /**
     * Cria a chave do lock de transação consolidada para o seu processamento
     *
     * @param idTransacaoConsolidada identificador da transacao consolidada
     * @return o lock criado
     */
    private String construirChaveLockTransacaoConsolidada(Long idTransacaoConsolidada) {
        return String.format("%s:%s", prefixoLockTransacaoConsolidada, idTransacaoConsolidada.toString());
    }

    /**
     * Extrai a lista de redes vinculados a um conjunto de transacoes consolidadas.
     * A lista retornada não contém duplicatas.
     *
     * @param transacoes lista de transacooes consolidadas
     * @return lista de identificadores das redes (duplicatas são ignoradas)
     */
    private Set<Long> obterRedes(List<TransacaoConsolidada> transacoes) {
        return transacoes.stream().map(t -> {
            PontoDeVenda pv = t.getFrotaPtov().getPontoVenda();
            return pv.getRede() != null ? pv.getRede().getId() : null;
        }).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * Atualiza o status de emissao de notas fiscais do ciclo
     * @param transacaoConsolidada O ciclo
     * @param listaAutorizacaoPagamento A lista de abastecimetnos do ciclo
     */
    public void atualizarStatusEmissaoNota(TransacaoConsolidada transacaoConsolidada, List<AutorizacaoPagamento> listaAutorizacaoPagamento) {
        BigDecimal valorEmitidoNotaFiscal = transacaoConsolidada.getValorEmitidoNotaFiscal();
        BigDecimal valorTotalNotaFiscal = transacaoConsolidada.getValorTotalNotaFiscal();

        boolean valorNotasMaiorZero = valorTotalNotaFiscal.compareTo(BigDecimal.ZERO) > 0;
        boolean todosAbastPossuemNotaEmitidaOuJustificativa = listaAutorizacaoPagamento.stream().allMatch(AutorizacaoPagamento::notaFiscalEstaEmitido);
        boolean quantidadeDeTransacoesPositivasAutorizadasIgualZero = obterQuantidadeDeTransacoesAutorizadasDoCiclo(transacaoConsolidada) == 0;
        boolean possuiValorEmitido = valorEmitidoNotaFiscal.compareTo(BigDecimal.ZERO) > 0;
        boolean possuiValorASerEmitido = valorEmitidoNotaFiscal.compareTo(valorTotalNotaFiscal) < 0;

        //Verifica se o consolidado atende as condicoes para ter statusNF EMITIDA
        //Caso atenda, seta o status como EMITIDA
        //Caso nao atenda, seta o status como PARCIALMENTE_EMITIDA ou PENDENTE
        if ((valorNotasMaiorZero && todosAbastPossuemNotaEmitidaOuJustificativa) || (!transacaoConsolidada.isPassivelDeEmissao())) {
            transacaoConsolidada.setStatusNotaFiscal(StatusNotaFiscal.EMITIDA.getValue());
        } else if(transacaoConsolidada.esta(FECHADA) && possuiValorEmitido && possuiValorASerEmitido) {
            transacaoConsolidada.setStatusNotaFiscal(StatusNotaFiscal.PARCIALMENTE_EMITIDA.getValue());
        } else  {
            transacaoConsolidada.setStatusNotaFiscal(StatusNotaFiscal.PENDENTE.getValue());
        }

        //Se o ciclo esta fechado e tiver zero abastecimentos aprovados com valor positivo, seu status NF deve ser SEM EMISSAO
        if(transacaoConsolidada.esta(FECHADA) && transacaoConsolidada.exigeEmissaoNF() && (quantidadeDeTransacoesPositivasAutorizadasIgualZero || !possuiValorEmitido)){
            transacaoConsolidada.setStatusNotaFiscal(StatusNotaFiscal.SEM_EMISSAO.getValue());
        }

        if(transacaoConsolidada.getReembolso() != null) {
            reembolsoSd.atualizarStatusReembolso(transacaoConsolidada.getReembolso());
        }
    }

    /**
     * Obtém os abastecimentos que devem ser considerados para o calculo do valor emitido do ciclo
     * Nota: Considera os abastecimentos realizados na vigencia do ciclo que nao foram postergados para outros ciclos e os abastecimentos postergados para esse ciclo.
     * Nota: Considera somente os abastecimentos que estão autorizadas e com valores acima de zero.
     *
     * @param transacaoConsolidada a transação consolidada a partir da qual serao buscadas as autorizacoes de pagamento
     * @param considerarAbastecimentosPostergadosParaOutrosCiclos define se os abastecimentos postergados para outro ciclo devem ser considerados ou nao
     * @return as autorizações de pagamento encontradas
     */
    private List<AutorizacaoPagamento>  obterAutorizacoesPagamentoAssociadasAoCiclo(TransacaoConsolidada transacaoConsolidada, boolean considerarAbastecimentosPostergadosParaOutrosCiclos) {
        if(considerarAbastecimentosPostergadosParaOutrosCiclos) {
            return transacaoConsolidada.getAutorizacoesPagamentoAssociadas().stream()
                    .filter(AutorizacaoPagamento::estaAutorizado)
                    .filter(autorizacaoPagamento -> autorizacaoPagamento.getValorTotal().compareTo(BigDecimal.ZERO) > 0)
                    .collect(Collectors.toList());
        }

        // Adiciona uma condicao de filtro para excluir os abastecimentos realizados na vigencia desse ciclo que foram postergados para outros ciclos
        // Nota: Essa exclusao e relevante somente no ultimo processamento do ciclo, que e realizado imediatamente apos o seu fechamento
        // Continuacao Nota: Nesse momento, os abastecimentos nao emitidos ja foram postergados e, por isso, precisam ser desconsiderados para o calculo do valor emitido do ciclo
        // Continuacao Nota: Isso evita que esses abastecimentos sejam considerados em duplicidade (no valor emitido do ciclo de origem e do ciclo de destino)
        return transacaoConsolidada.getAutorizacoesPagamentoAssociadas().stream()
                .filter(AutorizacaoPagamento::estaAutorizado)
                .filter(autorizacaoPagamento -> autorizacaoPagamento.getValorTotal().compareTo(BigDecimal.ZERO) > 0)
                .filter(autorizacaoPagamento -> !autorizacaoPagamentoFoiPostergadaParaOutroCiclo(autorizacaoPagamento, transacaoConsolidada))
                .collect(Collectors.toList());
    }

    /**
     * Verifica se a autorização de pagamento foi postergada para outro ciclo.
     * Nota: Essa verificacao só faz sentido no momento em que o ciclo está sendo processado pela última vez, o que ocorre imediatamente após o seu fechamento.
     *
     * @param autorizacaoPagamento autorização pagamento a ser consultada a postergação.
     * @param transacaoConsolidada ciclo consultado.
     * @return True, caso tenha sido postergada para outro ciclo.
     */
    private boolean autorizacaoPagamentoFoiPostergadaParaOutroCiclo(AutorizacaoPagamento autorizacaoPagamento, TransacaoConsolidada transacaoConsolidada) {
        if (autorizacaoPagamento.getTransacaoConsolidadaPostergada() != null) {
            return !autorizacaoPagamento.getTransacaoConsolidadaPostergada().getId().equals(transacaoConsolidada.getId());
        }
        return false;
    }

    /**
     * Realiza o cálculo dos valores de uma transação consolidada.
     *
     * @param transacaoConsolidada Transação que terá os valores calculados.
     */
    public void processarValoresTransacaoConsolidada(TransacaoConsolidada transacaoConsolidada) {
        consolidarNotasFiscaisEJustificativas(transacaoConsolidada);

        if(transacaoConsolidada.getReembolso() == null || !transacaoConsolidada.esta(StatusTransacaoConsolidada.FECHADA)){
            transacaoConsolidada.setValorTotal(calcularValorTotalTransacaoConsolidada(transacaoConsolidada));
            transacaoConsolidada.setMdr(obterTaxaMdr(transacaoConsolidada));
            transacaoConsolidada.setValorDesconto(calcularValorDescontoTransacaoConsolidada(transacaoConsolidada));
            transacaoConsolidada.setValorFaturamento(calcularFaturamentoTransacaoConsolidada(transacaoConsolidada));
            transacaoConsolidada.setValorReembolso(calcularValorReembolsoTransacaoConsolidada(transacaoConsolidada));
            transacaoConsolidada.setValorDescontoAbastecimentos(calcularValorDescontoCampanhasTransacaoConsolidada(transacaoConsolidada));
        }
    }


    /**
     * Calcula o valor total de uma transação consolidada.
     * O valor total de uma transconsol é a soma do valor total de todos os abastecimentos (originais e postergados de outros ciclos)
     * considerando apenas os autorizados e cancelados.
     *
     * @param transacaoConsolidada A transação consolidada que terá o valor calculado.
     * @return O valor calculado.
     */
    private BigDecimal calcularValorTotalTransacaoConsolidada(TransacaoConsolidada transacaoConsolidada) {

        Stream<AutorizacaoPagamento> autorizacaoPagamentoStream = transacaoConsolidada.getAutorizacoesPagamentoAssociadas().stream().filter(a -> {
            if (a.getValorTotal().compareTo(BigDecimal.ZERO) < 0) {
                //verifica se um abastecimento negativo deve ser descontado
                AutorizacaoPagamento autorizacaoCancelada = repositorioAutorizacaoPgto.obterPorId(a.getIdAutorizacaoEstorno());
                return autorizacaoPagamentoSd.valorDaTransacaoFoiContempladoEmReembolsoGerado(autorizacaoCancelada);
            } else {
                return !transacaoConsolidada.exigeEmissaoNF() || !a.isPendenteEmissaoNF(true);
            }
        });
        return somarValoresAutorizacaoPagamento(
                autorizacaoPagamentoStream,
                AutorizacaoPagamento::estaAutorizado,
                AutorizacaoPagamento::getValorTotal
        );
    }

    /**
     * Calcula o valor de desconto de uma transação consolidada.
     * O valor de desconto de uma transconsol é calculado aplicando o MDR em cima do valor total da transconsol
     * considerando apenas os abastecimentos emitidos, justificados ou sem exigência de NF.
     *
     * @param transacaoConsolidada A transação consolidada com os valores a serem calculados.
     * @return O valor calculado.
     */
    private BigDecimal calcularValorDescontoTransacaoConsolidada(TransacaoConsolidada transacaoConsolidada) {
        BigDecimal mdr = transacaoConsolidada.getMdr();
        return calcularPorcentagem(transacaoConsolidada.getValorTotal(), mdr).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Calcula o faturamento de uma transação consolidada.
     * O faturamento de uma transconsol é a subtração entre o valor total de todos os abastecimentos realizados dentro do ciclo
     * atual (considerando apenas os autorizados e cancelados) e o valor de desconto do ciclo. .
     *
     * @param transacaoConsolidada A transação consolidada que terá o valor calculado.
     * @return O valor calculado.
     */
    private BigDecimal calcularFaturamentoTransacaoConsolidada(TransacaoConsolidada transacaoConsolidada) {
        Stream<AutorizacaoPagamento> autorizacaoPagamentoStream = transacaoConsolidada.getAutorizacaoPagamentosStream().filter(a -> {
            if (a.getValorTotal().compareTo(BigDecimal.ZERO) < 0) {
                AutorizacaoPagamento autorizacaoCancelada = repositorioAutorizacaoPgto.obterPorId(a.getIdAutorizacaoEstorno());
                return autorizacaoCancelada.getTransacaoConsolidada().getId().equals(a.getTransacaoConsolidada().getId());
            } else {
                return true;
            }
        });
        return somarValoresAutorizacaoPagamento(
                autorizacaoPagamentoStream,
                AutorizacaoPagamento::estaAutorizadaOuCancelada,
                AutorizacaoPagamento::getValorTotal
        );
    }

    /**
     * Calcula o valor de reembolso de uma transação consolidada.
     * O valor de reembolso de uma transconsol é a subtração entre o valor total de
     * todos os abastecimentos (originais e postergados de outros ciclos) e o valor de desconto da transação consolidada.
     *
     * @param transacaoConsolidada A transação consolidada com os valores que serão calculados.
     * @return O valor calculado.
     */
    private BigDecimal calcularValorReembolsoTransacaoConsolidada(TransacaoConsolidada transacaoConsolidada) {
        BigDecimal desconto = transacaoConsolidada.getValorDesconto();
        return transacaoConsolidada.getValorTotal().subtract(desconto);
    }

    /**
     * Calcula o valor total dos descontos das campanhas de uma transação consolidada.
     * Esse calor é calculado com a soma do valor de desconto de campanha de todos os abastecimentos (originais e postergados de outros ciclos)
     * considerando apenas os autorizados e cancelados.
     *
     * @param transacaoConsolidada A transação consolidada que terá o valor calculado.
     * @return O valor calculado.
     */
    private BigDecimal calcularValorDescontoCampanhasTransacaoConsolidada(TransacaoConsolidada transacaoConsolidada) {
        Stream<AutorizacaoPagamento> autorizacaoPagamentoStream = transacaoConsolidada.getAutorizacoesPagamentoAssociadas().stream().filter(a -> {
            if (a.getValorTotal().compareTo(BigDecimal.ZERO) < 0) {
                //verifica se um abastecimento negativo deve ser descontado
                AutorizacaoPagamento autorizacaoCancelada = repositorioAutorizacaoPgto.obterPorId(a.getIdAutorizacaoEstorno());
                return autorizacaoPagamentoSd.valorDaTransacaoFoiContempladoEmReembolsoGerado(autorizacaoCancelada);
            } else {
                return !a.isPendenteEmissaoNF(true);
            }
        });
        return somarValoresAutorizacaoPagamento(
                autorizacaoPagamentoStream,
                AutorizacaoPagamento::estaAutorizado,
                AutorizacaoPagamento::getValorDescontoTotal
        );
    }

    /**
     * Realiza o somatório de um valor específico de uma lista de abastecimentos.
     *
     * @param autorizacoesPagamento lista de abastecimentos
     * @param filtro Predicate aplicado no filter
     * @param funcaoMapeadora Function aplicado no map.
     * @return Valor calculado.
     */
    private BigDecimal somarValoresAutorizacaoPagamento(Stream<AutorizacaoPagamento> autorizacoesPagamento, Predicate<AutorizacaoPagamento> filtro, Function<AutorizacaoPagamento, BigDecimal> funcaoMapeadora) {
        return somarValoresLista(autorizacoesPagamento
                .filter(filtro)
                .map(funcaoMapeadora)
        );
    }

    /**
     * Retorna a taxa de MDR que será aplicada em uma transação consolidada.
     * @param transacaoConsolidada Transação usada na obtenção da taxa de MDR.
     * @return a taxa de MDR.
     */
    private BigDecimal obterTaxaMdr(TransacaoConsolidada transacaoConsolidada) {
        FrotaPontoVenda frotaPtov = repositorioFrotaPontoVenda.obterPorId(transacaoConsolidada.getFrotaPtov().getId());
        PontoDeVenda pontoDeVenda = frotaPtov.getPontoVenda();
        BigDecimal mdr = MDR_PADRAO_SOLUCAO;
        Negociacao negociacao = repositorioNegociacao.obterPorFrotaPontoVenda(transacaoConsolidada.getFrotaPtov());
        if (negociacao != null && negociacao.getMdr() != null && negociacao.getMdr().compareTo(BigDecimal.ZERO) >= 0) {
            mdr = negociacao.getMdr();
        }else if(pontoDeVenda.getMdr() != null && pontoDeVenda.getMdr().compareTo(BigDecimal.ZERO) >= 0){
            mdr = pontoDeVenda.getMdr();
        }
        return mdr;
    }

    /**
     * Atualiza o faturamento de um ciclo que possui um abastecimento cancelado após postergação
     * @param abastecimentoCancelado Abastecimento cancelado após ter sido postergado
     */
    public void atualizarFaturamentoTransacaoConsolidadaAposCancelamento(AutorizacaoPagamento abastecimentoCancelado) {
        TransacaoConsolidada transacaoConsolidada = abastecimentoCancelado.getTransacaoConsolidada();
        transacaoConsolidada.setValorFaturamento(transacaoConsolidada.getValorFaturamento().subtract(abastecimentoCancelado.getValorTotal()));
        repositorio.armazenar(transacaoConsolidada);
    }

    /**
     * Atualiza o status de uma transação consolidada para "em ajuste" ou "fechada",
     * reprocessando a mesma
     * @param consolidado Transacao consolidada a ser processada
     * @return A transação consolidada atualizada
     */
    public TransacaoConsolidada atualizarCicloConsolidado(TransacaoConsolidada consolidado) {
        atualizarStatusCicloConsolidado(consolidado);
        processarValoresTransacaoConsolidada(consolidado);
        if(consolidado.esta(StatusTransacaoConsolidada.FECHADA)) {
            reverterEdicoesPendentes(consolidado);
        }
        return consolidado;
    }

    /**
     * Atualiza o status da transação consolidada para "em ajuste" quando o período de
     * abastecimento está encerrado e para "fechada" quando o período de ajuste
     * está encerrado
     * @param transacaoConsolidada O ciclo de faturamento (transacao consolidada)
     */
    private void atualizarStatusCicloConsolidado(TransacaoConsolidada transacaoConsolidada) {
        Date dataAtual = utilitarioAmbiente.buscarDataAmbiente();
        Date dataFimPeriodo = obterUltimoInstanteDia(transacaoConsolidada.getDataFimPeriodo());
        boolean isPeriodoEncerrado = dataFimPeriodo.compareTo(dataAtual) < 0;

        TransacaoConsolidadaPrazos prazos = transacaoConsolidada.getPrazos();
        //Nota: É considerada como data limite de ajuste a data limite de emissão de nota fiscal.
        Date dataStatusFechado = prazos.getPossuiPrazoAjuste() ? obterUltimoInstanteDia(prazos.getDataLimiteEmissaoNfe()) : dataFimPeriodo;

        if (dataStatusFechado.compareTo(dataAtual) < 0) {
            transacaoConsolidada.setStatusConsolidacao(StatusTransacaoConsolidada.FECHADA.getValue());
        } else if (prazos.getPossuiPrazoAjuste() && isPeriodoEncerrado) {
            transacaoConsolidada.setStatusConsolidacao(StatusTransacaoConsolidada.EM_AJUSTE.getValue());
        } else {
            transacaoConsolidada.setStatusConsolidacao(StatusTransacaoConsolidada.EM_ABERTO.getValue());
        }

        if (transacaoConsolidada.esta(FECHADA) && !transacaoConsolidada.exigeEmissaoNF()) {
            transacaoConsolidada.setProcessouPostergacao(true);
        }

        repositorio.armazenar(transacaoConsolidada);
    }

    /**
     * Verifica se existem edições pendentes nos abastecimentos de um consolidado e, caso exista, realiza a reversão
     * @param transacaoConsolidada O ciclo de faturamento (transacao consolidada)
     */
    private void reverterEdicoesPendentes(TransacaoConsolidada transacaoConsolidada) {
        List<AutorizacaoPagamento> autorizacaoPagamentosOriginais = transacaoConsolidada.getAutorizacaoPagamentos();
        List<AutorizacaoPagamento> autorizacoesPagamentoPostergadas = transacaoConsolidada.getAutorizacoesPagamentoPostergadas();
        List<AutorizacaoPagamento> autorizacoesPagamentoVigentesNoCiclo = transacaoConsolidada.getAutorizacoesPagamentoAssociadas();

        List<AutorizacaoPagamentoEdicao> autorizacoesPendentes = autorizacaoPagamentoSd.obtemAutorizacoesPendentesDeUmConsolidado(autorizacoesPagamentoVigentesNoCiclo);
        if (!autorizacoesPendentes.isEmpty()) {
            autorizacoesPendentes.forEach(a -> autorizacaoPagamentoSd.expirarEdicaoAbastecimento(a));
            transacaoConsolidada.setAutorizacaoPagamentos(autorizacaoPagamentoSd.reverterStatusEdicao(autorizacaoPagamentosOriginais));
            transacaoConsolidada.setAutorizacoesPagamentoPostergadas(autorizacaoPagamentoSd.reverterStatusEdicao(autorizacoesPagamentoPostergadas));

            try {
                notificacaoUsuarioSd.enviarNotificacaoEdicaoAbastecimentoExpirado(autorizacoesPendentes.stream().map(AutorizacaoPagamentoEdicao::getUsuario).collect(Collectors.toList()));
                enviarEmailsEdicoesRevertidas(transacaoConsolidada, autorizacoesPendentes);
            } catch (Exception e) {
                LOGGER.error(mensagens.obterMensagem("erro.consolidado.edicao.desfeita", transacaoConsolidada.getId()), e);
            }
        }
    }

    /**
     * Envia os emails de aviso após uma lista de edições de abastecimentos forem revertidas.
     *
     * @param transacaoConsolidada Ciclo de faturamento.
     * @param autorizacoesPendentes Lista de alterações de abastecimento que foram revertidas.
     */
    private void enviarEmailsEdicoesRevertidas(TransacaoConsolidada transacaoConsolidada, List<AutorizacaoPagamentoEdicao> autorizacoesPendentes) {
        //Envio de emails por usuario
        Map<String, List<AutorizacaoPagamentoEdicao>> autorizacoesPendentesPorUsuario = agrupar(autorizacoesPendentes, a -> a.getUsuario().getEmail());
        autorizacoesPendentesPorUsuario.forEach((email, edicoes) -> emailSd.enviarEmailCicloFechadoSemAprovacaoDeAlteracao(email, transacaoConsolidada, edicoes));

        //Envio de email completo para o financeiro
        emailSd.enviarEmailCicloFechadoSemAprovacaoDeAlteracao(destinatarioFinanceiro, transacaoConsolidada, autorizacoesPendentes);
    }

    /**
     * Realiza o cálculo do valor emitido do ciclo considerando as notas fiscais e as justificativas associadas aos abastecimentos
     *
     * @param transacaoConsolidada TransacaoConsolidada a ser processada
     */
    public void consolidarNotasFiscaisEJustificativas(TransacaoConsolidada transacaoConsolidada) {

        //Obtem a lista de abastecimentos que devem ser considerados para o calculo do valor emitido do ciclo
        //Nota: sao os abastecimentos postergados para o ciclo e os abastecimentos realizados na vigencia do ciclo que nao foram postergados para outro ciclo
        //Nota: Se o abastecimento foi postergado para outro ciclo, seu valor emitido (caso haja alguma nota fiscal emitida) nao deve ser contemplado em seu ciclo de origem
        List<AutorizacaoPagamento> abastecimentosParaCalculoDoValorEmitido =
                obterAutorizacoesPagamentoAssociadasAoCiclo(transacaoConsolidada,false);

        //Obtem a lista de abastecimentos que deveriam ter sido emitidos no ciclo para calculo do valor total de nota fical do ciclo
        //Nota: sao os abastecimentos postergados para o ciclo e os abastecimentos realizados na vigencia do ciclo (independente se foram ou nao postergados)
        List<AutorizacaoPagamento> abastecimentosParaCalculoDoValorTotalNotaFiscal =
                obterAutorizacoesPagamentoAssociadasAoCiclo(transacaoConsolidada,true);

        //Calcula o valor total que deveria ter sido emitido no ciclo
        transacaoConsolidada.setValorTotalNotaFiscal(
                UtilitarioCalculo.somarValoresLista(abastecimentosParaCalculoDoValorTotalNotaFiscal, AutorizacaoPagamento::getValorTotal)
                        .setScale(2, BigDecimal.ROUND_HALF_UP)
        );

        //calcula o total de abastecimentos associados ao ciclo
        transacaoConsolidada.setQuantidadeAbastecimentos((long) calcularQuantidadeDeTransacoesAssociadasACiclo(transacaoConsolidada));

        //Obtem todas as notas e justificativas associadas a abastecimentos postergados para o ciclo e realizados na vigencia do ciclo que nao foram postergados para outro ciclo
        List<NotaFiscal> notas = repositorioNF.obterNotasEJustificativasPorAbastecimentos(abastecimentosParaCalculoDoValorEmitido.stream().map(AutorizacaoPagamento::getId).collect(Collectors.toList()));

        //Obtem o valor total das notas fiscais emitidas no ciclo
        //Apesar de as justificativas se comportarem como NFs emitidas, essa soma nao as contempla
        //Para as justificativas, o valor da NF consta como zero
        BigDecimal valoresDeAbastecimentosComNotasFiscaisEmitidas = UtilitarioCalculo.somarValoresLista(notas, NotaFiscal::getValorTotal);

        //Lista somente os abastecimentos que tem justificativa associada
        List<AutorizacaoPagamento> abastecimentosComJustificativa =
                repositorioAutorizacaoPgto.obterAbastecimentosComJustificativaAssociadaPorAbastecimentos(
                        abastecimentosParaCalculoDoValorEmitido.stream().map(AutorizacaoPagamento::getId).collect(Collectors.toList()));

        BigDecimal totalValoresEmitidos;

        //caso haja abastecimentos com justificativa associada no ciclo, as contempla no valor emitido
        if(!abastecimentosComJustificativa.isEmpty()){

            //Obtem o valor total dos abastecimentos que tem justificativa associada
            BigDecimal valoresTotaisDeAbastecimentosComJustificativa = UtilitarioCalculo.somarValoresLista(
                    abastecimentosComJustificativa, AutorizacaoPagamento::getValorTotal);

            //Obtem o valor emitido (valor contemplado em NF(s)) dos abastecimentos que tem justificativa associada
            BigDecimal valoresEmitidosDeAbastecimentosComJustificativa = UtilitarioCalculo.somarValoresLista(
                    abastecimentosComJustificativa, AutorizacaoPagamento::getValorEmitido);

            //Calcula o valor justificado dos abastecimentos do ciclo
            //valor justificado = valor total - valor emitido
            BigDecimal valoresJustificados =
                    valoresTotaisDeAbastecimentosComJustificativa.
                            subtract(valoresEmitidosDeAbastecimentosComJustificativa);

            //Soma os valores emitidos por meio de NFs e os valores justificados para os abastecimentos do ciclo
            totalValoresEmitidos =
                    valoresDeAbastecimentosComNotasFiscaisEmitidas.add(valoresJustificados);

        }else{
            //o valor emitido total e somente referente as NFs emitidas, pois nao ha justificativas associadas aos abastecimentos do ciclo
            totalValoresEmitidos = valoresDeAbastecimentosComNotasFiscaisEmitidas;
        }

        //Atualiza o valor emitido na transacao consolidada
        //Esse valor contempla os valores que constam em NFs emitidas e tambem as justificativas (caso haja)
        transacaoConsolidada.setValorEmitidoNotaFiscal(totalValoresEmitidos.setScale(2,BigDecimal.ROUND_HALF_UP));

        //Calcula o desconto com base somente nos abastecimentos considerados para o calculo do valor emitido do ciclo
        BigDecimal valorDescontoNota = UtilitarioCalculo.somarValoresLista(abastecimentosParaCalculoDoValorEmitido, AutorizacaoPagamento::getValorDescontoTotal)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        transacaoConsolidada.setValorDescontoNotaFiscal(valorDescontoNota);

        atualizarStatusEmissaoNota(transacaoConsolidada, abastecimentosParaCalculoDoValorTotalNotaFiscal);

        transacaoConsolidada.setDataAtualizacao(utilitarioAmbiente.buscarDataAmbiente());
        repositorio.armazenar(transacaoConsolidada);
    }

    /**
     * Realiza uma consulta das transações consolidadas de acordo com o filtro informado.
     *
     * @param filtro O filtro da busca
     * @param usuarioLogado usuario logado que solicita a pesquisa
     * @return Uma lista transações consolidadas
     */
    public ResultadoPaginado<TransacaoConsolidada> pesquisar(FiltroPesquisaTransacaoConsolidadaVo filtro, Usuario usuarioLogado) {
        return repositorio.pesquisar(filtro, usuarioLogado);
    }

    /**
     * Obtém a quantidade de abastecimentos cancelados e estornados associados ao ciclo que devem ser contabilizados em sua quantidade de transações
     *
     * @param transacaoConsolidada ciclo que esta sendo processado
     * @return quantidade de abastecimentos cancelados ou estornados relevantes para contabilizacao no ciclo em questao
     */
    private int obterQuantidadeDeAbastecimentosCanceladosOuEstornadosRelevantesParaOCiclo(TransacaoConsolidada transacaoConsolidada){

        int quantidadeTransacoesCanceladasOuEstornadasQueDevemSerContabilizadas = 0;

        AutorizacaoPagamento transacaoNegativa;

        //obtem as transacoes canceladas associadas ao ciclo
        List<AutorizacaoPagamento> transacoesCanceladas = transacaoConsolidada.getAutorizacoesPagamentoAssociadas()
                .stream()
                .filter(AutorizacaoPagamento::estaCancelado)
                .collect(Collectors.toList());

        //contabiliza as transacoes canceladas e estornadas relevantes
        for (AutorizacaoPagamento transacaoCancelada : transacoesCanceladas) {
            if (transacaoCancelada.getMotivoEstorno() != null) {

                transacaoNegativa = repositorioAutorizacaoPgto.obterTransacaoNegativaOriundaDeEstorno(transacaoCancelada);

                if (MotivoEstorno.obterPorValor(transacaoCancelada.getMotivoEstorno()).getSemAlteracao()){
                    if(transacaoNegativa == null &&
                            autorizacaoPagamentoSd.abastecimentoCanceladoDeveSerContabilizadoNoCicloImediatamenteAposCancelamento(transacaoCancelada,transacaoConsolidada)){
                        //contabiliza as transacoes que estao sendo canceladas em cenarios cuja contabilizacao delas no ciclo é relevante
                        quantidadeTransacoesCanceladasOuEstornadasQueDevemSerContabilizadas++;
                    }else if(transacaoNegativa != null &&
                            autorizacaoPagamentoSd.abastecimentoCanceladoDeveSerExibidoEContabilizado(transacaoCancelada)){
                        //contabiliza as transacoes que já foram canceladas em cenarios cuja contabilizacao delas no ciclo é relevante
                        quantidadeTransacoesCanceladasOuEstornadasQueDevemSerContabilizadas++;
                    }
                } else if (!MotivoEstorno.obterPorValor(transacaoCancelada.getMotivoEstorno()).getSemAlteracao()){
                    if(transacaoNegativa == null &&
                            autorizacaoPagamentoSd.abastecimentoEstornadoDeveSerContabilizadoNoCicloImediatamenteAposEstorno(transacaoCancelada,transacaoConsolidada)){
                        //contabiliza as transacoes que estao sendo estornadas em cenarios cuja contabilizacao delas no ciclo é relevante
                        quantidadeTransacoesCanceladasOuEstornadasQueDevemSerContabilizadas++;
                    }else if(transacaoNegativa != null &&
                            autorizacaoPagamentoSd.abastecimentoEstornadoDeveSerExibidoEContabilizado(transacaoCancelada,transacaoConsolidada.getId())){
                        //contabiliza as transacoes que já foram estornadas em cenarios cuja contabilizacao delas no ciclo é relevante
                        quantidadeTransacoesCanceladasOuEstornadasQueDevemSerContabilizadas++;
                    }
                }
            }
        }

        return quantidadeTransacoesCanceladasOuEstornadasQueDevemSerContabilizadas;
    }

    /**
     * Calcula a quantidade de transacoes autorizadas de uma transação consolidada.
     *
     * @param transacaoConsolidada a ser verificada
     * @return A quantidade de transacoes autorizadas que devem ser contabilizadas em um ciclo
     */
    private int obterQuantidadeDeTransacoesAutorizadasDoCiclo(TransacaoConsolidada transacaoConsolidada){

        int quantidadeDetransacoesOriginaisDoCiclo = transacaoConsolidada.getAutorizacaoPagamentosStream()
                .filter(AutorizacaoPagamento::estaAutorizado)
                .filter(autorizacaoPagamento -> autorizacaoPagamento.getValorTotal().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.toList()).size();

        // Quanto aos abastecimentos postergados, considera somente aqueles que nao foram postergados novamente (para outro ciclo)
        // Nota: Esse tratamento se faz relevante somente no ultimo processamento do ciclo, que e realizado imediatamente apos o seu fechamento
        // Continuacao Nota: Um abastecimento somente deve ser contabilizado em seu ciclo original e em seu ciclo de postergacao mais recente (nunca em ciclos "intermediarios")
        int quantidadeDetransacoesPostergadasDoCiclo = transacaoConsolidada.getAutorizacoesPagamentoPostergadasStream()
                .filter(AutorizacaoPagamento::estaAutorizado)
                .filter(autorizacaoPagamento -> autorizacaoPagamento.getValorTotal().compareTo(BigDecimal.ZERO) > 0)
                .filter(autorizacaoPagamento -> !autorizacaoPagamentoFoiPostergadaParaOutroCiclo(autorizacaoPagamento, transacaoConsolidada))
                .collect(Collectors.toList()).size();

        return quantidadeDetransacoesOriginaisDoCiclo + quantidadeDetransacoesPostergadasDoCiclo;
    }

    /**
     * Calcula a quantidade de abastecimentos de uma transação consolidada.
     *
     * @param transacaoConsolidada a ser verificada
     * @return A quantidade de transacoes que devem ser contabilizadas em um ciclo
     */
    private int calcularQuantidadeDeTransacoesAssociadasACiclo(TransacaoConsolidada transacaoConsolidada){

        //contabiliza as transacoes autorizadas
        int quantidadeTransacoesAutorizadas = obterQuantidadeDeTransacoesAutorizadasDoCiclo(transacaoConsolidada);
        //contabiliza as transacoes canceladas ou estornadas relevantes
        int quantidadeTransacoesCanceladasOuEstornadasQueDevemSerContabilizadas = obterQuantidadeDeAbastecimentosCanceladosOuEstornadosRelevantesParaOCiclo(transacaoConsolidada);

        return quantidadeTransacoesAutorizadas + quantidadeTransacoesCanceladasOuEstornadasQueDevemSerContabilizadas;
    }

    /**
     * Verifica se uma {@link AutorizacaoPagamento} possui uma {@link TransacaoConsolidada} associada
     *
     * @param autorizacaoPagamento A autorização de pagamento a ser verificada
     * @throws ExcecaoValidacao Caso a autorização ainda não tenha sido processada
     */
    public void verificarAutorizacaoPagamentoConsolidada(AutorizacaoPagamento autorizacaoPagamento) throws ExcecaoValidacao {
        if (autorizacaoPagamento.getTransacaoConsolidada() == null) {
            throw new ExcecaoValidacao(Erro.AUTORIZACAO_NAO_CONSOLIDADA, mensagens.obterMensagem("Erro.AUTORIZACAO_NAO_CONSOLIDADA", autorizacaoPagamento.getId()));
        }
    }

    /**
     * Calcula os prazos de uma transação consolidada.
     * São eles: Prazo de pagamento, prazo de reembolso, prazo de geração de cobrança,
     * prazo de vencimento de cobrança e prazo de emissão de nota fiscal.
     * @param transacaoConsolidada a transação consolidada - ou ciclo - que terá os prazos calculados.
     * @return um objeto TransacaoConsolidadaPrazos com os prazos da transação consolidada.
     */
    public TransacaoConsolidadaPrazos calcularPrazosTransacaoConsolidada(TransacaoConsolidada transacaoConsolidada) {
        TransacaoConsolidadaPrazos prazosConsolidado = new TransacaoConsolidadaPrazos();
        Long prazoPagamento = 0L;
        Long prazoReembolso = 2L;
        Date dataLimiteEmissaoNfe = UtilitarioCalculoData.adicionarDiasData(transacaoConsolidada.getDataFimPeriodo(), 5);
        Boolean possuiPrazoAjuste = Boolean.FALSE;

        if(!transacaoConsolidada.getFrota().isPrePaga()){
            prazoPagamento = transacaoConsolidada.getFrota().getParametroCiclo().getPrazoPagamento();
            prazoReembolso = transacaoConsolidada.getFrota().getParametroCiclo().getPrazoReembolso();

            //Nota: Se o prazo de geração de cobrança for zero, não há prazo de ajuste e as notas só poderão ser emitidas durante o ciclo,
            // com a cobrança sendo gerada após o fechamento deste.
            Date dataGeracaoCobranca = UtilitarioCalculoData.adicionarDiasData(transacaoConsolidada.getDataFimPeriodo(), 1);
            dataLimiteEmissaoNfe = transacaoConsolidada.getDataFimPeriodo();

            //Nota: Ciclos de frotas pré-pagas não terão informações de cobrança, uma vez que elas não geram cobrança.
            Date dataVencimentoCobranca = UtilitarioCalculoData.adicionarDiasData(transacaoConsolidada.getDataFimPeriodo(), prazoPagamento.intValue());
            prazosConsolidado.setDataLimitePagamento(dataVencimentoCobranca);

            PrazoGeracaoCobranca prazoGeracaoCobranca = repositorioPrazoGeracaoCobranca.obterPorPrazoPagamento(prazoPagamento);
            if(prazoGeracaoCobranca.getPrazoGeracaoCobranca() > 0){
                dataGeracaoCobranca = UtilitarioCalculoData.adicionarDiasData(dataVencimentoCobranca, -prazoGeracaoCobranca.getPrazoGeracaoCobranca().intValue());
                dataLimiteEmissaoNfe = UtilitarioCalculoData.adicionarDiasData(dataGeracaoCobranca, -1);

                //Nota: Quando há prazo de ajuste no consolidado, os abastecimentos podem ser ajustados até a data limite de emissão de nota fiscal.
                possuiPrazoAjuste = Boolean.TRUE;
            }
            prazosConsolidado.setDataGeracaoCobranca(dataGeracaoCobranca);
        }

        prazosConsolidado.setPrazoPgto(prazoPagamento);
        prazosConsolidado.setPrazoReembolso(prazoReembolso);
        prazosConsolidado.setDataLimiteEmissaoNfe(dataLimiteEmissaoNfe);
        prazosConsolidado.setPossuiPrazoAjuste(possuiPrazoAjuste);

        return prazosConsolidado;
    }

    /**
     * Gera uma chave identificadora codificada com informações do ciclo
     * @param dataInicioPeriodo A data de início do ciclo
     * @param dataFimPeriodo A data de fim do ciclo
     * @param idFrota O identificador da frota
     * @return A chave codificada
     */
    public String gerarChaveIdentificadoraCodificadaAgrupamentoCiclos(Date dataInicioPeriodo, Date dataFimPeriodo, Long idFrota) {
        String chave = dataInicioPeriodo.getTime() + "|" +
                       dataFimPeriodo.getTime() + "|" +
                       idFrota.toString();
        return UtilitarioCriptografia.toBase64(chave.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Gera uma chave identificadora codificada com informações do ciclo
     * @param consolidado O consolidado a ser usado
     * @return A chave codificada
     */
    public String gerarChaveIdentificadoraCodificadaAgrupamentoCiclos(TransacaoConsolidada consolidado) {
        String chave = consolidado.getDataInicioPeriodo().getTime() + "|" +
                consolidado.getDataFimPeriodo().getTime() + "|" +
                consolidado.getFrota().getId().toString();
        return UtilitarioCriptografia.toBase64(chave.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Decodifica a chave identificadora de agrupamento de ciclos
     * @param chave A chave codificada
     * @return A chave decodificada
     */
    public String decodificarChaveIdentificadoraAgrupamentoCiclos(String chave) {
        return new String(UtilitarioCriptografia.fromBase64(chave));
    }

    /**
     * Divide a chave nos campos utilizados para gerá-la originalmente
     * @param chave A chave decodificada
     * @return Os campos da chave divididos
     */
    public String[] dividirChaveIdentificadoraAgrupamentoCiclos(String chave) {
        return StringUtils.split(chave, ConstantesFormatacao.SEPARADOR_CHAVE_IDENTIFICADORA_AGRUPAMENTO_CICLOS);
    }

    /**
     * Obtém um consolidado pela chave identificadora com informações do ciclo
     * @param chave A chave codificada
     * @return O consolidado encontrado
     */
    public List<TransacaoConsolidada> obterConsolidadosPorChaveIdentificadora(String chave) {
        String chaveDecodificada = decodificarChaveIdentificadoraAgrupamentoCiclos(chave);
        String[] camposChave = dividirChaveIdentificadoraAgrupamentoCiclos(chaveDecodificada);
        Date dataInicioCiclo = obterCampoDataInicioChaveIdentificadora(camposChave);
        Date dataFimCiclo = obterCampoDataFimChaveIdentificadora(camposChave);
        Long idFrota = obterCampoIdFrotaChaveIdentificadora(camposChave);
        return repositorio.obterConsolidadosPorPeriodoEFrota(dataInicioCiclo, dataFimCiclo, idFrota);
    }

    /**
     * Realiza a pesquisa de agrupamento de cobrança passando os filtros de inicio e fim do período e id da frota através de uma chave decodificada
     * @param chave A chave decodificada
     * @return O agrupamento encontrado
     */
    public AgrupamentoTransacaoConsolidadaCobrancaVo obterAgrupamentoCobrancaPorChaveIdentificadora(String chave){
        String chaveDecodificada = decodificarChaveIdentificadoraAgrupamentoCiclos(chave);
        String[] camposChave = dividirChaveIdentificadoraAgrupamentoCiclos(chaveDecodificada);

        FiltroPesquisaFinanceiroVo filtro = new FiltroPesquisaFinanceiroVo();
        filtro.setDe(obterCampoDataInicioChaveIdentificadora(camposChave));
        filtro.setAte(obterCampoDataFimChaveIdentificadora(camposChave));
        filtro.setFrota(new EntidadeVo(obterCampoIdFrotaChaveIdentificadora(camposChave)));
        filtro.setPaginacao(new InformacaoPaginacao());

        ResultadoPaginado<AgrupamentoTransacaoConsolidadaCobrancaVo> agrupamento = repositorio.pesquisarTransacoesPorCobranca(filtro, utilitarioAmbiente.getUsuarioLogado());

        return agrupamento.getRegistros().stream().findFirst().orElse(null);
    }

    /**
     * Obtém o campo data de início do ciclo a partir da chave
     * @param chaveDividida A chave identificadora dividida em suas três partes
     * @return A data de início do ciclo
     */
    public Date obterCampoDataInicioChaveIdentificadora(String[] chaveDividida) {
        return new Date(Long.parseLong(chaveDividida[0]));
    }

    /**
     * Obtém o campo data de fim do ciclo a partir da chave
     * @param chaveDividida A chave identificadora dividida em suas três partes
     * @return A data de fim do ciclo
     */
    public Date obterCampoDataFimChaveIdentificadora(String[] chaveDividida) {
        return new Date(Long.parseLong(chaveDividida[1]));
    }

    /**
     * Obtém o campo id da frota a partir da chave
     * @param chaveDividida A chave identificadora dividida em suas três partes
     * @return O identificador da frota do ciclo
     */
    public Long obterCampoIdFrotaChaveIdentificadora(String[] chaveDividida) {
        return Long.parseLong(chaveDividida[2]);
    }
}