package ipp.aci.boleia.util.seguranca;

/**
 * Contem o parametro enviado na requisicao
 * de tiket para download de arquivos
 */
class TokenDownloadParam {

    private String fileUrl;

    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * Necessario para serializacao JSON
     * @param fileUrl o  url do arquivo
     */
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}