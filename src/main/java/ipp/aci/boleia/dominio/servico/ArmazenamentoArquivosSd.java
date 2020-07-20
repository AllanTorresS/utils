package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IArmazenamentoDados;
import ipp.aci.boleia.dados.IDispositivoMotoristaPedidoDados;
import ipp.aci.boleia.dados.IMotorGeracaoRelatoriosDados;
import ipp.aci.boleia.dados.IMotoristaDados;
import ipp.aci.boleia.dados.INotaFiscalDados;
import ipp.aci.boleia.dados.IPontoDeVendaDados;
import ipp.aci.boleia.dados.IRepositorioBoleiaDados;
import ipp.aci.boleia.dominio.enums.TipoArquivo;
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
    private IMotoristaDados motoristaDados;

    @Autowired
    private INotaFiscalDados notaFiscalDados;

    @Autowired
    private IDispositivoMotoristaPedidoDados dispositivoMotoristaPedidoDados;

    @Autowired
    private IPontoDeVendaDados pontoVendaDados;

    @Autowired
    private IMotorGeracaoRelatoriosDados motorGeracaoRelatorios;

    private Map<TipoArquivo, IRepositorioBoleiaDados> repositoriosPorTipoArquivo;

    /**
     * Associa cada tipo de arquivo com seu repositorio correspondente
     */
    @PostConstruct
    public void montarMapaRepositorios() {
        repositoriosPorTipoArquivo = new HashMap<>();
        repositoriosPorTipoArquivo.put(TipoArquivo.FOTO_MOTORISTA, motoristaDados);
        repositoriosPorTipoArquivo.put(TipoArquivo.NOTA_FISCAL, notaFiscalDados);
        repositoriosPorTipoArquivo.put(TipoArquivo.FOTO_HODOMETRO_HORIMETRO, dispositivoMotoristaPedidoDados);
        repositoriosPorTipoArquivo.put(TipoArquivo.FOTO_PONTO_VENDA, pontoVendaDados);
        repositoriosPorTipoArquivo.put(TipoArquivo.JUSTIFICATIVA_NOTA, notaFiscalDados);
        repositoriosPorTipoArquivo.put(TipoArquivo.RELATORIO_48_HORAS_XLSX, motorGeracaoRelatorios);
        repositoriosPorTipoArquivo.put(TipoArquivo.RELATORIO_48_HORAS_TXT, motorGeracaoRelatorios);
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
    public String obterUrlArquivo(TipoArquivo tipoArquivo, Long idArquivo, Long idEntidadeRelacionada) {
        try {
            exigirPermissaoAcesso(tipoArquivo, idEntidadeRelacionada);
            Long id = tipoArquivo.isNomeArquivoAutoContido() ? idArquivo : idEntidadeRelacionada;
            return armazenamentoArquivos.obterUrlArquivo(tipoArquivo, id);
        } catch (ExcecaoArquivoNaoEncontrado e) {
            LOGGER.debug("Arquivo nao encontrado na AWS S3", e);
            return "";
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
