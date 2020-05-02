package ipp.aci.boleia.dados;

import ipp.aci.boleia.dados.servicos.rest.ConsumidorHttp;
import org.apache.http.Header;

import java.util.Map;

/**
 * Contrato para implementacao de um cliente REST
 */
public interface IClienteHttpDados {

    /**
     * Realiza uma requisição do tipo GET
     *
     * @param url Caminho do serviço
     * @param consumidorHttp A logica de consumo da resposta recebida
     * @param <T> O tipo do objeto de resposta
     * @return resposta da requisição
     */
    <T> T doGet(String url, ConsumidorHttp<T> consumidorHttp);

    /**
     * Realiza uma requisição do tipo GET
     *
     * @param url Caminho do serviço
     * @param headers Cabeçalhos adicionais
     * @param consumidorHttp A logica de consumo da resposta recebida
     * @param <T> O tipo do objeto de resposta
     * @return resposta da requisição
     */
    <T> T doGet(String url, Header[] headers, ConsumidorHttp<T> consumidorHttp);

    /**
     * Realiza uma requisição do tipo GET usando credenciais
     *
     * @param url Caminho do serviço
     * @param username Nome do usuário
     * @param password Senha do usuário
     * @param headers Cabeçalhos adicionais
     * @param consumidorHttp A logica de consumo da resposta recebida
     * @param <T> O tipo do objeto de resposta
     * @return resposta da requisição
     */
    <T> T doGet(String url, String username, String password, Header[] headers, ConsumidorHttp<T> consumidorHttp);

    /**
     * Realiza uma requisição do tipo POST
     *
     * @param url Caminho do serviço
     * @param body O objeto referente ao corpo da requisição
     * @param headers Cabeçalhos adicionais
     * @param consumidorHttp A logica de consumo da resposta recebida
     * @param <T> O tipo do objeto de resposta
     * @return resposta da requisição
     */
    <T> T doPostJson(String url, Object body, Header[] headers, ConsumidorHttp<T> consumidorHttp);

    /**
     * Realiza uma requisição do tipo POST
     *
     * @param url Caminho do serviço
     * @param body O Objeto referente ao corpo da requisição
     * @param headers Cabeçalhos adicionais
     * @param consumidorHttp A logica de consumo da resposta recebida
     * @param <T> O tipo do objeto de resposta
     * @return resposta da requisição
     */
    <T> T doPostXml(String url, Object body, Header[] headers, ConsumidorHttp<T> consumidorHttp);

    /**
     * Realiza uma requisição do tipo POST usando credenciais
     *
     * @param url Caminho do serviço
     * @param username Nome do usuário
     * @param password Senha do usuário
     * @param headers Cabeçalhos adicionais
     * @param consumidorHttp A logica de consumo da resposta recebida
     * @param <T> O tipo do objeto de resposta
     * @return resposta da requisição
     */
    <T> T doPostJson(String url, String username, String password, Header[] headers, ConsumidorHttp<T> consumidorHttp);

    /**
     * Realiza uma requisição do tipo POST usando credenciais
     *
     * @param url Caminho do serviço
     * @param username Nome do usuário
     * @param password Senha do usuário
     * @param body O objeto referente ao corpo da requisição
     * @param headers Cabeçalhos adicionais
     * @param consumidorHttp A logica de consumo da resposta recebida
     * @param <T> O tipo do objeto de resposta
     * @return resposta da requisição
     */
    <T> T doPostJson(String url, String username, String password, Object body, Header[] headers, ConsumidorHttp<T> consumidorHttp);

    /**
     * Realiza uma requisição do tipo POST usando credenciais
     *
     * @param url Caminho do serviço
     * @param username Nome do usuário
     * @param password Senha do usuário
     * @param body O objeto referente ao corpo da requisição
     * @param headers Cabeçalhos adicionais
     * @param consumidorHttp A logica do consumo da resposta recebida
     * @param <T> O tipo do objeto de resposta
     * @return resposta da requisição
     */
    <T> T doPostXml(String url, String username, String password, Object body, Header[] headers, ConsumidorHttp<T> consumidorHttp);

    /**
     * Realiza uma requisição do tipo POST com header FORM ENCODED
     *
     * @param url Caminho do serviço
     * @param form O formulario referente a requisição
     * @param consumidorHttp A logica de consumo da resposta recebida
     * @param <T> O tipo do objeto de resposta
     * @return resposta da requisição
     */
    <T> T doPostFormEncoded(String url, Map<String, String> form, ConsumidorHttp<T> consumidorHttp);

    /**
     * Realiza uma requisição do tipo PUT
     *
     * @param url Caminho do serviço
     * @param body O objeto referente ao corpo da requisição
     * @param headers Cabeçalhos adicionais
     * @param consumidorHttp A logica de consumo da resposta recebida
     * @param <T> O tipo do objeto de resposta
     * @return resposta da requisição
     */
    <T> T doPutJson(String url, Object body, Header[] headers, ConsumidorHttp<T> consumidorHttp);

    /**
     * Realiza uma requisição do tipo PUT usando credenciais
     *
     * @param url Caminho do serviço
     * @param username Nome do usuário
     * @param password Senha do usuário
     * @param body O objeto referente ao corpo da requisição
     * @param headers Cabeçalhos adicionais
     * @param consumidorHttp A logica de consumo da resposta recebida
     * @param <T> O tipo do objeto de resposta
     * @return resposta da requisição
     */
    <T> T doPutJson(String url, String username, String password, Object body, Header[] headers, ConsumidorHttp<T> consumidorHttp);

    /**
     * Realiza uma requisição do tipo DELETE
     *
     * @param url Caminho do serviço
     * @param headers Cabeçalhos adicionais
     * @param consumidorHttp A logica de consumo da resposta recebida
     * @param <T> O tipo do objeto de resposta
     * @return resposta da requisição
     */
    <T> T doDelete(String url, Header[] headers, ConsumidorHttp<T> consumidorHttp);

    /**
     * Realiza uma requisição do tipo DELETE usando credenciais
     *
     * @param url Caminho do serviço
     * @param username Nome do usuário
     * @param password Senha do usuário
     * @param headers Cabeçalhos adicionais
     * @param consumidorHttp A logica de consumo da resposta recebida
     * @param <T> O tipo do objeto de resposta
     * @return resposta da requisição
     */
    <T> T doDelete(String url, String username, String password, Header[] headers, ConsumidorHttp<T> consumidorHttp);

    /**
     * Realiza uma requisição do tipo PATCH
     *
     * @param url Caminho do serviço
     * @param body O objeto referente ao corpo da requisição
     * @param headers Cabeçalhos adicionais
     * @param consumidorHttp A logica de consumo da resposta recebida
     * @param <T> O tipo do objeto de resposta
     * @return resposta da requisição
     */
    <T> T doPatchJson(String url, Object body, Header[] headers, ConsumidorHttp<T> consumidorHttp);


}
