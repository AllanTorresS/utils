package ipp.aci.boleia.dados.servicos.salesforce;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import ipp.aci.boleia.dados.IChaveValorDados;
import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.dados.servicos.rest.ConsumidorHttp;
import ipp.aci.boleia.dominio.vo.salesforce.TokenAcessoVo;
import ipp.aci.boleia.util.ConstantesSalesForce;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;

/**
 * Classe base para as classes de acesso ao sales force
 */
public class AcessoSalesForceBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcessoSalesForceBase.class);

    private static final String TOKEN_ACESSO_SALESFORCE = "TOKEN_ACESSO_SALESFORCE";
    protected static final String CNPJ_URL = "{cnpj}";

    @Value("${salesforce.authorization.client.id}")
    protected String clientId;

    @Value("${salesforce.authorization.client.secret}")
    protected String clientSecret;

    @Value("${salesforce.authorization.username}")
    protected String username;

    @Value("${salesforce.authorization.password}")
    protected String password;

    @Value("${salesforce.authorization.environment.uri}")
    protected String environmentUri;

    @Autowired
    protected IClienteHttpDados restDados;

    @Autowired
    protected Mensagens mensagens;

    @Autowired
    protected UtilitarioAmbiente ambiente;

    @Autowired
    private IChaveValorDados<String> chaveValorDados;

    protected Header[] authorizationHeaders;

    protected String instanceUrl;

    protected String endpointUrl;

    protected Integer statusCode;

    protected String mensagem;

    protected String requestBody;

    protected JsonNode responseBody;

    protected InputStream inputStream;

    /**
     * Prepara os dados de requisi????o para chamada dos servi??os de integra????o com o Salesforce.
     *
     * @param servicoUrl Url do servi??o a ser chamada.
     * @param corpo Conte??do enviado na chamada.
     */
    protected void prepararRequisicao(String servicoUrl, Object corpo) {
        TokenAcessoVo tokenAcesso = obterTokenAcesso(this.getUsername());
        if(tokenAcesso != null) {
            atribuirDadosAutenticacao(tokenAcesso.getToken(), tokenAcesso.getUrlInstancia());
        } else {
            autenticarSalesforce();
        }
        this.endpointUrl = this.instanceUrl.concat(servicoUrl);
        this.requestBody = UtilitarioJson.toJSON(corpo != null ? corpo : "");
    }

    /**
     * Envia uma requisi????o POST da integra????o.
     * @param <T> Tipo de retorno da chamada.
     * @param consumidorRequisicao Objeto consumidor da requisi????o.
     * @return Retorno da chamada.
     */
    protected <T> T enviarRequisicaoPost(ConsumidorHttp<T> consumidorRequisicao) {
        return restDados.doPostJson(this.endpointUrl, this.requestBody, this.authorizationHeaders, response -> {
            if(response.getStatusLine().getStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
                autenticarSalesforce();
                return restDados.doPostJson(this.endpointUrl, this.requestBody, this.authorizationHeaders, consumidorRequisicao);
            }
            return consumidorRequisicao.consumir(response);
        });
    }

    /**
     * Envia uma requisi????o GET da integra????o.
     * @param <T> Tipo de retorno da chamada.
     * @param consumidorRequisicao Objeto consumidor da requisi????o.
     * @return Retorno da chamada.
     */
    protected <T> T enviarRequisicaoGet(ConsumidorHttp<T> consumidorRequisicao) {
        return restDados.doGet(this.endpointUrl, this.authorizationHeaders, response -> {
            if(response.getStatusLine().getStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
                autenticarSalesforce();
                return restDados.doGet(this.endpointUrl, this.authorizationHeaders, consumidorRequisicao);
            }
            return consumidorRequisicao.consumir(response);
        });
    }

    /**
     * Envia uma requisi????o GET da integra????o.
     * @param <T> Tipo de retorno da chamada.
     * @param urlRequisicao A URL utilizada na requisi????o.
     * @param consumidorRequisicao Objeto consumidor da requisi????o.
     * @return Retorno da chamada.
     */
    protected <T> T enviarRequisicaoGet(String urlRequisicao, ConsumidorHttp<T> consumidorRequisicao) {
        return restDados.doGet(urlRequisicao, this.authorizationHeaders, response -> {
            if(response.getStatusLine().getStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
                autenticarSalesforce();
                return restDados.doGet(urlRequisicao, this.authorizationHeaders, consumidorRequisicao);
            }
            return consumidorRequisicao.consumir(response);
        });
    }

    /**
     * Envia uma requisi????o PATCH da integra????o.
     * @param <T> Tipo de retorno da chamada.
     * @param consumidorRequisicao Objeto consumidor da requisi????o.
     * @return Retorno da chamada.
     */
    protected <T> T enviarRequisicaoPatch(ConsumidorHttp<T> consumidorRequisicao) {
        return restDados.doPatchJson(this.endpointUrl, this.requestBody, this.authorizationHeaders, response -> {
            if(response.getStatusLine().getStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
                autenticarSalesforce();
                return restDados.doPatchJson(this.endpointUrl, this.requestBody, this.authorizationHeaders, consumidorRequisicao);
            }
            return consumidorRequisicao.consumir(response);
        });
    }

    /**
     * Envia uma requisi????o DELETE da integra????o.
     * @param <T> Tipo de retorno da chamada.
     * @param consumidorRequisicao Objeto consumidor da requisi????o.
     * @return Retorno da chamada.
     */
    protected <T> T enviarRequisicaoDelete(ConsumidorHttp<T> consumidorRequisicao) {
        return restDados.doDelete(this.endpointUrl, this.authorizationHeaders, response -> {
            if(response.getStatusLine().getStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
                autenticarSalesforce();
                return restDados.doDelete(this.endpointUrl, this.authorizationHeaders, consumidorRequisicao);
            }
            return consumidorRequisicao.consumir(response);
        });
    }

	/**
     * Prepara os dados de resposta da integra????o com o Salesforce.
     *
     * @param httpResponse A resposta recebida do Salesforce.
     */
    protected void prepararResposta(CloseableHttpResponse httpResponse) {
        this.statusCode = httpResponse.getStatusLine().getStatusCode();
        this.responseBody = new ObjectMapper().createObjectNode();
        if (httpResponse.getEntity() != null) {
            this.responseBody = UtilitarioJson.toObject(httpResponse, JsonNode.class);
        }
        this.mensagem = this.statusCode + " " + httpResponse.getStatusLine().getReasonPhrase();
    }

    /**
     * Prepara os dados de resposta da integra????o com o Salesforce.
     *
     * @param httpResponse A resposta recebida do Salesforce.
     */
    protected void prepararRespostaArquivo(CloseableHttpResponse httpResponse) throws IOException {
        this.statusCode = httpResponse.getStatusLine().getStatusCode();
        if (httpResponse.getEntity() != null) {
            this.inputStream = httpResponse.getEntity().getContent();
        }
        this.mensagem = this.statusCode + " " + httpResponse.getStatusLine().getReasonPhrase();
    }


    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEnvironmentUri() {
        return environmentUri;
    }

    /**
     * Constroi o cabe??alho com os dados para autenticacao,
     * das futuras requsi????es de API??s de integra????o com o Salesforce.
     */
    private void autenticarSalesforce() {
        Map<String, String> form = new LinkedHashMap<>();
        form.put(ConstantesSalesForce.AUTHORIZATION_TIPO_CONCESSAO, ConstantesSalesForce.AUTHORIZATION_SENHA);
        form.put(ConstantesSalesForce.AUTHORIZATION_CLIENTE_ID, this.getClientId());
        form.put(ConstantesSalesForce.AUTHORIZATION_CLIENTE_SEGREDO, this.getClientSecret());
        form.put(ConstantesSalesForce.AUTHORIZATION_USUARIO, this.getUsername());
        form.put(ConstantesSalesForce.AUTHORIZATION_SENHA, this.getPassword());

        boolean autenticado = restDados.doPostFormEncoded(this.getEnvironmentUri(), form, this::tratarAutenticacaoSalesforce);
        if (!autenticado) {
            LOGGER.error(this.mensagem);
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, this.mensagem);
        }
    }

    /**
     * Tratamento da resposta da autentica????o para montar o cabe??alho
     * das futuras requisi????es ao Salesforce.
     *
     * @param httpResponse A resposta recebida do Salesforce.
     * @return true em caso sucesso na requisi????o, caso contrario false.
     */
    private Boolean tratarAutenticacaoSalesforce(CloseableHttpResponse httpResponse) {
        prepararResposta(httpResponse);
        if (this.statusCode == HttpStatus.OK.value()) {
            String token = this.responseBody.get(ConstantesSalesForce.CAMPO_TIPO_TOKEN).textValue() + " " + this.responseBody.get(ConstantesSalesForce.CAMPO_TOKEN_ACESSO).textValue();
            String urlInstancia = this.responseBody.get(ConstantesSalesForce.CAMPO_INSTANCIA_URL).textValue();
            salvarTokenAcesso(this.getUsername(), token, urlInstancia);
            atribuirDadosAutenticacao(token, urlInstancia);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Salva no redis as informa????es do token de acesso ao salesforce.
     * 
     * @param usernameAutenticacao Usernamen utilizado na autentica????o.
     * @param token Token de acesso gerado na autentica????o.
     * @param urlInstancia Url de inst??ncia que dever?? ser usada para acessar a API.
     */
    private void salvarTokenAcesso(String usernameAutenticacao, String token, String urlInstancia) {
        TokenAcessoVo vo = new TokenAcessoVo();
        vo.setToken(token);
        vo.setUrlInstancia(urlInstancia);

        chaveValorDados.inserir(TOKEN_ACESSO_SALESFORCE, usernameAutenticacao, UtilitarioJson.toJSON(vo));
	}

    /**
     * Obt??m no redis os dados de acesso para a API do salesforce.
     * 
     * @param usernameAutenticacao Username da autentica????o.
     * @return Objeto com os dados de acesso.
     */
    private TokenAcessoVo obterTokenAcesso(String usernameAutenticacao) {
        String json = chaveValorDados.obter(TOKEN_ACESSO_SALESFORCE, usernameAutenticacao);
        if(json != null) {
            return UtilitarioJson.toObjectWithConfigureFailOnUnknowProperties(json, TokenAcessoVo.class, false);
        }
        return null;
    }

    /**
     * Atribui no objeto o token de autentica????o e a url da inst??ncia.
     * 
     * @param token Token recebido na autentica????o.
     * @param urlInstancia Url de inst??ncia para a integra????o.
     */
    private void atribuirDadosAutenticacao(String token, String urlInstancia) {
        this.authorizationHeaders = new Header[] {
            new BasicHeader(ConstantesSalesForce.AUTHORIZATION_HEADER, token)
        };
        this.instanceUrl = urlInstancia;
    }
}
