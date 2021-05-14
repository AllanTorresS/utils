package ipp.aci.boleia.dominio.vo.salesforce;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * VO com as informações de um chamado do salesforce.
 *
 * @author pedro.silva
 */
public class ChamadoVo {
    @JsonProperty("CaseNumber")
    private String numero;
    @JsonProperty("CreatedDate")
    private String dataAbertura;
    @JsonProperty("CNPJPosto__c")
    private String cnpjPosto;
    @JsonProperty("CNPJFrota__c")
    private String cnpjFrota;
    @JsonProperty("Motivo__c")
    private String motivo;
    @JsonProperty("Status")
    private String status;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(String dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getCnpjPosto() {
        return cnpjPosto;
    }

    public void setCnpjPosto(String cnpjPosto) {
        this.cnpjPosto = cnpjPosto;
    }

    public String getCnpjFrota() {
        return cnpjFrota;
    }

    public void setCnpjFrota(String cnpjFrota) {
        this.cnpjFrota = cnpjFrota;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
