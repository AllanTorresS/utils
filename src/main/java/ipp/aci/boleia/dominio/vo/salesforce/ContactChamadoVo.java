package ipp.aci.boleia.dominio.vo.salesforce;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * VO com as informações do Contact do salesforce.
 *
 * @author rafael.cunha
 */
public class ContactChamadoVo {

    @JsonProperty("IdExterno__c")
    private String idExterno;

    /**
     * Construtor default
     */
    public ContactChamadoVo() {
    }

    public ContactChamadoVo(String idExterno) {
        this.idExterno = idExterno;
    }

    public String getIdExterno() {
        return idExterno;
    }

    public void setIdExterno(String idExterno) {
        this.idExterno = idExterno;
    }
}
