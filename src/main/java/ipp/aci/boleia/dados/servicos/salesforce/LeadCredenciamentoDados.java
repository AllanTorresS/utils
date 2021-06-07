package ipp.aci.boleia.dados.servicos.salesforce;

import ipp.aci.boleia.dados.ILeadCredenciamentoDados;
import ipp.aci.boleia.dominio.enums.EtapaCredenciamentoFrota;
import ipp.aci.boleia.dominio.vo.LeadCredenciamentoFrotaIntegradorVo;
import ipp.aci.boleia.dominio.vo.LeadCredenciamentoPostoIntegradorVo;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

/**
 * Respositório dos serviços de integração dos dados de lead do credenciamento com o sistema externo.
 */
@Repository
public class LeadCredenciamentoDados extends AcessoSalesForceBase implements ILeadCredenciamentoDados {

	private static final Logger LOGGER = LoggerFactory.getLogger(LeadCredenciamentoDados.class);

    private static final String CAMPO_ID = "id";
    private static final String CAMPO_SUCESSO = "success";
    private static final String CAMPO_TAMANHO = "totalSize";
    private static final String CAMPO_REGISTROS = "records";
    private static final String CAMPO_CNPJ = "Cnpj__c";


    private static final String ATRIBUTOS_URL = "{atributos}";
    private static final String CAMPO_PAGINA = "PaginaCredenciamento__c";
    private static final String ATRIBUTOS_CONSULTA_POSTO = "IdBanco__c,Banco__c,Agencia__c,DigitoAgencia__c,Conta__c,DigitoConta__c,TipoPessoa__c,TitularConta__c,CnpjCpfTitularConta__c";
    private static final String ATRIBUTOS_CONSULTA_FROTA = "CNPJTexto__c,Company,RazaoSocial__c,InscricaoEstadualIsento__c,InscricaoEstadual__c,InscricaoMunicipal__c,Phone";
    private static final String ATRIBUTOS_CONSULTA_FROTA_ENDERECO = "PostalCode,Street,Numero__c,Bairro__c,Complemento__c,City,State";
    private static final String ATRIBUTOS_CONSULTA_FROTA_RESPONSAVEL = "Cpf__c,ResponsavelFrotaNome__c,ResponsavelFrotaEmail__c,ResponsavelFrotaTelefone__c,ResponsavelFrotaCargo__c";
    private static final String ATRIBUTOS_CONSULTA_FROTA_ADICIONAL = "SegmentoAtuacao__c,CicloPrazo__c,AbasteceExternamente__c,AbasteceInternamente__c,VolEstimadoExternoCicloOtto__c,VolumeEstimadoCicloOtto__c,VolEstimadoExternoDiesel__c,VolumeEstimadoDiesel__c,QtdVeiculosLeves__c,QtdVeiculosPesados__c";

    
    @Value("${salesforce.credenciamento.atualizar.url}")
    private String criarAlterarLeadUrl;
    
    @Value("${salesforce.credenciamento.obter.url}")
    private String consultarLeadUrl;
    
    @Value("${salesforce.credenciamento.anexar.url}")
    private String anexarLeadUrl;
    
    @Override
	public String criarLead(String cnpj, Object corpo) throws ExcecaoValidacao {
    	if (Boolean.TRUE.equals(validarLeadExistente(cnpj))) {
			throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("credenciamento.servico.criarLead.existente"));
    	}
    	prepararRequisicao(this.criarAlterarLeadUrl.replace(CNPJ_URL, cnpj), corpo);
		String leadId = enviarRequisicaoPatch(this::tratarCriarLead);
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
		boolean leadAtualizado = enviarRequisicaoPatch(this::tratarAtualizacaoLead);
		if (!leadAtualizado) {
			LOGGER.error(this.mensagem);
			throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, this.mensagem);
		}
	}
	
	@Override
	public void anexarLead(Object corpo) {
		prepararRequisicao(this.anexarLeadUrl, corpo);
		boolean anexado = enviarRequisicaoPost(this::tratarAnexarLead);
		if (!anexado) {
			LOGGER.error(this.mensagem);
			throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, this.mensagem);
		}
	}
	
	@Override
	public LeadCredenciamentoPostoIntegradorVo consultarPostoLead(String cnpj) throws ExcecaoValidacao {
		prepararRequisicao(this.consultarLeadUrl.replace(ATRIBUTOS_URL, CAMPO_PAGINA+","+ATRIBUTOS_CONSULTA_POSTO).replace(CNPJ_URL, cnpj), null);
		LeadCredenciamentoPostoIntegradorVo postoLeadVo = enviarRequisicaoGet(this::tratarConsultaPostoLead);
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
	public LeadCredenciamentoFrotaIntegradorVo consultarFrotaLead(String cnpj, EtapaCredenciamentoFrota etapa) throws ExcecaoValidacao {
		prepararRequisicao(montaConsultaFrota(cnpj, etapa), null);
		LeadCredenciamentoFrotaIntegradorVo frotaLeadVo = enviarRequisicaoGet(this::tratarConsultaFrotaLead);
		if (frotaLeadVo == null) {
			LOGGER.error(this.mensagem);
			throw new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO, this.mensagem);
		}
		if (frotaLeadVo.getPaginaCredenciamento() == null) {
			throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO, mensagens.obterMensagem("credenciamento.servico.consultarLead.inexistente"));
		}
		return frotaLeadVo;
	}
	
	/**
     * Constroi a termo de consulta para o lead de frota por etapa.
     *
     * @param cnpj Parâmetro de busca do Lead.
     * @param etapa Etapa do credenciamento de frota
     * @return Termo de consulta.
     */
	private String montaConsultaFrota(String cnpj, EtapaCredenciamentoFrota etapa) {
		String atributosParaConsulta = CAMPO_PAGINA;
		if (etapa != null) {
			atributosParaConsulta = atributosParaConsulta.concat(",");
			switch (etapa) {
			case DADOS_FROTA:
				atributosParaConsulta = atributosParaConsulta.concat(ATRIBUTOS_CONSULTA_FROTA);
				break;
			case DADOS_ENDERECO:
				atributosParaConsulta = atributosParaConsulta.concat(ATRIBUTOS_CONSULTA_FROTA_ENDERECO);
				break;
			case DADOS_RESPONSAVEL:
				atributosParaConsulta = atributosParaConsulta.concat(ATRIBUTOS_CONSULTA_FROTA_RESPONSAVEL);
				break;
			case DADOS_ADICIONAIS:
				atributosParaConsulta = atributosParaConsulta.concat(ATRIBUTOS_CONSULTA_FROTA_ADICIONAL);
				break;
			default:
				break;
			}
		}
		return this.consultarLeadUrl.replace(ATRIBUTOS_URL, atributosParaConsulta).replace(CNPJ_URL, cnpj);
	}
	
	@Override
	public Boolean validarLeadExistente(String cnpj) {
		prepararRequisicao(this.consultarLeadUrl.replace(ATRIBUTOS_URL, CAMPO_CNPJ).replace(CNPJ_URL, cnpj), null);
		return enviarRequisicaoGet(this::tratarValidarExistenciaLead);
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
		return this.statusCode == HttpStatus.NO_CONTENT.value();
	}
	
	/**
	 * Tratamento da resposta do solicitação para anexar arquivo ao lead de credenciamento do Salesforce.
	 * 
	 * @param httpResponse A resposta recebida do Salesforce.
	 * @return true em caso sucesso na requisição, caso contrario false.
	 */
	private Boolean tratarAnexarLead(CloseableHttpResponse httpResponse) {
		prepararResposta(httpResponse);
		return this.statusCode == HttpStatus.CREATED.value() && this.responseBody.get(CAMPO_SUCESSO).asBoolean();
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
	 * Tratamento da resposta da solicitação de consulta dos dados do Lead de credenciamento de frota.
	 * 
	 * @param httpResponse A resposta recebida do Salesforce.
	 * @return {@link LeadCredenciamentoFrotaIntegradorVo} Dados do resultado da consulta.
	 */
	private LeadCredenciamentoFrotaIntegradorVo tratarConsultaFrotaLead(CloseableHttpResponse httpResponse) {
		prepararResposta(httpResponse);
		if (this.statusCode == HttpStatus.OK.value()) {
			if (this.responseBody.get(CAMPO_TAMANHO).intValue() > 0 && this.responseBody.get(CAMPO_REGISTROS).get(0) != null) {
				return UtilitarioJson.toObject(this.responseBody.get(CAMPO_REGISTROS).get(0).toString(), LeadCredenciamentoFrotaIntegradorVo.class);
			} else {
				return new LeadCredenciamentoFrotaIntegradorVo();
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
		return this.statusCode == HttpStatus.OK.value() && this.responseBody.get(CAMPO_TAMANHO).intValue() > 0;
	}
}
