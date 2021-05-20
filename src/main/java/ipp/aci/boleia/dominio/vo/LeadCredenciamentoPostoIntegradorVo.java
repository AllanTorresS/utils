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
public class LeadCredenciamentoPostoIntegradorVo extends BaseLeadCredenciamentoIntegradorVo {
	
	@Size(max = 15)
	@JsonProperty("IdBandeira__c")
	private String idBandeira;
	
	@Size(max = 30)
	@JsonProperty("Bandeira__c")
	private String bandeira;
	
	@Size(max = 5)
	@JsonProperty("BandeiraBranca__c")
	private String bandeiraBranca;

	@JsonProperty("ParticiparAntecipacaoRecebiveis__c")
	private Boolean aceiteAntecipacao;
	
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

	public Boolean isAceiteAntecipacao() { return aceiteAntecipacao; }

	public void setAceiteAntecipacao(Boolean aceiteAntecipacao) { this.aceiteAntecipacao = aceiteAntecipacao; }

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