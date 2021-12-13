package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.DownloadArquivos;

/**
 * Contrato para implementacao de repositorio
 * de entidades de  DownloadArquivos.
 */
public interface IDownloadArquivosDados extends IRepositorioBoleiaDados<DownloadArquivos> {

    /**
     * Obtém um DownloadArquivos a partir da chave Identifiadora.
     *
     * @param chaveIdentificadora Chave identificadora de um agrupamento de consolidados
     */
    DownloadArquivos obterDownloadArquivoPorChaveIdentificadora(String chaveIdentificadora);
}
