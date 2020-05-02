package ipp.aci.boleia.dados.servicos.rest;

import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;

/**
 * Implementa um consumidor de uma resposta HTTP
 * @param <T> A classe da resposta a ser consumida
 */
@FunctionalInterface
public interface ConsumidorHttp<T> {

    /**
     * Consome a resposta HTTP recebida
     * @param resposta A resposta
     * @return retorna um objeto do tipo &lt;T&gt; representando o tratamento da requisição
     * @throws IOException Quando o processamento da resposta falha
     */
    T consumir(CloseableHttpResponse resposta) throws IOException;
}
