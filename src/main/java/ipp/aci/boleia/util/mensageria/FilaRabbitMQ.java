package ipp.aci.boleia.util.mensageria;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class FilaRabbitMQ {
    /**
     * Nome do host da fila
     */
    @Value("${rabbitmq.hostname}")
    private String nomeHost;

    @Value("${topico.motor-geracao-relatorios}")
    private String nomeTopicoRelatorio;

    @Value("${rabbitmq.prefixo.fila}")
    private String prefixoFila;

    @Value("${rabbitmq.prefixo.chave-rota}")
    private String prefixoChaveRota;

    @Value("${rabbitmq.tipo-exchange}")
    private String tipoExchange;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    private Channel canal;

    protected String getNomeHost() {
        return nomeHost;
    }

    protected String getNomeTopicoRelatorio() {
        return nomeTopicoRelatorio;
    }

    protected String getPrefixoFila() {
        return prefixoFila;
    }

    protected String getPrefixoChaveRota() {
        return prefixoChaveRota;
    }

    protected String getNomeFila() {
        return this.prefixoFila + this.nomeTopicoRelatorio;
    }

    protected Channel getCanal() {
        return canal;
    }

    protected void setCanal(Channel canal) {
        this.canal = canal;
    }

    protected String getTipoExchange() {
        return tipoExchange;
    }

    protected String getUsername() {
        return username;
    }

    protected String getPassword() {
        return password;
    }

    /**
     * Envia mensagem para fila
     *
     * @param chaveRota A chave da fila
     * @param mensagem A mensagem a ser enviada
     */
    public void enviarMensagem(String chaveRota, String mensagem) {
        ConnectionFactory fabricaConexoes = new ConnectionFactory();
        fabricaConexoes.setHost(nomeHost);
        fabricaConexoes.setUsername(username);
        fabricaConexoes.setPassword(password);

        try {
            Connection connection = fabricaConexoes.newConnection();
            Channel canal = connection.createChannel();
            canal.exchangeDeclare(nomeTopicoRelatorio, getTipoExchange(), true);

            StringBuilder nomeChaveRota = new StringBuilder(prefixoChaveRota);
            nomeChaveRota.append(chaveRota);

            canal.basicPublish(nomeTopicoRelatorio, nomeChaveRota.toString(),
                    new AMQP.BasicProperties.Builder()
                        .contentType("text/plain")
                        .deliveryMode(2)
                        .priority(1)
                        .build(),
                    mensagem.getBytes());
        } catch (IOException | TimeoutException e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
    }
}
