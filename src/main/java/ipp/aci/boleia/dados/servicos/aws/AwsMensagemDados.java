package ipp.aci.boleia.dados.servicos.aws;

import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import ipp.aci.boleia.dados.IMensagemDados;
import ipp.aci.boleia.dominio.vo.CredenciaisAwsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe para integração com o serviço de notification Amazon SNS
 */
@Repository
public class AwsMensagemDados implements InitializingBean, IMensagemDados {

    @Autowired
    private CredenciaisAwsVo credenciais;

    @Value("${aws.sns.region}")
    private String region;

    @Value("${aws.sns.max.price}")
    private String maxPrice;

    @Value("${aws.sns.type}")
    private String type;

    private AmazonSNSAsync clienteSNS;

    private Map<String, MessageAttributeValue> atributosEnvioSms;

    @Override
    public void afterPropertiesSet() throws Exception {
        clienteSNS = AmazonSNSAsyncClientBuilder.standard()
                .withCredentials(credenciais)
                .withRegion(region)
                .build();
        prepararAtributosEnvioSms();
    }

    @Override
    public void enviarSMS(String mensagem, String numero) {
        clienteSNS.publishAsync(new PublishRequest()
                .withMessage(mensagem)
                .withPhoneNumber(numero)
                .withMessageAttributes(getAtributosEnvioSms()));
    }

    /**
     * @return mapa de atributos
     */
    public Map<String, MessageAttributeValue> getAtributosEnvioSms() {
        return atributosEnvioSms;
    }

    /**
     * Configura os atributos pertinentes ao envio de sms
     */
    private void prepararAtributosEnvioSms() {
        atributosEnvioSms = new HashMap<>();
        atributosEnvioSms.put("AWS.SNS.SMS.MaxPrice",
                new MessageAttributeValue().withStringValue(maxPrice).withDataType("Number"));
        atributosEnvioSms.put("AWS.SNS.SMS.SMSType",
                new MessageAttributeValue().withStringValue(type).withDataType("String"));
    }

}
