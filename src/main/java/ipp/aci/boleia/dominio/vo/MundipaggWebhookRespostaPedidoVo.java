package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.PedidoCreditoFrota;
import ipp.aci.boleia.dominio.enums.StatusPedidoCredito;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;

import java.math.RoundingMode;

/**
 * Classe com informacoes relacionadas ao pedido de credito enviado pelo webhook da Mundipagg
 */
public class MundipaggWebhookRespostaPedidoVo {

    private String id;
    private String code;
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
 