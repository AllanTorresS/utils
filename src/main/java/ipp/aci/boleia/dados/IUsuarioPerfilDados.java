package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.UsuarioPerfil;

/**
 * Repositório de dados do UsuarioPerfil.
 *
 * @author pedro.silva
 */
public interface IUsuarioPerfilDados {

    /**
     * Cria ou altera (caso possua ID) uma entidade
     * @param usuarioPerfil A entidade a ser criada/alterada
     * @return A entidade criada/alterada
     */
    UsuarioPerfil armazenar(UsuarioPerfil usuarioPerfil);

    /**
     * Desvincula os perfis temporários já expirados para um usuário.
     * @param idUsuario Identificador do usuário.
     */
    void desvincularPerfisTemporariosExpirados(Long idUsuario);
}
