package ipp.aci.boleia.dados;

/**
 * Contrato para processar arquivos em lote
 */
public interface IFilaProcessarArquivosLote {

    /**
     * Envia qual grupo de arquivos vai ser processado em lote para download.
     *
     * @param idDownloadArquivo Identificador do DownloadArquivos que vai ser processado
     */
    void enviarArquivosParaDownloadLote(Long idDownloadArquivo);
}
