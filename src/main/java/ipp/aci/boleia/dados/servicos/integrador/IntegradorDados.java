package ipp.aci.boleia.dados.servicos.integrador;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.dados.IIntegradorDados;
import ipp.aci.boleia.dominio.vo.AutenticacaoIntegradorVo;
import ipp.aci.boleia.dominio.vo.CredenciaisIntegradorVo;
import ipp.aci.boleia.dominio.vo.PontoVendaIntegradorVo;
import ipp.aci.boleia.dominio.vo.RespostaAbastecimentoIntegradorVo;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.seguranca.UtilitarioJwt;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 * Respositorio de entidades do Integrador
 */
@Repository
public class IntegradorDados implements IIntegradorDados {

    @Autowired
    private IClienteHttpDados clientRest;

    @Autowired
    private UtilitarioJwt utilitarioJwt;

    @Value("${integrador.api.autenticar.endereco.url}")
    private String integradorAutenticarUrl;

    @Value("${integrador.api.pv.endereco.url}")
    private String getIntegradorObterDadosPvUrl;

    @Value("${integrador.api.abastecimentos.endereco.url}")
    private String integradorObterAbastecimentosUrl;

    @Value("${integrador.api.chave}")
    private String chave;

    @Value("${integrador.api.token}")
    private String token;

    /**
     * Logger para possíveis erros na requisição.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(IntegradorDados.class);

    /**
     * Header a ser enviado nas requisições que precisarem do refresh token para o integrador.
     */
    private static final String REFRESH_TOKEN = "X-Refresh-Token";

    /**
     * Header a ser enviado nas requisições para o integrador.
     */
    private static final String TOKEN_JWT = "Authorization";

    /**
     * Prefixo que deve ser passado junto do token.
     */
    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * Header obtido nas respostas do integrador.
     */
    private static final String NOVO_JWT = "X-JWT";

    @Override
    public CredenciaisIntegradorVo obterCredenciaisAcesso(){
        AutenticacaoIntegradorVo body = new AutenticacaoIntegradorVo(chave, token);
        return clientRest.doPostJson(integradorAutenticarUrl, body, null, this::tratarRespostaAutenticacao);
    }

    /**
     * Trata a resposta enviada pela API de autenticação do Integrador
     * @param resp response da requisição
     * @return resposta do serviço
     */
    public CredenciaisIntegradorVo tratarRespostaAutenticacao(CloseableHttpResponse resp){
        String prefixoErroLog = "Integrador Autenticação - Error:";
        CredenciaisIntegradorVo response = new CredenciaisIntegradorVo();
        try{
            response = new ObjectMapper().readValue(EntityUtils.toString(resp.getEntity()), CredenciaisIntegradorVo.class);
        } catch (IOException e){
            LOGGER.error(prefixoErroLog, e);
        }
        return response;
    }

   @Override
    public PontoVendaIntegradorVo obterDadosPontoVenda(String tokenJWT, String refreshToken, Long codigoCorporativoPv) {
        return clientRest.doGet(getIntegradorObterDadosPvUrl.replace("{codigoPontoVendaCorporativo}", codigoCorporativoPv.toString()), montarHeader(tokenJWT, refreshToken), this::tratarRespostaPontoVenda);
    }

    /**
     * Trata a resposta enviada pelo integrador
     * @param resp a resposta
     * @return os dados do ponto de venda
     */
    public PontoVendaIntegradorVo tratarRespostaPontoVenda(CloseableHttpResponse resp) {
        String prefixoErroLog = "Integrador Obter Dados Ponto de Venda - Error:";
        PontoVendaIntegradorVo response = new PontoVendaIntegradorVo();
        try {
            response = UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(resp, PontoVendaIntegradorVo.class, false);
            response.setNovoTokenJWT(resp.getFirstHeader(NOVO_JWT) != null ? resp.getFirstHeader(NOVO_JWT).getValue() : null);
            response.setStatusRequisicao(resp.getStatusLine().getStatusCode());
        } catch (ExcecaoBoleiaRuntime e) {
            LOGGER.error(prefixoErroLog, e);
            throw new ExcecaoBoleiaRuntime(e);
        }
        return response;
    }

    @Override
    public RespostaAbastecimentoIntegradorVo obterAbastecimentos(String tokenJWT, String refreshToken, Long codigoPontoVendaCorporativo, String codigoBico, Integer offset){
        return clientRest.doGet(integradorObterAbastecimentosUrl.replace("{codigoPontoVendaCorporativo}", codigoPontoVendaCorporativo.toString()) + "&codigoBico=" + codigoBico + "&ord[0][campo]=dataAbastecimento&ord[0][ordem]=DESC" + (offset != null ? "&offset=" + offset : null), montarHeader(tokenJWT, refreshToken), this::tratarRespostaAbastecimentos);
    }

    /**
     * Trata a resposta enviada pelo integrador
     * @param resp a resposta
     * @return os dados do abastecimento
     */
    public RespostaAbastecimentoIntegradorVo tratarRespostaAbastecimentos(CloseableHttpResponse resp){
        String prefixoErroLog = "Integrador Obter Abastecimentos - Error:";
        RespostaAbastecimentoIntegradorVo response = new RespostaAbastecimentoIntegradorVo();
        try {
            response = UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(resp, RespostaAbastecimentoIntegradorVo.class, false);
            response.setNovoTokenJwt(resp.getFirstHeader(NOVO_JWT) != null ? resp.getFirstHeader(NOVO_JWT).getValue() : null);
            response.setStatusRequisicao(resp.getStatusLine().getStatusCode());
        } catch (Exception e){
            LOGGER.error(prefixoErroLog, e);
            throw new ExcecaoBoleiaRuntime(e);
        }
        return response;
    }

    /**
     * Cria o header a ser enviado nas requisições para o integrador.
     * Caso o tokenJWT esteja perto de expirar ou já está expirado, também é enviado o refresh token.
     *
     * @param tokenJWT o token JWT
     * @param refreshToken o refresh token
     * @return header montado
     */
    private Header[] montarHeader(String tokenJWT, String refreshToken){
        DecodedJWT decodedJWT = JWT.decode(tokenJWT);
        if(utilitarioJwt.isTokenExpirado(decodedJWT) || utilitarioJwt.expiracaoProxima(decodedJWT)){
            return new Header[]{
                    new BasicHeader(TOKEN_JWT, BEARER_PREFIX + tokenJWT),
                    new BasicHeader(REFRESH_TOKEN, refreshToken)};
        } else {
            return new Header[]{new BasicHeader(TOKEN_JWT, BEARER_PREFIX + tokenJWT)};
        }
    }
}
