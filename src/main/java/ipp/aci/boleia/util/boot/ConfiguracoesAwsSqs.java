package ipp.aci.boleia.util.boot;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.buffered.AmazonSQSBufferedAsyncClient;
import com.amazonaws.services.sqs.model.ListQueuesRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuração dos recursos de envio e recebimento de registros da nuvem
 */
@Configuration
public class ConfiguracoesAwsSqs {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguracoesAwsSqs.class);

    @Value("${aws.sqs.local.endpoint}")
    private String sqsLocalEndpoint;

    @Value("${aws.sqs.region}")
    private String region;

    /**
     * Prefixos dos registros na fila
     */
    @Value("${aws.sqs.prefixo}")
    private String prefixoFilas;

    /**
     * nome da fila que será acessada de autorização de pagamento
     */
    @Value("${aws.sqs.transacao-consolidada.autorizacao-pagamento}")
    private String nomeFilaAutorizacaoPagamentoConsolidacao;

    /**
     * nome da fila que conterá as transações consolidadas
     */
    @Value("${aws.sqs.transacao-consolidada.processar-ciclo}")
    private String nomeFilaTransacaoConsolidacao;

    @Value("${aws.credentials.accessKey.id}")
    private String accessKeyId;

    @Value("${aws.credentials.accessKey.secret}")
    private String secretAccessKey;

    /**
     * Nome da fila que conterá as autorizações de pagamento com desconto de Frotas leves
     */
    @Value("${aws.sqs.ciclo-repasse.autorizacao-pagamento}")
    private String nomeFilaAutorizacaoPagamentoRepasse;

    /**
     * Nome da fila que conterá os ciclos de repasse
     */
    @Value("${aws.sqs.ciclo-repasse.processar-ciclo}")
    private String nomeFilaCicloRepasse;

    /**
     * Nome da fila que conterá as autorizações de pagamento que devem ser utilizadas no gerador de campanhas
     */
    @Value("${aws.sqs.gerador-campanha.autorizacao-pagamento}")
    private String nomeFilaGeradorCampanha;

    @Value("${aws.sqs.transacao-consolidada.autorizacao-pagamento.erro-validacao-campanha}")
    private String nomeFilaErroTransConsolValidacaoCampanha;

    @Value("${aws.sqs.ciclo-repasse.autorizacao-pagamento.erro-validacao-campanha}")
    private String nomeFilaErroCicloRepasseValidacaoCampanha;

    @Value("${aws.sqs.ciclo-repasse.autorizacao-pagamento.erro-validacao-ciclo}")
    private String nomeFilaErroCicloRepasseValidacaoTransConsol;

    /**
     * Carrega as mensagens do sistema
     */
    @Autowired
    private Mensagens mensagens;

    /**
     * Cria o AmazonSQSAsync apontando para o SQS local.
     * Utilizado exclusivamente no ambiente de desenvolvimento local.
     *
     * @return Objeto SQS
     */
    @Primary
    @Bean
    @ConditionalOnProperty(value = "identificador.ambiente", havingValue = "dev-local")
    public AmazonSQSAsync amazonSqsLocal() {
        AmazonSQSAsync amazonSqs = AmazonSQSAsyncClient
                .asyncBuilder()
                .withCredentials(criarCrendetialProvider())
                .withEndpointConfiguration(new EndpointConfiguration(sqsLocalEndpoint, region))
                .build();
        return new AmazonSQSBufferedAsyncClient(amazonSqs);
    }

    /**
     * Cria o Autowired para o componente de envio de requisição de mensagens para a fila
     * @param amazonSQSAsync client de acesso ao serviço da aws
     * @return Gerenciado de mensagens da fila
     */
    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
        ArrayList<String> nomeFilas = new ArrayList<>(2);
        nomeFilas.add(nomeFilaAutorizacaoPagamentoConsolidacao);
        nomeFilas.add(nomeFilaTransacaoConsolidacao);
        nomeFilas.add(nomeFilaAutorizacaoPagamentoRepasse);
        nomeFilas.add(nomeFilaCicloRepasse);
        nomeFilas.add(nomeFilaGeradorCampanha);
        nomeFilas.add(nomeFilaErroTransConsolValidacaoCampanha);
        nomeFilas.add(nomeFilaErroCicloRepasseValidacaoCampanha);
        nomeFilas.add(nomeFilaErroCicloRepasseValidacaoTransConsol);
        criaFilaSeNaoExistem(amazonSQSAsync, nomeFilas);
        return new QueueMessagingTemplate(amazonSQSAsync);
    }

    /**
     * Cria todas as filas necessarias caso não exista
     *
     * @param amazonSQSAsync
     * @param nomeFilas
     */
    private void criaFilaSeNaoExistem(AmazonSQSAsync amazonSQSAsync, List<String> nomeFilas) {
        ListQueuesResult listQueuesResult = amazonSQSAsync.listQueues(new ListQueuesRequest(prefixoFilas));
        nomeFilas.forEach(x -> criaFilaSeNaoExistem(amazonSQSAsync, listQueuesResult, x));
    }

    /**
     * Cria a fila caso não exista
     *
     * @param amazonSQSAsync
     * @param listQueuesResult
     * @param nomeFila
     */
    private void criaFilaSeNaoExistem(AmazonSQSAsync amazonSQSAsync, ListQueuesResult listQueuesResult, String nomeFila) {
        if(!filaExiste(listQueuesResult, nomeFila)){
            criarFila(amazonSQSAsync, nomeFila);
            LOGGER.info(mensagens.obterMensagem("sqs.criando-fila", nomeFila));
            return;
        }
        LOGGER.info(mensagens.obterMensagem("sqs.fila-encontrada", nomeFila));

    }

    /**
     * Cria a fila na aws
     *
     * @param amazonSQSAsync
     * @param nomeFila
     */
    private void criarFila(AmazonSQSAsync amazonSQSAsync, String nomeFila) {
        amazonSQSAsync.createQueue(nomeFila);
    }

    /**
     * Verifica se a fila existe na aws
     *
     * @param listQueuesResult
     * @param nomeFila
     * @return
     */
    private boolean filaExiste(ListQueuesResult listQueuesResult, String nomeFila) {
        return listQueuesResult.getQueueUrls().stream().anyMatch(x -> x.contains(nomeFila));
    }

    /**
     * Cria o provedor de credencial da AWS que será usado na criação do AmazonSQSAsync.
     *
     * @return Provedor de credencial.
     */
    private AWSCredentialsProvider criarCrendetialProvider() {
        return new AWSCredentialsProvider() {
            @Override
            public AWSCredentials getCredentials() {
                return new AWSCredentials() {

                    @Override
                    public String getAWSAccessKeyId() {
                        return accessKeyId;
                    }

                    @Override
                    public String getAWSSecretKey() {
                        return secretAccessKey;
                    }
                };
            }

            @Override
            public void refresh() {
                //nada a fazer
            }
        };
    }
}
