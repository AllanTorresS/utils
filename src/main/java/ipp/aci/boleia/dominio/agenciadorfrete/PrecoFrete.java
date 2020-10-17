package ipp.aci.boleia.dominio.agenciadorfrete;

import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.PrecoBase;
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
@Table(name = "AG_FRETE_PRECO")
public class PrecoFrete implements IPersistente {

    @Id
    @Column(name = "CD_PRECO_FRETE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AG_FRETE_PRECO")
    @SequenceGenerator(name = "SEQ_AG_FRETE_PRECO", sequenceName = "SEQ_AG_FRETE_PRECO", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "VA_PRECO_FRETE")
    private BigDecimal precoFrete;

    @NotNull
    @Column(name = "VA_PRECO_BASE")
    private BigDecimal precoBase;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda posto;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CD_TIPO_COMBUSTIVEL")
    private TipoCombustivel combustivel;

    @NotNull
    @Column(name = "DT_VIGENCIA")
    private Date dataVigencia;

    @Column(name = "DT_EXCLUSAO")
    private Date dataExclusao;

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

    public BigDecimal getPrecoFrete() {
        return precoFrete;
    }

    public void setPrecoFrete(BigDecimal precoFrete) {
        this.precoFrete = precoFrete;
    }

    public BigDecimal getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(BigDecimal precoBase) {
        this.precoBase = precoBase;
    }

    public PontoDeVenda getPosto() {
        return posto;
    }

    public void setPosto(PontoDeVenda posto) {
        this.posto = posto;
    }

    public TipoCombustivel getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(TipoCombustivel combustivel) {
        this.combustivel = combustivel;
    }

    public Date getDataVigencia() {
        return dataVigencia;
    }

    public void setDataVigencia(Date dataVigencia) {
        this.dataVigencia = dataVigencia;
    }

    public Date getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(Date dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}
