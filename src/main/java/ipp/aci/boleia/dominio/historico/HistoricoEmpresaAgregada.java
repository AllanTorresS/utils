package ipp.aci.boleia.dominio.historico;

import ipp.aci.boleia.dominio.EmpresaAgregada;
import ipp.aci.boleia.dominio.interfaces.IPersistente;

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
 * Entidade que mantém o histórico de {@link EmpresaAgregada}.
 */
@Entity
@Table(name = "HISTORICO_EMPRESA_AGREGADA")
public class HistoricoEmpresaAgregada implements IPersistente {

    @Id
    @Column(name = "CD_HISTORICO_EMPR_AGREGADA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HISTORICO_EMPR_AGREGADA")
    @SequenceGenerator(name = "SEQ_HISTORICO_EMPR_AGREGADA", sequenceName = "SEQ_HISTORICO_EMPR_AGREGADA", allocationSize = 1)
    private Long id;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_EMPR_AGREGADA")
    private EmpresaAgregada empresaAgregada;
    
    @Column(name = "DT_HISTORICO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHistorico;

    @Column(name="ID_EXIGE_NOTA_FISCAL")
    private Boolean exigeNotaFiscal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataHistorico() {
        return dataHistorico;
    }

    public void setDataHistorico(Date dataHistorico) {
        this.dataHistorico = dataHistorico;
    }

    public Boolean getExigeNotaFiscal() {
        return exigeNotaFiscal;
    }

    public void setExigeNotaFiscal(Boolean exigeNotaFiscal) {
        this.exigeNotaFiscal = exigeNotaFiscal;
    }

    public EmpresaAgregada getEmpresaAgregada() {
        return empresaAgregada;
    }

    public void setEmpresaAgregada(EmpresaAgregada empresaAgregada) {
        this.empresaAgregada = empresaAgregada;
    }
}
