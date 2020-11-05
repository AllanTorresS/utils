package ipp.aci.boleia.dados.servicos.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.lifecycle.LifecycleFilter;
import com.amazonaws.services.s3.model.lifecycle.LifecyclePrefixPredicate;
import com.amazonaws.util.IOUtils;
import ipp.aci.boleia.dados.IArmazenamentoDados;
import ipp.aci.boleia.dominio.enums.TipoArquivo;
import ipp.aci.boleia.dominio.vo.CredenciaisAwsVo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioStreams;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoArquivoNaoEncontrado;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Implementa a comunicacao com o storage de arquivos AWS S3
 */
@Repository
public class AwsArmazenamentoDados implements InitializingBean, IArmazenamentoDados {

    private static final Logger LOGGER = LoggerFactory.getLogger(AwsArmazenamentoDados.class);

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Autowired
    private CredenciaisAwsVo credenciais;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    @Value("${aws.s3.bucket.relatorio48Horas.id}")
    private String idDescarteRelatorio48Horas;

    @Value("${aws.s3.bucket.downloadPresigned.id}")
    private String idDescarteDownloadTemporario;

    @Value("${aws.s3.bucket.relatorio48Horas.prefixo}")
    private String prefixoRelatorio48Horas;

    @Value("${aws.s3.bucket.relatorio48Horas.tempoDescarte}")
    private Integer tempoDescarteRelatorio48horas;

    @Value("${aws.s3.bucket.downloadPresigned.tempoDescarte}")
    private Integer tempoDescarteDownloadTemporario;

    @Value("${aws.s3.bucket.nfeAnexoArmazem.prefixo}")
    private String prefixoNfeAnexoArmazem;

    @Value("${aws.s3.bucket.downloadPresigned.prefixo}")
    private String prefixoDownloadTemporario;

    private AmazonS3 clienteS3;

    @Override
    public void afterPropertiesSet() {
        clienteS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(credenciais)
                .withRegion(region)
                .withPathStyleAccessEnabled(true)
                .build();

        criarDiretorioS3(bucketName, prefixoRelatorio48Horas);
        criarDiretorioS3(bucketName, prefixoNfeAnexoArmazem);
        criarDiretorioS3(bucketName, prefixoDownloadTemporario);

        if (!configuracaoRegraDescarteExiste(bucketName, idDescarteRelatorio48Horas)) {
            configurarRegrasDescarte(idDescarteRelatorio48Horas, prefixoRelatorio48Horas, tempoDescarteRelatorio48horas);
        } else {
            LOGGER.info("Regra de ciclo de vida no diretório {} do bucket já configurada.", prefixoRelatorio48Horas);
        }

        if(!configuracaoRegraDescarteExiste(bucketName, idDescarteDownloadTemporario)) {
            configurarRegrasDescarte(idDescarteDownloadTemporario, prefixoDownloadTemporario, tempoDescarteDownloadTemporario);
        } else {
            LOGGER.info("Regra de ciclo de vida no diretório {} do bucket já configurada.", prefixoDownloadTemporario);
        }
    }

    /**
     * Cria um diretório no bucket S3
     *
     * @param bucketName Nome do bucket a ser criado o diretório
     * @param diretorio  Nome do diretorio a ser criado
     */
    private void criarDiretorioS3(String bucketName, String diretorio) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(0);

            InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                    diretorio, emptyContent, metadata);

            clienteS3.putObject(putObjectRequest);

            LOGGER.info("Diretório do Bucket criado [{}] ", diretorio);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Verifica a existência da regra de descarte no ciclo de vida do bucket
     *
     * @return true se a regra existir
     */
    private Boolean configuracaoRegraDescarteExiste(String bucketName, String idDescarte) {
        BucketLifecycleConfiguration configuracaoBucket = clienteS3.getBucketLifecycleConfiguration(bucketName);
        if (configuracaoBucket != null) {
            List<BucketLifecycleConfiguration.Rule> regrasS3 = configuracaoBucket.getRules();

            for ( BucketLifecycleConfiguration.Rule regra : regrasS3 ) {
                if (regra.getId().equals(idDescarte)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Cria a regra de ciclo de vida para descarte de arquivos
     * @param idDescarte Id da regra de ciclo de vida
     * @param prefixo Prefixo do diretório do bucket
     * @param tempoDescarte O tempo até o descarte do arquivo
     *
     */
    private void configurarRegrasDescarte(String idDescarte, String prefixo, Integer tempoDescarte) {
        if (clienteS3 != null) {

            BucketLifecycleConfiguration.Rule regraDescarte = new BucketLifecycleConfiguration.Rule()
                    .withId(idDescarte)
                    .withFilter(new LifecycleFilter(new LifecyclePrefixPredicate(prefixo)))
                    .withExpirationInDays(tempoDescarte)
                    .withStatus(BucketLifecycleConfiguration.ENABLED);

            BucketLifecycleConfiguration configuracaoS3Descarte = new BucketLifecycleConfiguration()
                    .withRules(Arrays.asList(regraDescarte));

            try {
                clienteS3.setBucketLifecycleConfiguration(bucketName, configuracaoS3Descarte);

                LOGGER.info("Regra de ciclo de vida criada no bucket com id [{}]", idDescarte);
            } catch (AmazonS3Exception ae) {
                LOGGER.error(ae.getMessage(),ae);
            }
        }
    }

    @Override
    public void armazenarArquivo(TipoArquivo tipo, Long id, byte[] conteudo) {
        armazenarArquivo(tipo, id.toString(), conteudo);
    }

    @Override
    public void armazenarArquivo(TipoArquivo tipo, String nome, byte[] conteudo) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(conteudo.length);
            clienteS3.putObject(bucketName, tipo.montarCaminhoAcesso(nome), new ByteArrayInputStream(conteudo), metadata);
        } catch(AmazonClientException ace) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO,ace,this.getClass().getName());
        }
    }

    @Override
    public void removerArquivo(TipoArquivo tipo, Long id) {
        try {
            clienteS3.deleteObject(bucketName, tipo.montarCaminhoAcesso(id.toString()));
        } catch(AmazonClientException ace) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO,ace,this.getClass().getName());
        }
    }

    @Override
    public String obterUrlArquivo(TipoArquivo tipo, Long id) throws ExcecaoArquivoNaoEncontrado {
        if (id < 0L) {
            throw new ExcecaoArquivoNaoEncontrado();
        }
        return obterUrlArquivo(tipo, id.toString());
    }

    @Override
    public String obterUrlArquivo(TipoArquivo tipo, String nome) {

        //Expiração da url de download em x minutos, de acordo com o tipo de arquivo
        Date expiration = UtilitarioCalculoData.adicionarMinutosData(utilitarioAmbiente.buscarDataAmbiente(),
                tipo.getTempoMinutosUrl());
        String caminhoArquivo = tipo.montarCaminhoAcesso(nome);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, caminhoArquivo)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        URL url = clienteS3.generatePresignedUrl(generatePresignedUrlRequest);

        return url.toString();
    }

    @Override
    public InputStream obterArquivo(TipoArquivo tipo, Long id) throws ExcecaoArquivoNaoEncontrado {
        if (id < 0L) {
            throw new ExcecaoArquivoNaoEncontrado();
        }
        InputStream inputStream;
        String caminhoArquivo = tipo.montarCaminhoAcesso(id.toString());
        try (S3Object s3Object = clienteS3.getObject(bucketName, caminhoArquivo)) {
            ByteArrayOutputStream temp = new ByteArrayOutputStream();
            UtilitarioStreams.copiarStream(s3Object.getObjectContent(), temp);
            inputStream = new ByteArrayInputStream(temp.toByteArray());
        } catch (AmazonS3Exception ace) {
            if (ace.getStatusCode() == HttpStatus.NOT_FOUND.value()) {
                throw new ExcecaoArquivoNaoEncontrado();
            }
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, ace, this.getClass().getName());
        } catch (IOException ioe) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, ioe, this.getClass().getName());
        }
        return inputStream;
    }

    @Override
    public void copiarArquivo(TipoArquivo origem, Long idOrigem, TipoArquivo destino, String nomeDestino) {
        copiarArquivo(origem, idOrigem, destino, false, nomeDestino);
    }

    @Override
    public void moverArquivo(TipoArquivo origem, Long idOrigem, TipoArquivo destino, Long idDestino) {
        copiarArquivo(origem, idOrigem, destino, true, idDestino != null ? idDestino.toString() : null);
    }

    /**
     * Copia um arquivo de um diretório para outro de um bucket S3, podendo ou não remover o arquivo de origem
     *
     * @param origem Tipo de arquivo de origem
     * @param idOrigem Identificador do arquivo de origem
     * @param destino Tipo de arquivo de destino
     * @param removerOrigem Remove o arquivo de origem caso true, e não remove caso contrário
     * @param nomeDestino O nome
     */
    private void copiarArquivo(TipoArquivo origem, Long idOrigem, TipoArquivo destino, Boolean removerOrigem, String nomeDestino) {
        try {
            InputStream arquivoOrigem = obterArquivo(origem, idOrigem);
            String nome = nomeDestino != null ? nomeDestino : idOrigem.toString();
            armazenarArquivo(destino, nome, IOUtils.toByteArray(arquivoOrigem));
            if (removerOrigem)
                removerArquivo(origem, idOrigem);
        } catch (IOException | ExcecaoArquivoNaoEncontrado ioe) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, ioe, this.getClass().getName());
        }
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setClienteS3(AmazonS3 clienteS3) {
        this.clienteS3 = clienteS3;
    }

    public void setIdDescarteRelatorio48Horas(String idDescarteRelatorio48Horas) { this.idDescarteRelatorio48Horas = idDescarteRelatorio48Horas; }

    public void setPrefixoRelatorio48Horas(String prefixoRelatorio48Horas) { this.prefixoRelatorio48Horas = prefixoRelatorio48Horas; }

    public void setTempoDescarteRelatorio48horas(Integer tempoDescarteRelatorio48horas) { this.tempoDescarteRelatorio48horas = tempoDescarteRelatorio48horas; }
}
