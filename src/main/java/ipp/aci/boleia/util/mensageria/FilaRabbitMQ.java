package ipp.aci.boleia.util.mensageria;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Componente responsável pela configuração da conexão com servidor RabbitMQ utilizado para exportação de relatórios
 */
@Component
public class FilaRabbitMQ {
    /**
     * Nome do host da fila
     */
    @Value("${rabbitmq.hostname}")
    private String nomeHost;

    @Value("${rabbitmq.username}")
    private String usuario;

    @Value("${rabbitmq.password}")
    private String senha;

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

    @Value("${rabbitmq.vhost}")
    private String vhost;

    private Channel canal;
    
    private ConnectionFactory fabricaConexoes;
    
    private Connection conexao;

    @PostConstruct
    private void criarFabricaDeConexoes(){
        fabricaConexoes = new ConnectionFactory();
        fabricaConexoes.setHost(nomeHost);
        fabricaConexoes.setUsername(username);
        fabricaConexoes.setPassword(password);
        fabricaConexoes.setVirtualHost(vhost);
    }
    
    protected String getNomeHost() {
        return nomeHost;
    }

    protected String getUsuario() { return usuario; }

    protected String getSenha() { return senha; }

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

    protected String getVhost() {
        return vhost;
    }

    /**
     * Envia mensagem para fila
     *
     * @param chaveRota A chave da fila
     * @param mensagem A mensagem a ser enviada
     */
    public void enviarMensagem(String chaveRota, String mensagem) {

        try (Channel canal = getConexao().createChannel()){
            
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("x-delayed-type", "direct");
            canal.exchangeDeclare(nomeTopicoRelatorio, getTipoExchange(), true, false, args);

            StringBuilder nomeChaveRota = new StringBuilder(prefixoChaveRota);
            nomeChaveRota.append(chaveRota);

            Map<String, Object> headers = new HashMap<String, Object>();
            headers.put("x-delay", 2000);
            canal.basicPublish(nomeTopicoRelatorio, nomeChaveRota.toString(),
                    new AMQP.BasicProperties.Builder()
                        .headers(headers)
                        .contentType("text/plain")
                        .deliveryMode(2)
                        .priority(1)
                        .build(),
                    mensagem.getBytes());
        } catch (IOException | TimeoutException e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    private Connection getConexao() throws IOException, TimeoutException {    
        if (conexao == null || !conexao.isOpen()){
            conexao = fabricaConexoes.newConnection();
        }
        return conexao;
    }
}
