package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoDados;
import ipp.aci.boleia.dados.ICicloRepasseDados;
import ipp.aci.boleia.dados.IEntidadeRepasseDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.CicloRepasse;
import ipp.aci.boleia.dominio.ConfiguracaoRepasse;
import ipp.aci.boleia.dominio.EntidadeRepasse;
import ipp.aci.boleia.dominio.ParametroCiclo;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.enums.StatusCicloRepasse;
import ipp.aci.boleia.dominio.enums.StatusTransacaoConsolidada;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaCicloRepasseVo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.concorrencia.MapeadorLock;
import ipp.aci.boleia.util.concorrencia.Sincronizador;
import ipp.aci.boleia.util.excecao.ExcecaoRecursoBloqueado;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

import static ipp.aci.boleia.util.UtilitarioCalculo.calcularPorcentagem;
import static ipp.aci.boleia.util.UtilitarioCalculo.somarValoresLista;
import static ipp.aci.boleia.util.UtilitarioCalculoData.adicionarDiasData;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterPrimeiroInstanteDia;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterUltimoInstanteDia;

/**
 * Implementa as regras de negocio relacionadas a entidade CicloRepasse
 */
@Component
public class CicloRepasseSd {

    @Autowired
    @Qualifier("Redis")
    private Sincronizador sincronizador;

    @Value("${concorrencia.lock.ciclo-repasse-auto-pgto.prefixo}")
    private String prefixoLockAutorizacaoPagamento;

    @Value("${concorrencia.lock.ciclo-repasse.prefixo}")
    private String prefixoLockCicloRepasse;

    private MapeadorLock<Long> mapeadorLockAutorizacaoPagamento;
    private MapeadorLock<Long> mapeadorLockCicloRepasse;

    @Autowired
    private ICicloRepasseDados repositorio;

    @Autowired
    private IEntidadeRepasseDados repositorioEntidadeRepasse;

    @Autowired
    private IAutorizacaoPagamentoDados repositorioAutorizacaoPagamento;

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Configura o monitor de autorização de pagamento
     */
    @PostConstruct
    public void inicializador() {
        mapeadorLockAutorizacaoPagamento = new MapeadorLock<>(sincronizador, this::construirChaveLockAutorizacaoPagamento);
        mapeadorLockCicloRepasse = new MapeadorLock<>(sincronizador, this::construirChaveLockCicloRepasse);
    }

    /**
     * Cria a chave do lock de autorização de pagamento para criação de ciclos
     *
     * @param idAutorizacaoPagamento o identificador da AutorizacaoPagamento
     * @return o lock criado
     */
    private String construirChaveLockAutorizacaoPagamento(Long idAutorizacaoPagamento) {
        return String.format("%s:%s", prefixoLockAutorizacaoPagamento, idAutorizacaoPagamento.toString());
    }

    /**
     * Cria a chave do lock de ciclo de repasse para o seu processamento
     *
     * @param idCicloRepasse o identificador do Ciclo de Repasse
     * @return o lock criado
     */
    private String construirChaveLockCicloRepasse(Long idCicloRepasse) {
        return String.format("%s:%s", prefixoLockCicloRepasse, idCicloRepasse.toString());
    }

    /**
     * Cria um lock para exclusão mútua de um ciclo de repasse em processamento
     *
     * @param idCicloRepasse Identificador da transação consolidada
     * @return O Lock criado para a transação consolidada.
     */
    public Lock getLockCicloRepasse(Long idCicloRepasse) {
        return mapeadorLockCicloRepasse.getLock(idCicloRepasse);
    }

    /**
     * Cria um lock para exclusão mútua de autorização de pagamento em processamento
     *
     * @param idAutorizacaoPagamento Identificador da autorização de pagamento
     * @return O Lock criado
     */
    public Lock getLockAutorizacaoPagamento(Long idAutorizacaoPagamento) {
        return mapeadorLockAutorizacaoPagamento.getLock(idAutorizacaoPagamento);
    }

    /**
     * Cria um novo {@link CicloRepasse} a partir de uma {@link AutorizacaoPagamento}
     *
     * @param autorizacaoPagamento A autorização de pagamento
     * @return O novo ciclo de repasse criado
     */
    public CicloRepasse criarCicloRepasse(AutorizacaoPagamento autorizacaoPagamento){
        CicloRepasse novoCiclo = new CicloRepasse();

        EntidadeRepasse entidadeRepasse = repositorioEntidadeRepasse.obtemEntidadeDeRepassePadrao();
        ConfiguracaoRepasse configuracaoRepasse = entidadeRepasse.getConfiguracaoRepasse();

        CicloRepasse ultimoCicloAnteriorAoAbastecimento = repositorio.obterUltimoCicloRepasseAnteriorAData(autorizacaoPagamento.getDataProcessamento(), entidadeRepasse.getId(), autorizacaoPagamento.getPontoVenda().getId());

        Date dataInicio;
        if(ultimoCicloAnteriorAoAbastecimento != null && ultimoCicloAnteriorAoAbastecimento.getDataFim() != null){
            dataInicio = obterPrimeiroInstanteDia(adicionarDiasData(ultimoCicloAnteriorAoAbastecimento.getDataFim(),1));
        }
        else{
            dataInicio = calcularDataInicioCiclo(autorizacaoPagamento.getDataProcessamento(), configuracaoRepasse.getParametroCiclo());
        }

        Date dataFim = calcularDataFimCiclo(dataInicio, configuracaoRepasse.getParametroCiclo());

        novoCiclo.setConfiguracaoRepasse(configuracaoRepasse);
        novoCiclo.setDataInicio(dataInicio);
        novoCiclo.setDataFim(dataFim);
        novoCiclo.setStatus(StatusCicloRepasse.EM_ABERTO.getValue());

        novoCiclo.setDataVencimento(adicionarDiasData(dataFim, configuracaoRepasse.getParametroCiclo().getPrazoPagamento().intValue()));

        BigDecimal valorCredito = (ultimoCicloAnteriorAoAbastecimento != null && ultimoCicloAnteriorAoAbastecimento.getValorNominalRepasse().compareTo(BigDecimal.ZERO) < 0)? ultimoCicloAnteriorAoAbastecimento.getValorNominalRepasse().negate():null;
        novoCiclo.setValorCredito(valorCredito);

        BigDecimal valorPercentualRepasse = configuracaoRepasse.getValorPercentualRepasse();
        BigDecimal valorNominalRepasse = calcularPorcentagem(autorizacaoPagamento.getValorTotal(), valorPercentualRepasse);

        novoCiclo.setValorTotal(autorizacaoPagamento.getValorTotal());
        novoCiclo.setValorNominalRepasse(valorNominalRepasse);
        novoCiclo.setValorPercentualRepasse(valorPercentualRepasse);

        novoCiclo.setPontoDeVenda(autorizacaoPagamento.getPontoVenda());

        repositorio.armazenar(novoCiclo);

        return novoCiclo;
    }

    /**
     * Cria um novo {@link CicloRepasse} para conter os abastecimentos inválidos para envio ao Jde no ciclo repasse selecionado para envio.
     *
     * @param autorizacoesPagamento abastecimentos pendentes de emissão ou pertencentes a um ciclo em ajuste na data de envio.
     * @param pontoDeVenda o ponto de venda do ciclo de repasse
     * @param dataEnvioJde a data em que os ciclos de repasse serão enviados ao JDE.
     * @return retorna o ciclo de repasse criado.
     */
    public CicloRepasse criarCicloRepassePostergado(List<AutorizacaoPagamento> autorizacoesPagamento, PontoDeVenda pontoDeVenda, Date dataEnvioJde){
        CicloRepasse novoCiclo = new CicloRepasse();
        EntidadeRepasse entidadeRepasse = repositorioEntidadeRepasse.obtemEntidadeDeRepassePadrao();
        ConfiguracaoRepasse configuracaoRepasse = entidadeRepasse.getConfiguracaoRepasse();

        CicloRepasse ultimoCicloAnteriorAoAbastecimento = repositorio.obterUltimoCicloRepasseAnteriorAData(dataEnvioJde, entidadeRepasse.getId(), pontoDeVenda.getId());
        Date dataInicio = calcularDataInicioCiclo(dataEnvioJde, configuracaoRepasse.getParametroCiclo());
        Date dataFim = calcularDataFimCiclo(dataInicio, configuracaoRepasse.getParametroCiclo());

        novoCiclo.setConfiguracaoRepasse(configuracaoRepasse);
        novoCiclo.setDataInicio(dataInicio);
        novoCiclo.setDataFim(dataFim);
        novoCiclo.setStatus(StatusCicloRepasse.EM_ABERTO.getValue());

        novoCiclo.setDataVencimento(adicionarDiasData(dataFim, configuracaoRepasse.getParametroCiclo().getPrazoPagamento().intValue()));
        BigDecimal valorCredito = (ultimoCicloAnteriorAoAbastecimento != null && ultimoCicloAnteriorAoAbastecimento.getValorNominalRepasse().compareTo(BigDecimal.ZERO) < 0)? ultimoCicloAnteriorAoAbastecimento.getValorNominalRepasse().negate():null;
        novoCiclo.setValorCredito(valorCredito);
        BigDecimal valorPercentualRepasse = configuracaoRepasse.getValorPercentualRepasse();
        novoCiclo.setPontoDeVenda(pontoDeVenda);

        BigDecimal valorTotalAbastecimentos = autorizacoesPagamento.stream().map(AutorizacaoPagamento::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal valorNominalRepasse = calcularPorcentagem(valorTotalAbastecimentos, valorPercentualRepasse);
        novoCiclo.setValorTotal(valorTotalAbastecimentos);
        novoCiclo.setValorNominalRepasse(valorNominalRepasse);
        novoCiclo.setValorPercentualRepasse(valorPercentualRepasse);
        repositorio.armazenar(novoCiclo);

        return novoCiclo;
    }

    /**
     * É feita a validação dos ciclos de repasse que serão enviados ao Jde.
     *
     * @param repasses a lista de repasses que será validada antes do envio ao Jde.
     * @return a lista de ciclos de repasses validada, apta a seguir no processo de envio ao Jde.
     */
    public List<CicloRepasse> verificarPostergacaoCicloRepasse(List<CicloRepasse> repasses){
        List<CicloRepasse> repassesValidados = new ArrayList<>();

        for(CicloRepasse cicloRepasse : repasses){
            CicloRepasse repasseValidado = validarAbastecimentosCicloRepasse(cicloRepasse, ambiente.buscarDataAmbiente());
            if(repasseValidado != null){
                repassesValidados.add(repasseValidado);
            }
        }
        return repassesValidados;
    }

    /**
     * É feita a validação dos abastecimentos presentes em um ciclo de repasse.
     * Os abastecimentos pendentes de emissão, ou que não estejam em uma transação consolidada fechada,
     * terão os seus ciclos de repasse alterados e serão enviados para ciclos de repasse futuros.
     *
     * @param cicloRepasse o ciclo repasse que será validado.
     * @param dataEnvioJde a data em que os ciclos de repasse serão enviados ao JDE.
     * @return o ciclo repasse atualizado ou nulo, caso todos os seus abastecimentos tenham o repasse postergado.
     */
    private CicloRepasse validarAbastecimentosCicloRepasse(CicloRepasse cicloRepasse, Date dataEnvioJde){
        List<AutorizacaoPagamento> autorizacoesPagamentoRepasse = cicloRepasse.getAutorizacaoPagamentos();
        List<AutorizacaoPagamento> autorizacoesPagamentosParaPostergacaoCicloRepasse = autorizacoesPagamentoRepasse.stream()
                .filter(autorizacaoPagamento -> autorizacaoPagamento.isPendenteEmissaoNF(false) ||
                        (!autorizacaoPagamento.isPendenteEmissaoNF(false) &&
                                !autorizacaoPagamento.getTransacaoConsolidada().getStatusConsolidacao().equals(StatusTransacaoConsolidada.FECHADA.getValue())))
                .collect(Collectors.toList());

        EntidadeRepasse entidadeRepasse = repositorioEntidadeRepasse.obtemEntidadeDeRepassePadrao();
        CicloRepasse novoCicloRepasse = repositorio.obterCicloRepassePorDataEEntidade(dataEnvioJde, entidadeRepasse.getId(), cicloRepasse.getPontoDeVenda().getId());

        if(novoCicloRepasse == null){
            novoCicloRepasse = criarCicloRepassePostergado(autorizacoesPagamentosParaPostergacaoCicloRepasse, cicloRepasse.getPontoDeVenda(), dataEnvioJde);
        }

        //Atualiza o ciclo repasse dos abastecimentos pendentes de emissão ou cm consolidado em ajuste.
        for(AutorizacaoPagamento autorizacaoPagamento : autorizacoesPagamentosParaPostergacaoCicloRepasse){
            autorizacaoPagamento.setCicloRepasse(novoCicloRepasse);
            autorizacaoPagamento.setCicloRepasseOriginal(cicloRepasse);
            autorizacaoPagamento.setDataPostergacaoRepasse(dataEnvioJde);
            repositorioAutorizacaoPagamento.armazenar(autorizacaoPagamento);
        }

        //Atualiza a lista de abastecimentos do ciclo repasse informado.
        autorizacoesPagamentoRepasse.removeIf(autorizacaoPagamento -> !autorizacaoPagamento.getCicloRepasse().getId().equals(cicloRepasse.getId()));
        cicloRepasse.setAutorizacaoPagamentos(autorizacoesPagamentoRepasse);

        //Atualiza os valores do ciclo repasse informado.
        BigDecimal valorPostergado = novoCicloRepasse.getValorTotal();
        atualizarValoresCicloRepasseOriginal(cicloRepasse, valorPostergado);

        //É retornado nulo se todos os abastecimentos do ciclo tiveram o seu repasse postergado.
        return cicloRepasse.getValorTotal().compareTo(BigDecimal.ZERO) == 0 ? null : cicloRepasse;
    }

    /**
     * Atualiza os valores do ciclo de repasse informado após desvinculação dos abastecimentos pendentes de emissão ou
     * presentes em uma transação consolidada que não esteja fechada.
     *
     * @param cicloRepasse o ciclo de repasse original.
     * @param valorPostergado o valor que será postergado para o próximo ciclo de repasse.
     * @return retorna o ciclo de repasse original com os seus valores atualizados.
     */
    private CicloRepasse atualizarValoresCicloRepasseOriginal(CicloRepasse cicloRepasse, BigDecimal valorPostergado) {
        BigDecimal valorTotalRepasse = cicloRepasse.getValorTotal().subtract(valorPostergado);
        cicloRepasse.setValorTotal(valorTotalRepasse);

        BigDecimal valorNominalRepasse = calcularPorcentagem(valorTotalRepasse, cicloRepasse.getValorPercentualRepasse());
        cicloRepasse.setValorNominalRepasse(valorNominalRepasse);
        repositorio.armazenar(cicloRepasse);
        return cicloRepasse;
    }

    /**
     * Calcula a data de inicio de um novo ciclo de repasse a ser aberto, baseado na data vigente
     *
     * @param dataVigente A data vigente
     * @param parametroCiclo Os parametros de configuracao do ciclo
     * @return A data de inicio do ciclo
     */
    private Date calcularDataInicioCiclo(Date dataVigente, ParametroCiclo parametroCiclo) {
        Date diaInicioCiclo = UtilitarioCalculoData.obterPrimeiroDiaMes(dataVigente);
        Date ultimoDiaMes = UtilitarioCalculoData.obterUltimoDiaMes(dataVigente);
        Integer quantidadeDiasCiclo = parametroCiclo.getPrazoCiclo().intValue();

        List<Date> datasInicioNoMes = new ArrayList<>();
        datasInicioNoMes.add(diaInicioCiclo);

        while(diaInicioCiclo.before(ultimoDiaMes)){
            diaInicioCiclo = adicionarDiasData(diaInicioCiclo, quantidadeDiasCiclo);
            datasInicioNoMes.add(diaInicioCiclo);
        }

        return datasInicioNoMes.stream().filter(d -> d.compareTo(dataVigente) <= 0).max(Date::compareTo).get();
    }

    /**
     * Calcula a data de encerramento de um ciclo de repasse a partir de sua data de inicio e dos
     * parametros de configuracao do ciclo
     * @param dataInicioPeriodo A data de inicio do ciclo
     * @param parametroCiclo Os parametros de configuracao do ciclo
     * @return A data de encerramento do ciclo
     */
    private Date calcularDataFimCiclo(Date dataInicioPeriodo, ParametroCiclo parametroCiclo) {
        Integer anoVigente = UtilitarioCalculoData.obterCampoData(dataInicioPeriodo, Calendar.YEAR);
        Integer mesVigente = UtilitarioCalculoData.obterCampoData(dataInicioPeriodo, Calendar.MONTH);
        Integer diaVigente = UtilitarioCalculoData.obterCampoData(dataInicioPeriodo, Calendar.DAY_OF_MONTH);
        Integer prazoCiclo = parametroCiclo.getPrazoCiclo().intValue();

        int diasParaAdicionar = (diaVigente <= prazoCiclo) ? prazoCiclo - diaVigente : prazoCiclo - 1;
        Date dataFimPeriodo = adicionarDiasData(dataInicioPeriodo, diasParaAdicionar);

        if (UtilitarioCalculoData.obterCampoData(dataFimPeriodo, Calendar.MONTH) > mesVigente ||
                UtilitarioCalculoData.obterCampoData(dataFimPeriodo, Calendar.YEAR) > anoVigente) {
            return UtilitarioCalculoData.obterUltimoDiaMes(dataInicioPeriodo);
        }
        return obterUltimoInstanteDia(dataFimPeriodo);
    }

    /**
     * Atualiza um ciclo de repasse vindo da fila de processamento
     * @param idCicloRepasse O identificador do ciclo vindo da fila
     * @throws ExcecaoRecursoBloqueado quando possuir registro bloqueado por concorrencia.
     */
    public void processarCicloRepasse(Long idCicloRepasse) throws ExcecaoRecursoBloqueado {
        Lock lock = getLockCicloRepasse(idCicloRepasse);
        if(lock.tryLock()) {
            try {
                CicloRepasse cicloRepasse = repositorio.obterPorId(idCicloRepasse);
                cicloRepasse.setValorTotal(computarValorTotalDoCiclo(cicloRepasse));
                cicloRepasse.setValorNominalRepasse(computarValorRepasse(cicloRepasse));

                repositorio.armazenar(cicloRepasse);
            } finally {
                lock.unlock();
            }
        } else {
            throw new ExcecaoRecursoBloqueado(mensagens.obterMensagem("Erro.CICLO_REPASSE_LOCKADO", idCicloRepasse));
        }
    }

    /**
     * Calcula o valor total das autorizações de pagamento dentro de um ciclo de repasse
     * @param cicloRepasse O ciclo de repasse
     * @return O valor total dentro de um ciclo
     */
    private BigDecimal computarValorTotalDoCiclo(CicloRepasse cicloRepasse){
        return somarValoresLista(cicloRepasse.getAutorizacaoPagamentos().stream()
                .filter(AutorizacaoPagamento::estaAutorizadaOuCancelada)
                .map(AutorizacaoPagamento::getValorTotal));
    }

    /**
     * Calcula o valor a ser repassado dentro e um ciclo
     * @param cicloRepasse O ciclo de repasse
     * @return O valor total a ser repassado
     */
    private BigDecimal computarValorRepasse(CicloRepasse cicloRepasse){
        BigDecimal valorRepasse = somarValoresLista(cicloRepasse.getAutorizacaoPagamentos().stream()
                .filter(AutorizacaoPagamento::estaAutorizadaOuCancelada)
                .map(a -> calcularPorcentagem(a.getValorTotal(), cicloRepasse.getValorPercentualRepasse()) ));

        final Boolean CICLO_POSSUI_CREDITO = cicloRepasse.getValorCredito() != null && cicloRepasse.getValorCredito().compareTo(BigDecimal.ZERO) != 0;

        return CICLO_POSSUI_CREDITO? valorRepasse.subtract(cicloRepasse.getValorCredito()):valorRepasse;
    }

    /**
     * Obtem lista de Informações de um Repasse que serão exibidas nas linhas da tabela de exportação
     * de acordo com o filtro informado.
     * @param filtro O filtro da ultima busca
     * @return Uma lista de ciclos de repasse para exportação
     */
    public ResultadoPaginado<CicloRepasse> pesquisarRepasses(FiltroPesquisaCicloRepasseVo filtro) {
        return repositorio.pesquisarRepasse(filtro);
    }
}
