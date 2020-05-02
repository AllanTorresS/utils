package ipp.aci.boleia.dados.servicos.aws;

import ipp.aci.boleia.dados.IFilaAutorizacaoPagamentoRepasseDados;
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
public class AwsFilaAutorizacaoPagamentoRepasseDados implements IFilaAutorizacaoPagamentoRepasseDados {

    /**
     * nome da fila que será acessada de autorização de pagamento
     */
    @Value("${aws.sqs.ciclo-repasse.autorizacao-pagamento}")
    private String nomeFilaAutorizacaoPagamentoRepasse;

    @Value("${aws.sqs.ciclo-repasse.processar-ciclo}")
    private String nomeFilaConsolidacao;

    @Value("${aws.sqs.ciclo-repasse.autorizacao-pagamento.erro-validacao-campanha}")
    private String nomeFilaErroValidacaoCampanha;

    @Value("${aws.sqs.ciclo-repasse.autorizacao-pagamento.erro-validacao-ciclo}")
    private String nomeFilaErroValidacaoCiclo;

    /**
     * Gerenciador de acesso à fila
     */
    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Override
    public void enviarAutorizacaoPagamento(AutorizacaoPagamento autorizacaoPagamento) {
        this.queueMessagingTemplate.send(nomeFilaAutorizacaoPagamentoRepasse,
                MessageBuilder
                        .withPayload(autorizacaoPagamento.getId().toString())
                        .build());
    }

    @Override
    public void enviarParaFilaConsolidacao(Long identificadorCiclo) {
        this.queueMessagingTemplate.send(nomeFilaConsolidacao,
                MessageBuilder
                        .withPayload(identificadorCiclo.toString())
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
    public void enviarMensagemErroValidacaoCiclo(Long idAutorizacaoPagamento) {
        this.queueMessagingTemplate.send(nomeFilaErroValidacaoCiclo,
                MessageBuilder
                        .withPayload(idAutorizacaoPagamento.toString())
                        .build());
    }
}
