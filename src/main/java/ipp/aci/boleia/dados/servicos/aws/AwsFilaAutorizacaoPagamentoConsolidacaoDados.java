package ipp.aci.boleia.dados.servicos.aws;

import ipp.aci.boleia.dados.IFilaAutorizacaoPagamentoConsolidacaoDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositorio da fila de autorização de pagamento consolidado
 */
@Repository
public class AwsFilaAutorizacaoPagamentoConsolidacaoDados implements IFilaAutorizacaoPagamentoConsolidacaoDados {

    /**
     * nome da fila que será acessada de autorização de pagamento
     */
    @Value("${aws.sqs.transacao-consolidada.autorizacao-pagamento}")
    private String nomeFilaAutorizacaoPagamentoConsolidacao;

    @Value("${aws.sqs.transacao-consolidada.autorizacao-pagamento.erro-validacao-campanha}")
    private String nomeFilaErroValidacaoCampanha;

    @Value("${aws.sqs.transacao-consolidada.processar-ciclo}")
    private String nomeFilaConsolidacaoCiclo;

    /**
     * Gerenciador de acesso à fila
     */
    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Override
    public void enviarAutorizacaoPagamento(AutorizacaoPagamento autorizacaoPagamento) {
        this.queueMessagingTemplate.send(nomeFilaAutorizacaoPagamentoConsolidacao,
                MessageBuilder
                        .withPayload(autorizacaoPagamento.getId().toString())
                        .build());
    }

    @Override
    public void enviarMensagemErroValidacaoCampanha(Long idAutorizacaoPagamento) {

        this.queueMessagingTemplate.send(nomeFilaErroValidacaoCampanha,
                MessageBuilder
                        .withPayload(idAutorizacaoPagamento.toString())
                        .build());
    }

    @Override
    public void enviarParaFilaConsolidacao(Long identificadorCiclo) {

        this.queueMessagingTemplate.send(nomeFilaConsolidacaoCiclo,
                MessageBuilder
                        .withPayload(identificadorCiclo.toString())
                        .build());
    }

}
