package ipp.aci.boleia.dominio.enums;

/**
 * Tipo de arquivo a ser armazenado no storage
 */
public enum TipoRelatorio {

    PDF(".pdf", "application/pdf"),
    EXCEL(".xls", "application/vnd.ms-excel");

    private final String extensao;
    private final String mimeType;

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
