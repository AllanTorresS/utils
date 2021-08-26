package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import ipp.aci.boleia.dominio.Frota;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe com informações do Checkout do pedido enviado pelo Webhook da Mundipagg
 */
public class MundipaggWebhookCheckoutPedidoVo {

    @JsonProperty("data")
    private  MundipaggWebhookRespostaPedidoVo mundipaggWebhookRespostaPedidoVo;

    public MundipaggWebhookCheckoutPedidoVo() {
    }

    public MundipaggWebhookRespostaPedidoVo getMundipaggWebhookRespostaPedidoVo() {
        return mundipaggWebhookRespostaPedidoVo;
    }

    public void setMundipaggWebhookRespostaPedidoVo(MundipaggWebhookRespostaPedidoVo mundipaggWebhookRespostaPedidoVo) {
        this.mundipaggWebhookRespostaPedidoVo = mundipaggWebhookRespostaPedidoVo;
    }
}
 