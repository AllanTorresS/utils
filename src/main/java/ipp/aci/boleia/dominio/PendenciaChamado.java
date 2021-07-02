package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Entidade de mapeamento da tabela PENDENCIA_CHAMADO.
 *
 * @author pedro.silva
 */
@Audited
@Entity
@Table(name = "PENDENCIA_CHAMADO")
public class PendenciaChamado implements IPersistente {

    private static final long serialVersionUID = 1128385331716547831L;

    @Id
    @Column(name = "CD_PENDENCIA_CHAMADO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PENDENCIA_CHAMADO")
    @SequenceGenerator(name = "SEQ_PENDENCIA_CHAMADO", sequenceName = "SEQ_PENDENCIA_CHAMADO", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @NotNull
    @Column(name = "CD_CHAMADO_SALESFORCE")
    private String idChamadoSalesforce;

    @NotNull
    @Column(name = "DT_PENDENCIA")
    private Date dataPendencia;

    @NotNull
    @Column(name = "ID_STATUS_PENDENCIA")
    private Integer statusPendencia;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getIdChamadoSalesforce() {
        return idChamadoSalesforce;
    }

    public void setIdChamadoSalesforce(String idChamadoSalesforce) {
        this.idChamadoSalesforce = idChamadoSalesforce;
    }

    public Date getDataPendencia() {
        return dataPendencia;
    }

    public void setDataPendencia(Date dataPendencia) {
        this.dataPendencia = dataPendencia;
    }

    public Integer getStatusPendencia() {
        return statusPendencia;
    }

    public void setStatusPendencia(Integer statusPendencia) {
        this.statusPendencia = statusPendencia;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}
