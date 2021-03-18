package ipp.aci.boleia.dominio.vo.conectcar;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Vo de integração de transação conectcar
 *
 */
public class TransacaoParceiroPropriedadesVo {

	@JsonProperty("CodigoInternoParceiro")
	private String codigoInternoParceiro;

	@JsonProperty("DataDePassagem")
	private Date dataDePassagem;

	@JsonProperty("NumeroSerieTag")
	private String numeroSerieTag;

	@JsonProperty("PassagemId")
	private Long passagemId;

	@JsonProperty("DataEstorno")
	private Date dataEstorno;

	@JsonProperty("DataContestacao")
	private Date dataContestacao;

	@JsonProperty("EstornoId")
	private Long estornoId;

	@JsonProperty("TransacaoOrigemId")
	private Long transacaoOrigemId;

	@JsonProperty("RazaoSocialConveniado")
	private String razaoSocialConveniado;
	
	@JsonProperty("IdentificacaoPraca")
	private String identificacaoPraca;

	@JsonProperty("Protocolo")
	private String protocolo;

	@JsonProperty("CategoriaCobrada")
	private String categoriaCobrada;

	@JsonProperty("NumeroEixo")
	private String numeroEixo;

	public TransacaoParceiroPropriedadesVo() {
	}

	public String getCodigoInternoParceiro() {
		return codigoInternoParceiro;
	}

	public void setCodigoInternoParceiro(String codigoInternoParceiro) {
		this.codigoInternoParceiro = codigoInternoParceiro;
	}

	public Date getDataDePassagem() {
		return dataDePassagem;
	}

	public void setDataDePassagem(Date dataDePassagem) {
		this.dataDePassagem = dataDePassagem;
	}

	public Long getPassagemId() {
		return passagemId;
	}

	public void setPassagemId(Long passagemId) {
		this.passagemId = passagemId;
	}

	public Date getDataEstorno() {
		return dataEstorno;
	}

	public void setDataEstorno(Date dataEstorno) {
		this.dataEstorno = dataEstorno;
	}

	public Long getEstornoId() {
		return estornoId;
	}

	public void setEstornoId(Long estornoId) {
		this.estornoId = estornoId;
	}

	public Long getTransacaoOrigemId() {
		return transacaoOrigemId;
	}

	public void setTransacaoOrigemId(Long transacaoOrigemId) {
		this.transacaoOrigemId = transacaoOrigemId;
	}

	public String getRazaoSocialConveniado() {
		return razaoSocialConveniado;
	}

	public void setRazaoSocialConveniado(String razaoSocialConveniado) {
		this.razaoSocialConveniado = razaoSocialConveniado;
	}

	public String getIdentificacaoPraca() {
		return identificacaoPraca;
	}

	public void setIdentificacaoPraca(String identificacaoPraca) {
		this.identificacaoPraca = identificacaoPraca;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public Date getDataContestacao() {
		return dataContestacao;
	}

	public void setDataContestacao(Date dataContestacao) {
		this.dataContestacao = dataContestacao;
	}

	public String getNumeroSerieTag() {
		return numeroSerieTag;
	}

	public void setNumeroSerieTag(String numeroSerieTag) {
		this.numeroSerieTag = numeroSerieTag;
	}

	public String getCategoriaCobrada() {
		return categoriaCobrada;
	}

	public void setCategoriaCobrada(String categoriaCobrada) {
		this.categoriaCobrada = categoriaCobrada;
	}

	public String getNumeroEixo() {
		return numeroEixo;
	}

	public void setNumeroEixo(String numeroEixo) {
		this.numeroEixo = numeroEixo;
	}

}