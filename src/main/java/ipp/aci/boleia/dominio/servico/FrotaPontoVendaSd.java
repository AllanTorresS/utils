package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IFrotaPontoVendaDados;
import ipp.aci.boleia.dados.IHistoricoBloqueioFrotaPontoVendaDados;
import ipp.aci.boleia.dados.IHistoricoFrotaPontoVendaDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.FrotaPontoVenda;
import ipp.aci.boleia.dominio.HistoricoFrotaPontoVenda;
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
    private PontoDeVendaSd pontoDeVendaSd;

    /**
     * Cria um novo registro de {@link FrotaPontoVenda}.
     *
     * @param frota Registro da frota.
     * @param pontoVenda Registro do ponto de venda.
     * @return O registro de {@link FrotaPontoVenda} criado.
     */
    public FrotaPontoVenda criarFrotaPontoVenda(Frota frota, PontoDeVenda pontoVenda) {
        return criarFrotaPontoVenda(frota, pontoVenda, false);
    }

    /**
     * Cria um novo registro de {@link FrotaPontoVenda}.
     *
     * @param frota Registro da frota.
     * @param pontoVenda Registro do ponto de venda.
     * @param semIsolamento Informa se o armazenamento será feito sem isolamento de dados.
     * @return O registro de {@link FrotaPontoVenda} criado.
     */
    public FrotaPontoVenda criarFrotaPontoVenda(Frota frota, PontoDeVenda pontoVenda, boolean semIsolamento) {
        FrotaPontoVenda frotaPontoVenda = new FrotaPontoVenda();
        frotaPontoVenda.setFrota(frota);
        frotaPontoVenda.setPontoVenda(pontoVenda);
        frotaPontoVenda.setStatusBloqueio(StatusBloqueio.DESBLOQUEADO.getValue());
        frotaPontoVenda.setStatusVinculo(StatusVinculoFrotaPontoVenda.ATIVO.getValue());
        frotaPontoVenda.setVersao(0L);
        frotaPontoVenda.setDataAtualizacao(ambiente.buscarDataAmbiente());
        FrotaPontoVenda retorno;
        if(semIsolamento) {
            retorno = frotaPontoVendaDados.armazenarSemIsolamentoDeDados(frotaPontoVenda);
        } else{
            retorno = frotaPontoVendaDados.armazenar(frotaPontoVenda);            
        }        
        armazenarHistoricoFrotaPontoVenda(frotaPontoVenda);
        
        return retorno;
    }
    
    /**
     * Preenche e armazena o historico de um {@link FrotaPontoVenda}.
     * 
     * @param entidade o {@link FrotaPontoVenda} que o historico sera salvo
     */
    public void armazenarHistoricoFrotaPontoVenda(FrotaPontoVenda entidade) {
        HistoricoFrotaPontoVenda historicoFrotaPontoVenda = new HistoricoFrotaPontoVenda();
        historicoFrotaPontoVenda.setFrotaPontoVenda(entidade);
        historicoFrotaPontoVenda.setUsuario(ambiente.getUsuarioLogado());
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
}
