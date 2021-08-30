package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe com informacoes relacionadas sobre o meio de pagamento do pedido de credito enviado pelo webhook da Mundipagg
 */
public class MundipaggWebhookChargePedidoVo {

    @JsonProperty("payment_method")
    public String meioDePagamento;;


    public MundipaggWebhookChargePedidoVo() {
    }

    public String getMeioDePagamento() {
        return meioDePagamento;
    }

    public void setMeioDePagamento(String meioDePagamento) {
        this.meioDePagamento = meioDePagamento;
    }
}
 