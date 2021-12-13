package ipp.aci.boleia.dominio.servico;

import com.google.common.collect.ImmutableList;
import ipp.aci.boleia.dados.IArquivoDados;
import ipp.aci.boleia.dados.IComponenteDados;
import ipp.aci.boleia.dados.IDownloadArquivosDados;
import ipp.aci.boleia.dados.IEmpresaAgregadaDados;
import ipp.aci.boleia.dados.IFrotaDados;
import ipp.aci.boleia.dados.INfeAnexosArmazemDados;
import ipp.aci.boleia.dados.INotaFiscalDados;
import ipp.aci.boleia.dados.ITipoCombustivelDados;
import ipp.aci.boleia.dados.ITransacaoConsolidadaDados;
import ipp.aci.boleia.dados.IUnidadeDados;
import ipp.aci.boleia.dados.IUsuarioDados;
import ipp.aci.boleia.dominio.DownloadArquivos;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusDownloadArquivos;
import ipp.aci.boleia.dominio.enums.TipoArquivo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

/**
 * Oferece funcionalidades para {@link ipp.aci.boleia.dominio.DownloadArquivos}
 */
@Component
public class DownloadArquivosSd {

    private static final Logger LOG = LoggerFactory.getLogger(DownloadArquivosSd.class);
    private static final int LIMITE_NO_SERIE = 10;
    private static final String MODELO_PERMITIDO = "55";
    private static final List<Erro> ERROS_BLOQUEANTES = ImmutableList.of(Erro.NOTA_FISCAL_UPLOAD_VERSAO_INVALIDA, Erro.NOTA_FISCAL_UPLOAD_NOTA_REPETIDA);

    @Autowired
    private ArmazenamentoArquivosSd armazenamentoArquivosSd;

    @Autowired
    private NotaFiscalParserSd notaFiscalParserSd;

    @Autowired
    TransacaoConsolidadaSd transacaoConsolidadaSd;

    @Autowired
    private ITransacaoConsolidadaDados transacaoConsolidadaDados;

    @Autowired
    private IUsuarioDados usuarioDados;

    @Autowired
    private INotaFiscalDados repositorio;

    @Autowired
    private IArquivoDados repositorioArquivo;

    @Autowired
    private INfeAnexosArmazemDados nfeAnexosArmazemDados;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Autowired
    private IComponenteDados componenteDados;

    @Autowired
    private IFrotaDados frotaDados;

    @Autowired
    private IUnidadeDados unidadeDados;

    @Autowired
    private IDownloadArquivosDados downloadArquivosDados;

    @Autowired
    private IEmpresaAgregadaDados empresaAgregadaDados;

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private ITipoCombustivelDados tipoCombustivelDados;

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Salva um {@link DownloadArquivos}  com o status "EM_ANDAMENTO"
     *
     * @param chaveIdentificadora Chave que identifica um grupo de transações consolidadas
     * @param usuarioLogado       usuario logado que solicitou o processamento
     * @param tipoArquivo         tipo do arquivo
     * @param dataRequisicao      A data e hora na qual a requisição foi feita
     * @return DownloadArquivos salvo
     */
    public DownloadArquivos salvarDownloadArquivos(String chaveIdentificadora, Usuario usuarioLogado, String tipoArquivo, Date dataRequisicao , String formato) {

        DownloadArquivos downloadArquivos = new DownloadArquivos();
        downloadArquivos.setDataRequisicao(dataRequisicao);
        downloadArquivos.setStatus(StatusDownloadArquivos.EM_ANDAMENTO.getValue());
        downloadArquivos.setUsuario(usuarioLogado);
        downloadArquivos.setTipoArquivo(tipoArquivo);
        downloadArquivos.setChaveIdentificadora(chaveIdentificadora);
        downloadArquivos.setFormato(formato);

        downloadArquivos = downloadArquivosDados.armazenar(downloadArquivos);
        return downloadArquivos;
    }

    /**
     * Armazena arquivos no s3
     *
     * @param idDownloadArquivo Id que identifica o DownloadArquivo
     * @param tipoArquivo       tipo do arquivo
     * @param out               Stream do conteudo a ser salvo no s3
     */
    public void salvarArquivosS3(TipoArquivo tipoArquivo,Long idDownloadArquivo, ByteArrayOutputStream out) {
        DownloadArquivos downloadArquivos = downloadArquivosDados.obterPorId(idDownloadArquivo);
        armazenamentoArquivosSd.armazenarArquivo(tipoArquivo,
                null,
                downloadArquivos.getId().toString(),
                out.toByteArray());
        this.atualizarStatus(idDownloadArquivo, downloadArquivos, StatusDownloadArquivos.CONCLUIDO);
    }

    /**
     * Atualiza o status do processamento de um {@link DownloadArquivos} a partir da chave identificadora ou do proprio registro
     *
     * @param idDownloadArquivo Id que identifica o arquivo
     * @param status            o Status para ser atualizado no registro
     * @return Registro com o status atualizado
     */
    public DownloadArquivos atualizarStatus(Long idDownloadArquivo, DownloadArquivos registroSalvo, StatusDownloadArquivos status) {

        DownloadArquivos downloadArquivos = registroSalvo != null ? registroSalvo : downloadArquivosDados.obterPorId(idDownloadArquivo);
        downloadArquivos.setStatus(status.getValue());
        downloadArquivosDados.armazenar(downloadArquivos);
        return downloadArquivos;
    }

    /**
     * Retorna o usaurio que solicitou o processamento dos arquivos
     *
     * @param chaveIdentificadora Chave que identifica o grupo de arquivos
     * @return O Usuario que pediu o download de arquivos em lote
     */
    public Usuario buscarUsarioLogado(String chaveIdentificadora) {

        DownloadArquivos downloadArquivos = downloadArquivosDados.obterDownloadArquivoPorChaveIdentificadora(chaveIdentificadora);
        Long idUsuarioLogado = downloadArquivos.getUsuario().getId();
        return usuarioDados.obterPorId(idUsuarioLogado);
    }

    /**
     * Obtem o nome do aquivo a partir da chave Identificadora
     *
     * @param chaveIdentificadora Chave que identifica um grupo de transações consolidadas
     * @return O nome do arquivo
     */
    public String obterNomeDoArquivo(String chaveIdentificadora){
        String chaveDecodificada = transacaoConsolidadaSd.decodificarChaveIdentificadoraAgrupamentoCiclos(chaveIdentificadora);
        String[] camposChave = transacaoConsolidadaSd.dividirChaveIdentificadoraAgrupamentoCiclos(chaveDecodificada);
        Date dataInicioCiclo = transacaoConsolidadaSd.obterCampoDataInicioChaveIdentificadora(camposChave);
        Date dataFimCiclo = transacaoConsolidadaSd.obterCampoDataFimChaveIdentificadora(camposChave);
        Long idFrota = transacaoConsolidadaSd.obterCampoIdFrotaChaveIdentificadora(camposChave);
        Frota frota = frotaDados.obterPorId(idFrota);

       return  gerarNomeDoArquivo(dataInicioCiclo,dataFimCiclo,frota.getCnpj());
    }

    /**
     * Gera uma string concatenando data de inicio de um ciclo com a data final de um ciclo e o cnpj da frota
     *
     * @param dataInicioCiclo data de inicio do clico
     * @param dataFimCiclo data do fim do ciclo
     * @param cnpj cnpj da transportadora
     * @return String com o formato Nfe_dataIniciociclo_dataFinalCiclo_CnpjFrota
     */
    private String gerarNomeDoArquivo(Date dataInicioCiclo, Date dataFimCiclo, Long cnpj) {
        String PREFIXO = "Nfe_";
        String dataInicioCicloFormatada = UtilitarioFormatacaoData.formatarDataCurta(dataInicioCiclo).replace('/','_');
        String dataFimCicloFormatada = UtilitarioFormatacaoData.formatarDataCurta(dataFimCiclo).replace('/','_');
        String cnpjFormatado = UtilitarioFormatacao.formatarCpfCnpjString(cnpj);
        return PREFIXO.concat(dataInicioCicloFormatada)
                .concat("-")
                .concat(dataFimCicloFormatada)
                .concat("_")
                .concat(cnpjFormatado);
    };


    /**
     * Veriifca se o arquivo está disponivel para download
     *
     * @param chaveIdentificadora Chave que identifica um grupo de transações consolidadas
     * @return O status do DownloadArquivo
     */
    public StatusDownloadArquivos verificarArquivoDisponivelDownload(String chaveIdentificadora) {
        DownloadArquivos downloadArquivos = downloadArquivosDados.obterDownloadArquivoPorChaveIdentificadora(chaveIdentificadora);

        if (downloadArquivos != null) {
            Date dataRequisicao = downloadArquivos.getDataRequisicao();

            if (StatusDownloadArquivos.CONCLUIDO.getValue().equals(downloadArquivos.getStatus())) {
                return calculaDataExpiracaoArquivo(downloadArquivos, dataRequisicao);
            } else if (StatusDownloadArquivos.EM_ANDAMENTO.getValue().equals(downloadArquivos.getStatus())) {
                return StatusDownloadArquivos.EM_ANDAMENTO;
            } else {
               return  StatusDownloadArquivos.obterPorValor(downloadArquivos.getStatus());
            }
        }else
            return null;
    }

    /**
     * Verifica se já faz mais de 48 horas que o download do arquivo foi solicitado
     *
     * @param downloadArquivos o registro a ser verificado
     * @param dataRequisicao data da requisição do download
     * @return O status do arquivo, caso tenha passado de 48 Horas retorna "EXCLUIDO"
     */
    private StatusDownloadArquivos calculaDataExpiracaoArquivo(DownloadArquivos downloadArquivos, Date dataRequisicao) {
        if (UtilitarioCalculoData.adicionarDiasData(dataRequisicao, 2).after(ambiente.buscarDataAmbiente())) {
            return StatusDownloadArquivos.obterPorValor(downloadArquivos.getStatus());
        } else {
            atualizarStatus(null, downloadArquivos, StatusDownloadArquivos.EXCLUIDO);
            return StatusDownloadArquivos.EXCLUIDO;
        }

    }

}