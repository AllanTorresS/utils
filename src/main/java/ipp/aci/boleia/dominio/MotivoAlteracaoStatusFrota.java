package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.TipoAlteracaoStatusFrota;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Representa a tabela de Motivo Alteracao Status Frota
 */
@Entity
@Audited
@Table(name = "MOTIVO_ALT_STATUS_FROTA")
public class MotivoAlteracaoStatusFrota implements IPersistente {

    private static final long serialVersionUID = -1173019737404734970L;

    @Id
    @Column(name = "CD_MOTIVO_ALT_STATUS_FROTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MOTIV_ALT_STATUS_FROTA")
    @SequenceGenerator(name = "SEQ_MOTIV_ALT_STATUS_FROTA", sequenceName = "SEQ_MOTIV_ALT_STATUS_FROTA", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @Column(name="ID_TIPO_MOTIVO")
    private Integer tipoMotivo;

    @NotNull
    @Column(name="ID_TIPO_ALTERACAO")
    private Integer tipoAlteracaoStatus;

    @NotNull
    @Column(name="ID_STATUS_ALTERACAO")
    private Integer statusVigenciaAlteracao;

    @Column(name="DT_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Column(name="DT_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicio;

    @Column(name="DT_FIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFim;

    @Size(max=1000)
    @Column(name="DS_JUSTIFICATIVA_MOTIVO")
    private String motivo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_ULTIMO_MOTIVO")
    private MotivoAlteracaoStatusFrota ultimoMotivoDefinitivo;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public Integer getTipoMotivo() {
        return tipoMotivo;
    }

    public void setTipoMotivo(Integer tipoMotivo) {
        this.tipoMotivo = tipoMotivo;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Integer getTipoAlteracaoStatus() {
        return tipoAlteracaoStatus;
    }

    public void setTipoAlteracaoStatus(Integer tipoAlteracaoStatus) {
        this.tipoAlteracaoStatus = tipoAlteracaoStatus;
    }

    public Integer getStatusVigenciaAlteracao() {
        return statusVigenciaAlteracao;
    }

    public void setStatusVigenciaAlteracao(Integer statusVigenciaAlteracao) {
        this.statusVigenciaAlteracao = statusVigenciaAlteracao;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public MotivoAlteracaoStatusFrota getUltimoMotivoDefinitivo() {
        return ultimoMotivoDefinitivo;
    }

    public void setUltimoMotivoDefinitivo(MotivoAlteracaoStatusFrota ultimoMotivoDefinitivo) {
        this.ultimoMotivoDefinitivo = ultimoMotivoDefinitivo;
    }

    /**
     * Verifica se o motivo é um motivo de ativação
     *
     * @return True caso seja um motivo de ativação, false caso contrário
     */
    @Transient
    public boolean isAtivacao() {
        return TipoAlteracaoStatusFrota.ATIVACAO.getValue().equals(tipoAlteracaoStatus);
    }

    /**
     * Verifica se o motivo é um motivo de inativação
     *
     * @return True caso seja um motivo de inativação, false caso contrário
     */
    @Transient
    public boolean isInativacao() {
        return TipoAlteracaoStatusFrota.INATIVACAO.getValue().equals(tipoAlteracaoStatus);
    }
}
