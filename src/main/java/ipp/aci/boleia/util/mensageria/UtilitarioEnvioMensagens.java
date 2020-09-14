package ipp.aci.boleia.util.mensageria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Utilitário para envio de informações a um serviço de mensageria (ex.: RabbitMQ)
 */
@Component
public class UtilitarioEnvioMensagens {

    @Autowired
    private FilaRabbitMQ filaRabbitMQ;

    public void enviarMensagem(String mensagem) throws IOException {
        filaRabbitMQ.enviarMensagem(filaRabbitMQ.getNomeTopicoRelatorio(), mensagem);
    }
}
