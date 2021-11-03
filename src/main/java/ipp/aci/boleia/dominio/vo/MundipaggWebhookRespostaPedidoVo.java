package ipp.aci.boleia.dominio.vo;

import java.util.List;

/**
 * Classe com informacoes relacionadas ao pedido de credito enviado pelo webhook da Mundipagg
 */
public class MundipaggWebhookRespostaPedidoVo {

    private String id;
    private String code;
    private List<MundipaggWebhookChargePedidoVo> charges;

    public MundipaggWebhookRespostaPedidoVo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<MundipaggWebhookChargePedidoVo> getCharges() {
        return charges;
    }

    public void setCharges(List<MundipaggWebhookChargePedidoVo> charges) {
        this.charges = charges;
    }
}
 