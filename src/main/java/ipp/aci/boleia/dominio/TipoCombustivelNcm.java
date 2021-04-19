package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
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
 * Representa a tabela de Tipo de Combustivel NCM
 */
@Audited
@Entity
@Table(name = "TIPO_COMBUSTIVEL_NCM")
public class TipoCombustivelNcm implements IPersistente, IExclusaoLogica {

    private static final long serialVersionUID = 3211158387436126772L;

    @Id
    @Column(name = "CD_TIPO_COMBUSTIVEL_NCM")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPO_COMB_NCM")
    @SequenceGenerator(name = "SEQ_TIPO_COMB_NCM", sequenceName = "SEQ_TIPO_COMB_NCM", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TIPO_COMBUSTIVEL")
    private TipoCombustivel tipoCombustivel;

    @NotNull
    @Column(name = "CD_NCM")
    private Long codigoNcm;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public TipoCombustivel getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public Long getCodigoNcm() {
        return codigoNcm;
    }

    public void setCodigoNcm(Long codigoNcm) {
        this.codigoNcm = codigoNcm;
    }

    @Override
    public Boolean getExcluido() {
        return excluido;
    }

    @Override
    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }
}
