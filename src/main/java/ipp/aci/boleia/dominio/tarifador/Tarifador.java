package ipp.aci.boleia.dominio.tarifador;

import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @AuditJoinTable(name = "TARIFADOR_FROTA_AUD")
    @JoinTable(name="TARIFADOR_FROTA", joinColumns={@JoinColumn(name="CD_TARIFADOR")}, inverseJoinColumns={@JoinColumn(name="CD_FROTA")})
    private List<Frota> frotas;

    @NotNull
    @Column(name = "DT_INI_VIGENCIA")
    private Date dataInicioVigencia;

    @Column(name = "DT_FIM_VIGENCIA")
    private Date dataFimVigencia;

    @NotNull
    @Column(name = "ID_TIPO_TAXA")
    private Integer tipoTaxa;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tarifador")
    private List<TaxaTarifador> taxas;

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

    public List<Frota> getFrotas() {
        return frotas;
    }

    public void setFrotas(List<Frota> frotas) {
        this.frotas = frotas;
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

    public List<TaxaTarifador> getTaxas() {
        return taxas;
    }

    public void setTaxas(List<TaxaTarifador> taxas) {
        this.taxas = taxas;
    }
}
