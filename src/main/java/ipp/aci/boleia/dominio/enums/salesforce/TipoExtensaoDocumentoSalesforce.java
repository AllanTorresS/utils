package ipp.aci.boleia.dominio.enums.salesforce;

/**
 * Enumera os tipos de extensão de documentos do salesforce manipulados pelo sistema.
 *
 * @author pedro.silva
 */
public enum TipoExtensaoDocumentoSalesforce {

    JPG("jpg", "image/jpg"),
    JPEG("jpeg", "image/jpeg"),
    PNG("png", "image/png"),
    PDF("pdf", "application/pdf");

    private String extensao;
    private String contentType;

    /**
     * Construtor do enum.
     *
     * @param extensao String para representar a extensão.
     * @param contentType Content type da extensão.
     */
    TipoExtensaoDocumentoSalesforce(String extensao, String contentType) {
        this.extensao = extensao;
        this.contentType = contentType;
    }

    public String getExtensao() {
        return extensao;
    }

    public String getContentType() {
        return contentType;
    }

    /**
     * Retorna um item do enum a partir de uma extensão de arquivo.
     *
     * @param extensao Extensão usada na busca.
     * @return Item do enum encontrado.
     */
    public static TipoExtensaoDocumentoSalesforce obterPorExtensao(String extensao) {
        for(TipoExtensaoDocumentoSalesforce tipoExtensao : TipoExtensaoDocumentoSalesforce.values()) {
            if(tipoExtensao.getExtensao().equals(extensao)) {
                return tipoExtensao;
            }
        }
        return null;
    }
}
