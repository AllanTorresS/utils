package ipp.aci.boleia.dominio;

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

/**
 * Entidade que mantém o histórico do {@link PontoDeVenda}.
 * 
 * @author Rodrigo Salvatore
 */
@Entity
@Table(name = "HISTORICO_PONTO_VENDA")
public class HistoricoPontoVenda implements IPersistente {
    
    @Id
    @Column(name = "CD_HISTORICO_PONTO_VENDA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HISTORICO_PONTO_VENDA")
    @SequenceGenerator(name = "SEQ_HISTORICO_PONTO_VENDA", sequenceName = "SEQ_HISTORICO_PONTO_VENDA", allocationSize = 1)
    private Long id;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda pontoDeVenda;
    
    @Column(name = "DT_HISTORICO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHistorico;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;
    
    @Column(name = "ID_RESTRICAO_VISIBILIDADE")
    private Integer restricaoVisibilidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PontoDeVenda getPontoDeVenda() {
        return pontoDeVenda;
    }

    public void setPontoDeVenda(PontoDeVenda pontoDeVenda) {
        this.pontoDeVenda = pontoDeVenda;
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

    public Integer getRestricaoVisibilidade() {
        return restricaoVisibilidade;
    }

    public void setRestricaoVisibilidade(Integer restricaoVisibilidade) {
        this.restricaoVisibilidade = restricaoVisibilidade;
    }
}
