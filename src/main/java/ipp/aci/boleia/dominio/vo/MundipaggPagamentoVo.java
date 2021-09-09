package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import ipp.aci.boleia.dominio.Frota;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Classe com informacoes relacionadas ao pagamento do boleto Mundipagg
 */
public class MundipaggPagamentoVo {

    private static final String CHECKOUT_PAYMENT_METHOD = "checkout";
    private static final String BRL_CURRENCY = "BRL";

    @JsonProperty("payment_method")
    private String paymentMethod;
    private String currency;
    private Integer amount;
    private MundipaggCheckoutPedidoVo checkout;
    private String code;

    public MundipaggPagamentoVo() {

    }

    /**
     * Construtor do pagamento Mundipagg
     * @param frota A frota que realiza o pagamento
     * @param valor O valor do pagamento
     * @param vencimentoBoleto A data de vencimento do boleto
     * @param code Flag para utilização do ambiente de testes da mundipagg
     */
    public MundipaggPagamentoVo(Frota frota, BigDecimal valor, Date vencimentoBoleto, String code) {
        paymentMethod = CHECKOUT_PAYMENT_METHOD;
        currency = BRL_CURRENCY;
        amount = valor.multiply(new BigDecimal(100)).intValue();;
        checkout = new MundipaggCheckoutPedidoVo(frota, vencimentoBoleto);
        this.code = code;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public MundipaggCheckoutPedidoVo getCheckout() {
        return checkout;
    }

    public void setCheckout(MundipaggCheckoutPedidoVo checkout) {
        this.checkout = checkout;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
 