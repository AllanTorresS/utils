package ipp.aci.boleia.dados.servicos.ctaplus;

import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.dados.ICtaPlusDados;
import ipp.aci.boleia.dominio.vo.CtaPlusInformaSincronismoVo;
import ipp.aci.boleia.dominio.vo.CtaPlusRequestInformaSincronismoVo;
import ipp.aci.boleia.dominio.vo.CtaPlusSincronizaAbastecimentoVo;
import ipp.aci.boleia.util.ConstantesCta;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Respositorio de conexão com os servicos externos do Connect CTA
 */

@Repository
public class CtaPlusDados implements ICtaPlusDados {

    @Autowired
    private IClienteHttpDados clienteRest;

    @Value("${ctaplus.sincronizaAbastecimentos.url}")
    private String ctaPlusSincronizaAbastecimentoUrl;

    @Value("${ctaplus.informaSincronismo.url}")
    private String ctaPlusInformaSincronismoUrl;

    /**
     * Logger para possíveis erros na requisição
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CtaPlusSincronizaAbastecimentoVo.class);

    @Override
    public CtaPlusSincronizaAbastecimentoVo buscarAbastecimentos(String tokenFrota) {
        String url = String.format(ctaPlusSincronizaAbastecimentoUrl, tokenFrota, ConstantesCta.QUANTIDADE_MAX_ABASTECIMENTOS, ConstantesCta.FORMATO_RESPOSTA);
        return clienteRest.doGet(url, null, this::tratarResposta);
    }

    @Override
    public CtaPlusSincronizaAbastecimentoVo informarSincronismo(String tokenFrota, CtaPlusInformaSincronismoVo ctaPlusInformaSincronismoVo) {
        List<CtaPlusInformaSincronismoVo> ctaPlusInformaSincronismoVoList = new ArrayList<>();
        ctaPlusInformaSincronismoVoList.add(ctaPlusInformaSincronismoVo);
        CtaPlusRequestInformaSincronismoVo ctaPlusRequestInformaSincronismoVo = new CtaPlusRequestInformaSincronismoVo(ctaPlusInformaSincronismoVoList);
        try{
            String json = URLEncoder.encode(UtilitarioJson.toJSON(ctaPlusRequestInformaSincronismoVo), StandardCharsets.UTF_8.displayName());
            String url = String.format(ctaPlusInformaSincronismoUrl, tokenFrota, ConstantesCta.FORMATO_RESPOSTA, json);
            return clienteRest.doPostJson(url, null, null, this::tratarRespostaInformaSincronismo);
        }catch (UnsupportedEncodingException e){
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    /**
     * Trata a resposta enviada pela api do CTA Plus
     * @param resp response da requisição
     * @return resposta do serviço de sincronizar abastecimentos
     */
    private CtaPlusSincronizaAbastecimentoVo tratarResposta(CloseableHttpResponse resp){
        CtaPlusSincronizaAbastecimentoVo response = new CtaPlusSincronizaAbastecimentoVo();
        try {
            response = UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(resp, CtaPlusSincronizaAbastecimentoVo.class, false);
        } catch (Exception e){
            LOGGER.error(ConstantesCta.PREFIXO_ERRO_LOG, e);
        }
        return response;
    }

    /**
     * Trata a resposta enviada pela api do CTA Plus para o endpoint Informa Sincronismo
     * @param resp response da requisição
     * @return resposta do serviço de sincronizar abastecimentos
     */
    private CtaPlusSincronizaAbastecimentoVo tratarRespostaInformaSincronismo(CloseableHttpResponse resp) {
        try {
            return UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(resp, CtaPlusSincronizaAbastecimentoVo.class, false);
        } catch (Exception e){
            LOGGER.error(ConstantesCta.PREFIXO_ERRO_LOG, e);
            throw new ExcecaoBoleiaRuntime(e);
        }
    }
}