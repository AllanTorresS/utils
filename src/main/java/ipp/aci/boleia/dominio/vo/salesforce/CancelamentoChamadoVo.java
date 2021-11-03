package ipp.aci.boleia.dominio.vo.salesforce;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * VO para mapear a requisição da integração com a API de cancelamento de chamados do salesforce.
 *
 * @author pedro.silva
 */
public class CancelamentoChamadoVo {
    @JsonProperty("Status")
    private String status;

    @JsonProperty("MotivoCancelamento__c")
    private String motivoCancelamento;

    @JsonProperty("DescricaoDeCancelamento__c")
    private String descricaoCancelamento;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public void setMotivoCancelamento(String motivoCancelamento) {
        this.motivoCancelamento = motivoCancelamento;
    }

    public String getDescricaoCancelamento() {
        return descricaoCancelamento;
    }

    public void setDescricaoCancelamento(String descricaoCancelamento) {
        this.descricaoCancelamento = descricaoCancelamento;
    }
}
