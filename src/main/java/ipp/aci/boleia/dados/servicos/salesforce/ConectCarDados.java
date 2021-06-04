package ipp.aci.boleia.dados.servicos.salesforce;

import ipp.aci.boleia.dados.ILeadConectCarDados;
import ipp.aci.boleia.dominio.vo.SolicitacaoLeadExtRegistroVo;
import ipp.aci.boleia.dominio.vo.SolicitacaoLeadExtVo;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

/**
 * Respositório dos serviços de integração dos dados de conectCar com o sistema externo.
 */
@Repository
public class ConectCarDados extends AcessoSalesForceBase implements ILeadConectCarDados {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConectCarDados.class);

    private static final String CAMPO_SUCESSO = "success";
    private static final String CAMPO_TAMANHO = "totalSize";
    private static final String CAMPO_REGISTROS = "records";
    private static final String CAMPO_RECORD_TYPE = "Pró-Frotas Go";
    private static final String CAMPO_STAGE_NAME = "Prospect";

    @Value("${salesforce.profrotasgo.authorization.client.id}")
    private String clientId;
    
    @Value("${salesforce.profrotasgo.authorization.client.secret}")
    private String clientSecret;
    
    @Value("${salesforce.profrotasgo.authorization.username}")
    private String username;
    
    @Value("${salesforce.profrotasgo.authorization.password}")
    private String password;
    
    @Value("${salesforce.profrotasgo.authorization.environment.uri}")
    private String environmentUri;
    
    @Value("${salesforce.profrotasgo.account.obter.url}")
    private String consultarAccount;
    
    @Value("${salesforce.profrotasgo.oportunidade.criar.url}")
    private String oportunidadeCriarUrl;
    
	
	@Override
	public boolean enviarLead(Object corpo) {
		
		SolicitacaoLeadExtVo solicitacao = (SolicitacaoLeadExtVo) corpo;
		
		prepararRequisicao(this.consultarAccount.replace(CNPJ_URL, solicitacao.getCnpj()), solicitacao);
		LOGGER.info("ConectCarDados.enviarLead.endpointUrl: " + this.endpointUrl);
		LOGGER.info("ConectCarDados.enviarLead.authorizationHeaders: " + this.authorizationHeaders);
		SolicitacaoLeadExtVo solicitacaoRetorno = enviarRequisicaoGet(this::tratarConsultaAccount);
		String id = solicitacaoRetorno.getId();
		
		if (id == null) {
			throw new ExcecaoBoleiaRuntime(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("servico.conectcar.erro.envio.oportunidade"));
		}
		
		solicitacao.setAccountId(id);
		solicitacao.setCnpj(null);
		solicitacao.setRecordType(new SolicitacaoLeadExtRegistroVo(CAMPO_RECORD_TYPE));
		solicitacao.setStageName(CAMPO_STAGE_NAME);
		solicitacao.setGastoMensal(solicitacao.getGastoMensal().replace(".", "").replace(",", "."));
		prepararRequisicao(this.oportunidadeCriarUrl, solicitacao);
		boolean enviadoSucesso = enviarRequisicaoPost(this::tratarEnviarLead);
		if (!enviadoSucesso) {
			LOGGER.error(this.mensagem);
			throw new ExcecaoBoleiaRuntime(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("servico.conectcar.erro.envio.oportunidade"));
		}
		
		return enviadoSucesso;
	}
	
	/**
	 * Tratamento da resposta do solicitação para anexar arquivo ao lead de credenciamento do Salesforce.
	 * 
	 * @param httpResponse A resposta recebida do Salesforce.
	 * @return true em caso sucesso na requisição, caso contrario false.
	 */
	private Boolean tratarEnviarLead(CloseableHttpResponse httpResponse) {
		prepararResposta(httpResponse);
		return this.statusCode == HttpStatus.CREATED.value() && this.responseBody.get(CAMPO_SUCESSO).asBoolean();
	}
	
	private SolicitacaoLeadExtVo tratarConsultaAccount(CloseableHttpResponse httpResponse) {
		prepararResposta(httpResponse);
		if (this.statusCode == HttpStatus.OK.value()) {
			if (this.responseBody.get(CAMPO_TAMANHO).intValue() > 0 && this.responseBody.get(CAMPO_REGISTROS).get(0) != null) {
				
				return UtilitarioJson.toObject(this.responseBody.get(CAMPO_REGISTROS).get(0).toString(), SolicitacaoLeadExtVo.class);
			} else {
				return new SolicitacaoLeadExtVo();
			}
		}
		return null;
	}

	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public String getClientSecret() {
		return clientSecret;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}
}
