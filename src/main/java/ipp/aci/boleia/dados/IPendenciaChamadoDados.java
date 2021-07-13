package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.PendenciaChamado;

import java.util.List;

/**
 * Interface do repositório de dados de uma {@link PendenciaChamado}.
 *
 * @author pedro.silva
 */
public interface IPendenciaChamadoDados  extends IRepositorioBoleiaDados<PendenciaChamado> {

    /**
     * Retorna a ultima pendencia de chamado de um usuário.
     *
     * @param idUsuario Identificador do usuário.
     * @return Pendencia encontrada.
     */
    PendenciaChamado obterUltimaPendencia(Long idUsuario);

    /**
     * Retorna as pendências ainda não resolvidas para um chamado.
     * @param idSalesforce Identificador salesforce do chamado.
     * @return Lista com as pendencias.
     */
    List<PendenciaChamado> listarPendenciasEmAberto(String idSalesforce);
}
