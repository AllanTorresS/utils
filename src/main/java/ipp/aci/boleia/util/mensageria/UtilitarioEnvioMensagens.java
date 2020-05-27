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

    /**
     * Envia uma mensagem para uma fila RabbitMQ
     *
     * @param mensagem A mensagem a ser enviada
     * @param nomeTopico O nome do tópico
     * @throws IOException Exceção lançada em caso de erro no envio
     */
    public void enviarMensagem(String mensagem, String nomeTopico) throws IOException {
        filaRabbitMQ.enviarMensagem(nomeTopico, mensagem);
    }
}
