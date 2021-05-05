package ipp.aci.boleia.dados.servicos.roteirizador;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.dados.IRoteirizadorDados;
import ipp.aci.boleia.dominio.vo.RequisicaoRoteirizadorValidarVo;
import ipp.aci.boleia.dominio.vo.RequisicaoRoteirizadorVo;
import ipp.aci.boleia.dominio.vo.RespostaRoteirizadorValidarVo;
import ipp.aci.boleia.dominio.vo.RespostaRoteirizadorVo;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 * Respositorio de entidades do roteirizador
 */
@Repository
public class RoteirizadorDados implements IRoteirizadorDados {

    @Autowired
    private IClienteHttpDados clientRest;

    /**
     * Logger para possíveis erros na requisição.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RoteirizadorDados.class);

    @Value("${roteirizador.api.gateway.endereco.url}")
    private String roteirizadorUrl;

    @Value("${roteirizador.validador.api.gateway.endereco.url}")
    private String validadorUrl;

    @Override
    public RespostaRoteirizadorVo calcularRota(RequisicaoRoteirizadorVo requisicao) {
        try {
            LOGGER.info(String.format("URL %s", roteirizadorUrl));
            String stringRequest = new ObjectMapper().writeValueAsString(requisicao);
            LOGGER.info(String.format("Requisição enviada ao gateway %s", stringRequest));
            return clientRest.doPostJson(roteirizadorUrl, requisicao, null, this::tratarResposta);
        } catch (JsonProcessingException e) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, e);
        }
    }

    @Override
    public RespostaRoteirizadorValidarVo validarRota(RequisicaoRoteirizadorValidarVo requisicao){
        try {
            LOGGER.info(String.format("URL %s", validadorUrl));
            String stringRequest = new ObjectMapper().writeValueAsString(requisicao);
            LOGGER.info(String.format("Requisição enviada ao gateway %s", stringRequest));
            return clientRest.doPostJson(validadorUrl, requisicao, null, this::tratarRespostaValidador);
        } catch (JsonProcessingException e) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, e);
        }
    }


    /**
     * Trata resposta recebida através de requisição HTTP
     * @param resp {@link CloseableHttpResponse} Objeto que representa a resposta
     * @return {@link RespostaRoteirizadorVo} Resposta convertida
     */
    public RespostaRoteirizadorVo tratarResposta(CloseableHttpResponse resp) {
        RespostaRoteirizadorVo response;
        try {
            response = UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(resp, RespostaRoteirizadorVo.class, false);
            response.setStatusRequisicao(resp.getStatusLine().getStatusCode());
            LOGGER.info(String.format("Resposta recebida do gateway : %s", new ObjectMapper().writeValueAsString(response)));
        } catch (IOException|ExcecaoBoleiaRuntime e) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, e);
        }
        return response;
    }

    /**
     * Trata resposta recebida através de requisição HTTP
     * @param resp {@link CloseableHttpResponse} Objeto que representa a resposta
     * @return {@link RespostaRoteirizadorVo} Resposta convertida
     */
    public RespostaRoteirizadorValidarVo tratarRespostaValidador(CloseableHttpResponse resp) {
        RespostaRoteirizadorValidarVo response;
        try {
            response = UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(resp, RespostaRoteirizadorValidarVo.class, false);
            response.setStatusRequisicao(resp.getStatusLine().getStatusCode());
            LOGGER.info(String.format("Resposta recebida do gateway : %s", new ObjectMapper().writeValueAsString(response)));
        } catch (IOException|ExcecaoBoleiaRuntime e) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, e);
        }
        return response;
    }

}
