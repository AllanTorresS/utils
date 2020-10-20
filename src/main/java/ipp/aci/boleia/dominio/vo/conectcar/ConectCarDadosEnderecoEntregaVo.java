package ipp.aci.boleia.dominio.vo.conectcar;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Vo de integração do endereço de pedidos com a conectcar 
 */
public class ConectCarDadosEnderecoEntregaVo {

	@JsonProperty("CEP")
	private String cep;

	@JsonProperty("Logradouro")
	private String logradouro;

	@JsonProperty("Numero")
	private Integer numero;

	@JsonProperty("Complemento")
	private String complemento;

	@JsonProperty("Bairro")
	private String bairro;

	@JsonProperty("Cidade")
	private String cidade;

	@JsonProperty("UF")
	private String uf;

	/**
	 * Construtor default da classe.
	 */
	public ConectCarDadosEnderecoEntregaVo() {
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

}
