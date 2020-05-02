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
import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa os parâmetros de uma entidade de repasse
 */
@Audited
@Entity
@Table(name="CONFIGURACAO_REPASSE")
public class ConfiguracaoRepasse implements IPersistente {

    private static final long serialVersionUID = -9149677734218595358L;

    @Id
    @Column(name = "CD_CONFIGURACAO_REPASSE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONFIGURACAO_REPASSE")
    @SequenceGenerator(name = "SEQ_CONFIGURACAO_REPASSE", sequenceName = "SEQ_CONFIGURACAO_REPASSE", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_ENTIDADE_REPASSE")
    private EntidadeRepasse entidadeRepasse;

    @Column(name = "VA_PERCENTUAL_REPASSE")
    private BigDecimal valorPercentualRepasse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CD_PARAM_CICLO")
    private ParametroCiclo parametroCiclo;

    @Column(name = "DT_VIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataVigencia;

    @Column(name = "ID_STATUS")
    private Integer status;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public EntidadeRepasse getEntidadeRepasse() {
        return entidadeRepasse;
    }

    public void setEntidadeRepasse(EntidadeRepasse entidadeRepasse) {
        this.entidadeRepasse = entidadeRepasse;
    }

    public BigDecimal getValorPercentualRepasse() {
        return valorPercentualRepasse;
    }

    public void setValorPercentualRepasse(BigDecimal valorPercentualRepasse) {
        this.valorPercentualRepasse = valorPercentualRepasse;
    }

    public ParametroCiclo getParametroCiclo() {
        return parametroCiclo;
    }

    public void setParametroCiclo(ParametroCiclo parametroCiclo) {
        this.parametroCiclo = parametroCiclo;
    }

    public Date getDataVigencia() {
        return dataVigencia;
    }

    public void setDataVigencia(Date dataVigencia) {
        this.dataVigencia = dataVigencia;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
