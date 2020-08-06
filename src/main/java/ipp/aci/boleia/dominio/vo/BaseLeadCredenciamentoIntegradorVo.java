package ipp.aci.boleia.dominio.vo;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe base para a criação de VOs de lead do Salesforce para credenciamento.
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseLeadCredenciamentoIntegradorVo {
	
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
	@JsonProperty("LeadSource")
	private String leadSource;
	
	@JsonProperty("RecordType")
	private LeadRecordTypeIntegradorVo recordType;
	
	@Size(max = 250)
	@JsonProperty("Company")
	private String company;
	
	@Size(max = 250)
	@JsonProperty("RazaoSocial__c")
	private String razaoSocial;
	
	@Size(max = 18)
	@JsonProperty("CNPJTexto__c")
	private String cnpjTexto;
	
	@Size(max = 14)
	@JsonProperty("CPF__c")
	private String cpf;
	
	@Size(max = 80)
	@JsonProperty("LastName")
	private String lastName;
	
	@Size(max = 80)
	@JsonProperty("Email")
	private String email;
	
	@Size(max = 40)
	@JsonProperty("Phone")
	private String phone;
	
	public BaseLeadCredenciamentoIntegradorVo() {
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
	
}