package ipp.aci.boleia.dominio.tarifador;

import ipp.aci.boleia.dominio.Cobranca;
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
import java.math.BigDecimal;
import java.util.List;

/**
 * Representa a tabela de Faixa de Taxa do Tarifador
 */
@Entity
@Audited
@Table(name = "FAIXA_TAXA_TARIFADOR")
public class FaixaTaxaTarifador implements IPersistente {

    @Id
    @Column(name = "CD_FAIXA_TAXA_TARIFADOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FAIXA_TAXA_TARIFADOR")
    @SequenceGenerator(name = "SEQ_FAIXA_TAXA_TARIFADOR", sequenceName = "SEQ_FAIXA_TAXA_TARIFADOR", allocationSize = 1)
    private Long id;

    @Column(name = "ID_TIPO_FAIXA")
    private Integer tipoFaixa;

    @Column(name = "VA_INICIO_FAIXA_REAL")
    private BigDecimal inicioFaixaReais;

    @Column(name = "VA_FIM_FAIXA_REAL")
    private BigDecimal fimFaixaReais;

    @Column(name = "VA_INICIO_FAIXA_PERCENT")
    private BigDecimal inicioFaixaPercentual;

    @Column(name = "VA_FIM_FAIXA_PERCENT")
    private BigDecimal fimFaixaPercentual;

    @Column(name = "VA_PERCENTUAL")
    private BigDecimal valorPercentual;

    @Column(name = "VA_REAIS")
    private BigDecimal valorEmReais;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "CD_TAXA_TARIFADOR")
    private TaxaTarifador taxa;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "faixaTarifadorAplicada")
    private List<Cobranca> cobrancas;

    public FaixaTaxaTarifador() {
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

    public Integer getTipoFaixa() {
        return tipoFaixa;
    }

    public void setTipoFaixa(Integer tipoFaixa) {
        this.tipoFaixa = tipoFaixa;
    }

    public BigDecimal getInicioFaixaPercentual() {
        return inicioFaixaPercentual;
    }

    public void setInicioFaixaPercentual(BigDecimal inicioFaixaPercentual) {
        this.inicioFaixaPercentual = inicioFaixaPercentual;
    }

    public BigDecimal getFimFaixaPercentual() {
        return fimFaixaPercentual;
    }

    public void setFimFaixaPercentual(BigDecimal fimFaixaPercentual) {
        this.fimFaixaPercentual = fimFaixaPercentual;
    }

    public BigDecimal getValorPercentual() {
        return valorPercentual;
    }

    public void setValorPercentual(BigDecimal valorPercentual) {
        this.valorPercentual = valorPercentual;
    }

    public BigDecimal getValorEmReais() {
        return valorEmReais;
    }

    public void setValorEmReais(BigDecimal valorEmReais) {
        this.valorEmReais = valorEmReais;
    }

    public BigDecimal getInicioFaixaReais() {
        return inicioFaixaReais;
    }

    public void setInicioFaixaReais(BigDecimal inicioFaixaReais) {
        this.inicioFaixaReais = inicioFaixaReais;
    }

    public BigDecimal getFimFaixaReais() {
        return fimFaixaReais;
    }

    public void setFimFaixaReais(BigDecimal fimFaixaReais) {
        this.fimFaixaReais = fimFaixaReais;
    }

    public TaxaTarifador getTaxa() {
        return taxa;
    }

    public void setTaxa(TaxaTarifador taxa) {
        this.taxa = taxa;
    }

    public List<Cobranca> getCobrancas() {
        return cobrancas;
    }

    public void setCobrancas(List<Cobranca> cobrancas) {
        this.cobrancas = cobrancas;
    }
}
