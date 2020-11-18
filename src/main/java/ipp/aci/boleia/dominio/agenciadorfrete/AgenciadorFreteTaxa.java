package ipp.aci.boleia.dominio.agenciadorfrete;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Date;
/**
 * Representa a tabela de Taxa
 */
@Entity
@Audited
@Table(name = "AGENCIADOR_FRETE_TAXA")
public class AgenciadorFreteTaxa implements IPersistente {

    @Id
    @Column(name = "CD_AGENCIADOR_FRETE_TAXA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AGENCIADOR_FRETE_TAXA")
    @SequenceGenerator(name = "SEQ_AGENCIADOR_FRETE_TAXA", sequenceName = "SEQ_AGENCIADOR_FRETE_TAXA", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_AGENCIADOR")
    private AgenciadorFrete agenciadorFrete;

    @Column(name = "VA_TX_SAQUE_AG_REAIS")
    private BigDecimal taxaSaqueAgenciadorReais;

    @Column(name = "VA_TX_SAQUE_REAIS")
    private BigDecimal taxaSaqueReais;

    @Column(name = "VA_TX_SAQUE_AG_PERCENTUAL")
    private BigDecimal taxaSaqueAgenciadorPercentual;

    @Column(name = "VA_TX_SAQUE_PERCENTUAL")
    private BigDecimal taxaSaquePercentual;

    @Column(name = "VA_FEE_PARCEIRO")
    private BigDecimal taxaFeeParceiro;

    @Column(name = "DT_VIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataVigencia;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    public Long getId() {
        return id;
    }

    public boolean definidaEmPercentual() {
        return taxaSaqueAgenciadorPercentual != null || taxaSaquePercentual != null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AgenciadorFrete getAgenciadorFrete() {
        return agenciadorFrete;
    }

    public void setAgenciadorFrete(AgenciadorFrete agenciadorFrete) {
        this.agenciadorFrete = agenciadorFrete;
    }

    public BigDecimal getTaxaSaqueAgenciadorReais() {
        return taxaSaqueAgenciadorReais;
    }

    public void setTaxaSaqueAgenciadorReais(BigDecimal taxaSaqueAgenciadorReais) {
        this.taxaSaqueAgenciadorReais = taxaSaqueAgenciadorReais;
    }

    public BigDecimal getTaxaSaqueReais() {
        return taxaSaqueReais;
    }

    public void setTaxaSaqueReais(BigDecimal taxaSaqueReais) {
        this.taxaSaqueReais = taxaSaqueReais;
    }

    public BigDecimal getTaxaSaqueAgenciadorPercentual() {
        return taxaSaqueAgenciadorPercentual;
    }

    public void setTaxaSaqueAgenciadorPercentual(BigDecimal taxaSaqueAgenciadorPercentual) {
        this.taxaSaqueAgenciadorPercentual = taxaSaqueAgenciadorPercentual;
    }

    public BigDecimal getTaxaSaquePercentual() {
        return taxaSaquePercentual;
    }

    public void setTaxaSaquePercentual(BigDecimal taxaSaquePercentual) {
        this.taxaSaquePercentual = taxaSaquePercentual;
    }

    public Date getDataVigencia() {
        return dataVigencia;
    }

    public void setDataVigencia(Date dataVigencia) {
        this.dataVigencia = dataVigencia;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public BigDecimal getTaxaFeeParceiro() {
        return taxaFeeParceiro;
    }

    public void setTaxaFeeParceiro(BigDecimal taxaFeeParceiro) {
        this.taxaFeeParceiro = taxaFeeParceiro;
    }
}
