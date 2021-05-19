package ipp.aci.boleia.dados.servicos.salesforce;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Classe base para as classes de acesso ao sales force
 */
public class AcessoSalesForceBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcessoSalesForceBase.class);

    protected static final String AUTHORIZATION_HEADER = "Authorization";
    protected static final String AUTHORIZATION_TIPO_CONCESSAO = "grant_type";
    protected static final String AUTHORIZATION_CLIENTE_ID = "client_id";
    protected static final String AUTHORIZATION_CLIENTE_SEGREDO = "client_secret";
    protected static final String AUTHORIZATION_USUARIO = "username";
    protected static final String AUTHORIZATION_SENHA = "password";


    protected static final String CAMPO_TIPO_TOKEN = "token_type";
    protected static final String CAMPO_TOKEN_ACESSO = "access_token";
    protected static final String CAMPO_INSTANCIA_URL = "instance_url";
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

    protected Header[] authorizationHeaders;

    protected String instanceUrl;

    protected String endpointUrl;

    protected Integer statusCode;

    protected String mensagem;

    protected String requestBody;

    protected JsonNode responseBody;

    /**
     * Prepara os dados de requisição para chamada dos serviços de integração com o Salesforce.
     *
     * @param servicoUrl Url do serviço a ser chamada.
     * @param corpo Conteúdo enviado na chamada.
     */
    protected void prepararRequisicao(String servicoUrl, Object corpo) {
        autenticarSalesforce();
        this.endpointUrl = this.instanceUrl.concat(servicoUrl);
        this.requestBody = UtilitarioJson.toJSON(corpo != null ? corpo : "");
    }

    /**
     * Constroi o cabeçalho com os dados para autenticacao,
     * das futuras requsições de API´s de integração com o Salesforce.
     */
    protected void autenticarSalesforce() {
        Map<String, String> form = new LinkedHashMap<>();
        form.put(AUTHORIZATION_TIPO_CONCESSAO, AUTHORIZATION_SENHA);
        form.put(AUTHORIZATION_CLIENTE_ID, this.getClientId());
        form.put(AUTHORIZATION_CLIENTE_SEGREDO, this.getClientSecret());
        form.put(AUTHORIZATION_USUARIO, this.getUsername());
        form.put(AUTHORIZATION_SENHA, this.getPassword());

        boolean autenticado = restDados.doPostFormEncoded(this.getEnvironmentUri(), form, this::tratarAutenticacaoSalesforce);
        if (!autenticado) {
            LOGGER.error(this.mensagem);
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, this.mensagem);
        }
    }

    /**
     * Tratamento da resposta da autenticação para montar o cabeçalho
     * das futuras requisições ao Salesforce.
     *
     * @param httpResponse A resposta recebida do Salesforce.
     * @return true em caso sucesso na requisição, caso contrario false.
     */
    protected Boolean tratarAutenticacaoSalesforce(CloseableHttpResponse httpResponse) {
        prepararResposta(httpResponse);
        if (this.statusCode == HttpStatus.OK.value()) {
            this.authorizationHeaders = new Header[]{new BasicHeader(
                    AUTHORIZATION_HEADER, this.responseBody.get(CAMPO_TIPO_TOKEN).textValue() + " " + this.responseBody.get(CAMPO_TOKEN_ACESSO).textValue())};
            this.instanceUrl = this.responseBody.get(CAMPO_INSTANCIA_URL).textValue();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Prepara os dados de resposta da integração com o Salesforce.
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
}
