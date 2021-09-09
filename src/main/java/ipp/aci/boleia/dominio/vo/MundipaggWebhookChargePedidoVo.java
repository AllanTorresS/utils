package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Classe com informacoes relacionadas sobre o meio de pagamento do pedido de credito enviado pelo webhook da Mundipagg
 */
public class MundipaggWebhookChargePedidoVo {

    @JsonProperty("payment_method")
    private String meioDePagamento;;

    @JsonProperty("paid_amount")
    private BigDecimal valorPago;

    private String status;


    public MundipaggWebhookChargePedidoVo() {
    }

    public String getMeioDePagamento() {
        return meioDePagamento;
    }

    public void setMeioDePagamento(String meioDePagamento) {
        this.meioDePagamento = meioDePagamento;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
 