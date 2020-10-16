package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IArmazenamentoDados;
import ipp.aci.boleia.dados.IArquivoDados;
import ipp.aci.boleia.dados.IDispositivoMotoristaPedidoDados;
import ipp.aci.boleia.dados.INotaFiscalDados;
import ipp.aci.boleia.dados.IPontoDeVendaDados;
import ipp.aci.boleia.dados.IRepositorioBoleiaDados;
import ipp.aci.boleia.dominio.enums.TipoArquivo;
import ipp.aci.boleia.dominio.vo.UrlS3PreAssinadaVo;
import ipp.aci.boleia.util.UtilitarioStreams;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoArquivoNaoEncontrado;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Trata a logica de permissionamento para acesso a arquivos armazenados no storage do sistema
 */
@Component
public class ArmazenamentoArquivosSd {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArmazenamentoArquivosSd.class);

    @Autowired
    private IArmazenamentoDados armazenamentoArquivos;

    @Autowired
    private INotaFiscalDados notaFiscalDados;

    @Autowired
    private IDispositivoMotoristaPedidoDados dispositivoMotoristaPedidoDados;

    @Autowired
    private IPontoDeVendaDados pontoVendaDados;

    @Autowired
    private IArquivoDados arquivoDados;

    private Map<TipoArquivo, IRepositorioBoleiaDados> repositoriosPorTipoArquivo;

    /**
     * Associa cada tipo de arquivo com seu repositorio correspondente
     */
    @PostConstruct
    public void montarMapaRepositorios() {
        repositoriosPorTipoArquivo = new HashMap<>();
        repositoriosPorTipoArquivo.put(TipoArquivo.NOTA_FISCAL, notaFiscalDados);
        repositoriosPorTipoArquivo.put(TipoArquivo.FOTO_HODOMETRO_HORIMETRO, dispositivoMotoristaPedidoDados);
        repositoriosPorTipoArquivo.put(TipoArquivo.FOTO_PONTO_VENDA, pontoVendaDados);
        repositoriosPorTipoArquivo.put(TipoArquivo.JUSTIFICATIVA_NOTA, notaFiscalDados);
        repositoriosPorTipoArquivo.put(TipoArquivo.DOWNLOAD_PRESIGNED_NOTA_FISCAL_PDF, arquivoDados);
        repositoriosPorTipoArquivo.put(TipoArquivo.DOWNLOAD_PRESIGNED_NOTA_FISCAL_XML, arquivoDados);
        repositoriosPorTipoArquivo.put(TipoArquivo.RELATORIO_48_HORAS_XLSX, arquivoDados);
        repositoriosPorTipoArquivo.put(TipoArquivo.RELATORIO_48_HORAS_TXT, arquivoDados);
        repositoriosPorTipoArquivo.put(TipoArquivo.RELATORIO_48_HORAS_PDF, arquivoDados);
    }

    /**
     * Copia o conteudo de um arquivo armazenado no bucket do Boleia na AWS (S3) para o stream de saida informado
     *
     * @param tipoArquivo tipo arquivo
     * @param idArquivo identificador da entidade que representa o arquivo
     * @param idEntidadeRelacionada identificador da entidade que contem o arquivo
     * @param stream stream do response
     * @return True caso o arquivo seja copiado e false caso contrario
     */
    public boolean copiarArquivoParaStreamSaida(TipoArquivo tipoArquivo, Long idArquivo, Long idEntidadeRelacionada,
                                                OutputStream stream) {
        try {
            exigirPermissaoAcesso(tipoArquivo, idEntidadeRelacionada);
            Long id = tipoArquivo.isNomeArquivoAutoContido() ? idArquivo : idEntidadeRelacionada;
            UtilitarioStreams.copiarStream(armazenamentoArquivos.obterArquivo(tipoArquivo, id), stream);
            return true;
        } catch (ExcecaoArquivoNaoEncontrado e) {
            LOGGER.debug("Arquivo nao encontrado na AWS S3", e);
            return false;
        }
    }

    /**
     * Obtem o link para o download de um arquivo no bucket do boleia amazon
     *
     * @param tipoArquivo tipo arquivo
     * @param idArquivo id
     * @param idEntidadeRelacionada identificador da entidade que contem o arquivo
     * @return String com a url pré-assinada do arquivo, com tempo de expiração de acordo com o tipo de arquivo
     */
    public UrlS3PreAssinadaVo obterUrlArquivo(TipoArquivo tipoArquivo, Long idArquivo, Long idEntidadeRelacionada) {
        return obterUrlArquivo(tipoArquivo, idArquivo, idEntidadeRelacionada, idEntidadeRelacionada.toString());
    }

    /**
     * Obtem o link para o download de um arquivo no bucket do boleia amazon
     *
     * @param tipoArquivo tipo arquivo
     * @param idArquivo id
     * @param idEntidadeRelacionada identificador da entidade que contem o arquivo
     * @param nome nome do arquivo para a url
     * @return String com a url pré-assinada do arquivo, com tempo de expiração de acordo com o tipo de arquivo
     */
    public UrlS3PreAssinadaVo obterUrlArquivo(TipoArquivo tipoArquivo, Long idArquivo, Long idEntidadeRelacionada,
                                              String nome) {
        try {
            exigirPermissaoAcesso(tipoArquivo, idEntidadeRelacionada);
            String id = tipoArquivo.isNomeArquivoAutoContido() ? idArquivo.toString() : nome;
            return new UrlS3PreAssinadaVo(armazenamentoArquivos.obterUrlArquivo(tipoArquivo, id));
        } catch (ExcecaoArquivoNaoEncontrado e) {
            LOGGER.debug("Arquivo nao encontrado na AWS S3", e);
            return null;
        }
    }
    /**
     * Obtem o stream do arquivo presente no bucket do Boleia na AWS (S3)
     *
     * @param tipoArquivo tipo arquivo
     * @param idArquivo identificador da entidade que representa o arquivo
     * @param idEntidadeRelacionada identificador da entidade que contem o arquivo
     * @return O stream do arquivo
     * @throws ExcecaoArquivoNaoEncontrado quando o arquivo nao existe no S3
     */
    public InputStream obterArquivo(TipoArquivo tipoArquivo, Long idArquivo, Long idEntidadeRelacionada)
            throws ExcecaoArquivoNaoEncontrado {
        exigirPermissaoAcesso(tipoArquivo, idEntidadeRelacionada);
        Long id = tipoArquivo.isNomeArquivoAutoContido() ? idArquivo : idEntidadeRelacionada;
        return armazenamentoArquivos.obterArquivo(tipoArquivo, id);
    }

    /**
     * Armazena um arquivo no bucket do boleia amazon
     *
     * @param tipoArquivo tipo arquivo
     * @param idArquivo identificador da entidade que representa o arquivo
     * @param idEntidadeRelacionada identificador da entidade que contem o arquivo
     * @param nome nome do arquivo a ser armazenado
     * @param conteudo  conteudo arquivo
     */
    public void armazenarArquivo(TipoArquivo tipoArquivo, Long idArquivo, Long idEntidadeRelacionada, String nome, byte[] conteudo) {
        exigirPermissaoAcesso(tipoArquivo, idEntidadeRelacionada);
        armazenamentoArquivos.armazenarArquivo(tipoArquivo, nome, conteudo);
    }

    /**
     * Armazena um arquivo no bucket do boleia amazon
     *
     * @param tipoArquivo tipo arquivo
     * @param idArquivo identificador da entidade que representa o arquivo
     * @param idEntidadeRelacionada identificador da entidade que contem o arquivo
     * @param conteudo  conteudo arquivo
     */
    public void armazenarArquivo(TipoArquivo tipoArquivo, Long idArquivo, Long idEntidadeRelacionada, byte[] conteudo) {
        exigirPermissaoAcesso(tipoArquivo, idEntidadeRelacionada);
        Long id = tipoArquivo.isNomeArquivoAutoContido() ? idArquivo : idEntidadeRelacionada;
        armazenamentoArquivos.armazenarArquivo(tipoArquivo, id, conteudo);
    }

    /**
     * Remove um arquivo no bucket do boleia amazon
     *
     * @param tipoArquivo tipo arquivo
     * @param idArquivo identificador da entidade que representa o arquivo
     * @param idEntidadeRelacionada identificador da entidade que contem o arquivo
     */
    public void removerArquivo(TipoArquivo tipoArquivo, Long idArquivo, Long idEntidadeRelacionada) {
        exigirPermissaoAcesso(tipoArquivo, idEntidadeRelacionada);
        Long id = tipoArquivo.isNomeArquivoAutoContido() ? idArquivo : idEntidadeRelacionada;
        armazenamentoArquivos.removerArquivo(tipoArquivo, id);
    }

    /**
     * Copia um arquivo de um diretório para outro em um bucket AWS S3
     *
     * @param origem Arquivo de origem
     * @param idOrigem Identificador do arquivo de origem
     * @param destino Arquivo de destino
     * @param nomeDestino nome do arquivo de destino
     */
    public void copiarArquivo(TipoArquivo origem, Long idOrigem, TipoArquivo destino, String nomeDestino) {
        armazenamentoArquivos.copiarArquivo(origem, idOrigem, destino, nomeDestino);
    }

    /**
     * Verificacao se o usuario que esta solicitando o arquivo possui permissao para ve-lo
     *
     * @param tipoArquivo O tipo do arquivo
     * @param idEntidadeRelacionada identificador da entidade que contem o arquivo
     */
    private void exigirPermissaoAcesso(TipoArquivo tipoArquivo, Long idEntidadeRelacionada) {
        IRepositorioBoleiaDados repo = repositoriosPorTipoArquivo.get(tipoArquivo);
        if(repo == null) {
            LOGGER.error("Tentativa de acesso a arquivo sem a permissao necessaria! O tipo de arquivo {} não esta vinculado a nenhum repositorio de entidade", tipoArquivo);
            throw new ExcecaoBoleiaRuntime(Erro.VIOLACAO_ISOLAMENTO_DADOS);
        }
        // Forca a verificacao de isolamento de dados, para evitar acesso indevido
        repo.obterPorId(idEntidadeRelacionada);
    }
}
