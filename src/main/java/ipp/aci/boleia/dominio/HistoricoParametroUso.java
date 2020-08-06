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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Representa um registro de histórico de ativação de um parâmetro de uso.
 *
 * @author pedro.silva
 */
@Entity
@Audited
@Table(name = "HIST_PARAM_USO")
public class HistoricoParametroUso implements IPersistente {

    private static final long serialVersionUID = 4389862055167807653L;

    @Id
    @Column(name = "CD_HIST_PARAM_USO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIST_PARAM_USO")
    @SequenceGenerator(name = "SEQ_HIST_PARAM_USO", sequenceName = "SEQ_HIST_PARAM_USO", allocationSize = 1)
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;
    @NotNull
    @Column(name = "ID_PARAMETRO")
    private Integer parametroSistema;
    @NotNull
    @Column(name = "ID_RESTRITIVO")
    private Boolean restritivo;
    @NotNull
    @Column(name = "DT_ATIVACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtivacao;
    @Column(name = "DT_INATIVACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInativacao;
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

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public Integer getParametroSistema() {
        return parametroSistema;
    }

    public void setParametroSistema(Integer parametroSistema) {
        this.parametroSistema = parametroSistema;
    }

    public Boolean getRestritivo() {
        return restritivo;
    }

    public void setRestritivo(Boolean restritivo) {
        this.restritivo = restritivo;
    }

    public Date getDataAtivacao() {
        return dataAtivacao;
    }

    public void setDataAtivacao(Date dataAtivacao) {
        this.dataAtivacao = dataAtivacao;
    }

    public Date getDataInativacao() {
        return dataInativacao;
    }

    public void setDataInativacao(Date dataInativacao) {
        this.dataInativacao = dataInativacao;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}
