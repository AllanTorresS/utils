package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
 * Representa a tabela de ClonagemPerfil
 */
@Audited
@Entity
@Table(name = "CLONAGEM_PERFIL")
public class ClonagemPerfil implements IPersistente{

    @Id
    @Column(name = "CD_CLONAGEM_PERFIL")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CLONAGEM_PERFIL")
    @SequenceGenerator(name = "SEQ_CLONAGEM_PERFIL", sequenceName = "SEQ_CLONAGEM_PERFIL", allocationSize = 1)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO_ORIGEM")
    private Usuario usuarioOrigem;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO_DESTINO")
    private Usuario usuarioDestino;

    @NotNull
    @Column(name = "ID_DEFINITIVA")
    private Boolean isDefinitiva;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_MOTIVO_CLONAGEM")
    private MotivoClonagem motivo;

    @NotNull
    @Size(max = 1080)
    @Column(name = "DS_DESCRICAO")
    private String descricao;
    @Column(name = "DT_INATIVACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInativacao;

    @NotNull
    @Column(name = "DT_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @NotAudited
    @ManyToMany(fetch = FetchType.LAZY)
    @AuditJoinTable(name = "CLONAGEM_PERFIS_AUD")
    @JoinTable(name="CLONAGEM_PERFIS", joinColumns={@JoinColumn(name="CD_CLONAGEM_PERFIL")}, inverseJoinColumns={@JoinColumn(name="CD_PERFIL")})
    private List<Perfil> perfis;

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

    public Usuario getUsuarioOrigem() {
        return usuarioOrigem;
    }

    public void setUsuarioOrigem(Usuario usuarioOrigem) {
        this.usuarioOrigem = usuarioOrigem;
    }

    public Usuario getUsuarioDestino() {
        return usuarioDestino;
    }

    public void setUsuarioDestino(Usuario usuarioDestino) {
        this.usuarioDestino = usuarioDestino;
    }

    public Boolean getIsDefinitiva() {
        return isDefinitiva;
    }

    public void setIsDefinitiva(Boolean isDefinitiva) {
        this.isDefinitiva = isDefinitiva;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataInativacao() {
        return dataInativacao;
    }

    public void setDataInativacao(Date dataInativacao) {
        this.dataInativacao = dataInativacao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(List<Perfil> perfis) {
        this.perfis = perfis;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public MotivoClonagem getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivoClonagem motivo) {
        this.motivo = motivo;
    }
}
