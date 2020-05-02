package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Entidade que mantém as informações da tabela armazém
 * da recolha automática de notas fiscais
 */
@Entity
@Audited
@Table(name = "NFE_ARMAZEM")
public class NfeArmazem implements IPersistente {

    private static final long serialVersionUID = -3417780685210926010L;

    @Id
    @Column(name = "CD_NFE_ARMAZEM")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NFE_ARMAZEM")
    @SequenceGenerator(name = "SEQ_NFE_ARMAZEM", sequenceName = "SEQ_NFE_ARMAZEM", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "DS_REMETENTE")
    private String remetente;

    @NotNull
    @Column(name = "DT_ENVIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEnvio;

    @NotNull
    @Column(name = "DT_RECEBIMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRecebimento;

    @NotNull
    @Column(name = "DT_ULTIMA_LEITURA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataUltimaLeitura;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "email")
    private List<NfeAnexosArmazem> anexos;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public Date getDataUltimaLeitura() {
        return dataUltimaLeitura;
    }

    public void setDataUltimaLeitura(Date dataUltimaLeitura) {
        this.dataUltimaLeitura = dataUltimaLeitura;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public List<NfeAnexosArmazem> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<NfeAnexosArmazem> anexos) {
        this.anexos = anexos;
    }
}
