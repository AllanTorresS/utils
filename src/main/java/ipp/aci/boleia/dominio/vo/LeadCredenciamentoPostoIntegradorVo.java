package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;

/**
 * Vo para envio das informações de lead do credenciamento de postos para o Salesforce.
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeadCredenciamentoPostoIntegradorVo {
	
	@Size(max = 40)
	@JsonProperty("PaginaCredenciamento__c")
	private String paginaCredenciamento;
	
	@Size(max = 5)
	@JsonProperty("NotificarLead__c")
	private String notificarLead;
	
	@Size(max = 4000)
	@JsonProperty("UrlCredenciamento__c")
	private String urlCredenciamento;
	
	@Size(max = 40)
	private String leadSource;
	
	private LeadRecordTypeIntegradorVo recordType;
	
	@Size(max = 250)
	private String company;
	
	@Size(max = 250)
	@JsonProperty("RazaoSocial__c")
	private String razaoSocial;
	
	@Size(max = 18)
	@JsonProperty("CnpjTexto__c")
	private String cnpjTexto;
	
	@Size(max = 14)
	@JsonProperty("Cpf__c")
	private String cpf;
	
	@Size(max = 80)
	private String lastName;
	
	@Size(max = 80)
	private String email;
	
	@Size(max = 40)
	private String phone;
	
	@Size(max = 15)
	@JsonProperty("IdBandeira__c")
	private String idBandeira;
	
	@Size(max = 30)
	@JsonProperty("Bandeira__c")
	private String bandeira;
	
	@Size(max = 5)
	@JsonProperty("BandeiraBranca__c")
	private String bandeiraBranca;
	
	@Size(max = 5)
	@JsonProperty("AceiteTermo__c")
	private String aceiteTermo;
	
	@Size(max = 15)
	@JsonProperty("Taxa__c")
	private String taxa;
	
	@Size(max = 250)
	@JsonProperty("Perfil__c")
	private String perfil;
	
	@Size(max = 15)
	@JsonProperty("IdBanco__c")
	private String idBanco;
	
	@Size(max = 250)
	@JsonProperty("Banco__c")
	private String banco;
	
	@Size(max = 5)
	@JsonProperty("Agencia__c")
	private String agencia;
	
	@Size(max = 1)
	@JsonProperty("DigitoAgencia__c")
	private String digitoAgencia;
	
	@Size(max = 20)
	@JsonProperty("Conta__c")
	private String conta;
	
	@Size(max = 2)
	@JsonProperty("DigitoConta__c")
	private String digitoConta;
	
	@Size(max = 40)
	@JsonProperty("TipoPessoa__c")
	private String tipoPessoa;
	
	@Size(max = 250)
	@JsonProperty("TitularConta__c")
	private String titularConta;
	
	@Size(max = 22)
	@JsonProperty("CnpjCpfTitularConta__c")
	private String cnpjCpfTitularConta;
	
	public LeadCredenciamentoPostoIntegradorVo() {
		// serializacao json
	}

	public String getPaginaCredenciamento() {
		return paginaCredenciamento;
	}

	public void setPaginaCredenciamento(String paginaCredenciamento) {
		this.paginaCredenciamento = paginaCredenciamento;
	}

	public String getNotificarLead() {
		return notificarLead;
	}

	public void setNotificarLead(String notificarLead) {
		this.notificarLead = notificarLead;
	}

	public String getUrlCredenciamento() {
		return urlCredenciamento;
	}

	public void setUrlCredenciamento(String urlCredenciamento) {
		this.urlCredenciamento = urlCredenciamento;
	}

	public String getLeadSource() {
		return leadSource;
	}

	public void setLeadSource(String leadSource) {
		this.leadSource = leadSource;
	}

	public LeadRecordTypeIntegradorVo getRecordType() {
		return recordType;
	}

	public void setRecordType(LeadRecordTypeIntegradorVo recordType) {
		this.recordType = recordType;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpjTexto() {
		return cnpjTexto;
	}

	public void setCnpjTexto(String cnpjTexto) {
		this.cnpjTexto = cnpjTexto;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdBandeira() {
		return idBandeira;
	}

	public void setIdBandeira(String idBandeira) {
		this.idBandeira = idBandeira;
	}

	public String getBandeira() {
		return bandeira;
	}

	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}

	public String getBandeiraBranca() {
		return bandeiraBranca;
	}

	public void setBandeiraBranca(String bandeiraBranca) {
		this.bandeiraBranca = bandeiraBranca;
	}

	public String getAceiteTermo() {
		return aceiteTermo;
	}

	public void setAceiteTermo(String aceiteTermo) {
		this.aceiteTermo = aceiteTermo;
	}

	public String getTaxa() {
		return taxa;
	}

	public void setTaxa(String taxa) {
		this.taxa = taxa;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(String idBanco) {
		this.idBanco = idBanco;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getDigitoAgencia() {
		return digitoAgencia;
	}

	public void setDigitoAgencia(String digitoAgencia) {
		this.digitoAgencia = digitoAgencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getDigitoConta() {
		return digitoConta;
	}

	public void setDigitoConta(String digitoConta) {
		this.digitoConta = digitoConta;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getTitularConta() {
		return titularConta;
	}

	public void setTitularConta(String titularConta) {
		this.titularConta = titularConta;
	}

	public String getCnpjCpfTitularConta() {
		return cnpjCpfTitularConta;
	}

	public void setCnpjCpfTitularConta(String cnpjCpfTitularConta) {
		this.cnpjCpfTitularConta = cnpjCpfTitularConta;
	}
	
}