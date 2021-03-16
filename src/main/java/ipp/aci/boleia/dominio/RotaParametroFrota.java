package ipp.aci.boleia.dominio;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


/**
 * Representa a ligacao entra a tabela de Rota e Parametros de Rota
 */
@Entity
@Audited
@Table(name = "ROTA_FROTA_PARAM_SIS")
@IdClass(RotaParametroFrotaPk.class)
public class RotaParametroFrota  {

    private static final long serialVersionUID = -2992575318587803244L;

    @Id
    @Column(name = "CD_ROTA")
    private Long idRota;

    @Id
    @Column(name = "ID_PARAMETRO")
    private Integer idParametro;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_ROTA", insertable = false, updatable = false)
    private Rota rota;

    public Long getIdRota() {
        return idRota;
    }

    public void setIdRota(Long idRota) {
        this.idRota = idRota;
    }

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
}
