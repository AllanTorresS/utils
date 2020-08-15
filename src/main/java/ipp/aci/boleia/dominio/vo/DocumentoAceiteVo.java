package ipp.aci.boleia.dominio.vo;

/**
 * Objeto para apresentacao da entidade DocumentoAceite
 */
public class DocumentoAceiteVo {

    private Long documentoId;
    private Boolean houveAceite;
    private Integer idSistema;

    /**
     * Serializacao JSON
     */
    public DocumentoAceiteVo() {
    }

    public DocumentoAceiteVo(Long documentoId, Boolean houveAceite, Integer idSistema) {
        this.documentoId = documentoId;
        this.houveAceite = houveAceite;
        this.idSistema = idSistema;
    }

    public Boolean getHouveAceite() {
        return houveAceite;
    }

    public void setHouveAceite(Boolean houveAceite) {
        this.houveAceite = houveAceite;
    }

    public Long getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(Long documentoId) {
        this.documentoId = documentoId;
    }

    public Integer getIdSistema() { return idSistema; }

    public void setIdSistema(Integer idSistema) { this.idSistema = idSistema; }
}
