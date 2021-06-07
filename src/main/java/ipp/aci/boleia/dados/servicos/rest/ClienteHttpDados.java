package ipp.aci.boleia.dados.servicos.rest;

import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.UtilitarioXml;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Implementa um cliente HTTP
 */
@Repository
public class ClienteHttpDados implements IClienteHttpDados {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteHttpDados.class);

    public static final String APPLICATION_XML = "application/xml;";
    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";
    public static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    private CloseableHttpClient cliente;

    @Value("${timeout.http.request}")
    private int timeout;

    /**
     * Cria o cliente HTTP reutilizavel
     */
    @PostConstruct
    public void init(){
        this.cliente = HttpClientBuilder.create().build();
    }

    @Override
    public <T> T doGet(String url, ConsumidorHttp<T> consumidorHttp) {
        return doGet(url, null, null, null, consumidorHttp);
    }

    @Override
    public <T> T doGet(String url, Header[] headers, ConsumidorHttp<T> consumidorHttp){
        return doGet(url, null, null, headers, consumidorHttp);
    }

    @Override
    public <T> T doGetJson(String url, Object body, Header[] headers, ConsumidorHttp<T> consumidorHttp){
        HttpGetWithEntity request = prepararRequest(new HttpGetWithEntity(url), null, null, APPLICATION_JSON_CHARSET_UTF_8, headers);
        if(body != null) {
            StringEntity params = new StringEntity(UtilitarioJson.toJSON(body), StandardCharsets.UTF_8);
            request.setEntity(params);
        }
        return executar(request, consumidorHttp);
    }

    @Override
    public <T> T doGet(String url, String username, String password, Header[] headers, ConsumidorHttp<T> consumidorHttp) {
        HttpGet request = prepararRequest(new HttpGet(url), username, password, null, headers);
        return executar(request, consumidorHttp);
    }

    @Override
    public <T> T doPostJson(String url, Object body, Header[] headers, ConsumidorHttp<T> consumidorHttp){
        return doPostJson(url, null, null, body, headers, consumidorHttp);
    }


    @Override
    public <T> T doPostXml(String url, Object body, Header[] headers, ConsumidorHttp<T> consumidorHttp){
        return doPostXml(url, null, null, body, headers, consumidorHttp);
    }

    @Override
    public <T> T doPostXml(String url, String username, String password, Object body, Header[] headers, ConsumidorHttp<T> consumidorHttp){
        HttpPost request = prepararRequest(new HttpPost(url), username, password, APPLICATION_XML, headers);
        StringEntity params = new StringEntity(UtilitarioXml.toXml(body), StandardCharsets.UTF_8);
        request.setEntity(params);
        return executar(request, consumidorHttp);
    }

    @Override
    public <T> T doPostJson(String url, String username, String password, Header[] headers, ConsumidorHttp<T> consumidorHttp){
        return doPostJson(url, username, password, null, headers, consumidorHttp);
    }

    @Override
    public <T> T doPostJson(String url, String username, String password, Object body, Header[] headers, ConsumidorHttp<T> consumidorHttp){
        HttpPost request = prepararRequest(new HttpPost(url), username, password, APPLICATION_JSON_CHARSET_UTF_8, headers);
        if(body != null) {
            String requestBody;
            if(body instanceof String) {
                requestBody = (String) body;
            } else {
                requestBody = UtilitarioJson.toJSON(body);
            }
            StringEntity params = new StringEntity(requestBody, StandardCharsets.UTF_8);
            request.setEntity(params);
        }
        return executar(request, consumidorHttp);
    }

    @Override
    public <T> T doPostFormEncoded(String url, Map<String, String> form, ConsumidorHttp<T> consumidorHttp){
        HttpPost request = prepararRequest(new HttpPost(url), null, null, APPLICATION_X_WWW_FORM_URLENCODED, null);
        ByteArrayEntity params = criarCorpoFormPost(form);
        request.setEntity(params);
        return executar(request, consumidorHttp);
    }

    @Override
    public <T> T doPutJson(String url, Object body, Header[] headers, ConsumidorHttp<T> consumidorHttp){
        return doPutJson(url, null, null, body, headers, consumidorHttp);
    }

    @Override
    public <T> T doPutJson(String url, String username, String password, Object body, Header[] headers, ConsumidorHttp<T> consumidorHttp){
        HttpPut request = prepararRequest(new HttpPut(url), username, password, APPLICATION_JSON_CHARSET_UTF_8, headers);
        StringEntity params = new StringEntity(UtilitarioJson.toJSON(body), StandardCharsets.UTF_8);
        request.setEntity(params);
        return executar(request, consumidorHttp);
    }

    @Override
    public <T> T doPatchJson(String url, Object body, Header[] headers, ConsumidorHttp<T> consumidorHttp){
        HttpPatch request = prepararRequest(new HttpPatch(url), null, null, APPLICATION_JSON_CHARSET_UTF_8, headers);
        StringEntity params = new StringEntity(UtilitarioJson.toJSON(body), StandardCharsets.UTF_8);
        request.setEntity(params);
        return executar(request, consumidorHttp);
    }

    @Override
    public <T> T doDelete(String url, Header[] headers, ConsumidorHttp<T> consumidorHttp){
        return doDelete(url, null, null, headers, consumidorHttp);
    }

    @Override
    public <T> T doDelete(String url, String username, String password, Header[] headers, ConsumidorHttp<T> consumidorHttp) {
        HttpDelete request = prepararRequest(new HttpDelete(url), username, password, APPLICATION_JSON_CHARSET_UTF_8, headers);
        return executar(request, consumidorHttp);
    }


    /**
     * Executa uma requisicao HTTP
     *
     * @param request A requisicao
     * @param consumidorHttp O consumidor da resposta
     * @param <T> O tipo da resposta a ser retornada
     * @return A resposta processada pelo consumidor
     */
    private <T> T executar(HttpRequestBase request, ConsumidorHttp<T> consumidorHttp){
        CloseableHttpResponse resposta = null;
        try {
            resposta = cliente.execute(request);
            if(consumidorHttp != null) {
                return consumidorHttp.consumir(resposta);
            }
            return null;
        } catch (IOException e) {
            throw new ExcecaoBoleiaRuntime(e);
        } finally {
            fecharResposta(resposta);
        }
    }

    /**
     * Prepara a requisicao inserindo os headers desejados
     *
     * @param request A requisicao
     * @param username O usuario para autenticacao HTTP BASIC ou null, caso desnecessario
     * @param password A senha para autenticacao HTTP BASIC ou null, caso desnecessario
     * @param contentType O tipo de codificacao do corpo
     * @param headers Os cabecalhos a serem adicionados
     * @param <R> O tipo do objeto de requisicao
     * @return A requisicao montada
     */
    public <R extends HttpRequestBase> R prepararRequest(R request, String username, String password, String contentType, Header[] headers){

        request.setConfig(createTimeoutConfig());

        if(headers != null) {
            for(Header header : headers){
                request.addHeader(header);
            }
        }

        if(contentType != null) {
            request.addHeader("Content-Type", contentType);
        }

        if(username != null && password != null) {
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
            String authHeader = "Basic " + new String(encodedAuth);
            request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
        }

        return request;
    }

    /**
     * Transforma um map em um corpo de requisicao no formato <code>application/x-www-form-urlencoded</code>
     *
     * @param form O mapa de parametros
     * @return O corpo do post
     */
    private ByteArrayEntity criarCorpoFormPost(Map<String, String> form) {
        try {
            URLCodec codec = new URLCodec(StandardCharsets.UTF_8.displayName());
            StringBuilder strForm = new StringBuilder();
            int numParams = form.size();
            int paramCorrente = 1;
            for(String key : form.keySet()) {
                strForm.append(codec.encode(key));
                strForm.append("=");
                strForm.append(codec.encode(form.get(key)));
                if(paramCorrente < numParams) {
                    strForm.append("&");
                }
            }
            byte[] bytes = strForm.toString().getBytes(StandardCharsets.UTF_8);
            return new ByteArrayEntity(bytes);
        } catch (EncoderException e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    /**
     * Fecha a resposta HTTP se possivel
     *
     * @param resposta A resposta recebida
     */
    private void fecharResposta(CloseableHttpResponse resposta) {
        try {
            if(resposta != null) {
                resposta.close();
            }
        } catch (IOException e) {
            // nada a fazer
            LOGGER.debug(e.getMessage(), e);
        }
    }

    /**
     * Cria as configuracoes de timeout para a requisicao
     *
     * @return As configuracoes de timeout
     */
    private RequestConfig createTimeoutConfig() {
        RequestConfig.Builder requestConfig = RequestConfig.custom();
        requestConfig.setConnectTimeout(timeout * 1000);
        requestConfig.setConnectionRequestTimeout(timeout * 1000);
        requestConfig.setSocketTimeout(timeout * 1000);
        return requestConfig.build();
    }
}
