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
 * Representa a ligacao entra a tabela de Rota e Parametros do Sistema
 */
@Entity
@Audited
@Table(name = "ROTA_FROTA_PARAM_SIS")
public class RotaParametroFrota implements IPersistente {

    private static final long serialVersionUID = -2992575318587803244L;

    @Id
    @Column(name = "CD_ROTA_FROTA_PARAM_SIS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROTA_FROTA_PARAM_SIS")
    @SequenceGenerator(name = "SEQ_ROTA_FROTA_PARAM_SIS", sequenceName = "SEQ_ROTA_FROTA_PARAM_SIS", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "ID_PARAMETRO")
    private Integer idParametro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_ROTA")
    private Rota rota;

    public Integer getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Integer idParametro) {
        this.idParametro = idParametro;
    }

    public Rota getRota() {
        return rota;
    }

    public void setRota(Rota rota) {
        this.rota = rota;
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
