package ipp.aci.boleia.dominio;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Entidade que mantém o histórico de {@link FrotaPontoVenda}.
 * 
 * @author Rodrigo Salvatore
 */
public class HistoricoFrotaPontoVenda {
    
    @Id
    @Column(name = "CD_HISTORICO_FROTA_PTOV")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HISTORICO_FROTA_PTOV")
    @SequenceGenerator(name = "SEQ_HISTORICO_FROTA_PTOV", sequenceName = "SEQ_HISTORICO_FROTA_PTOV", allocationSize = 1)
    private Long id;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PTOV")
    private FrotaPontoVenda frotaPontoVenda;
    
    @Column(name = "DT_HISTORICO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHistorico;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;
    
    @Column(name = "ID_STATUS")
    private Integer statusVinculo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FrotaPontoVenda getFrotaPontoVenda() {
        return frotaPontoVenda;
    }

    public void setFrotaPontoVenda(FrotaPontoVenda frotaPontoVenda) {
        this.frotaPontoVenda = frotaPontoVenda;
    }

    public Date getDataHistorico() {
        return dataHistorico;
    }

    public void setDataHistorico(Date dataHistorico) {
        this.dataHistorico = dataHistorico;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getStatusVinculo() {
        return statusVinculo;
    }

    public void setStatusVinculo(Integer statusVinculo) {
        this.statusVinculo = statusVinculo;
    }
}
