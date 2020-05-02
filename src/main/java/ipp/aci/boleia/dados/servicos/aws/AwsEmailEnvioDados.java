package ipp.aci.boleia.dados.servicos.aws;


import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClientBuilder;
import com.amazonaws.services.simpleemail.model.RawMessage;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import com.amazonaws.services.simpleemail.model.SendRawEmailResult;
import ipp.aci.boleia.dados.IEmailEnvioDados;
import ipp.aci.boleia.dominio.vo.CredenciaisAwsVo;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
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
import java.util.stream.Collectors;

/**
 * Classe para integração com o serviço de email Amazon SES
 */
@Repository
public class AwsEmailEnvioDados implements InitializingBean, IEmailEnvioDados {

    private static final Logger LOGGER = LoggerFactory.getLogger(AwsEmailEnvioDados.class);
    private static final int MAX_DESTINATARIOS_POR_EMAIL = 50;
    private static final String CONTENT_TYPE_CHARSET = "text/html; charset=UTF-8";

    @Autowired
    private CredenciaisAwsVo credenciais;

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Value("${aws.ses.region}")
    private String region;

    @Value("${aws.ses.mail}")
    private String mail;

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
     * @param assunto O assunto
     * @param corpo O corpo
     * @param destinatarios A lista de destinatarios
     * @param anexo o arquivo a ser anexado ao email
     * @param nomeAnexo o nome do arquivo anexado
     */
    private void enviarLote(String assunto, String corpo, List<String> destinatarios, DataSource anexo, String nomeAnexo) {
        try {
            MimeMessage message = construirMensagemEmail(assunto, corpo, destinatarios, anexo, nomeAnexo);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);
            RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));
            SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest(rawMessage);
            SendRawEmailResult sendResult = clienteSES.sendRawEmail(rawEmailRequest);
            LOGGER.info("Mensagem [{}] enviada a partir de [{}] para {}", sendResult.getMessageId(), ambiente.getIdentificadorAmbiente(), destinatarios);
        } catch(Exception ex) {
            LOGGER.error(ex.getMessage(),ex);
        }
    }

    /**
     * Cria a mensagem de email utilizando a javax-mail-api, para permitir codificacao de caracteres UTF-8 no campo from
     * @param assunto O assunto do email
     * @param corpo O corpo do email
     * @param destinatarios A lista de destinatarios
     * @param anexo o arquivo a ser anexado ao email
     * @param nomeAnexo Nome do arquivo a ser anexado ao email
     * @return A mensagem a ser enviada
     * @throws MessagingException Quando ocorre algum erro ao codificar a mensagem
     */
    private MimeMessage construirMensagemEmail(String assunto, String corpo, List<String> destinatarios, DataSource anexo, String nomeAnexo) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);
        message.addHeader("Source-Env", ambiente.getIdentificadorAmbiente());
        message.setSubject(assunto, "UTF-8");
        message.setFrom(new InternetAddress(ambiente.getNomeSistema() + " <" + mail + ">"));
        List<InternetAddress> enderecosDestinatarios = destinatarios.stream().map(d -> {
            try {
                return new InternetAddress(d);
            } catch (AddressException e) {
                throw new ExcecaoBoleiaRuntime(e);
            }
        }).collect(Collectors.toList());
        message.setRecipients(Message.RecipientType.TO, enderecosDestinatarios.toArray(new InternetAddress[enderecosDestinatarios.size()]));

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
