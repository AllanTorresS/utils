package ipp.aci.boleia.dados.servicos.posabasteceai;

import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.dados.IConciliacaoPosAbasteceAiDados;
import ipp.aci.boleia.dominio.vo.RequisicaoServicoConciliacaoPosAbasteceAiVo;
import ipp.aci.boleia.dominio.vo.RespostaPosAbasteceAiVo;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.List;

/**
 * Classe que implementa IConciliacaoPosAbasteceAiDados para acesso ao servico do POS/Abastece Aí
 */
@Repository
public class ConciliacaoPosAbasteceAiDados implements IConciliacaoPosAbasteceAiDados {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BASIC_AUTHORIZATION_PREFIX = "Basic ";

    @Autowired
    private IClienteHttpDados clienteRest;

    @Value("${posabasteceai.service.url}")
    private String posAbasteceAiServicoUrl;

    @Value("${posabasteceai.service.login}")
    private String posAbasteceAiServiceLogin;

    @Value("${posabasteceai.service.senha}")
    private String posAbasteceAiServiceSenha;

    private Header[] authorizationHeaders;

    /**
     * Método chamado para a construção da classe durante a injeção de dependencia.
     */
    @PostConstruct
    public void init() {
        authorizationHeaders = new Header[]{new BasicHeader(AUTHORIZATION_HEADER, BASIC_AUTHORIZATION_PREFIX + Base64.getEncoder().encodeToString((posAbasteceAiServiceLogin + ":" + posAbasteceAiServiceSenha).getBytes()))};
    }

    @Override
    public RespostaPosAbasteceAiVo obterStatusTransacoes(List<RequisicaoServicoConciliacaoPosAbasteceAiVo> requisicaoPOSAbasteceAi) {
        try {
            return clienteRest.doPostJson(posAbasteceAiServicoUrl, requisicaoPOSAbasteceAi, authorizationHeaders , this::tratarRespostaPOSAbasteceAi);
        }
        catch (Exception e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    /**
     * Trata a resposta enviada pelo serviço do POS/Abastece Aí
     *
     * @param resp a resposta do requisição
     * @return resposta tratada
     */
    private RespostaPosAbasteceAiVo tratarRespostaPOSAbasteceAi(CloseableHttpResponse resp) {
        try {
            RespostaPosAbasteceAiVo respostaPOSAbasteceAi = UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(resp, RespostaPosAbasteceAiVo.class, false);
            return respostaPOSAbasteceAi;
        } catch (Exception e){
            throw new ExcecaoBoleiaRuntime(e);
        }
    }
}
