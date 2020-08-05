package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

/**
 * Representa os valores da transacao consolidada
 */
@Entity
@Audited
@Table(name = "TRANS_CONSOL_VR")
public class TransacaoConsolidadaValores implements IPersistente {

    private static final long serialVersionUID = 7224431090214282950L;

    @Id
    @Column(name = "CD_TRANS_CONSOL_VR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANS_CONSOL_VR")
    @SequenceGenerator(name = "SEQ_TRANS_CONSOL_VR", sequenceName = "SEQ_TRANS_CONSOL_VR", allocationSize = 1)
    private Long id;

    @DecimalMin("-999999999999.9999")
    @DecimalMax("999999999999.9999")
    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_TOTAL")
    private BigDecimal valorTotal;

    @Column(name = "VR_REEMB")
    private BigDecimal valorReembolso;

    @Column(name = "VR_DESC")
    private BigDecimal valorDesconto;

    @Column(name = "VR_FATURAMENTO")
    private BigDecimal valorFaturamento;

    @Column(name = "MDR")
    private BigDecimal mdr;

    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_TOTAL_NF")
    private BigDecimal valorTotalNotaFiscal;

    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_EMITIDO_NF")
    private BigDecimal valorEmitidoNotaFiscal;

    @DecimalMin("-999999999999.9999")
    @DecimalMax("999999999999.9999")
    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_DESCONTO_ABASTECIMENTOS")
    private BigDecimal valorDescontoAbastecimentos;

    @DecimalMin("-999999999999.9999")
    @DecimalMax("999999999999.9999")
    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_DESCONTO_NOTA_FISCAL")
    private BigDecimal valorDescontoNotaFiscal;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorReembolso() {
        return valorReembolso;
    }

    public void setValorReembolso(BigDecimal valorReembolso) {
        this.valorReembolso = valorReembolso;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public BigDecimal getValorFaturamento() {
        return valorFaturamento;
    }

    public void setValorFaturamento(BigDecimal valorFaturamento) {
        this.valorFaturamento = valorFaturamento;
    }

    public BigDecimal getMdr() {
        return mdr;
    }

    public void setMdr(BigDecimal mdr) {
        this.mdr = mdr;
    }

    public BigDecimal getValorTotalNotaFiscal() {
        return valorTotalNotaFiscal;
    }

    public void setValorTotalNotaFiscal(BigDecimal valorTotalNotaFiscal) {
        this.valorTotalNotaFiscal = valorTotalNotaFiscal;
    }

    public BigDecimal getValorEmitidoNotaFiscal() {
        return valorEmitidoNotaFiscal;
    }

    public void setValorEmitidoNotaFiscal(BigDecimal valorEmitidoNotaFiscal) {
        this.valorEmitidoNotaFiscal = valorEmitidoNotaFiscal;
    }

    public BigDecimal getValorDescontoAbastecimentos() {
        return valorDescontoAbastecimentos != null ? valorDescontoAbastecimentos : BigDecimal.ZERO;
    }

    public void setValorDescontoAbastecimentos(BigDecimal valorDescontoAbastecimentos) {
        this.valorDescontoAbastecimentos = valorDescontoAbastecimentos;
    }

    public BigDecimal getValorDescontoNotaFiscal() {
        return valorDescontoNotaFiscal;
    }

    public void setValorDescontoNotaFiscal(BigDecimal valorDescontoNotaFiscal) {
        this.valorDescontoNotaFiscal = valorDescontoNotaFiscal;
    }
}
