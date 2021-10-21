package ipp.aci.boleia.dominio.vo.salesforce;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * VO com as informações da Account do salesforce.
 *
 * @author rafael.cunha
 */
public class AccountChamadoVo {

    @JsonProperty("ID_Externo__c")
    private String idExterno;

    /**
     * Construtor default
     */
    public AccountChamadoVo() {
    }

    /**
     * Construtor do Vo
     * 
     * @param idExterno Identificador externo do contato
     **/
    public AccountChamadoVo(String idExterno) {
        this.idExterno = idExterno;
    }

    public String getIdExterno() {
        return idExterno;
    }

    public void setIdExterno(String idExterno) {
        this.idExterno = idExterno;
    }
}
