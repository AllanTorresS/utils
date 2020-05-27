package ipp.aci.boleia.util.mensageria;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import ipp.aci.boleia.dominio.enums.TipoRelatorioMotorGerador;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    private Channel canal;

    private void criarFilas(String nomeFila, String nomeChaveRota) throws IOException {
        this.canal.queueDeclare(nomeFila, true, false, false, null);
        this.canal.queueBind(nomeFila, this.nomeTopicoRelatorio, nomeChaveRota);
    }

    @PostConstruct
    public void configurarFila() throws IOException, TimeoutException {
        ConnectionFactory fabricaConexoes = new ConnectionFactory();
        fabricaConexoes.setHost(nomeHost);

        try {
            Connection connection = fabricaConexoes.newConnection();

            this.canal = connection.createChannel();

            this.canal.exchangeDeclare(nomeTopicoRelatorio, tipoExchange, true);

            for (TipoRelatorioMotorGerador tipoRelatorio : TipoRelatorioMotorGerador.values()) {
                StringBuilder nomeFila = new StringBuilder(prefixoFila);
                StringBuilder nomeChaveRota = new StringBuilder(prefixoChaveRota);
                nomeFila.append(tipoRelatorio.name());
                nomeChaveRota.append(tipoRelatorio.name());

                this.criarFilas(nomeFila.toString(), nomeChaveRota.toString());
            }

        } catch (IOException | TimeoutException e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    /**
     * Envia uma mensagem para uma fila RabbitMQ
     *
     * @param chaveRota A chave da rota(ou tópico) para a qual a mensagem será enviada
     * @param mensagem A mensagem a ser enviada
     * @throws IOException Exceção lançada em caso de erro no envio
     */
    public void enviarMensagem(String chaveRota, String mensagem) throws IOException {

        StringBuilder nomeChaveRota = new StringBuilder(prefixoChaveRota);
        nomeChaveRota.append(chaveRota);

        canal.basicPublish(nomeTopicoRelatorio, nomeChaveRota.toString(), null, mensagem.getBytes());
    }
}
