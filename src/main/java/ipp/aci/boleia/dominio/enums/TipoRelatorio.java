package ipp.aci.boleia.dominio.enums;

/**
 * Tipo de arquivo a ser armazenado no storage
 */
public enum TipoRelatorio {

    PDF(".pdf", "application/pdf"),
    EXCEL(".xls", "application/vnd.ms-excel");

    private final String extensao;
    private final String mimeType;

    /**
     * Construtor do enum
     * @param extensao Extensão de arquivo do relatório
     * @param mimeType Mime type do relatório
     */
    TipoRelatorio(String extensao, String mimeType) {
        this.extensao = extensao;
        this.mimeType = mimeType;
    }

    public String getExtensao() {
        return extensao;
    }

    public String getMimeType() {
        return mimeType;
    }

}
