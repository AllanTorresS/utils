package ipp.aci.boleia.dominio.vo.salesforce;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Vo com os dados de um contato salesforce.
 *
 * @author pedro.silva
 */
public class ContatoSalesforceVo {
    @JsonProperty("Id")
    private String idSalesforce;
    @JsonProperty("Name")
    private String nome;

    public String getIdSalesforce() {
        return idSalesforce;
    }

    public void setIdSalesforce(String idSalesforce) {
        this.idSalesforce = idSalesforce;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
