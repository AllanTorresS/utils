package ipp.aci.boleia.dados.servicos.roteirizador;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;


/**
 * Respositorio de entidades do roteirizador
 */
@Repository
public class RoteirizadorDados implements IRoteirizadorDados {

    @Autowired
    private IClienteHttpDados clientRest;


    @Value("${roteirizador.api.gateway.endereco.url}")
    private String roteirizadorUrl;

    @Value("${roteirizador.validador.api.gateway.endereco.url}")
    private String validadorUrl;

    @Override
    public RespostaRoteirizadorVo calcularRota(RequisicaoRoteirizadorVo requisicao) {
        return clientRest.doPostJson(roteirizadorUrl, requisicao, null, this::tratarResposta);
    }

    @Override
    public RespostaRoteirizadorValidarVo validarRota(RequisicaoRoteirizadorValidarVo requisicao) {
        return clientRest.doPostJson(validadorUrl, requisicao, null, this::tratarRespostaValidador);
    }


    /**
     * Trata resposta recebida através de requisição HTTP
     * @param resp {@link CloseableHttpResponse} Objeto que representa a resposta
     * @return {@link RespostaRoteirizadorVo} Resposta convertida
     */
    private RespostaRoteirizadorVo tratarResposta(CloseableHttpResponse resp) {
        RespostaRoteirizadorVo response;
        try {
            response = UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(resp, RespostaRoteirizadorVo.class, false);
            response.setStatusRequisicao(resp.getStatusLine().getStatusCode());
        } catch (ExcecaoBoleiaRuntime e) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, e);
        }
        return response;
    }

    /**
     * Trata resposta recebida através de requisição HTTP
     * @param resp {@link CloseableHttpResponse} Objeto que representa a resposta
     * @return {@link RespostaRoteirizadorVo} Resposta convertida
     */
    private RespostaRoteirizadorValidarVo tratarRespostaValidador(CloseableHttpResponse resp) {
        RespostaRoteirizadorValidarVo response;
        try {
            response = UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(resp, RespostaRoteirizadorValidarVo.class, false);
            response.setStatusRequisicao(resp.getStatusLine().getStatusCode());
        } catch (ExcecaoBoleiaRuntime e) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, e);
        }
        return response;
    }

}
