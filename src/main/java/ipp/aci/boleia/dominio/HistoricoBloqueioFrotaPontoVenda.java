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
 * Entidade que mantém o histórico do status e bloqueio de uma {@link FrotaPontoVenda}.
 */
@Entity
@Audited
@Table(name = "HIST_BLOQUEIO_FROTA_PTOV")
public class HistoricoBloqueioFrotaPontoVenda implements IPersistente {

    private static final long serialVersionUID = 5332493917164018504L;

    @Id
    @Column(name = "CD_HIST_BLOQUEIO_FROTA_PTOV")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIST_BLOQUEIO_FROTA_PTOV")
    @SequenceGenerator(name = "SEQ_HIST_BLOQUEIO_FROTA_PTOV", sequenceName = "SEQ_HIST_BLOQUEIO_FROTA_PTOV", allocationSize = 1)
    private Long id;
    @NotNull
    @Column(name = "DT_BLOQUEIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataBloqueio;
    @Column(name = "DT_DESBLOQUEIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDesbloqueio;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PTOV")
    private FrotaPontoVenda frotaPontoVenda;
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

    public Date getDataBloqueio() {
        return dataBloqueio;
    }

    public void setDataBloqueio(Date dataBloqueio) {
        this.dataBloqueio = dataBloqueio;
    }

    public Date getDataDesbloqueio() {
        return dataDesbloqueio;
    }

    public void setDataDesbloqueio(Date dataDesbloqueio) {
        this.dataDesbloqueio = dataDesbloqueio;
    }

    public FrotaPontoVenda getFrotaPontoVenda() {
        return frotaPontoVenda;
    }

    public void setFrotaPontoVenda(FrotaPontoVenda frotaPontoVenda) {
        this.frotaPontoVenda = frotaPontoVenda;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}
