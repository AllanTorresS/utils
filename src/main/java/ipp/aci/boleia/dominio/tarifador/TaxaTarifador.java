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
@Table(name = "TAXA_TARIFADOR")
public class TaxaTarifador implements IPersistente {

    @Id
    @Column(name = "CD_TAXA_TARIFADOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TAXA_TARIFADOR")
    @SequenceGenerator(name = "SEQ_TAXA_TARIFADOR", sequenceName = "SEQ_TAXA_TARIFADOR", allocationSize = 1)
    private Long id;

    @Column(name = "ID_TIPO_CALCULO")
    private Integer tipoCalculoTaxa;

    @Column(name = "VA_INICIO_FAIXA_REAL")
    private BigDecimal inicioFaixaReais;

    @Column(name = "VA_FIM_FAIXA_REAL")
    private BigDecimal fimFaixaReais;

    @Column(name = "VA_INICIO_FAIXA_LIT")
    private BigDecimal inicioFaixaLitragem;

    @Column(name = "VA_FIM_FAIXA_LIT")
    private BigDecimal fimFaixaLitragem;

    @Column(name = "VA_PERCENTUAL")
    private BigDecimal valorPercentual;

    @Column(name = "VA_REAIS")
    private BigDecimal valorEmReais;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "CD_TARIFADOR")
    private Tarifador tarifador;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "taxaAplicada")
    private List<Cobranca> cobrancas;

    public TaxaTarifador() {
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

    public Integer getTipoCalculoTaxa() {
        return tipoCalculoTaxa;
    }

    public void setTipoCalculoTaxa(Integer tipoCalculoTaxa) {
        this.tipoCalculoTaxa = tipoCalculoTaxa;
    }

    public BigDecimal getInicioFaixaLitragem() {
        return inicioFaixaLitragem;
    }

    public void setInicioFaixaLitragem(BigDecimal inicioFaixaLitragem) {
        this.inicioFaixaLitragem = inicioFaixaLitragem;
    }

    public BigDecimal getFimFaixaLitragem() {
        return fimFaixaLitragem;
    }

    public void setFimFaixaLitragem(BigDecimal fimFaixaLitragem) {
        this.fimFaixaLitragem = fimFaixaLitragem;
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

    public Tarifador getTarifador() {
        return tarifador;
    }

    public void setTarifador(Tarifador tarifador) {
        this.tarifador = tarifador;
    }

    public List<Cobranca> getCobrancas() {
        return cobrancas;
    }

    public void setCobrancas(List<Cobranca> cobrancas) {
        this.cobrancas = cobrancas;
    }
}
