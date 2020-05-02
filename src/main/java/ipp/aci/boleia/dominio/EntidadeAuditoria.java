package ipp.aci.boleia.dominio;

import ipp.aci.boleia.util.negocio.AuditoriaListener;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidade de auditoria para mapear campos extras de auditoria
 */
@Table(name="ENTIDADE_AUDITORIA")
@Entity
@RevisionEntity(AuditoriaListener.class)
public class EntidadeAuditoria {

    private static final long serialVersionUID = 2702723931211631358L;

    @Id
    @GeneratedValue
    @RevisionNumber
    @Column(name = "REV")
    private Integer id;

    @RevisionTimestamp
    @Column(name = "REVTSTMP")
    private long timestamp;

    @Column(name = "TXT_USUARIO")
    private String usuario;

    @Column(name = "NUM_IP")
    private String ip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
