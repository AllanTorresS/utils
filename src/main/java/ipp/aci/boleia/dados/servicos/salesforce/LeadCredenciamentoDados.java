package ipp.aci.boleia.dados.servicos.salesforce;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ipp.aci.boleia.dados.IClienteHttpDados;
import ipp.aci.boleia.dados.ILeadCredenciamentoDados;
import ipp.aci.boleia.dominio.vo.LeadCredenciamentoPostoIntegradorVo;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Respositório dos serviços de integração dos dados de lead do credenciamento com o sistema externo.
 */
@Repository
public class LeadCredenciamentoDados implements ILeadCredenciamentoDados {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LeadCredenciamentoDados.class);

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_TIPO_CONCESSAO = "grant_type";
    private static final String AUTHORIZATION_CLIENTE_ID = "client_id";
    private static final String AUTHORIZATION_CLIENTE_SEGREDO = "client_secret";
    private static final String AUTHORIZATION_USUARIO = "username";
    private static final String AUTHORIZATION_SENHA = "password";
    
    private static final String CAMPO_TIPO_TOKEN = "token_type";
    private static final String CAMPO_TOKEN_ACESSO = "access_token";
    private static final String CAMPO_INSTANCIA_URL = "instance_url";
    private static final String CAMPO_ID = "id";
    private static final String CAMPO_SUCESSO = "success";
    private static final String CAMPO_TAMANHO = "totalSize";
    private static final String CAMPO_REGISTROS = "records";
    private static final String CAMPO_CNPJ = "Cnpj__c";

    private static final String CNPJ_URL = "{cnpj}";
    private static final String ATRIBUTOS_URL = "{atributos}";
    private static final String ATRIBUTOS_CONSULTA_POSTO = "PaginaCredenciamento__c,IdBanco__c,Banco__c,Agencia__c,DigitoAgencia__c,Conta__c,DigitoConta__c,TipoPessoa__c,TitularConta__c,CnpjCpfTitularConta__c";

    @Value("${salesforce.authorization.client.id}")
    private String client_id;
    
    @Value("${salesforce.authorization.client.secret}")
    private String client_secret;
    
    @Value("${salesforce.authorization.username}")
    private String username;
    
    @Value("${salesforce.authorization.password}")
    private String password;
    
    @Value("${salesforce.authorization.environment.uri}")
    private String environment_uri;
    
    @Value("${salesforce.credenciamento.atualizar.url}")
    private String criarAlterarLeadUrl;
    
    @Value("${salesforce.credenciamento.obter.url}")
    private String consultarLeadUrl;
    
    @Value("${salesforce.credenciamento.anexar.url}")
    private String anexarLeadUrl;

    @Autowired
    private IClienteHttpDados restDados;
    
    @Autowired
	private Mensagens mensagens;
    
    private Header[] authorizationHeaders;
    
    private String instance_url;
    
    private String endpointUrl;
    
    private Integer statusCode;
    
    private String mensagem;
    
    private String requestBody;
    
    private JsonNode responseBody;
    
    @Override
    public String criarLead(String cnpj, Object corpo) throws ExcecaoValidacao {
        if (Boolean.TRUE.equals(validarLeadExistente(cnpj))) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("credenciamento.servico.criarLead.existente"));
        }
        prepararRequisicao(this.criarAlterarLeadUrl.replace(CNPJ_URL, cnpj), corpo);
        String leadId = restDados.doPatchJson(this.endpointUrl, corpo, this.authorizationHeaders, this::tratarCriarLead);
        if (leadId == null) {
            LOGGER.error(this.mensagem);
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, this.mensagem);
        }
        return leadId;
	}

	@Override
    public void atualizarLead(String cnpj, Object corpo) throws ExcecaoValidacao {
        if (Boolean.FALSE.equals(validarLeadExistente(cnpj))) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("credenciamento.servico.atualizarLead.inexistente"));
        }
        prepararRequisicao(this.criarAlterarLeadUrl.replace(CNPJ_URL, cnpj), corpo);
		boolean leadAtualizado = restDados.doPatchJson(this.endpointUrl, corpo, this.authorizationHeaders, this::tratarAtualizacaoLead);
		if (!leadAtualizado) {
			LOGGER.error(this.mensagem);
			throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, this.mensagem);
		}
	}
	
	@Override
	public void anexarLead(Object corpo) {
        prepararRequisicao(this.anexarLeadUrl, corpo);
		boolean anexado = restDados.doPostJson(this.endpointUrl, corpo, this.authorizationHeaders, this::tratarAnexarLead);
		if (!anexado) {
			LOGGER.error(this.mensagem);
			throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, this.mensagem);
		}
	}
	
	@Override
    public LeadCredenciamentoPostoIntegradorVo consultarPostoLead(String cnpj) throws ExcecaoValidacao {
        prepararRequisicao(this.consultarLeadUrl.replace(ATRIBUTOS_URL, ATRIBUTOS_CONSULTA_POSTO).replace(CNPJ_URL, cnpj), null);
        LeadCredenciamentoPostoIntegradorVo postoLeadVo = restDados.doGet(this.endpointUrl, this.authorizationHeaders, this::tratarConsultaPostoLead);
        if (postoLeadVo == null) {
            LOGGER.error(this.mensagem);
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, this.mensagem);
        }
        if (postoLeadVo.getPaginaCredenciamento() == null) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("credenciamento.servico.consultarLead.inexistente"));
        }
        return postoLeadVo;
    }

    @Override
    public Boolean validarLeadExistente(String cnpj) {
        prepararRequisicao(this.consultarLeadUrl.replace(ATRIBUTOS_URL, CAMPO_CNPJ).replace(CNPJ_URL, cnpj), null);
        return restDados.doGet(this.endpointUrl, this.authorizationHeaders, this::tratarValidarExistenciaLead);
    }

    /**
     * Prepara os dados de requisição para chamada dos serviços de integração com o Salesforce.
     *
     * @param servicoUrl Url do serviço a ser chamada.
     * @param corpo      Conteúdo enviado na chamada.
     */
    private void prepararRequisicao(String servicoUrl, Object corpo) {
		autenticarSalesforce();
        this.endpointUrl = this.instance_url.concat(servicoUrl);
        this.requestBody = UtilitarioJson.toJSON(corpo != null ? corpo : new String());
    }

    /**
     * Prepara os dados de resposta da integração com o Salesforce.
     *
     * @param httpResponse A resposta recebida do Salesforce.
     */
    private void prepararResposta(CloseableHttpResponse httpResponse) {
        this.statusCode = httpResponse.getStatusLine().getStatusCode();
        this.responseBody = new ObjectMapper().createObjectNode();
        if (httpResponse.getEntity() != null) {
            this.responseBody = UtilitarioJson.toObject(httpResponse, JsonNode.class);
        }
        String responseStatus = this.statusCode + " " + httpResponse.getStatusLine().getReasonPhrase();
        this.mensagem = mensagens.obterMensagem("credenciamento.resposta.integracao",
                this.endpointUrl, this.requestBody, responseStatus, responseBody.toString());
	}
	
	/**
	 * Constroi o cabeçalho com os dados para autenticacao,
	 * das futuras requsições de API´s de integração com o Salesforce.
	 */
	private void autenticarSalesforce() {
		Map<String, String> form = new LinkedHashMap<>();
		form.put(AUTHORIZATION_TIPO_CONCESSAO, AUTHORIZATION_SENHA);
		form.put(AUTHORIZATION_CLIENTE_ID, client_id);
		form.put(AUTHORIZATION_CLIENTE_SEGREDO, client_secret);
		form.put(AUTHORIZATION_USUARIO, username);
		form.put(AUTHORIZATION_SENHA, password);
		
		boolean autenticado = restDados.doPostFormEncoded(environment_uri, form, this::tratarAutenticacaoSalesforce);
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
	private Boolean tratarAutenticacaoSalesforce(CloseableHttpResponse httpResponse) {
		prepararResposta(httpResponse);
		if (this.statusCode == HttpStatus.OK.value()) {
			this.authorizationHeaders = new Header[]{new BasicHeader(
					AUTHORIZATION_HEADER, this.responseBody.get(CAMPO_TIPO_TOKEN).textValue() + " " + this.responseBody.get(CAMPO_TOKEN_ACESSO).textValue())};
			this.instance_url = this.responseBody.get(CAMPO_INSTANCIA_URL).textValue();
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Tratamento da resposta da solicitação de criação do Lead de credenciamento do Salesforce.
	 * 
	 * @param httpResponse A resposta recebida do Salesforce.
     * @return Identificador do Lead criado.
	 */
	private String tratarCriarLead(CloseableHttpResponse httpResponse) {
		prepararResposta(httpResponse);
        if (this.statusCode == HttpStatus.CREATED.value() && this.responseBody.get(CAMPO_ID) != null) {
			return this.responseBody.get(CAMPO_ID).textValue();
		} else {
			return null;
		}
	}
	
	/**
	 * Tratamento da resposta da solicitação de atualização do lead de credenciamento do Salesforce.
	 * 
	 * @param httpResponse A resposta recebida do Salesforce.
	 * @return true em caso sucesso na requisição, caso contrario false.
	 */
	private Boolean tratarAtualizacaoLead(CloseableHttpResponse httpResponse) {
		prepararResposta(httpResponse);
		if (this.statusCode == HttpStatus.NO_CONTENT.value()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Tratamento da resposta do solicitação para anexar arquivo ao lead de credenciamento do Salesforce.
	 * 
	 * @param httpResponse A resposta recebida do Salesforce.
	 * @return true em caso sucesso na requisição, caso contrario false.
	 */
	private Boolean tratarAnexarLead(CloseableHttpResponse httpResponse) {
		prepararResposta(httpResponse);
		if (this.statusCode == HttpStatus.CREATED.value() && this.responseBody.get(CAMPO_SUCESSO).asBoolean()) {
			return true;
		} else {
            return false;
        }
    }

    /**
     * Tratamento da resposta da solicitação de consulta dos dados do Lead de credenciamento de posto.
	 * 
	 * @param httpResponse A resposta recebida do Salesforce.
	 * @return {@link LeadCredenciamentoPostoIntegradorVo} Dados do resultado da consulta.
	 */
	private LeadCredenciamentoPostoIntegradorVo tratarConsultaPostoLead(CloseableHttpResponse httpResponse) {
		prepararResposta(httpResponse);
		if (this.statusCode == HttpStatus.OK.value()) {
			if (this.responseBody.get(CAMPO_TAMANHO).intValue() > 0 && this.responseBody.get(CAMPO_REGISTROS).get(0) != null) {
				return UtilitarioJson.toObject(this.responseBody.get(CAMPO_REGISTROS).get(0).toString(), LeadCredenciamentoPostoIntegradorVo.class);
			} else {
				return new LeadCredenciamentoPostoIntegradorVo();
            }
        }
        return null;
    }

	/**
	 * Tratamento da resposta da solicitação de validação da existencia do Lead para o CNPJ informado.
	 *
	 * @param httpResponse A resposta recebida do Salesforce.
	 * @return true em caso de existencia do Lead, caso contrario false.
	 */
	private boolean tratarValidarExistenciaLead(CloseableHttpResponse httpResponse) {
		prepararResposta(httpResponse);
		if (this.statusCode == HttpStatus.OK.value() && this.responseBody.get(CAMPO_TAMANHO).intValue() > 0) {
			return true;
		} else {
			return false;
		}
	}
}

