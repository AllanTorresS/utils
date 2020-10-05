package ipp.aci.boleia.dominio.agenciadorfrete;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa a tabela de pedido Motorista autonomo
 */
@Entity
@Audited
@Table(name = "AG_FRETE_SAQUE")
public class Saque implements IPersistente {

    @Id
    @Column(name = "CD_SAQUE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AG_FRETE_SAQUE")
    @SequenceGenerator(name = "SEQ_AG_FRETE_SAQUE", sequenceName = "SEQ_AG_FRETE_SAQUE", allocationSize = 1)
    private Long id;

    @Column(name = "VA_SOLICITADO")
    private BigDecimal valorSolicitado;

    @Column(name = "VA_TAXA")
    private BigDecimal taxa;

    @Column(name = "VA_TAXA_AG_FRETE")
    private BigDecimal taxaAgenciadorFrete;

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

    public BigDecimal getValorSolicitado() {
        return valorSolicitado;
    }

    public void setValorSolicitado(BigDecimal valorSolicitado) {
        this.valorSolicitado = valorSolicitado;
    }

    public BigDecimal getTaxa() {
        return taxa;
    }

    public void setTaxa(BigDecimal taxa) {
        this.taxa = taxa;
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

    public BigDecimal getTaxaAgenciadorFrete() {
        return taxaAgenciadorFrete;
    }

    public void setTaxaAgenciadorFrete(BigDecimal taxaAgenciadorFrete) {
        this.taxaAgenciadorFrete = taxaAgenciadorFrete;
    }
}
