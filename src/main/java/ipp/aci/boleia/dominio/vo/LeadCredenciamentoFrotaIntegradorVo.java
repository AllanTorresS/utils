package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;

/**
 * Vo para envio das informações de lead do credenciamento de frotas para o Salesforce.
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeadCredenciamentoFrotaIntegradorVo extends BaseLeadCredenciamentoIntegradorVo {
	
	@Size(max = 4)
	@JsonProperty("QtdVeiculosLeves__c")
	private String qtdVeiculosLeves;
	
	@Size(max = 4)
	@JsonProperty("QtdVeiculosPesados__c")
	private String qtdVeiculosPesados;
	
	@Size(max = 15)
	@JsonProperty("InscricaoEstadual__c")
	private String inscricaoEstadual;
	
	@Size(max = 15)
	@JsonProperty("InscricaoEstadualIsento__c")
	private String inscricaoEstadualIsento;
	
	@Size(max = 15)
	@JsonProperty("InscricaoMunicipal__c")
	private String inscricaoMunicipal;
	
	@Size(max = 8)
	@JsonProperty("PostalCode")
	private String postalCode;
	
	@Size(max = 150)
	@JsonProperty("Street")
	private String street;
	
	@Size(max = 8)
	@JsonProperty("Numero__c")
	private String numero;
	
	@Size(max = 150)
	@JsonProperty("Bairro__c")
	private String bairro;
	
	@Size(max = 20)
	@JsonProperty("Complemento__c")
	private String complemento;
	
	@Size(max = 50)
	@JsonProperty("City")
	private String city;
	
	@Size(max = 80)
	@JsonProperty("State")
	private String state;
	
	@Size(max = 80)
	@JsonProperty("ResponsavelFrotaNome__c")
	private String responsavelFrotaNome;
	
	@Size(max = 14)
	@JsonProperty("ResponsavelFrotaCPF__c")
	private String responsavelFrotaCPF;
	
	@Size(max = 80)
	@JsonProperty("ResponsavelFrotaEmail__c")
	private String responsavelFrotaEmail;
	
	@Size(max = 11)
	@JsonProperty("ResponsavelFrotaTelefone__c")
	private String responsavelFrotaTelefone;
	
	@Size(max = 250)
	@JsonProperty("ResponsavelFrotaCargo__c")
	private String responsavelFrotaCargo;
	
	@Size(max = 40)
	@JsonProperty("SegmentoAtuacao__c")
	private String segmentoAtuacao; 
	
	@Size(max = 7)
	@JsonProperty("CicloPrazo__c")
	private String cicloPrazo;
	
	@Size(max = 15)
	@JsonProperty("AbasteceExternamente__c")
	private String abasteceExternamente;
	
	@Size(max = 15)
	@JsonProperty("AbasteceInternamente__c")
	private String abasteceInternamente;
	
	@Size(max = 15)
	@JsonProperty("VolEstimadoExternoCicloOtto__c")
	private String volEstimadoExternoCicloOtto;
	
	@Size(max = 15)
	@JsonProperty("VolumeEstimadoCicloOtto__c")
	private String volEstimadoCicloOtto;
	
	@Size(max = 15)
	@JsonProperty("VolEstimadoExternoDiesel__c")
	private String volEstimadoExternoDiesel;
	
	@Size(max = 15)
	@JsonProperty("VolumeEstimadoDiesel__c")
	private String volEstimadoDiesel;
	
	public LeadCredenciamentoFrotaIntegradorVo() {
		// serializacao json
	}

	public String getQtdVeiculosLeves() {
		return qtdVeiculosLeves;
	}

	public void setQtdVeiculosLeves(String qtdVeiculosLeves) {
		this.qtdVeiculosLeves = qtdVeiculosLeves;
	}

	public String getQtdVeiculosPesados() {
		return qtdVeiculosPesados;
	}

	public void setQtdVeiculosPesados(String qtdVeiculosPesados) {
		this.qtdVeiculosPesados = qtdVeiculosPesados;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getInscricaoEstadualIsento() {
		return inscricaoEstadualIsento;
	}

	public void setInscricaoEstadualIsento(String inscricaoEstadualIsento) {
		this.inscricaoEstadualIsento = inscricaoEstadualIsento;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getResponsavelFrotaNome() {
		return responsavelFrotaNome;
	}

	public void setResponsavelFrotaNome(String responsavelFrotaNome) {
		this.responsavelFrotaNome = responsavelFrotaNome;
	}

	public String getResponsavelFrotaCPF() {
		return responsavelFrotaCPF;
	}

	public void setResponsavelFrotaCPF(String responsavelFrotaCPF) {
		this.responsavelFrotaCPF = responsavelFrotaCPF;
	}

	public String getResponsavelFrotaEmail() {
		return responsavelFrotaEmail;
	}

	public void setResponsavelFrotaEmail(String responsavelFrotaEmail) {
		this.responsavelFrotaEmail = responsavelFrotaEmail;
	}

	public String getResponsavelFrotaTelefone() {
		return responsavelFrotaTelefone;
	}

	public void setResponsavelFrotaTelefone(String responsavelFrotaTelefone) {
		this.responsavelFrotaTelefone = responsavelFrotaTelefone;
	}

	public String getResponsavelFrotaCargo() {
		return responsavelFrotaCargo;
	}

	public void setResponsavelFrotaCargo(String responsavelFrotaCargo) {
		this.responsavelFrotaCargo = responsavelFrotaCargo;
	}

	public String getSegmentoAtuacao() {
		return segmentoAtuacao;
	}

	public void setSegmentoAtuacao(String segmentoAtuacao) {
		this.segmentoAtuacao = segmentoAtuacao;
	}

	public String getCicloPrazo() {
		return cicloPrazo;
	}

	public void setCicloPrazo(String cicloPrazo) {
		this.cicloPrazo = cicloPrazo;
	}

	public String getAbasteceExternamente() {
		return abasteceExternamente;
	}

	public void setAbasteceExternamente(String abasteceExternamente) {
		this.abasteceExternamente = abasteceExternamente;
	}

	public String getAbasteceInternamente() {
		return abasteceInternamente;
	}

	public void setAbasteceInternamente(String abasteceInternamente) {
		this.abasteceInternamente = abasteceInternamente;
	}

	public String getVolEstimadoExternoCicloOtto() {
		return volEstimadoExternoCicloOtto;
	}

	public void setVolEstimadoExternoCicloOtto(String volEstimadoExternoCicloOtto) {
		this.volEstimadoExternoCicloOtto = volEstimadoExternoCicloOtto;
	}

	public String getVolEstimadoCicloOtto() {
		return volEstimadoCicloOtto;
	}

	public void setVolEstimadoCicloOtto(String volEstimadoCicloOtto) {
		this.volEstimadoCicloOtto = volEstimadoCicloOtto;
	}

	public String getVolEstimadoExternoDiesel() {
		return volEstimadoExternoDiesel;
	}

	public void setVolEstimadoExternoDiesel(String volEstimadoExternoDiesel) {
		this.volEstimadoExternoDiesel = volEstimadoExternoDiesel;
	}

	public String getVolEstimadoDiesel() {
		return volEstimadoDiesel;
	}

	public void setVolEstimadoDiesel(String volEstimadoDiesel) {
		this.volEstimadoDiesel = volEstimadoDiesel;
	}

}