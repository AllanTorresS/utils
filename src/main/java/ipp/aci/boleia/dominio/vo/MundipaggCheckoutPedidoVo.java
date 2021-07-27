package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import ipp.aci.boleia.dominio.Frota;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe com infromacoes do Checkout do pedido Mundipagg
 */
public class MundipaggCheckoutPedidoVo {

    private static final String BOLETO_PAYMENT_METHOD = "boleto";
    private static final String PIX_PAYMENT_METHOD = "pix";

    @JsonProperty("accepted_payment_methods")
    private List<String> acceptedPaymentMethods;

    @JsonProperty("billing_address_editable")
    private boolean billingAddressEditable;

    @JsonProperty("billing_address")
    private MundipaggEnderecoVo billingAddress;

    private MundipaggBoletoCheckoutPedidoVo boleto;

    @JsonProperty("customer_editable")
    private Boolean customerEditable;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    @JsonProperty("success_url")
    private String successUrl;

    private MundipaggPixCheckoutPedidoVo pix;

    public MundipaggCheckoutPedidoVo() {

    }

    /**
     * Gera o checkout do pedido Mundipagg
     * @param frota A frota que faz o pedido
     * @param vencimentoBoleto A data de vencimetno do boleto
     */
    public MundipaggCheckoutPedidoVo(Frota frota, Date vencimentoBoleto) {
        List<String> paymentMethods = new ArrayList<String>();
        //paymentMethods.add(PIX_PAYMENT_METHOD);
        paymentMethods.add(BOLETO_PAYMENT_METHOD);

        acceptedPaymentMethods = paymentMethods;
        billingAddressEditable = false;
        billingAddress = new MundipaggEnderecoVo(frota);
        boleto = new MundipaggBoletoCheckoutPedidoVo(vencimentoBoleto);
        customerEditable = false;
        // cinco dias, em minutos
        expiresIn = 5 * 24 * 60;
        pix = new MundipaggPixCheckoutPedidoVo();
        successUrl = "/";
    }

    public List<String> getAcceptedPaymentMethods() {
        return acceptedPaymentMethods;
    }

    public void setAcceptedPaymentMethods(List<String> acceptedPaymentMethods) {
        this.acceptedPaymentMethods = acceptedPaymentMethods;
    }

    public boolean isBillingAddressEditable() {
        return billingAddressEditable;
    }

    public void setBillingAddressEditable(boolean billingAddressEditable) {
        this.billingAddressEditable = billingAddressEditable;
    }

    public MundipaggEnderecoVo getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(MundipaggEnderecoVo billingAddress) {
        this.billingAddress = billingAddress;
    }

    public MundipaggBoletoCheckoutPedidoVo getBoleto() {
        return boleto;
    }

    public void setBoleto(MundipaggBoletoCheckoutPedidoVo boleto) {
        this.boleto = boleto;
    }

    public Boolean getCustomerEditable() {
        return customerEditable;
    }

    public void setCustomerEditable(Boolean customerEditable) {
        this.customerEditable = customerEditable;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public MundipaggPixCheckoutPedidoVo getPix() { return pix; }

    public void setPix(MundipaggPixCheckoutPedidoVo pix) { this.pix = pix; }
}
 