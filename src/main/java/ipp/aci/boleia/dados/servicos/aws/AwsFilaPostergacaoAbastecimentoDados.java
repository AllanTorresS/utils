package ipp.aci.boleia.dados.servicos.aws;

import ipp.aci.boleia.dados.IFilaPostergacaoAbastecimentoDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositorio da fila de postergação de abastecimentos.
 */
@Repository
public class AwsFilaPostergacaoAbastecimentoDados implements IFilaPostergacaoAbastecimentoDados {

    @Value("${aws.sqs.transacao-consolidada.postergar-abastecimentos}")
    private String nomeFilaPostergarAbastecimentos;

    @Value("${aws.sqs.transacao-consolidada.processar-ciclo-postergacao}")
    private String nomeFilaProcessarCicloPostergacao;

    /**
     * Gerenciador de acesso à fila
     */
    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Override
    public void enviarParaPostergarAbastecimentos(Long idAbastecimento) {
        this.queueMessagingTemplate.send(nomeFilaPostergarAbastecimentos,
                MessageBuilder
                        .withPayload(idAbastecimento.toString())
                        .build());
    }

    @Override
    public void enviarParaProcessarCicloPostergacao(Long idConsolidado) {
        this.queueMessagingTemplate.send(nomeFilaProcessarCicloPostergacao,
                MessageBuilder
                        .withPayload(idConsolidado.toString())
                        .build());
    }
}
