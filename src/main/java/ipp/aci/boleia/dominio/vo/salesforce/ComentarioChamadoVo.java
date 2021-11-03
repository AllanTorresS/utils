package ipp.aci.boleia.dominio.vo.salesforce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Vo com as informações de um comentário de chamado do salesforce.
 *
 * @author pedro.silva
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComentarioChamadoVo {

    @JsonProperty("Id")
    private String idSalesforce;
    @JsonProperty("CommentBody")
    private String corpo;
    private String nomeAutor;
    private String emailAutor;
    @JsonProperty("CreatedDate")
    private String dataCriacao;

    public String getIdSalesforce() {
        return idSalesforce;
    }

    public void setIdSalesforce(String idSalesforce) {
        this.idSalesforce = idSalesforce;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public String getEmailAutor() {
        return emailAutor;
    }

    public void setEmailAutor(String emailAutor) {
        this.emailAutor = emailAutor;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    /**
     * Realiza o mapeamento das informações de criação do comentário.
     *
     * @param createdBy Mapa com as informações da criação.
     */
    @JsonProperty("CreatedBy")
    private void mapearCreatedBy(Map<String, Object> createdBy) {
        this.nomeAutor = (String) createdBy.get("Name");
        this.emailAutor = (String) createdBy.get("Email");
    }
}
