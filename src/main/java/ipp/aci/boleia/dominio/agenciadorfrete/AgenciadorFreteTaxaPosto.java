package ipp.aci.boleia.dominio.agenciadorfrete;

import ipp.aci.boleia.dominio.PontoDeVenda;
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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa a tabela de Taxa Posto
 */
@Entity
@Audited
@Table(name = "AG_FRETE_TAXA_POSTO")
public class AgenciadorFreteTaxaPosto implements IPersistente {

    @Id
    @Column(name = "CD_AG_FRETE_TAXA_POSTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AG_FRETE_TAXA_POSTO")
    @SequenceGenerator(name = "SEQ_AG_FRETE_TAXA_POSTO", sequenceName = "SEQ_AG_FRETE_TAXA_POSTO", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda pontoVenda;

    @Column(name = "VA_MDR")
    @DecimalMin("00.00")
    @DecimalMax("99.99")
    private BigDecimal mdr;

    @Column(name = "DT_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicio;

    @Column(name = "DT_FIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFim;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PontoDeVenda getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(PontoDeVenda pontoVenda) {
        this.pontoVenda = pontoVenda;
    }

    public BigDecimal getMdr() {
        return mdr;
    }

    public void setMdr(BigDecimal mdr) {
        this.mdr = mdr;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

}
