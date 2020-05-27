package ipp.aci.boleia.dados.servicos.aws;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClientBuilder;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.amazonaws.services.simpleemail.model.SendRawEmailResult;
import com.google.common.util.concurrent.RateLimiter;
import ipp.aci.boleia.dados.IEmailEnvioDados;
import ipp.aci.boleia.dominio.vo.CredenciaisAwsVo;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Properties;

/**
 * Classe para integração com o serviço de email Amazon SES
 */
@Repository
public class AwsEmailEnvioDados implements InitializingBean, IEmailEnvioDados {

    private static final Logger LOGGER = LoggerFactory.getLogger(AwsEmailEnvioDados.class);
    private static final int MAX_DESTINATARIOS_POR_EMAIL = 50;
    private static final String CONTENT_TYPE_CHARSET = "text/html; charset=UTF-8";
    public static final String THROTTLING = "Throttling";
    public static final String MAXIMUM_SENDING_RATE_EXCEEDED = "Maximum sending rate exceeded.";
    public static final int TEMPO_ESPERA_MAXIMO_MILISSEGUNDOS = 3000;
    public static final int TEMPO_ESPERA_MINIMO_MILISSEGUNDOS = 10;

    @Autowired
    private CredenciaisAwsVo credenciais;

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private Mensagens mensagens;

    @Value("${aws.ses.region}")
    private String region;

    @Value("${aws.ses.mail}")
    private String mail;

    @Value("${aws.ses.maxSendRate}")
    private Integer requestsPorSegundoAoSES;

    @Value("${aws.ses.maxSendRateRetryLimit}")
    private Integer tentativasAposAtingirLimitDeRequestSES;

    private AmazonSimpleEmailServiceAsync clienteSES;

    @Override
    public void afterPropertiesSet() throws Exception {
        clienteSES = AmazonSimpleEmailServiceAsyncClientBuilder.standard()
                .withCredentials(credenciais)
                .withRegion(region)
                .build();
    }

    @Override
    public void enviarEmail(String assunto, String corpo, List<String> destinatarios) {
        enviarEmail(assunto, corpo, destinatarios, null, null);
    }

    @Override
    public void enviarEmail(String assunto, String corpo, List<String> destinatarios, DataSource anexo, String nomeAnexo) {
        while (!destinatarios.isEmpty()) {
            int tamanhoLote = Math.min(destinatarios.size(), MAX_DESTINATARIOS_POR_EMAIL);
            List<String> lote = destinatarios.subList(0, tamanhoLote);
            enviarLote(assunto, corpo, lote, anexo, nomeAnexo);
            destinatarios = destinatarios.subList(tamanhoLote, destinatarios.size());
        }
    }

    /**
     * Envia o email ao conjunto de destinatarios informado
     * Segue a recomentação da AWS conforme em:
     * <a href="https://aws.amazon.com/pt/blogs/messaging-and-targeting/how-to-handle-a-throttling-maximum-sending-rate-exceeded-error/">Throttling – Maximum sending rate exceeded</a>
     *
     * Note que o limite é baseada no número de destinatários, e não no número de mensagens.
     *
     * @param assunto O assunto
     * @param corpo O corpo
     * @param destinatarios A lista de destinatarios
     * @param anexo o arquivo a ser anexado ao email
     * @param nomeAnexo o nome do arquivo anexado
     */
    private void enviarLote(String assunto, String corpo, List<String> destinatarios, DataSource anexo, String nomeAnexo) {

            RateLimiter limitadorDeTaxa = RateLimiter.create(requestsPorSegundoAoSES);
            try {
                MimeMessage message = construirMensagemEmail(assunto, corpo, anexo, nomeAnexo);
                for (String destinatario : destinatarios) {
                    int tentativas = tentativasAposAtingirLimitDeRequestSES;
                    while (tentativas --> 0){
                        try{
                            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                            message.setRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
                            message.writeTo(outputStream);

                            RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));
                            SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest(rawMessage);

                            //Esperar pela permissão estar disponivel para enviar
                            limitadorDeTaxa.acquire();

                            SendRawEmailResult sendResult = clienteSES.sendRawEmail(rawEmailRequest);
                            LOGGER.info(mensagens.obterMensagem("envio.email.tentativa.sucesso"), assunto, ambiente.getIdentificadorAmbiente(), destinatario);
                            break;
                        } catch (AmazonServiceException e) {
                            if (THROTTLING.equals(e.getErrorCode()) && MAXIMUM_SENDING_RATE_EXCEEDED.equals(e.getMessage())) {
                                long tempoDeEsperaDeTentativa = obterTempoDeEspera(tentativas, TEMPO_ESPERA_MINIMO_MILISSEGUNDOS, TEMPO_ESPERA_MAXIMO_MILISSEGUNDOS);
                                if (tentativas > 0) {
                                    LOGGER.warn(mensagens.obterMensagem("envio.email.tentativa.falha"), destinatario);
                                } else {
                                    LOGGER.error(mensagens.obterMensagem("envio.email.erro.maximo.tentativas"), destinatario, e);
                                }

                                try {
                                    Thread.sleep(tempoDeEsperaDeTentativa);
                                } catch (InterruptedException e1) {
                                    return;
                                }
                            } else {
                                LOGGER.error(mensagens.obterMensagem("envio.email.erro.amazon"), e.getErrorCode(), destinatario, e);
                                break;
                            }

                        } catch (AddressException e) {
                            LOGGER.error(mensagens.obterMensagem("envio.email.erro.formato.invalido"), destinatario, e);
                            break;
                        } catch(Exception e) {
                            LOGGER.error(mensagens.obterMensagem("envio.email.erro.inesperado"), destinatario, e);
                            break;
                        }
                    }
                }

            } catch (Exception e){
                LOGGER.error(mensagens.obterMensagem("envio.email.erro.inesperado.lote"), e);
            }
    }


    /**
     * Retorna o tempo de espera conforme o numero de tentativas, limitando entre intervalo de Minimo e Maximo.
     *
     * @param tentativaAtual quantidade de tentativas
     * @param tempoEsperaMinimoMilissegundos tempo minimo
     * @param tempoEsperaMaximoMilissegundos tempo maximo
     * @return tempo em millisegundos
     */
    private long obterTempoDeEspera(int tentativaAtual, long tempoEsperaMinimoMilissegundos, long tempoEsperaMaximoMilissegundos) {
        tentativaAtual = Math.max(0, tentativaAtual);
        long tempoDeEsperaEmMillisegundos = (long) (tempoEsperaMinimoMilissegundos*Math.pow(2, tentativaAtual));
        return Math.min(tempoDeEsperaEmMillisegundos, tempoEsperaMaximoMilissegundos);
    }

    /**
     * Cria a mensagem de email utilizando a javax-mail-api, para permitir codificacao de caracteres UTF-8 no campo from
     * @param assunto O assunto do email
     * @param corpo O corpo do email
     * @param anexo o arquivo a ser anexado ao email
     * @param nomeAnexo Nome do arquivo a ser anexado ao email
     * @return A mensagem a ser enviada
     * @throws MessagingException Quando ocorre algum erro ao codificar a mensagem
     */
    private MimeMessage construirMensagemEmail(String assunto, String corpo, DataSource anexo, String nomeAnexo) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.addHeader("Source-Env", ambiente.getIdentificadorAmbiente());
        message.setSubject(assunto, "UTF-8");
        message.setFrom(new InternetAddress(ambiente.getNomeSistema() + " <" + mail + ">"));

        if (anexo != null) {
            Multipart multipartMessage = new MimeMultipart();
            BodyPart textPart = new MimeBodyPart();
            BodyPart attachmentPart = new MimeBodyPart();

            textPart.setContent(corpo, CONTENT_TYPE_CHARSET);
            attachmentPart.setDataHandler(new DataHandler(anexo));
            attachmentPart.setFileName(nomeAnexo);

            multipartMessage.addBodyPart(textPart);
            multipartMessage.addBodyPart(attachmentPart);

            message.setContent(multipartMessage);
            message.saveChanges();
        } else {
            message.setContent(corpo, CONTENT_TYPE_CHARSET);
        }

        return message;
    }

    public void setClienteSES(AmazonSimpleEmailServiceAsync clienteSES) {
        this.clienteSES = clienteSES;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
