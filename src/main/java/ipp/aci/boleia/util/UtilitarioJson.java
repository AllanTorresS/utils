package ipp.aci.boleia.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Ferramentas para manipulacao de objetos JSON
 */
public class UtilitarioJson {
    private static final String PREFIX_JSON = "{";
    private static final String SUFIX_JSON = "}";
    /**
     * Transforma um objeto java em uma string no formato json
     * @param o objeto
     * @return Parseado
     */
    public static String toJSON(Object o) {
        try {
            return new ObjectMapper().writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    /**
     * Le uma requisição http convertendo-a em um objeto da classe informada
     * @param response a requisição http
     * @param classe a classe alvo
     * @param <T> O tipo alvo da conversao
     * @return Um objeto da classe informada
     */
    public static <T> T toObject(HttpResponse response, Class<T> classe) {
        try {
            String json = EntityUtils.toString(response.getEntity());
            return toObject(json, classe);
        } catch (IOException e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    /**
     * Le uma string Json convertendo-a em um objeto da classe informada
     * @param json a string no formato json
     * @param classe a classe alvo
     * @param <T> O tipo alvo da conversao
     * @return Um objeto da classe informada
     */
    public static <T> T toObject(String json, Class<T> classe) {
        return toObjectWithConfigureFailOnUnknowProperties(json, classe,true);
    }


    /**
     * Le uma string Json convertendo-a em um objeto da classe informada
     * e configurando se a mesma deve falhar quando encontrar propriedades desconhecidas
     * @param json a string no formato json
     * @param classe a classe alvo
     * @param failOnUnknownProperties se a mesma deve falhar quando encontrar propriedades desconhecidas
     * @param <T> O tipo alvo da conversao
     * @return Um objeto da classe informada
     */
    public static <T> T toObjectWithConfigureFailOnUnknowProperties(String json,
                                                                    Class<T> classe,
                                                                    Boolean failOnUnknownProperties) {
        try {
            return new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknownProperties)
                    .readValue(json, classe);
        } catch (IOException e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    /**
     * Le uma requisição http convertendo-a em um objeto da classe informada
     * e configurando se a mesma deve falhar quando encontrar propriedades desconhecidas
     * @param response a requisição http
     * @param classe a classe alvo
     * @param failOnUnknownProperties se a mesma deve falhar quando encontrar propriedades desconhecidas
     * @param <T> O tipo alvo da conversao
     * @return Um objeto da classe informada
     */
    public static <T> T toObjectWithConfigureFailOnUnknowProperties(HttpResponse response,
                                                                    Class<T> classe,
                                                                    Boolean failOnUnknownProperties) {
        try {
            String json = EntityUtils.toString(response.getEntity());
            return toObjectWithConfigureFailOnUnknowProperties(json, classe,failOnUnknownProperties);
        } catch (IOException e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    /**
     * Converte um objeto em uma String em formato JSON
     *
     * @param o Objeto a ser convertido
     * @return String no formato JSON do objeto a ser convertido
     */
    public static String toJSONString(Object o) {
        return PREFIX_JSON + toJSON(o) + SUFIX_JSON;
    }
}
