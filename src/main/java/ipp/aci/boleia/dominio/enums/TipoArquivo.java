package ipp.aci.boleia.dominio.enums;

/**
 * Tipo de arquivo a ser armazenado no storage
 */
public enum TipoArquivo {

    FOTO_PONTO_VENDA("foto_ponto_venda/",".jpg", "image/jpeg", true, 30),
    FOTO_HODOMETRO_HORIMETRO("foto_hodometro_horimetro/",".jpg", "image/jpeg", false, 30),
    NOTA_FISCAL("nota_fiscal/",".xml", "text/xml", false, 120),
    JUSTIFICATIVA_NOTA("justificativa_nota_fiscal/",".pdf", "application/pdf", false, 120),
    RELATORIO_48_HORAS_XLSX("relatorio_48_horas/", ".xlsx", "application/vnd.ms-excel", false, 120),
    RELATORIO_48_HORAS_TXT("relatorio_48_horas/",".txt","text/plain", false, 60),
    RELATORIO_48_HORAS_PDF("relatorio_48_horas/",".pdf","application/pdf", false, 120),
    NOTA_FISCAL_ARMAZEM("nfe_anexo_armazem/", ".xml", "text/xml", false, 120),
    DOWNLOAD_PRESIGNED_NOTA_FISCAL_PDF("download_presigned/nota_fiscal/pdf/", ".pdf", "application/pdf", false, 120),
    DOWNLOAD_PRESIGNED_NOTA_FISCAL_XML("download_presigned/nota_fiscal/xml/", ".xml", "text/xml", false, 120);

    public static final String PREFIXO_BASE64 = "base64,";

    private final String prefixo;
    private final String sufixo;
    private final String mimeType;
    private final boolean nomeArquivoAutoContido;
    private final Integer tempoMinutosUrl;

    /**
     * Construtor do enum
     * @param prefixo Prefixo do arquivo
     * @param sufixo Sufixo do arquivo
     * @param mimeType mimeType do arquivo
     * @param nomeArquivoAutoContido True caso o arquivo tenha seu nome autocontido
     */
    TipoArquivo(String prefixo, String sufixo, String mimeType, boolean nomeArquivoAutoContido, Integer tempoMinutosUrl) {
        this.prefixo = prefixo;
        this.sufixo = sufixo;
        this.mimeType = mimeType;
        this.nomeArquivoAutoContido = nomeArquivoAutoContido;
        this.tempoMinutosUrl = tempoMinutosUrl;
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

    public Integer getTempoMinutosUrl() { return tempoMinutosUrl; }

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
