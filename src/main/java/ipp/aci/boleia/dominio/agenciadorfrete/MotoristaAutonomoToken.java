package ipp.aci.boleia.dominio.agenciadorfrete;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Audited
@Table(name = "MOTORISTA_AUTONOMO_TOKEN")
public class MotoristaAutonomoToken implements IPersistente {

    @Id
    @Column(name = "CD_MOTORISTA_AUTONOMO_TOKEN")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MOTORISTA_AUTONOMO_TOKEN")
    @SequenceGenerator(name = "SEQ_MOTORISTA_AUTONOMO_TOKEN", sequenceName = "SEQ_MOTORISTA_AUTONOMO_TOKEN", allocationSize = 1)
    private Long id;

    @Size(max = 250)
    @Column(name = "DS_TOKEN")
    private String token;

    @Column(name = "DT_EXPIRACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataExpiracao;

    @NotNull
    @OneToOne
    @JoinColumn(name = "CD_MOTORISTA_AUTONOMO")
    private MotoristaAutonomo motorista;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public MotoristaAutonomo getMotorista() {
        return motorista;
    }

    public void setMotorista(MotoristaAutonomo motorista) {
        this.motorista = motorista;
    }
}
