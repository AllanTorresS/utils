package ipp.aci.boleia.dominio.historico;

import ipp.aci.boleia.dominio.Frota;
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
 * Entidade que mantém o histórico de {@link Frota}.
 */
@Entity
@Table(name = "HISTORICO_FROTA")
public class HistoricoFrota implements IPersistente {

    @Id
    @Column(name = "CD_HISTORICO_FROTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HISTORICO_FROTA")
    @SequenceGenerator(name = "SEQ_HISTORICO_FROTA", sequenceName = "SEQ_HISTORICO_FROTA", allocationSize = 1)
    private Long id;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;
    
    @Column(name = "DT_HISTORICO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHistorico;

    @Column(name="ID_EXIGE_NOTA_FISCAL")
    private Boolean exigeNotaFiscal;

    @Column(name = "ID_LOCAL_PADRAO_NFE_UF")
    private Boolean localDestinoPadraoNfeUf;

    @Column(name = "ID_LEMBRAR_PARAMETRIZACAO_NF")
    private Boolean lembrarParametrizacaoNf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
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

    public Boolean getLocalDestinoPadraoNfeUf() {
        return localDestinoPadraoNfeUf;
    }

    public void setLocalDestinoPadraoNfeUf(Boolean localDestinoPadraoNfeUf) {
        this.localDestinoPadraoNfeUf = localDestinoPadraoNfeUf;
    }

    public Boolean getLembrarParametrizacaoNf() {
        return lembrarParametrizacaoNf;
    }

    public void setLembrarParametrizacaoNf(Boolean lembrarParametrizacaoNf) {
        this.lembrarParametrizacaoNf = lembrarParametrizacaoNf;
    }
}
