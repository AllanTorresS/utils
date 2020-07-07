package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IFrotaPontoVendaDados;
import ipp.aci.boleia.dados.IHistoricoBloqueioFrotaPontoVendaDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.FrotaPontoVenda;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.enums.StatusBloqueio;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

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
        frotaPontoVenda.setVersao(0L);
        frotaPontoVenda.setDataAtualizacao(ambiente.buscarDataAmbiente());
        if(semIsolamento) {
            return frotaPontoVendaDados.armazenarSemIsolamentoDeDados(frotaPontoVenda);
        }
        return frotaPontoVendaDados.armazenar(frotaPontoVenda);
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
}
