package ipp.aci.boleia.dados.servicos.aws;

import ipp.aci.boleia.dados.IFilaProcessarArquivosLote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementação do repositório da fila de processamento de arquivos em lote
 */
@Repository
public class AwsFilaGeradoraNotasFiscaisLoteDados implements IFilaProcessarArquivosLote {

    /**
     * Nome da fila que será acessada
     */
    @Value("${aws.sqs.arquivos.download-lote}")
    private String nomeFilaDownloadLote;

    /**
     * Gerenciador de acesso à fila
     */
    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Override
    public void enviarArquivosParaDownloadLote(Long idDownloadArquivo) {
        this.queueMessagingTemplate.send(nomeFilaDownloadLote,
                MessageBuilder
                        .withPayload(idDownloadArquivo.toString())
                        .build());
    }
}
