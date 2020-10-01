package ipp.aci.boleia.dominio.vo.conectcar;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransacaoParceiroPropriedadesVo {
	
	@JsonProperty("CodigoInternoParceiro")
	private String codigoInternoParceiro;

	@JsonProperty("DataDePassagem")
	private Date dataDePassagem;

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

	@JsonProperty("IdentificadorPraca")
	private String identificadorPraca;

	@JsonProperty("Protocolo")
	private String protocolo;

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

	public String getIdentificadorPraca() {
		return identificadorPraca;
	}

	public void setIdentificadorPraca(String identificadorPraca) {
		this.identificadorPraca = identificadorPraca;
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

}
