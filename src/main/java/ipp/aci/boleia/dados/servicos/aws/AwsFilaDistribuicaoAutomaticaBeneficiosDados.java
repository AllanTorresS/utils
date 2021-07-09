package ipp.aci.boleia.dados.servicos.aws;

import ipp.aci.boleia.dados.IFilaDistribuicaoAutomaticaBeneficios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositorio da fila de distribuição automática.
 *
 * @author pedro.silva
 */
@Repository
public class AwsFilaDistribuicaoAutomaticaBeneficiosDados implements IFilaDistribuicaoAutomaticaBeneficios {

    @Value("${aws.sqs.beneficios.distribuicao-automatica}")
    private String nomeFilaDistribuicaoAutomatica;

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Override
    public void enviarParaDistribuicaoAutomatica(Long idDistribuicaoAutomatica) {
        queueMessagingTemplate.send(nomeFilaDistribuicaoAutomatica,
                MessageBuilder
                        .withPayload(idDistribuicaoAutomatica.toString())
                        .build());
    }
}
