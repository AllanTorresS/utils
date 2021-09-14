package ipp.aci.boleia.dados.servicos.aws;

import ipp.aci.boleia.dados.IFilaEmailsAlteracaoStatusFrota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Repository;

@Repository
public class AwsFilaEmailsAlteracaoStatusFrota implements IFilaEmailsAlteracaoStatusFrota {

    @Value("${aws.sqs.envio-email-status-frota}")
    private String nomeFilaEmailsAlteracaoStatusFrota;

    /**
     * Gerenciador de acesso à fila
     */
    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Override
    public void enviarMotivoParaFila(Long idMotivoAlteracaoStatus) {
        this.queueMessagingTemplate.send(nomeFilaEmailsAlteracaoStatusFrota,
                MessageBuilder
                        .withPayload(idMotivoAlteracaoStatus.toString())
                        .build());
    }
}
