package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.StatusNotaFiscalAbastecimento;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Entidade que mantém as informações de um item de ajuste de cobrança.
 */
@Audited
@Entity
@Table(name = "ITEM_AJUSTE_COBRANCA")
public class ItemAjusteCobranca implements IPersistente {

    private static final long serialVersionUID = -464651722623946110L;

    @Id
    @Column(name = "CD_ITEM_AJUSTE_COBRANCA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEM_AJUSTE_COBRANCA")
    @SequenceGenerator(name = "SEQ_ITEM_AJUSTE_COBRANCA", sequenceName = "SEQ_ITEM_AJUSTE_COBRANCA", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_AJUSTE_COBRANCA")
    private AjusteCobranca ajusteCobranca;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_AUTORIZACAO_PAGAMENTO")
    private AutorizacaoPagamento autorizacaoPagamento;

    @NotNull
    @Column(name = "ID_STATUS_NF")
    private Integer statusNotaFiscal;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public AjusteCobranca getAjusteCobranca() {
        return ajusteCobranca;
    }

    public void setAjusteCobranca(AjusteCobranca ajusteCobranca) {
        this.ajusteCobranca = ajusteCobranca;
    }

    public AutorizacaoPagamento getAutorizacaoPagamento() {
        return autorizacaoPagamento;
    }

    public void setAutorizacaoPagamento(AutorizacaoPagamento autorizacaoPagamento) {
        this.autorizacaoPagamento = autorizacaoPagamento;
    }

    public Integer getStatusNotaFiscal() {
        return statusNotaFiscal;
    }

    public void setStatusNotaFiscal(Integer statusNotaFiscal) {
        this.statusNotaFiscal = statusNotaFiscal;
    }

    /**
     * Retorna o valor considerado de ajuste para esse item de ajuste.
     *
     * @return valor de ajuste para o item.
     */
    @Transient
    public BigDecimal obterValorItemAjuste() {
        if (autorizacaoPagamento.estaAutorizado() && autorizacaoPagamento.getValorTotal().compareTo(BigDecimal.ZERO) > 0 && !StatusNotaFiscalAbastecimento.EMITIDA.equals(StatusNotaFiscalAbastecimento.obterPorValor(statusNotaFiscal))) {
            return autorizacaoPagamento.getValorTotal().negate();
        }
        return autorizacaoPagamento.getValorTotal();
    }
}
