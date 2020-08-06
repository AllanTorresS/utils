package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Mantém as tentativas de acesso ao portal que falharam com o objetivo de
 * realizar diferentes ações para cada um.
 *
 * @author pedro.silva
 */
@Entity
@Audited
@Table(name = "CONTROLE_ACESSO")
public class ControleAcesso implements IPersistente {

    @Id
    @Column(name = "CD_CONTROLE_ACESSO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTROLE_ACESSO_PORTAL")
    @SequenceGenerator(name = "SEQ_CONTROLE_ACESSO_PORTAL", sequenceName = "SEQ_CONTROLE_ACESSO_PORTAL", allocationSize = 1)
    private Long id;

    @Size(max = 250)
    @Column(name = "NM_LOGIN_ACESSO")
    private String login;

    @NotNull
    @Column(name = "NO_RESTANTE_CAPTCHA")
    private Integer tentativasRestantesCaptcha;

    @NotNull
    @Column(name = "ID_TIPO_ACESSO")
    private Integer tipoAcesso;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getTentativasRestantesCaptcha() {
        return tentativasRestantesCaptcha;
    }

    public void setTentativasRestantesCaptcha(Integer tentativasRestantesCaptcha) {
        this.tentativasRestantesCaptcha = tentativasRestantesCaptcha;
    }

    public Integer getTipoAcesso() {
        return tipoAcesso;
    }

    public void setTipoAcesso(Integer tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}
