package ipp.aci.boleia.dominio;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * Entidade de mapeamento da tabela USUARIO_PERFIL.
 *
 * @author pedro.silva
 */
@Audited
@Entity
@Table(name = "USUARIO_PERFIL")
@IdClass(UsuarioPerfilPk.class)
public class UsuarioPerfil implements Serializable {

    private static final long serialVersionUID = 903930347711864180L;

    @Id
    @Column(name = "CD_USUARIO")
    private Long idUsuario;

    @Id
    @Column(name = "CD_PERFIL")
    private Long idPerfil;

    @Column(name = "DT_EXPIRACAO")
    private Date dataExpiracao;

    @Transient
    public UsuarioPerfilPk getId() {
        if(idUsuario != null && idPerfil != null) {
            return new UsuarioPerfilPk(idUsuario, idPerfil);
        }
        return null;
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

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }
}
