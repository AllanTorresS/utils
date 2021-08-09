package ipp.aci.boleia.dominio.vo.salesforce;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Vo com as informações de um anexo de chamado do salesforce.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnexoChamadoVo {

    @JsonProperty("ContentDocumentId")
    private String idSalesforce;
    private String latestPublishedVersionId;
    private String titulo;
    private String extensaoArquivo;
    private String nomeAutor;
    private String emailAutor;
    private String dataCriacao;

    public String getIdSalesforce() {
        return idSalesforce;
    }

    public void setIdSalesforce(String idSalesforce) {
        this.idSalesforce = idSalesforce;
    }

    public String getLatestPublishedVersionId() {
        return latestPublishedVersionId;
    }

    public void setLatestPublishedVersionId(String latestPublishedVersionId) {
        this.latestPublishedVersionId = latestPublishedVersionId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getExtensaoArquivo() {
        return extensaoArquivo;
    }

    public void setExtensaoArquivo(String extensaoArquivo) {
        this.extensaoArquivo = extensaoArquivo;
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
     * @param contentDocument Mapa com as informações da criação.
     */
    @JsonProperty("ContentDocument")
    private void mapearContentDocument(Map<String, Object> contentDocument) {
        this.titulo = (String) contentDocument.get("Title");
        this.extensaoArquivo = (String) contentDocument.get("FileExtension");
        this.latestPublishedVersionId = (String) contentDocument.get("LatestPublishedVersionId");
        mapearOwner((LinkedHashMap) contentDocument.get("Owner"));
        mapearLatestPublishedVersion((LinkedHashMap) contentDocument.get("LatestPublishedVersion"));
    }

        /**
     * Realiza o mapeamento das informações de criação do comentário.
     *
     * @param contentDocument Mapa com as informações da criação.
     */
    @JsonProperty("Owner")
    private void mapearOwner(Map<String, Object> owner) {
        this.nomeAutor = (String) owner.get("Name");
        this.emailAutor = (String) owner.get("Email");
    }

    /**
     * Realiza o mapeamento das informações de criação do comentário.
     *
     * @param contentDocument Mapa com as informações da criação.
     */
    @JsonProperty("LatestPublishedVersion")
    private void mapearLatestPublishedVersion(Map<String, Object> version) {
        this.dataCriacao = (String) version.get("CreatedDate");
    }
}
