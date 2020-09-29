package ipp.aci.boleia.dominio.vo.conectcar;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConectcarCriarPedidoEntregaVo {

	@JsonProperty("CodigoInternoParceiro")
	private String codigoInternoParceiro;

	@JsonProperty("Nome")
	private String nome;

	@JsonProperty("Documento")
	private String documento;

	@JsonProperty("TipoDocumento")
	private String tipoDocumento;

	@JsonProperty("TipoPessoa")
	private String tipoPessoa;

	@JsonProperty("InscricaoEstadual")
	private String inscricaoEstadual;

	@JsonProperty("InscricaoMunicipal")
	private String inscricaoMunicipal;

	@JsonProperty("Email")
	private String email;

	@JsonProperty("TelefoneDDD")
	private String telefoneDDD;

	@JsonProperty("Telefone")
	private String telefone;

	@JsonProperty("CelularDDD")
	private String celularDDD;

	@JsonProperty("Celular")
	private String celular;

	@JsonProperty("EnderecoEntrega")
	private ConectCarDadosEnderecoEntregaVo enderecoEntrega;

	@JsonProperty("Itens")
	private List<ConectcarCriarPedidoEntregaItensVo> itens;

	public ConectcarCriarPedidoEntregaVo() {

	}

	public String getCodigoInternoParceiro() {
		return codigoInternoParceiro;
	}

	public void setCodigoInternoParceiro(String codigoInternoParceiro) {
		this.codigoInternoParceiro = codigoInternoParceiro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefoneDDD() {
		return telefoneDDD;
	}

	public void setTelefoneDDD(String telefoneDDD) {
		this.telefoneDDD = telefoneDDD;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelularDDD() {
		return celularDDD;
	}

	public void setCelularDDD(String celularDDD) {
		this.celularDDD = celularDDD;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public ConectCarDadosEnderecoEntregaVo getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(ConectCarDadosEnderecoEntregaVo enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public List<ConectcarCriarPedidoEntregaItensVo> getItens() {
		return itens;
	}

	public void setItens(List<ConectcarCriarPedidoEntregaItensVo> itens) {
		this.itens = itens;
	}

}
