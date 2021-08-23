package ipp.aci.boleia.util.boot;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.support.AcknowledgmentHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.support.HeaderMethodArgumentResolver;
import org.springframework.messaging.handler.annotation.support.PayloadArgumentResolver;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.amazonaws.services.sqs.buffered.AmazonSQSBufferedAsyncClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.ListQueuesRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.QueueAttributeName;
import com.fasterxml.jackson.databind.ObjectMapper;

import ipp.aci.boleia.util.i18n.Mensagens;

/**
 * Configuração dos recursos de envio e recebimento de registros da nuvem
 */
@Configuration
public class ConfiguracoesAwsSqs {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguracoesAwsSqs.class);
    
    private static final String MESSAGE_TIMEOUT_PADRAO = "30";
    private static final String MESSAGE_RETENTION_PADRAO = "4";
    private static final String RECEIVE_MESSAGE_WAIT_TIME_PADRAO = "20";

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

    @Value("${aws.sqs.cobranca.requisicao-incluir-fatura}")
    private String nomeFilaCobrancaRequisicaoIncluirFatura;

    @Value("${aws.sqs.cobranca.resposta-incluir-fatura}")
    private String nomeFilaCobrancaRespostaIncluirFatura;

    /**
     * Nome da fila que aplica novo parâmetro de ciclo na frota
     */
    @Value("${aws.sqs.frota-param-ciclo.aplica-novo-ciclo}")
    private String nomeFilaFrotaParamCiclo;

    @Value("${aws.sqs.transacao.consolidacao}")
    private String nomeFilaTransacaoConsolidacaoAgFrete;

    @Value("${aws.sqs.transacao-consolidada.postergar-abastecimentos}")
    private String nomeFilaPostergacaoAbastecimentos;

    @Value("${aws.sqs.transacao-consolidada.processar-ciclo-postergacao}")
    private String nomeFilaProcessarCicloPostergacao;

    @Value("${aws.sqs.anonimizacao.motorista}")
    private String nomeFilaAnonimizacaMotorista;

    @Value("${aws.sqs.anonimizacao-exclusao.motorista-auditoria}")
    private String nomeFilaAnonimizacaoExclusaoMotoristaAuditoria;

    /**
     * Nome da fila que processa propostas de antecipação
     */
    @Value("${aws.sqs.processar-proposta-antecipacao}")
    private String nomeFilaProcessarPropostaAntecipacao;

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
        List<String> nomeFilas = Arrays.asList(
                         nomeFilaAutorizacaoPagamentoConsolidacao
                        ,nomeFilaTransacaoConsolidacao
                        ,nomeFilaAutorizacaoPagamentoRepasse
                        ,nomeFilaCicloRepasse
                        ,nomeFilaGeradorCampanha
                        ,nomeFilaErroTransConsolValidacaoCampanha
                        ,nomeFilaErroCicloRepasseValidacaoCampanha
                        ,nomeFilaErroCicloRepasseValidacaoTransConsol
                        ,nomeFilaCobrancaRequisicaoIncluirFatura
                        ,nomeFilaCobrancaRespostaIncluirFatura
                        ,nomeFilaFrotaParamCiclo
                        ,nomeFilaTransacaoConsolidacaoAgFrete
                        ,nomeFilaPostergacaoAbastecimentos
                        ,nomeFilaProcessarCicloPostergacao
                        ,nomeFilaAnonimizacaMotorista
                        ,nomeFilaAnonimizacaoExclusaoMotoristaAuditoria
                        ,nomeFilaProcessarPropostaAntecipacao);
        criaFilaSeNaoExistem(amazonSQSAsync, nomeFilas);
        return new QueueMessagingTemplate(amazonSQSAsync);
    }

    /**
     * Cria o QueueMessageHandlerFactory para a serialização de mensagens das fila
     * @param objectMapper Serializador Json
     * @return o QueueMessageHandlerFactory configurado
     */
    @Bean
    public QueueMessageHandlerFactory queueMessageHandlerFactory(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter mappingJackson2MessageConverter = new MappingJackson2MessageConverter();
        mappingJackson2MessageConverter.setSerializedPayloadClass(String.class);
        mappingJackson2MessageConverter.setObjectMapper(objectMapper);
        mappingJackson2MessageConverter.setStrictContentTypeMatch(false);
        QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
        factory.setArgumentResolvers(Arrays.asList(
                new AcknowledgmentHandlerMethodArgumentResolver("Acknowledgment"),
                new HeaderMethodArgumentResolver(null, null),
                new PayloadArgumentResolver(mappingJackson2MessageConverter, null, false) {
                    @Override
                    public boolean supportsParameter(MethodParameter parameter) {
                        return parameter.hasParameterAnnotation(Payload.class) || !parameter.hasParameterAnnotations();
                    }
                }
        ));
        return factory;
    }

    /**
     * Cria todas as filas necessarias caso não exista
     *
     * @param amazonSQSAsync client de acesso ao serviço da aws
     * @param nomeFilas Lista dos nomes das filas usadas na aplicação
     */
    private void criaFilaSeNaoExistem(AmazonSQSAsync amazonSQSAsync, List<String> nomeFilas) {
        ListQueuesResult listQueuesResult = amazonSQSAsync.listQueues(new ListQueuesRequest(prefixoFilas));
        nomeFilas.forEach(x -> criaFilaSeNaoExistem(amazonSQSAsync, listQueuesResult, x));
    }

    /**
     * Cria a fila caso não exista
     *
     * @param amazonSQSAsync client de acesso ao serviço da aws
     * @param listQueuesResult lista de filas existentes na aws
     * @param nomeFila nome da fila que precisa ser verificada
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
     * @param amazonSQSAsync client de acesso ao serviço da aws
     * @param nomeFila nome da fila que precisa ser criada
     */
    private void criarFila(AmazonSQSAsync amazonSQSAsync, String nomeFila) {
        final CreateQueueRequest createQueueRequest = new CreateQueueRequest()
                    .withQueueName(nomeFila)
                    .addAttributesEntry(
                            QueueAttributeName.VisibilityTimeout.toString()
                            , getMessageTimeoutPorNomeFila(nomeFila))
                    .addAttributesEntry(
                            QueueAttributeName.MessageRetentionPeriod.toString()
                            , getMessageRetentionPorNomeFila(nomeFila))
                    .addAttributesEntry(
                            QueueAttributeName.ReceiveMessageWaitTimeSeconds.toString()
                            , RECEIVE_MESSAGE_WAIT_TIME_PADRAO);
        amazonSQSAsync.createQueue(createQueueRequest);
    }

    /**
     * Verifica se a fila existe na aws
     *
     * @param listQueuesResult lista de filas na aws
     * @param nomeFila nome da fila que precisa ser verificada
     * @return true caso a fila exista na lista de filas na aws
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

    private String getMessageTimeoutPorNomeFila(String nomeFila) {
        if (nomeFilaAnonimizacaoExclusaoMotoristaAuditoria.equals(nomeFila)) {
            return "120";
        }
        return MESSAGE_TIMEOUT_PADRAO;
    }

    private String getMessageRetentionPorNomeFila(String nomeFila) {
        if (nomeFilaAnonimizacaoExclusaoMotoristaAuditoria.equals(nomeFila)) {
            return "14";
        }
        return MESSAGE_RETENTION_PADRAO;
    }
}
