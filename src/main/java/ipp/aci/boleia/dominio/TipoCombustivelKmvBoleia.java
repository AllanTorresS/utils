package ipp.aci.boleia.dominio;

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

/**
 * Representa a tabela de Tipo Combustivel KMV Boleia
 */
@Entity
@Table(name = "TIPO_COMBUSTIVEL_KMV_BOLEIA")
public class TipoCombustivelKmvBoleia implements IPersistente {

    private static final long serialVersionUID = 9184855036776788052L;

    @Id
    @Column(name = "CD_KMV_BOLEIA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMB_KMV_BOLEIA")
    @SequenceGenerator(name = "SEQ_COMB_KMV_BOLEIA", sequenceName = "SEQ_COMB_KMV_BOLEIA", allocationSize = 1)
    private Long id;

    @Column(name = "CD_COMBUSTIVEL_KMV")
    private Long idCombustivelKmv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_COMBUSTIVEL_BOLEIA")
    private TipoCombustivel combustivelBoleia;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCombustivelKmv() { return idCombustivelKmv; }

    public void setIdCombustivelKmv(Long idCombustivelKmv) { this.idCombustivelKmv = idCombustivelKmv; }

    public TipoCombustivel getCombustivelBoleia() { return combustivelBoleia; }

    public void setCombustivelBoleia(TipoCombustivel combustivelBoleia) { this.combustivelBoleia = combustivelBoleia; }

}