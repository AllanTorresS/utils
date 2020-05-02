package ipp.aci.boleia.dominio.vo;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Expõe as credenciais de acesso à API do AWS para os serviços de
 * integração da aplicação (S3, SNS, ...).
 */
@Component
@Primary
public class CredenciaisAwsVo implements AWSCredentialsProvider {

    @Value("${aws.credentials.accessKey.id}")
    private String accessKeyId;

    @Value("${aws.credentials.accessKey.secret}")
    private String secretAccessKey;

    @Override
    public AWSCredentials getCredentials() {
        return new AWSCredentials() {

            @Override
            public String getAWSAccessKeyId() {
                return getAccessKeyId();
            }

            @Override
            public String getAWSSecretKey() {
                return getSecretAccessKey();
            }
        };
    }

    @Override
    public void refresh() {
        // nada a fazer
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }
}
