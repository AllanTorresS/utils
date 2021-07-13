package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.PendenciaChamado;

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
}
