package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import ipp.aci.boleia.dominio.PedidoCreditoFrota;
import ipp.aci.boleia.dominio.enums.StatusPedidoCredito;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Classe com informacoes relacionadas ao pedido de credito enviado pelo webhook da Mundipagg
 */
public class MundipaggWebhookRespostaPedidoVo {

    private String id;
    private String code;
    private String status;
    @JsonProperty("amount")
    private BigDecimal valorPago;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public List<MundipaggWebhookChargePedidoVo> getCharges() {
        return charges;
    }

    public void setCharges(List<MundipaggWebhookChargePedidoVo> charges) {
        this.charges = charges;
    }
}
 