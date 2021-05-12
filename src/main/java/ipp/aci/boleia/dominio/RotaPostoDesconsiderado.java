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
import javax.validation.constraints.NotNull;


/**
 * Representa a ligacao entra a tabela de Rota e Postos
 */
@Entity
@Audited
@Table(name = "ROTA_PTOV_DESCON")
public class RotaPostoDesconsiderado implements IPersistente {

    private static final long serialVersionUID = -2992523418587803244L;

    @Id
    @Column(name = "CD_ROTA_PTOV_DESCON")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROTA_PTOV_DESCON")
    @SequenceGenerator(name = "SEQ_ROTA_PTOV_DESCON", sequenceName = "SEQ_ROTA_PTOV_DESCON", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_ROTA")
    private Rota rota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda pontoDeVenda;


    public Rota getRota() {
        return rota;
    }

    public void setRota(Rota rota) {
        this.rota = rota;
    }

    public PontoDeVenda getPontoDeVenda() {
        return pontoDeVenda;
    }

    public void setPontoDeVenda(PontoDeVenda pontoDeVenda) {
        this.pontoDeVenda = pontoDeVenda;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
