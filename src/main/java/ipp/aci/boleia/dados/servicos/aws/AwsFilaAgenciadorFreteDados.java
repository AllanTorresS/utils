package ipp.aci.boleia.dados.servicos.aws;

import ipp.aci.boleia.dados.IFilaAgenciadorFreteDados;
import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositorio da fila de autorização de pagamento consolidado
 */
@Repository
public class AwsFilaAgenciadorFreteDados implements IFilaAgenciadorFreteDados {

    /**
     * nome da fila de processamento do consolidado de uma transacao
     */
    @Value("${aws.sqs.transacao.consolidacao}")
    private String nomeFilaTransacaoConsolidacao;

    /**
     * Gerenciador de acesso à fila
     */
    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Override
    public void enviarTransacaoParaFilaDeConsolidacao(Transacao transacao) {
        this.queueMessagingTemplate.send(nomeFilaTransacaoConsolidacao, MessageBuilder.withPayload(transacao.getId()).build());
    }

}
