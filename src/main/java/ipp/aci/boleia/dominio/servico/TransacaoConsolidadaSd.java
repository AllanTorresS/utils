package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoDados;
import ipp.aci.boleia.dados.IFrotaDados;
import ipp.aci.boleia.dados.INegociacaoDados;
import ipp.aci.boleia.dados.INotaFiscalDados;
import ipp.aci.boleia.dados.IParametroCicloDados;
import ipp.aci.boleia.dados.ITransacaoConsolidadaDados;
import ipp.aci.boleia.dados.ITransacaoConsolidadaDetalheDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.EmpresaAgregada;
import ipp.aci.boleia.dominio.FrotaPontoVenda;
import ipp.aci.boleia.dominio.Negociacao;
import ipp.aci.boleia.dominio.NotaFiscal;
import ipp.aci.boleia.dominio.ParametroCiclo;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.TransacaoConsolidadaDetalhe;
import ipp.aci.boleia.dominio.Unidade;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.ModalidadePagamento;
import ipp.aci.boleia.dominio.enums.StatusAutorizacao;
import ipp.aci.boleia.dominio.enums.StatusNotaFiscal;
import ipp.aci.boleia.dominio.enums.StatusTransacaoConsolidada;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.AutorizacaoPagamentoOrfaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaTransacaoConsolidadaDetalheVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaTransacaoConsolidadaVo;
import ipp.aci.boleia.dominio.vo.VolumeRealizadoVo;
import ipp.aci.boleia.util.UtilitarioCalculo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.concorrencia.MapeadorLock;
import ipp.aci.boleia.util.concorrencia.Sincronizador;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoRecursoBloqueado;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

import static ipp.aci.boleia.util.UtilitarioCalculo.calcularPorcentagem;
import static ipp.aci.boleia.util.UtilitarioCalculo.somarValoresLista;
import static ipp.aci.boleia.util.UtilitarioCalculoData.adicionarDiasData;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterPrimeiroInstanteDia;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterUltimoInstanteDia;


/**
 * Implementa as regras de negocio relacionadas a entidade TransacaoConsolidada
 */
@Component
public class TransacaoConsolidadaSd {

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
    private IFrotaDados repositorioFrota;

    @Autowired
    private INotaFiscalDados repositorioNF;

    @Autowired
    private NotificacaoUsuarioSd notificacaoUsuarioSd;

    @Autowired
    private ReembolsoSd reembolsoSd;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private IAutorizacaoPagamentoDados repositorioAutorizacaoPgto;

    @Autowired
    @Qualifier("Redis")
    private Sincronizador sincronizador;

    @Value("${concorrencia.lock.trans-consol-auto-pgto.prefixo}")
    private String prefixoLockAutorizacaoPagamento;

    @Value("${concorrencia.lock.trans-consol.prefixo}")
    private String prefixoLockTransacaoConsolidada;

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
                calcularTotalConsolidado(transacaoConsolidada);
                fecharCicloConsolidadoSePossivel(transacaoConsolidada);
                consolidarNotasFiscais(transacaoConsolidada);
                repositorio.armazenar(transacaoConsolidada);
            } finally {
                lock.unlock();
            }
        } else {
            throw new ExcecaoRecursoBloqueado(mensagens.obterMensagem("Erro.TRANS_CONSOL_LOCKADO", idTransacaoConsolidada));
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
                consolidarNotasFiscais(transacaoConsolidada);
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
        return repositorio.obterConsolidadoPorAbastecimento(idAbastecimento);
    }

    /**
     * Atualiza o cálculo do consolidado de notas fiscais a partir de uma Autorizacao de Pagamento
     *
     * @param idAutorizacaoPagamento Autorizacao de Pagamento a ser processada
     */
    public  void atualizarConsolidadoNotaFiscal(Long idAutorizacaoPagamento) {
        TransacaoConsolidada transacaoConsolidada = obterConsolidadoPorAbastecimento(idAutorizacaoPagamento);
        atualizarConsolidadoNotaFiscal(transacaoConsolidada);
    }

    /**
     * Atualiza o cálculo do consolidado de notas fiscais a partir de uma Autorizacao de Pagamento
     *
     * @param transacaoConsolidada Autorizacao de Pagamento a ser processada
     */
    public void atualizarConsolidadoNotaFiscal(TransacaoConsolidada transacaoConsolidada){
        if(transacaoConsolidada !=  null){
            consolidarNotasFiscais(transacaoConsolidada);
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
        List<TransacaoConsolidada> transacoes = new ArrayList<>();
        List<TransacaoConsolidada> transacoes24horas;
        List<TransacaoConsolidada> transacoes72horas;

        // 24 horas
        Date dataVencimento24hrMin = obterPrimeiroInstanteDia(adicionarDiasData(dataAtual,1));
        Date dataVencimento24hrMax = obterUltimoInstanteDia(adicionarDiasData(dataAtual,1));
        transacoes24horas = repositorio.obterConsolidacoesSemNotaFiscalEntreDatas(
                dataVencimento24hrMin, dataVencimento24hrMax);
        transacoes.addAll(transacoes24horas);

        if(!transacoes24horas.isEmpty()) {
            notificacaoUsuarioSd.enviarNotificacaoCiclosAVencerSolucao();
        }

        // 72 horas
        Date dataVencimento72hrMin = obterPrimeiroInstanteDia(adicionarDiasData(dataAtual,3));
        Date dataVencimento72hrMax = obterUltimoInstanteDia(adicionarDiasData(dataAtual,3));
        transacoes72horas = repositorio.obterConsolidacoesSemNotaFiscalEntreDatas(
                dataVencimento72hrMin, dataVencimento72hrMax);
        transacoes.addAll(transacoes72horas);

        if (!transacoes.isEmpty()) {
            notificacaoUsuarioSd.enviarNotificacaoCiclosAVencer(obterRedes(transacoes));
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
        List<TransacaoConsolidada> transacoes = new ArrayList<>();
        List<TransacaoConsolidada> transacoes24horas;
        List<TransacaoConsolidada> transacoes72horas;

        // Vencidas há mais de 24 horas (e menos de 48 horas)
        Date data24HorasAposVencimento = obterPrimeiroInstanteDia(adicionarDiasData(dataAtual,-2));
        Date data48HorasAposVencimento = obterUltimoInstanteDia(adicionarDiasData(dataAtual,-2));
        transacoes24horas = repositorio.obterConsolidacoesSemNotaFiscalEntreDatas(
                data24HorasAposVencimento, data48HorasAposVencimento);
        transacoes.addAll(transacoes24horas);

        // Vencidas há mais de 72 horas (e menos de 96 horas)
        Date data72HorasAposVencimento = obterPrimeiroInstanteDia(adicionarDiasData(dataAtual,-4));
        Date data96HorasAposVencimento = obterUltimoInstanteDia(adicionarDiasData(dataAtual,-4));
        transacoes72horas = repositorio.obterConsolidacoesSemNotaFiscalEntreDatas(
                data72HorasAposVencimento, data96HorasAposVencimento);
        transacoes.addAll(transacoes72horas);

        if (!transacoes.isEmpty()) {
            notificacaoUsuarioSd.enviarNotificacaoCiclosAtrasados(obterRedes(transacoes));
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
            transacoes.forEach(transacao -> atualizarConsolidadoNotaFiscal(transacao));
            notificacaoUsuarioSd.enviarNotificacaoCiclosEncerrados(ontemComecoDoDia);
        }
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
        TransacaoConsolidada maiorConsolidado = consolidados.stream().max(Comparator.comparing(TransacaoConsolidada::getModalidadePagamento)).get();
        vo.setDataInicioCiclo(UtilitarioFormatacaoData.formatarDataDiaMes(maiorConsolidado.getDataInicioPeriodo()));
        vo.setDataFimCiclo(UtilitarioFormatacaoData.formatarDataDiaMes(maiorConsolidado.getDataFimPeriodo()));
        vo.setVolumeCiclo(UtilitarioFormatacao.formatarDecimal(volume.setScale(1, BigDecimal.ROUND_HALF_UP)));

        return vo;
    }

    /**
     * Calcula a data de inicio de um novo ciclo de faturamento a ser aberto, baseado na data vigente
     *
     * @param orfa Transação orfã que terá o ciclo criado.
     * @param idEmpresaAgregada O identificador da empresa agregada.
     * @param idUnidade O identificador da unidade.
     * @param parametroCiclo Os parâmetros de configuração do ciclo.
     * @return A data de início do ciclo.
     */
    public Date calcularDataInicioPeriodo(AutorizacaoPagamentoOrfaVo orfa, Long idEmpresaAgregada, Long idUnidade, ParametroCiclo parametroCiclo) {
        Date dataProcessamento = orfa.getDataProcessamento();
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

        Date dataInicioCandidata = datasInicioNoMes.stream().filter(
                d -> (d.compareTo(dataProcessamento) <= 0) && (ultimoDiaMes - UtilitarioCalculoData.obterCampoData(d, Calendar.DAY_OF_MONTH) > 3)
        ).max(Date::compareTo).get();

        // Verifica se existe um "salto" entre as datas inicial e final, se houver, a data inicial será imediatamente após à data final do ciclo anterior.
        TransacaoConsolidada consolidadoSobreposto = repositorio.obterUltimoConsolidadoAnteriorAoAbastecimentoSemCiclo(orfa.getIdFrota(), orfa.getIdPontoVenda(), idEmpresaAgregada, idUnidade, dataInicioCandidata, orfa.getDataProcessamento());
        if (consolidadoSobreposto != null) {
            dataInicioCandidata = obterPrimeiroInstanteDia(adicionarDiasData(consolidadoSobreposto.getDataFimPeriodo(), 1));
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
        dataFimPeriodo = (ultimoDiaMes - diaFimCalculado <= 3) ? ultimaDataMes : dataFimPeriodo;

        if (UtilitarioCalculoData.obterCampoData(dataFimPeriodo, Calendar.MONTH) > mesVigente ||
                UtilitarioCalculoData.obterCampoData(dataFimPeriodo, Calendar.YEAR) > anoVigente) {
            return UtilitarioCalculoData.obterUltimoDiaMes(dataInicioPeriodo);
        }
        return obterUltimoInstanteDia(dataFimPeriodo);
    }

    /**
     * Calcula a data de emissao da nota fiscal de um ciclo de faturamento, dada a data de encerramento do ciclo
     * @param dataFimPeriodo A data de encerramento do ciclo
     * @param parametroCiclo O parametro de configuracao do ciclo
     * @return A data e emissao de notas fiscais para o ciclo em questao
     */
    public Date calcularDataPrazoEmissaoNotaFiscalCicloPosPago(Date dataFimPeriodo, ParametroCiclo parametroCiclo) {
        return new DateTime(dataFimPeriodo).plusDays(parametroCiclo.getPrazoPagamento().intValue()).toDate();
    }

    /**
     * Cria uma transacao consolidada para o VO informado, adidionando-a na lista <code>listaTC</code> informada como parametro
     *
     * @param orfa Um VO que representa uma transacao consolidada a ser criada
     * @param frotaPtov A frota ponto venda para associacao
     * @param empresaAgregada A empresa agregada, caso exija nota fiscal
     * @param unidade A Unidade, caso exija nota fiscal
     * @param prePago Se true, indica que o ciclo da transacao consolidada deve seguir as regras da modalidade pre-pago.
     * @return A transacao consolidada
     */
    public TransacaoConsolidada criarTransacaoConsolidada(AutorizacaoPagamentoOrfaVo orfa, FrotaPontoVenda frotaPtov, EmpresaAgregada empresaAgregada, Unidade unidade, boolean prePago) {

        TransacaoConsolidada tc = new TransacaoConsolidada();
        /**
         * Nota: Consolidação de autorização de pagamento PRE-PAGO é diária.
         */
        Date dataInicio = UtilitarioCalculoData.obterPrimeiroInstanteDia(orfa.getDataProcessamento());
        Date dataFim = UtilitarioCalculoData.obterUltimoInstanteDia(orfa.getDataProcessamento());
        /**
         * Nota: Prazo de 5 dias será utilizado apenas para autorizações de pagamento pré-pago.
         */
        Date dataEmissaoNfe = UtilitarioCalculoData.adicionarDiasData(dataFim, 5);
        Long prazoPagamentoDias = 0L;
        Long prazoReembolsoDias = 2L;
        Long idEmpresaAgregada = empresaAgregada != null ? empresaAgregada.getId() : null;
        Long idUnidade = unidade != null ? unidade.getId() : null;

        if(!prePago) {
            // Carrega os ciclos de pagamento, ex.: Ciclos de 7 em 7 dias, de 15 em 15 dias e etc.
            ParametroCiclo parametroCiclo = parametroCicloDados.obterParametroCicloDaFrota(orfa.getIdFrota());
            // Calcula a data de início dentro dos ciclos criados.
            dataInicio = calcularDataInicioPeriodo(orfa, idEmpresaAgregada, idUnidade, parametroCiclo);

            // Calcula a data fim com relação à data de início e o parâmetro de ciclo. Porém, se o mês termina antes de terminar o ciclo, então a dataFinal será o fim do mês.
            dataFim = calcularDataFimPeriodo(dataInicio, parametroCiclo);
            dataEmissaoNfe = calcularDataPrazoEmissaoNotaFiscalCicloPosPago(dataFim, parametroCiclo);
            prazoPagamentoDias = parametroCiclo.getPrazoPagamento();
            prazoReembolsoDias = parametroCiclo.getPrazoReembolsoDias();
        }

        tc.setModalidadePagamento(prePago ? ModalidadePagamento.PRE_PAGO.getValue() : ModalidadePagamento.POS_PAGO.getValue());
        tc.setFrotaPtov(frotaPtov);
        if(empresaAgregada != null) {
            tc.setEmpresaAgregada(empresaAgregada);
        } else if(unidade != null) {
            tc.setUnidade(unidade);
        }
        tc.setDataInicioPeriodo(dataInicio);
        tc.setDataFimPeriodo(dataFim);
        tc.setDataPrazoEmissaoNfe(dataEmissaoNfe);
        tc.setPrazoPagtoDias(prazoPagamentoDias);
        tc.setPrazoReembolsoDias(prazoReembolsoDias);
        tc.setStatusConsolidacao(StatusTransacaoConsolidada.ABERTA.getValue());
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
     * Cria a chave do lock de autorização de pagamento para criação de ciclos
     *
     * @param idAutorizacaoPagamento
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
    private void atualizarStatusEmissaoNota(TransacaoConsolidada transacaoConsolidada, List<AutorizacaoPagamento> listaAutorizacaoPagamento) {
        Long abastecimentosComNotaEmitida = listaAutorizacaoPagamento.stream()
                .filter(AutorizacaoPagamento::notaFiscalEstaEmitido)
                .count();
        Long abastecimentosComJustificativa = listaAutorizacaoPagamento.stream()
                .filter(AutorizacaoPagamento::notaFiscalEstaJustificada)
                .count();

        boolean cicloFechado = transacaoConsolidada.esta(StatusTransacaoConsolidada.FECHADA);
        boolean valorNotasMaiorZero = transacaoConsolidada.getValorTotalNotaFiscal().compareTo(BigDecimal.ZERO) > 0;
        boolean valorNotasZero = transacaoConsolidada.getValorTotalNotaFiscal().compareTo(BigDecimal.ZERO) == 0;
        boolean todosAbastPossuemNota = transacaoConsolidada.getQuantidadeAbastecimentos().equals(abastecimentosComNotaEmitida + abastecimentosComJustificativa);
        boolean totalCicloMenorIgualZero = transacaoConsolidada.getValorTotal().compareTo(BigDecimal.ZERO) <= 0;

        if(cicloFechado) {
            if (!transacaoConsolidada.exigeEmissaoNF() || (valorNotasMaiorZero && todosAbastPossuemNota) || (valorNotasZero && totalCicloMenorIgualZero)) {
                transacaoConsolidada.setStatusNotaFiscal(StatusNotaFiscal.EMITIDA.getValue());
            } else {
                transacaoConsolidada.setStatusNotaFiscal(StatusNotaFiscal.PENDENTE.getValue());
            }
        } else {
            transacaoConsolidada.setStatusNotaFiscal(StatusNotaFiscal.PENDENTE.getValue());
        }
        if (transacaoConsolidada.getReembolso() != null) {
            reembolsoSd.atualizarStatusReembolso(transacaoConsolidada.getReembolso());
        }
    }

    /**
     * Calcula o valor total da consolidação das autorizações de pagamento
     * @param transacaoConsolidada A consolidação de transações a serem agrupadas
     * @return O somatório dos valores totais das autorizaçãoes de pagamento que compõe a consolidação
     */
    private BigDecimal computarValorTotalDaConsolidacao(TransacaoConsolidada transacaoConsolidada) {
        return somarValoresLista(transacaoConsolidada.getAutorizacaoPagamentosStream()
                .filter(AutorizacaoPagamento::estaAutorizadaOuCancelada)
                .map(AutorizacaoPagamento::getValorTotal)
        );
    }

    /**
     * Calcula o valor total de descontos das autorizações de pagamento na consolidação
     * @param transacaoConsolidada A consolidação de transações a serem agrupadas
     * @return O somatório dos descontos nas autorizaçãoes de pagamento que compõe a consolidação
     */
    private BigDecimal computarValorDescontoTotalDaConsolidacao(TransacaoConsolidada transacaoConsolidada) {
        return somarValoresLista( transacaoConsolidada.getAutorizacaoPagamentosStream()
                .filter(AutorizacaoPagamento::estaAutorizadaOuCancelada)
                .map(AutorizacaoPagamento::getValorDescontoTotal)
        );
    }

    /**
     * Obtem as autorizações que devem ser consolidadas para valores da nota fiscal
     * Nota: As autorizações de pagamento que estão autorizadas e com valores acima de zero.
     *
     * @param transacaoConsolidada a transacao consolidada a partir da qual sera buscado a autorizacao de pagamento
     * @return as autorizacoes de pagamento encontradas
     */
    private List<AutorizacaoPagamento> obterAutorizacaoPagamentoParaNotaFiscal(TransacaoConsolidada transacaoConsolidada) {
        return transacaoConsolidada.getAutorizacaoPagamentosStream()
                .filter(autorizacaoPagamento -> autorizacaoPagamento.getStatus().equals(StatusAutorizacao.AUTORIZADO.getValue()))
                .filter(autorizacaoPagamento -> autorizacaoPagamento.getValorTotal().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList());
    }

    /**
     * Totaliza o valor de um ciclo de faturamento, buscando os abastecimentos englobados por ele.
     *
     * @param transacaoConsolidada O ciclo de faturamento (transacao consolidada)
     */
    private void calcularTotalConsolidado(TransacaoConsolidada transacaoConsolidada) {
        PontoDeVenda pontoDeVenda = transacaoConsolidada.getPontoVenda();

        // Atualiza o valor total da consolidação
        transacaoConsolidada.setValorTotal(computarValorTotalDaConsolidacao(transacaoConsolidada));
        transacaoConsolidada.setValorDescontoAbastecimentos(computarValorDescontoTotalDaConsolidacao(transacaoConsolidada));

        BigDecimal mdr = MDR_PADRAO_SOLUCAO;
        Negociacao negociacao = repositorioNegociacao.obterPorFrotaPontoVenda(transacaoConsolidada.getFrotaPtov());
        if (negociacao != null && negociacao.getMdr() != null && negociacao.getMdr().compareTo(BigDecimal.ZERO) >= 0) {
            mdr = negociacao.getMdr();
        }else if(pontoDeVenda.getMdr() != null && pontoDeVenda.getMdr().compareTo(BigDecimal.ZERO) >= 0){
            mdr = pontoDeVenda.getMdr();
        }
        BigDecimal desconto = calcularPorcentagem(transacaoConsolidada.getValorTotal(), mdr).setScale(2, BigDecimal.ROUND_HALF_UP);
        transacaoConsolidada.setValorDesconto(desconto);
        transacaoConsolidada.setValorReembolso(transacaoConsolidada.getValorTotal().subtract(desconto));
        transacaoConsolidada.setMdr(mdr);
    }

    /**
     * Fechar o consolidado, reprocessando o mesmo
     *
     * @param consolidado Transacao consolidada a ser processada
     *
     */
    public void fecharCicloConsolidadoAbertos(TransacaoConsolidada consolidado) {
        fecharCicloConsolidadoSePossivel(consolidado);
        consolidarNotasFiscais(consolidado);
        repositorio.armazenar(consolidado);
    }

    /**
     * Fechar o Consolidado quando a data de Fim for maior que hoje.
     * @param transacaoConsolidada O ciclo de faturamento (transacao consolidada)
     */
    private void fecharCicloConsolidadoSePossivel(TransacaoConsolidada transacaoConsolidada) {
        Date fimPeriodo = obterUltimoInstanteDia(transacaoConsolidada.getDataFimPeriodo());
        boolean periodoEncerrado = fimPeriodo.compareTo(utilitarioAmbiente.buscarDataAmbiente()) < 0;
        ajustarParametroCiclo(transacaoConsolidada, periodoEncerrado);
        transacaoConsolidada.setStatusConsolidacao(periodoEncerrado ? StatusTransacaoConsolidada.FECHADA.getValue() : StatusTransacaoConsolidada.ABERTA.getValue());
    }

    /**
     * Verifica se houve agendamento de alteração de ciclo da frota e atualiza.
     *
     * @param transacaoConsolidada TransacaoConsolidada a ser processada
     * @param periodoEncerrado     Booleano que representa se o periodo esta encerrado (true) ou aberto (false)
     */
    private void ajustarParametroCiclo(TransacaoConsolidada transacaoConsolidada, boolean periodoEncerrado) {
        if (periodoEncerrado && transacaoConsolidada.getFrota().getNovoParametroCiclo() != null) {
            transacaoConsolidada.getFrota().setParametroCiclo(transacaoConsolidada.getFrota().getNovoParametroCiclo());
            transacaoConsolidada.getFrota().setNovoParametroCiclo(null);
            transacaoConsolidada.getFrota().setDataAlteracaoCiclo(null);
            repositorioFrota.armazenar(transacaoConsolidada.getFrota());
        }
    }

    /**
     * Realiza o cálculo do consolidado do valor de emissão de notas fiscais
     *
     * @param transacaoConsolidada TransacaoConsolidada a ser processada
     */
    private void consolidarNotasFiscais(TransacaoConsolidada transacaoConsolidada){
        if(transacaoConsolidada.getValorTotal() != null){
            List<AutorizacaoPagamento> listaAutorizacaoPagamento = obterAutorizacaoPagamentoParaNotaFiscal(transacaoConsolidada);
            transacaoConsolidada.setValorTotalNotaFiscal(
                    UtilitarioCalculo.somarValoresLista(listaAutorizacaoPagamento, AutorizacaoPagamento::getValorTotal)
                            .setScale(2, BigDecimal.ROUND_HALF_UP)
            );
            transacaoConsolidada.setQuantidadeAbastecimentos((long) listaAutorizacaoPagamento.size());

            List<NotaFiscal> notas = repositorioNF.obterNotaDeVariosAbastecimentos(listaAutorizacaoPagamento.stream().map(AutorizacaoPagamento::getId).collect(Collectors.toList()));
            BigDecimal valoresEmitidos = UtilitarioCalculo.somarValoresLista(notas, NotaFiscal::getValorTotal);
            transacaoConsolidada.setValorEmitidoNotaFiscal(valoresEmitidos.setScale(2,BigDecimal.ROUND_HALF_UP));
            BigDecimal valorDescontoNota = UtilitarioCalculo.somarValoresLista(listaAutorizacaoPagamento, AutorizacaoPagamento::getValorDescontoTotal)
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
            transacaoConsolidada.setValorDescontoNotaFiscal(valorDescontoNota);
            atualizarStatusEmissaoNota(transacaoConsolidada, listaAutorizacaoPagamento);
        }
    }

    /**
     * Realiza uma consulta das transações consolidadas de acordo com o filtro informado.
     *
     * @param filtro        O filtro da busca
     * @param usuarioLogado usuario logado que solicita a pesquisa
     * @return Uma lista transações consolidadas
     */
    public ResultadoPaginado<TransacaoConsolidada> pesquisar(FiltroPesquisaTransacaoConsolidadaVo filtro, Usuario usuarioLogado) {
        ResultadoPaginado<TransacaoConsolidada> transacoesConsolidadas = repositorio.pesquisar(filtro, usuarioLogado);
        transacoesConsolidadas.getRegistros().forEach(this::atualizarContagemAbastecimentosSeCicloAberto);
        return transacoesConsolidadas;
    }

    /**
     * Obtém transacoes consolidadas pelo identificador
     *
     * @param id identificador da transacap
     * @return transacao consolidada
     */
    public TransacaoConsolidada obterTransacaoConsolidadaPorId(Long id) {
        TransacaoConsolidada transacao = repositorio.obterPorId(id);
        atualizarContagemAbastecimentosSeCicloAberto(transacao);
        return transacao;
    }

    /**
     * Verifica se a Consolidação está aberta, para obter o número correto de abastecimentos
     *
     * @param transacao a ser verificada
     */
    private void atualizarContagemAbastecimentosSeCicloAberto(TransacaoConsolidada transacao) {
        if (StatusTransacaoConsolidada.ABERTA.getValue().equals(transacao.getStatusConsolidacao())) {
            List<AutorizacaoPagamento> autorizacoes = repositorioAutorizacaoPgto.obterAbastecimentosParaNotaFiscal(
                    transacao.getFrota().getId(),
                    transacao.getPontoVenda().getId(),
                    transacao.getEmpresaAgregada(),
                    transacao.getUnidade(),
                    transacao.getDataInicioPeriodo(),
                    transacao.getDataFimPeriodo(),
                    ModalidadePagamento.PRE_PAGO.getValue().equals(transacao.getModalidadePagamento()));
            transacao.setQuantidadeAbastecimentos((long) autorizacoes.size());
        }
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
}
