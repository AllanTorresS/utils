package ipp.aci.boleia.dominio.vo.salesforce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representa um documento de anexo do salesforce.
 *
 * @author pedro.silva
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentoSalesforceVo {

    @JsonProperty("Id")
    private String idSalesforce;
    @JsonProperty("Title")
    private String nome;
    @JsonProperty("FileExtension")
    private String extensao;

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

    public String getExtensao() {
        return extensao;
    }

    public void setExtensao(String extensao) {
        this.extensao = extensao;
    }
}
