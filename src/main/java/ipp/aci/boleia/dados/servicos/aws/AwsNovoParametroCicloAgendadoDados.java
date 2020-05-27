package ipp.aci.boleia.dados.servicos.aws;

import ipp.aci.boleia.dados.IFilaNovoParametroCicloAgendadoDados;
import ipp.aci.boleia.dominio.Frota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Repository;

/**
 * Implementação da fila de Alteração de Parâmetro de ciclo de uma frota
 */
@Repository
public class AwsNovoParametroCicloAgendadoDados implements IFilaNovoParametroCicloAgendadoDados {

    /**
     * Nome da fila que será acessada
     */
    @Value("${aws.sqs.frota-param-ciclo.aplica-novo-ciclo}")
    private String nomeFilaFrotaParamCiclo;

    /**
     * Gerenciador de acesso à fila
     */
    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Override
    public void enviarFrotaParaAplicarNovoCiclo(Frota frota) {
        this.queueMessagingTemplate.send(nomeFilaFrotaParamCiclo,
                MessageBuilder
                        .withPayload(frota.getId())
                        .build());
    }
}
