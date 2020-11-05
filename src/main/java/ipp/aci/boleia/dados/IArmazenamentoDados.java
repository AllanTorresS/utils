package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.enums.TipoArquivo;
import ipp.aci.boleia.util.excecao.ExcecaoArquivoNaoEncontrado;

import java.io.InputStream;

/**
 * Contrato para implementacao de armazenamento (storage) de arquivos.
 */
public interface IArmazenamentoDados {

    /**
     * Armazena um arquivo no bucket do boleia amazon
     *
     * @param tipo      tipo arquivo
     * @param id        id
     * @param conteudo  conteudo arquivo
     */
    void armazenarArquivo(TipoArquivo tipo, Long id, byte[] conteudo);

    /**
     * Armazena um arquivo no bucket do boleia amazon
     *
     * @param tipo      tipo arquivo
     * @param nome      nome do arquivo
     * @param conteudo  conteudo arquivo
     */
    void armazenarArquivo(TipoArquivo tipo, String nome, byte[] conteudo);

    /**
     * Remove um arquivo no bucket do boleia amazon
     *
     * @param tipo  tipo arquivo
     * @param id    id
     */
    void removerArquivo(TipoArquivo tipo, Long id);

    /**
     * Obtem o conteudo de um arquivo no bucket do boleia amazon
     *
     * @param tipo  tipo arquivo
     * @param id    id
     * @return stream do arquivo
     * @throws ExcecaoArquivoNaoEncontrado Quando o arquivo não existe no storage
     */
    InputStream obterArquivo(TipoArquivo tipo, Long id) throws ExcecaoArquivoNaoEncontrado;

    /**
     * Obtem o conteudo de um arquivo no bucket do boleia amazon
     *
     * @param tipo  tipo arquivo
     * @param nome  nome do arquivo
     * @return stream do arquivo
     * @throws ExcecaoArquivoNaoEncontrado Quando o arquivo não existe no storage
     */
    InputStream obterArquivo(TipoArquivo tipo, String nome) throws ExcecaoArquivoNaoEncontrado;

    /**
     * Obtém um arquivo em um caminho do bucket e o move para outro caminho dentro do mesmo bucket
     *
     * @param origem Tipo de arquivo de origem
     * @param idOrigem Identificador do arquivo de origem
     * @param destino Tipo de arquivo de destino
     * @param idDestino Identificador do arquivo de destino
     */
    void moverArquivo(TipoArquivo origem, Long idOrigem, TipoArquivo destino, Long idDestino);

    /**
     * Obtem o link para o download de um arquivo no bucket do boleia amazon
     *
     * @param tipo  tipo arquivo
     * @param id    id
     * @return String com a url pré-assinada do arquivo, com tempo de expiração de acordo com o tipo de arquivo
     * @throws ExcecaoArquivoNaoEncontrado Quando o arquivo não existe no storage
     */
    String obterUrlArquivo(TipoArquivo tipo, Long id) throws ExcecaoArquivoNaoEncontrado;

    /**
     * Obtem o link para o download de um arquivo no bucket do boleia amazon
     *
     * @param tipo  tipo arquivo
     * @param nome  nome do arquivo
     * @return String com a url pré-assinada do arquivo, com tempo de expiração de acordo com o tipo de arquivo
     * @throws ExcecaoArquivoNaoEncontrado Quando o arquivo não existe no storage
     */
    String obterUrlArquivo(TipoArquivo tipo, String nome) throws ExcecaoArquivoNaoEncontrado;

    /**
     * Copia um arquivo de um diretório para outro de um bucket S3
     *
     * @param origem Tipo de arquivo de origem
     * @param idOrigem Identificador do arquivo de origem
     * @param destino Tipo de arquivo de destino
     * @param nomeDestino Identificador do arquivo de destino
     */
    void copiarArquivo(TipoArquivo origem, Long idOrigem, TipoArquivo destino, String nomeDestino);
}