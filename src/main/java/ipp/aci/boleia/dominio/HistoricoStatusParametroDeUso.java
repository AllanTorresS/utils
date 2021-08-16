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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Representa um registro de histórico de ativação de um parâmetro de uso.
 *
 * @author tiago.marques
 */
@Entity
@Audited
@Table(name = "HIST_STATUS_PARAM")
public class HistoricoStatusParametroDeUso implements IPersistente {

    private static final long serialVersionUID = 4389862055167807653L;

    @Id
    @Column(name = "CD_HIST_STATUS_PARAM")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIST_STATUS_PARAM")
    @SequenceGenerator(name = "SEQ_HIST_STATUS_PARAM", sequenceName = "SEQ_HIST_STATUS_PARAM", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @NotNull
    @Column(name = "ID_PARAMETRO")
    private Integer parametroSistema;

    @NotNull
    @Column(name = "ID_ATIVO")
    private Boolean ativo;

    @NotNull
    @Column(name = "ID_RESTRITIVO")
    private Boolean restritivo;

    @NotNull
    @Column(name = "DT_ALTERACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getParametroSistema() {
        return parametroSistema;
    }

    public void setParametroSistema(Integer parametroSistema) {
        this.parametroSistema = parametroSistema;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getRestritivo() {
        return restritivo;
    }

    public void setRestritivo(Boolean restritivo) {
        this.restritivo = restritivo;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }
}
