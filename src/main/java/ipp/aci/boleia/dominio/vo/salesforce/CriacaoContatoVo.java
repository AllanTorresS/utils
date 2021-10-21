package ipp.aci.boleia.dominio.vo.salesforce;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Vo com as informações usadas para criar um contato no salesforce.
 *
 * @author pedro.silva
 */
public class CriacaoContatoVo {
    @JsonProperty("FirstName")
    private String nome;
    @JsonProperty("LastName")
    private String sobrenome;
    @JsonProperty("CPF__c")
    private String cpf;
    @JsonIgnore
    private String cnpj;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * Obtém o identificador externo do contato
     */
    @JsonProperty("IdExterno__c")
    public String obterIdExterno() {
        return cpf.concat(cnpj);
    }
}
