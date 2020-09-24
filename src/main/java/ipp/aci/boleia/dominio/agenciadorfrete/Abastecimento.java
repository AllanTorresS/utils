package ipp.aci.boleia.dominio.agenciadorfrete;

import ipp.aci.boleia.dominio.TipoCombustivel;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa a tabela de abastecimento de um Motorista autonomo
 */
@Entity
@Audited
@Table(name = "AG_FRETE_ABASTECIMENTO")
public class Abastecimento implements IPersistente {

    @Id
    @Column(name = "CD_ABASTECIMENTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AG_FRETE_ABAST")
    @SequenceGenerator(name = "SEQ_AG_FRETE_ABAST", sequenceName = "SEQ_AG_FRETE_ABAST", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "VA_LITRAGEM")
    private BigDecimal litragem;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CD_TIPO_COMBUSTIVEL")
    private TipoCombustivel combustivel;

    @NotNull
    @Column(name = "VA_COMB")
    private BigDecimal precoCombustivel;

    @Column(name = "VA_FEE")
    private BigDecimal taxaFee;

    @Column(name = "VA_MDR")
    @DecimalMin("00.00")
    @DecimalMax("99.99")
    private BigDecimal mdr;

    @NotNull
    @Column(name = "DT_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Column(name = "NO_VERSAO")
    @Version
    private Long versao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getLitragem() {
        return litragem;
    }

    public void setLitragem(BigDecimal litragem) {
        this.litragem = litragem;
    }

    public TipoCombustivel getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(TipoCombustivel combustivel) {
        this.combustivel = combustivel;
    }

    public BigDecimal getPrecoCombustivel() {
        return precoCombustivel;
    }

    public void setPrecoCombustivel(BigDecimal precoCombustivel) {
        this.precoCombustivel = precoCombustivel;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
    
    public BigDecimal getTaxaFee() {
        return taxaFee;
    }

    public void setTaxaFee(BigDecimal taxaFee) {
        this.taxaFee = taxaFee;
    }

    public BigDecimal getMdr() {
        return mdr;
    }

    public void setMdr(BigDecimal mdr) {
        this.mdr = mdr;
    }
}
