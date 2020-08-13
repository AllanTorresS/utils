package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.StatusVinculoFrotaPontoVenda;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Entidade que mantém o histórico de {@link FrotaPontoVenda}.
 * 
 * @author Rodrigo Salvatore
 */
@Entity
@Table(name = "HISTORICO_FROTA_PTOV")
public class HistoricoFrotaPontoVenda implements IPersistente {
    
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;
    
    @Column(name = "ID_STATUS")
    private Integer statusVinculo;
    
    @Column(name = "DS_JUSTIFICATIVA_VINCULO")
    private String justificativaVinculo;

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

    public String getJustificativaVinculo() {
        return justificativaVinculo;
    }

    public void setJustificativaVinculo(String justificativaVinculo) {
        this.justificativaVinculo = justificativaVinculo;
    }
    
    /**
     * Informa se a relacao {@link FrotaPontoVenda} estava ativa.
     *
     * @return true, caso estivesse ativa.
     */
    @Transient
    public boolean isVinculoAtivo(){
        return StatusVinculoFrotaPontoVenda.ATIVO.equals(StatusVinculoFrotaPontoVenda.obterPorValor(this.statusVinculo));
    }
}
