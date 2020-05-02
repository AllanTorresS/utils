package ipp.aci.boleia.dominio.enums;

/**
 * Tipo de arquivo a ser armazenado no storage
 */
public enum TipoArquivo {

    FOTO_MOTORISTA("foto_motorista/",".jpg", "image/jpeg", false),
    FOTO_PONTO_VENDA("foto_ponto_venda/",".jpg", "image/jpeg", true),
    FOTO_HODOMETRO_HORIMETRO("foto_hodometro_horimetro/",".jpg", "image/jpeg", false),
    NOTA_FISCAL("nota_fiscal/",".xml", "text/xml", false),
    JUSTIFICATIVA_NOTA("justificativa_nota_fiscal/",".pdf", "application/pdf", false),
    RELATORIO_48_HORAS_XLSX("relatorio_48_horas/", ".xlsx", "application/vnd.ms-excel", false),
    RELATORIO_48_HORAS_TXT("relatorio_48_horas/",".txt","text/plain", false),
    NOTA_FISCAL_ARMAZEM("nfe_anexo_armazem/", ".xml", "text/xml", false);

    public static final String PREFIXO_BASE64 = "base64,";

    private final String prefixo;
    private final String sufixo;
    private final String mimeType;
    private final boolean nomeArquivoAutoContido;

    TipoArquivo(String prefixo, String sufixo, String mimeType, boolean nomeArquivoAutoContido) {
        this.prefixo = prefixo;
        this.sufixo = sufixo;
        this.mimeType = mimeType;
        this.nomeArquivoAutoContido = nomeArquivoAutoContido;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public String getSufixo() {
        return sufixo;
    }

    public boolean isNomeArquivoAutoContido() {
        return nomeArquivoAutoContido;
    }

    /**
     * Monta o caminho de acesso do arquivo
     * @param nome nome arquivo
     * @return caminho de acesso
     */
    public String montarCaminhoAcesso(String nome) {
        return prefixo + nome + sufixo;
    }
}
