package ipp.aci.boleia.dominio;

import java.io.Serializable;

/**
 * Chaves primárias da entidade {@link UsuarioPerfil}.
 *
 * @author pedro.silva
 */
public class UsuarioPerfilPk  implements Serializable {

    private static final long serialVersionUID = -2149367708878415466L;

    private Long idUsuario;
    private Long idPerfil;

    /**
     * Construtor default.
     */
    public UsuarioPerfilPk() {
    }

    /**
     * Construtor da classe
     *
     * @param idUsuario Identificador do usuário.
     * @param idPerfil Identificador do perfil.
     */
    public UsuarioPerfilPk(Long idUsuario, Long idPerfil) {
        this.idUsuario = idUsuario;
        this.idPerfil = idPerfil;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Long idPerfil) {
        this.idPerfil = idPerfil;
    }
}