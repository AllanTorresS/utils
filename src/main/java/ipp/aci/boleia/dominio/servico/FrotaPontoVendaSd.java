package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IFrotaPontoVendaDados;
import ipp.aci.boleia.dados.IHistoricoBloqueioFrotaPontoVendaDados;
import ipp.aci.boleia.dados.IHistoricoFrotaPontoVendaDados;
import ipp.aci.boleia.dados.IHistoricoPontoVendaDados;
import ipp.aci.boleia.dados.IPontoDeVendaDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.FrotaPontoVenda;
import ipp.aci.boleia.dominio.HistoricoFrotaPontoVenda;
import ipp.aci.boleia.dominio.HistoricoPontoVenda;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.enums.RestricaoVisibilidadePontoVenda;
import ipp.aci.boleia.dominio.enums.StatusBloqueio;
import ipp.aci.boleia.dominio.enums.StatusVinculoFrotaPontoVenda;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Serviços de domínio da entidade {@link FrotaPontoVenda}.
 */
@Component
public class FrotaPontoVendaSd {

    @Autowired
    private IFrotaPontoVendaDados frotaPontoVendaDados;

    @Autowired
    private IHistoricoBloqueioFrotaPontoVendaDados historicoBloqueioFrotaPontoVendaDados;

    @Autowired
    protected Mensagens mensagens;

    @Autowired
    protected UtilitarioAmbiente ambiente;
    
    @Autowired
    private IHistoricoFrotaPontoVendaDados historicoFrotaPontoVendaDados;
    
    @Autowired
    private IPontoDeVendaDados pontoDeVendaDados;
    
    @Autowired
    private PontoDeVendaSd pontoDeVendaSd;
    
    @Autowired
    private IHistoricoPontoVendaDados historicoPontoVendaDados;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    /**
     * Cria um novo registro de {@link FrotaPontoVenda}.
     *
     * @param frota Registro da frota.
     * @param pontoVenda Registro do ponto de venda.
     * @return O registro de {@link FrotaPontoVenda} criado.
     */
    public FrotaPontoVenda criarFrotaPontoVenda(Frota frota, PontoDeVenda pontoVenda) {
        return criarFrotaPontoVenda(frota, pontoVenda, false, null, StatusVinculoFrotaPontoVenda.INATIVO);
    }

    /**
     * Cria um novo registro de {@link FrotaPontoVenda}.
     *
     * @param frota Registro da frota.
     * @param pontoVenda Registro do ponto de venda.
     * @param justificativa Motivo para criacao do vinculo.
     * @return O registro de {@link FrotaPontoVenda} criado.
     */
    public FrotaPontoVenda criarFrotaPontoVenda(Frota frota, PontoDeVenda pontoVenda,String justificativa) {
        return criarFrotaPontoVenda(frota, pontoVenda, false, justificativa, StatusVinculoFrotaPontoVenda.INATIVO);
    }
    
    /**
     * Cria um novo registro de {@link FrotaPontoVenda}.
     *
     * @param frota Registro da frota.
     * @param pontoVenda Registro do ponto de venda.
     * @param semIsolamento Informa se o armazenamento será feito sem isolamento de dados.
     * @param justificativa Motivo para criacao do vinculo.
     * @param statusVinculo Status do vinculo
     * @return O registro de {@link FrotaPontoVenda} criado.
     */
    public FrotaPontoVenda criarFrotaPontoVenda(Frota frota, PontoDeVenda pontoVenda, boolean semIsolamento, String justificativa, StatusVinculoFrotaPontoVenda statusVinculo) {
        FrotaPontoVenda frotaPontoVenda = new FrotaPontoVenda();
        frotaPontoVenda.setFrota(frota);
        frotaPontoVenda.setPontoVenda(pontoVenda);
        frotaPontoVenda.setStatusBloqueio(StatusBloqueio.DESBLOQUEADO.getValue());
        frotaPontoVenda.setStatusVinculo(statusVinculo.getValue());
        frotaPontoVenda.setVersao(0L);
        frotaPontoVenda.setDataAtualizacao(ambiente.buscarDataAmbiente());
        frotaPontoVenda.setJustificativaVinculo(justificativa);
        FrotaPontoVenda retorno;
        if(semIsolamento) {
            retorno = frotaPontoVendaDados.armazenarSemIsolamentoDeDados(frotaPontoVenda);
        } else{
            retorno = frotaPontoVendaDados.armazenar(frotaPontoVenda);            
        }        
        armazenarHistoricoFrotaPontoVenda(frotaPontoVenda, justificativa != null);
        
        return retorno;
    }
    
    /**
     * Cria um novo registro de {@link FrotaPontoVenda}.
     *
     * @param autorizacaoPagamento A autorizacao de pagamento que originou a necessidade da criacao.
     * @param semIsolamento Informa se o armazenamento será feito sem isolamento de dados.
     * @return O registro de {@link FrotaPontoVenda} criado.
     */
    public FrotaPontoVenda criarFrotaPontoVenda(AutorizacaoPagamento autorizacaoPagamento, boolean semIsolamento){
        return criarFrotaPontoVenda(autorizacaoPagamento.getFrota(), autorizacaoPagamento.getPontoVenda(), semIsolamento, null, StatusVinculoFrotaPontoVenda.INATIVO);
    }
    
    /**
     * Preenche e armazena o historico de um {@link FrotaPontoVenda}.
     * 
     * @param entidade o {@link FrotaPontoVenda} que o historico sera salvo
     */
    public void armazenarHistoricoFrotaPontoVenda(FrotaPontoVenda entidade) {
        armazenarHistoricoFrotaPontoVenda(entidade, true);
    }
    
    /**
     * Preenche e armazena o historico de um {@link FrotaPontoVenda}.
     * 
     * @param entidade o {@link FrotaPontoVenda} que o historico sera salvo
     * @param gravarUsuario se deve gravar o usuário logado no historico
     */
    private void armazenarHistoricoFrotaPontoVenda(FrotaPontoVenda entidade, boolean gravarUsuario) {
        HistoricoFrotaPontoVenda historicoFrotaPontoVenda = new HistoricoFrotaPontoVenda();
        historicoFrotaPontoVenda.setFrotaPontoVenda(entidade);
        historicoFrotaPontoVenda.setUsuario(gravarUsuario ? ambiente.getUsuarioLogado() : null);
        historicoFrotaPontoVenda.setDataHistorico(ambiente.buscarDataAmbiente());
        historicoFrotaPontoVenda.setStatusVinculo(entidade.getStatusVinculo());
        historicoFrotaPontoVenda.setJustificativaVinculo(entidade.getJustificativaVinculo());
        historicoFrotaPontoVendaDados.armazenar(historicoFrotaPontoVenda);
    }

    /**
     * Realiza a validação do status de bloqueio de um {@link FrotaPontoVenda}.
     *
     * @param idFrota Identificador da frota
     * @param idPv Identificador do pv
     * @return true caso a frota tenha bloqueado o posto, false caso contrário
     */
    public boolean validarBloqueio(Long idFrota, Long idPv) {
        FrotaPontoVenda frotaPontoVenda = frotaPontoVendaDados.obterFrotaPontoVenda(idFrota, idPv);
        return frotaPontoVenda != null && frotaPontoVenda.isBloqueado();
    }
    
    /**
     * Realiza a validação se um {@link PontoDeVenda} deve estar visível para uma {@link Frota}
     *
     * @param idFrota Identificador da frota
     * @param pontoDeVenda o Ponto de venda
     * @return true caso o posto deva estar visível para a frota.
     */
    public boolean validarVisibilidade(Long idFrota, PontoDeVenda pontoDeVenda) {
        if (pontoDeVenda.isVisivelApenasParaFrotasVinculadas()){            
            FrotaPontoVenda frotaPontoVenda = frotaPontoVendaDados.obterFrotaPontoVenda(idFrota, pontoDeVenda.getId());
            return frotaPontoVenda != null && frotaPontoVenda.isVinculoAtivo();
        }
        return true;
    }
    
    /**
     * Realiza a validação se um {@link PontoDeVenda} deve estar visível para uma {@link Frota}
     *
     * @param idFrota Identificador da frota
     * @param idPontoDeVenda Identificador do pv
     * @return true caso o posto deva estar visível para a frota.
     */
    public boolean validarVisibilidade(Long idFrota, Long idPontoDeVenda) {
        return this.validarVisibilidade(idFrota, pontoDeVendaDados.obterPorId(idPontoDeVenda));
    }
    
    /**
     * Realiza validação se um {@link PontoDeVenda} deve estar visível e não bloqueado para uma {@link Frota}
     * 
     * @param idFrota Identificador da frota
     * @param pontoDeVenda o Ponto de venda
     * @return true caso o posto esteja disponpivel para a frota.
     */
    public boolean validarDisponibilidade(Long idFrota, PontoDeVenda pontoDeVenda) {
        FrotaPontoVenda frotaPontoVenda = frotaPontoVendaDados.obterFrotaPontoVenda(idFrota, pontoDeVenda.getId());
        if (pontoDeVenda.isVisivelApenasParaFrotasVinculadas() && (frotaPontoVenda == null || !frotaPontoVenda.isVinculoAtivo())){
            return false;
        }
        if (frotaPontoVenda != null && frotaPontoVenda.isBloqueado()){
            return false;
        }
        return true;
    }

    /**
     * Realiza validação se um {@link PontoDeVenda} deve estar vinculado com a {@link Frota}
     *
     * @param idFrota Identificador da frota
     * @param pontoDeVenda o Ponto de venda
     * @return true caso o posto esteja vinculado com a frota.
     */
    public boolean validarCerca(Long idFrota, PontoDeVenda pontoDeVenda) {
        FrotaPontoVenda frotaPontoVenda = frotaPontoVendaDados.obterFrotaPontoVenda(idFrota, pontoDeVenda.getId());
        if (frotaPontoVenda != null ) {
            if (frotaPontoVenda.isVinculoAtivo()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se a relação FrotaPontoVenda estava bloqueada na data do abastecimento.
     *
     * @param idFrota Identificador da frota
     * @param idPv Identificador do ponto de venda
     * @param dataAbastecimento Data do abastecimento
     * @throws ExcecaoValidacao Exceção lançada caso exista um bloqueio na data do abastecimento
     */
    public void validarHistoricoBloqueio(Long idFrota, Long idPv, Date dataAbastecimento) throws ExcecaoValidacao {
        FrotaPontoVenda frotaPontoVenda = frotaPontoVendaDados.obterFrotaPontoVenda(idFrota, idPv);
        if(frotaPontoVenda != null){
            boolean estavaBloqueado = historicoBloqueioFrotaPontoVendaDados.obterBloqueioPorData(frotaPontoVenda.getId(), dataAbastecimento) != null;
            if(estavaBloqueado) {
                throw new ExcecaoValidacao(Erro.FROTA_PONTO_VENDA_BLOQUEADO);
            }
        }
    }  
    
    /**
     * Verifica se a relação FrotaPontoVenda permitia visibilidade na data do abastecimento.
     *
     * @param idFrota Identificador da frota
     * @param idPv Identificador do ponto de venda
     * @param dataAbastecimento Data do abastecimento
     * @throws ExcecaoValidacao Exceção lançada caso a relação não estivesse visível na data do abastecimento
     */
    public void validarHistoricoVisibilidade(Long idFrota, Long idPv, Date dataAbastecimento) throws ExcecaoValidacao {
        FrotaPontoVenda frotaPontoVenda = frotaPontoVendaDados.obterFrotaPontoVenda(idFrota, idPv);
        HistoricoPontoVenda historicoPontoVenda = historicoPontoVendaDados.obterHistoricoPontoVendaPorData(idPv, dataAbastecimento);
        if(frotaPontoVenda != null){
            if((historicoPontoVenda != null && historicoPontoVenda.isVisivelApenasParaFrotasVinculadas())
                || (historicoPontoVenda == null && frotaPontoVenda.getPontoVenda().isVisivelApenasParaFrotasVinculadas())) {

                HistoricoFrotaPontoVenda historicoFrotaPontoVenda = historicoFrotaPontoVendaDados.obterHistoricoFrotaPontoVendaPorData(frotaPontoVenda.getId(), dataAbastecimento);
                if((historicoFrotaPontoVenda != null && !historicoFrotaPontoVenda.isVinculoAtivo())
                    || (historicoFrotaPontoVenda == null && !frotaPontoVenda.isVinculoAtivo())) {
                    throw new ExcecaoValidacao(Erro.FROTA_PONTO_VENDA_NAO_VISIVEL);
                }
            }            
        } else {
            if((historicoPontoVenda != null && historicoPontoVenda.isVisivelApenasParaFrotasVinculadas())
                || (historicoPontoVenda == null && pontoDeVendaDados.obterPorId(idPv).isVisivelApenasParaFrotasVinculadas())) {
                throw new ExcecaoValidacao(Erro.FROTA_PONTO_VENDA_NAO_VISIVEL);
            }
        }
    }
    
    /**
     * Aplica a regra de alteracao da flag de visibilidade do ponto de venda.
     * O historico tambem e gerado caso necessario.
     * 
     * @param pontoDeVenda o ponto de venda para qual a regra deve ser aplicada
     * @param desativando se esta sendo feita uma desativacao
     */
    public void aplicarRegraAlteracaoFlagVisibilidadePontoVenda(PontoDeVenda pontoDeVenda, boolean desativando){
        if (desativando){
            List<FrotaPontoVenda> relacionados = frotaPontoVendaDados.buscarPorPontoVenda(pontoDeVenda.getId());
            boolean nenhumVinculoAtivo = !relacionados.stream().anyMatch(r -> StatusVinculoFrotaPontoVenda.ATIVO.getValue().equals(r.getStatusVinculo()));
            if (nenhumVinculoAtivo){
                if (pontoDeVenda.isVisivelApenasParaFrotasVinculadas()){
                    pontoDeVenda.setRestricaoVisibilidade(RestricaoVisibilidadePontoVenda.SEM_RESTRICAO.getValue());
                    pontoDeVendaSd.armazenarHistorico(pontoDeVenda);                    
                }
                return;
            }
        }
        if (!pontoDeVenda.isVisivelApenasParaFrotasVinculadas()){
            pontoDeVenda.setRestricaoVisibilidade(RestricaoVisibilidadePontoVenda.VISIVEL_APENAS_PARA_FROTAS_COM_VINCULO_ATIVO.getValue());
            pontoDeVendaSd.armazenarHistorico(pontoDeVenda);
        }    
    }

    /**
     * Aplica a regra de atualização de associação Frota x Posto para uma frota recem habilitada,
     * podendo disponibilizar essa associação para uma exportação da APCO.
     * O historico tambem e gerado caso necessario.
     *
     * @param frota a frota para qual a regra deve ser aplicada
     */
    public void atualizaFrotaPontoVendaAposHabilitarPreCadastro(Frota frota){
        List<FrotaPontoVenda> associacoes = frotaPontoVendaDados.buscarPorFrota(frota);
        associacoes.forEach(a -> a.setDataAtualizacao(utilitarioAmbiente.buscarDataAmbiente()));
        frotaPontoVendaDados.armazenarLista(associacoes);
    }
}
