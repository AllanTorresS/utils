package ipp.aci.boleia.dominio.tarifador;

import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Taxa do Tarifador
 */
@Entity
@Audited
@Table(name = "TARIFADOR")
public class Tarifador implements IPersistente {

    @Id
    @Column(name = "CD_TARIFADOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TARIFADOR")
    @SequenceGenerator(name = "SEQ_TARIFADOR", sequenceName = "SEQ_TARIFADOR", allocationSize = 1)
    private Long id;

    @Column(name = "NM_TARIFADOR")
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "CD_FROTA")
    private Frota frota;

    @Column(name = "DT_INI_VIGENCIA")
    private Date dataInicioVigencia;

    @Column(name = "DT_FIM_VIGENCIA")
    private Date dataFimVigencia;

    @Column(name = "ID_TIPO_TAXA")
    private Integer tipoTaxa;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tarifador")
    private List<TaxaTarifador> faixas;

    public Tarifador() {
        //construtor default
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public Date getDataInicioVigencia() {
        return dataInicioVigencia;
    }

    public void setDataInicioVigencia(Date dataInicioVigencia) {
        this.dataInicioVigencia = dataInicioVigencia;
    }

    public Date getDataFimVigencia() {
        return dataFimVigencia;
    }

    public void setDataFimVigencia(Date dataFimVigencia) {
        this.dataFimVigencia = dataFimVigencia;
    }

    public Integer getTipoTaxa() {
        return tipoTaxa;
    }

    public void setTipoTaxa(Integer tipoTaxa) {
        this.tipoTaxa = tipoTaxa;
    }

    public List<TaxaTarifador> getFaixas() {
        return faixas;
    }

    public void setFaixas(List<TaxaTarifador> faixas) {
        this.faixas = faixas;
    }
}
