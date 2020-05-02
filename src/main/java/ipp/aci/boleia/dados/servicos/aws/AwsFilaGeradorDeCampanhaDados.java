package ipp.aci.boleia.dados.servicos.aws;

import ipp.aci.boleia.dados.IFilaGeradorCampanhaDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório da fila de geração de campanhas
 */
@Repository
public class AwsFilaGeradorDeCampanhaDados implements IFilaGeradorCampanhaDados {

    /**
     * Nome da fila que será acessada
     */
    @Value("${aws.sqs.gerador-campanha.autorizacao-pagamento}")
    private String nomeFilaCampanha;

    /**
     * Gerenciador de acesso à fila
     */
    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Override
    public void enviarAutorizacaoPagamento(AutorizacaoPagamento autorizacaoPagamento) {
        this.queueMessagingTemplate.send(nomeFilaCampanha,
                MessageBuilder
                        .withPayload(autorizacaoPagamento.getId().toString())
                        .build());
    }
}
