package ipp.aci.boleia.dominio.historico;

import ipp.aci.boleia.dominio.ParametroNotaFiscalUf;
import ipp.aci.boleia.dominio.Unidade;
import ipp.aci.boleia.dominio.interfaces.IParametroNotaFiscalUf;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import java.util.Date;
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
import javax.validation.constraints.Size;

/**
 * Entidade que mantém o histórico de {@link HistoricoParametroNotaFiscal}.
 */
@Entity
@Table(name = "HISTORICO_PARAM_NF_UF")
public class HistoricoParametroNotaFiscalUf implements IPersistente, IParametroNotaFiscalUf{
    
    @Id
    @Column(name = "CD_HISTORICO_PARAM_NF_UF")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HISTORICO_PARAM_NF_UF")
    @SequenceGenerator(name = "SEQ_HISTORICO_PARAM_NF_UF", sequenceName = "SEQ_HISTORICO_PARAM_NF_UF", allocationSize = 1)
    private Long id;
    
    @Column(name = "DT_HISTORICO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHistorico;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_HISTORICO_PARAM_NF")
    private HistoricoParametroNotaFiscal parametroNotaFiscal;
    
    @NotNull
    @Size(max=2)
    @Column(name = "SG_UF")
    private String uf;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_UNIDADE_LOCAL_DEST")
    private Unidade unidadeLocalDestino;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataHistorico() {
        return dataHistorico;
    }

    public void setDataHistorico(Date dataHistorico) {
        this.dataHistorico = dataHistorico;
    }

    public HistoricoParametroNotaFiscal getParametroNotaFiscal() {
        return parametroNotaFiscal;
    }

    public void setParametroNotaFiscal(HistoricoParametroNotaFiscal parametroNotaFiscal) {
        this.parametroNotaFiscal = parametroNotaFiscal;
    }

    @Override
    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public Unidade getUnidadeLocalDestino() {
        return unidadeLocalDestino;
    }

    public void setUnidadeLocalDestino(Unidade unidadeLocalDestino) {
        this.unidadeLocalDestino = unidadeLocalDestino;
    }
}
