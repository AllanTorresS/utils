package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoDados;
import ipp.aci.boleia.dados.ICicloRepasseDados;
import ipp.aci.boleia.dados.IEntidadeRepasseDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.CicloRepasse;
import ipp.aci.boleia.dominio.ConfiguracaoRepasse;
import ipp.aci.boleia.dominio.EntidadeRepasse;
import ipp.aci.boleia.dominio.ParametroCiclo;
import ipp.aci.boleia.dominio.enums.StatusPagamentoCobranca;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaCicloRepasseVo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.concorrencia.MapeadorLock;
import ipp.aci.boleia.util.concorrencia.Sincronizador;
import ipp.aci.boleia.util.excecao.ExcecaoRecursoBloqueado;
import ipp.aci.boleia.util.i18n.Mensagens;
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
     * @param dataReferencia A data de referência para criação do ciclo de repasse,
     * sendo a data de processamento para abastecimentos novos e a data de postergação para abastecimentos postergados.
     * @return O novo ciclo de repasse criado
     */
    public CicloRepasse criarCicloRepasse(AutorizacaoPagamento autorizacaoPagamento, Date dataReferencia){
        CicloRepasse novoCiclo = new CicloRepasse();

        EntidadeRepasse entidadeRepasse = repositorioEntidadeRepasse.obtemEntidadeDeRepassePadrao();
        ConfiguracaoRepasse configuracaoRepasse = entidadeRepasse.getConfiguracaoRepasse();

        CicloRepasse ultimoCicloAnteriorAoAbastecimento = repositorio.obterUltimoCicloRepasseAnteriorAData(dataReferencia, entidadeRepasse.getId(), autorizacaoPagamento.getPontoVenda().getId());

        Date dataInicio;
        if(ultimoCicloAnteriorAoAbastecimento != null && ultimoCicloAnteriorAoAbastecimento.getDataFim() != null){
            dataInicio = obterPrimeiroInstanteDia(adicionarDiasData(ultimoCicloAnteriorAoAbastecimento.getDataFim(),1));
        }
        else{
            dataInicio = calcularDataInicioCiclo(dataReferencia, configuracaoRepasse.getParametroCiclo());
        }

        Date dataFim = calcularDataFimCiclo(dataInicio, configuracaoRepasse.getParametroCiclo());

        novoCiclo.setConfiguracaoRepasse(configuracaoRepasse);
        novoCiclo.setDataInicio(dataInicio);
        novoCiclo.setDataFim(dataFim);
        novoCiclo.setStatus(StatusPagamentoCobranca.EM_ABERTO.getValue()); //TODO: CRIAR enum?

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
     * É feita a validação do ciclo de repasse de um abastecimento que será postergado.
     * Caso ele se mantenha o mesmo, nenhuma ação é tomada. Do contrário, é gerado um novo ciclo de repasse e o original é alterado.
     * @param autorizacoesPagamentoParaPostergacao autorizações de pagamento que serão postergadas para o próximo ciclo.
     * @param dataPostergacao a data de postergação dos abastecimentos não emitidos.
     */
    public void verificarCicloRepasseAbastecimentosParaPostergacao(List<AutorizacaoPagamento> autorizacoesPagamentoParaPostergacao, Date dataPostergacao){
        List<AutorizacaoPagamento> abastecimentosParaPostergacaoComCicloRepasse = autorizacoesPagamentoParaPostergacao
                .stream().filter(autorizacaoPagamento -> autorizacaoPagamento.isPendenteEmissaoNF() && autorizacaoPagamento.getCicloRepasse() != null)
                .collect(Collectors.toList());

        for(AutorizacaoPagamento autorizacaoPagamento : abastecimentosParaPostergacaoComCicloRepasse){
            CicloRepasse cicloRepasseOriginal = autorizacaoPagamento.getCicloRepasse();
            CicloRepasse cicloRepasseAtual = obterCicloRepasseDeAbastecimentoPostergadoParaProximoCiclo(autorizacaoPagamento, dataPostergacao);

            //Verifica se houve troca de ciclo de repasse do abastecimento.
            if(cicloRepasseAtual != null && !cicloRepasseOriginal.getId().equals(cicloRepasseAtual.getId())){
                //Desvinculando abastecimento a um ciclo de repasse.
                removerAutorizacaoPagamentoDeCicloRepasse(cicloRepasseOriginal, autorizacaoPagamento);
            }

            //Atualizando o ciclo de repasse do abastecimento.
            autorizacaoPagamento.setCicloRepasse(cicloRepasseAtual);
            repositorioAutorizacaoPagamento.armazenar(autorizacaoPagamento); //TODO?
        }
    }

    /**
     * É efetuada a desvinculação de um abastecimento de determinado ciclo de repasse, que tem os seus valores recalculados.
     * Caso o ciclo de repasse possua apenas um abastecimento, o mesmo deve ser removido.
     *
     * @param cicloRepasseOriginal o ciclo de repasse que será recalculado.
     * @param autorizacaoPagamento a autorização de pagamento que será desvinculada do ciclo de repasse informado.
     */
    private void removerAutorizacaoPagamentoDeCicloRepasse(CicloRepasse cicloRepasseOriginal, AutorizacaoPagamento autorizacaoPagamento){
        BigDecimal valorTotalRepasse = cicloRepasseOriginal.getValorTotal().subtract(autorizacaoPagamento.getValorTotal());

        //Verifica se o ciclo de repasse continha apenas 1 abastecimento.
        if(valorTotalRepasse.compareTo(BigDecimal.ZERO) == 0){
            repositorio.excluir(cicloRepasseOriginal.getId()); //TODO?

        } else{
            cicloRepasseOriginal.setValorTotal(valorTotalRepasse);
            BigDecimal valorNominalRepasse = calcularPorcentagem(valorTotalRepasse, cicloRepasseOriginal.getValorPercentualRepasse());
            cicloRepasseOriginal.setValorNominalRepasse(valorNominalRepasse);
        }
        autorizacaoPagamento.setCicloRepasse(null);
    }

    /**
     * Retorna o ciclo de repasse de um abastecimento postergado para o próximo ciclo.
     * @param autorizacaoPagamento o abastecimento que será postergado.
     * @param dataPostergacao a data de postergação do abastecimento para o próximo ciclo.
     * @return o ciclo de repasse do abastecimento postergado, que pode ser o mesmo ou um novo.
     */
    public CicloRepasse obterCicloRepasseDeAbastecimentoPostergadoParaProximoCiclo(AutorizacaoPagamento autorizacaoPagamento, Date dataPostergacao){
        EntidadeRepasse entidadeRepasse = repositorioEntidadeRepasse.obtemEntidadeDeRepassePadrao();
        CicloRepasse cicloRepasse = repositorio.obterCicloRepassePorDataEEntidade(dataPostergacao, entidadeRepasse.getId(), autorizacaoPagamento.getPontoVenda().getId());

        //Será criado um novo ciclo de repasse para o abastecimento postergado.
        if(cicloRepasse == null){
            cicloRepasse = criarCicloRepasse(autorizacaoPagamento, dataPostergacao);
        }
        //Será retornado um ciclo de repasse existente, que pode ser o mesmo.
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
