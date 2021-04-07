package ipp.aci.boleia.dominio.vo.conectcar;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Vo de integração de adesivos da conectcar
 *
 */
public class TagSubstituicaoIntegracaoVo {

	@JsonProperty("CodigoInternoParceiro")
	private String codigoInternoParceiro;

	@JsonProperty("placaAtual")
	private String placaAtual;

	@JsonProperty("NumeroSerieTagAtual")
	private Long numeroSerieTagAtual;
	
	@JsonProperty("NumeroSerieTagNovo")
	private Long numeroSerieTagNovo;
	
	/*
	 * Códigos Motivos Substituição 
	 1 – Falha / Defeito no TAG
	 2 – Instalação incorreta
	 3 – Retenção
	 4 – TAG inexistente
	 5 – Troca de Para-Brisa
	 6 – Troca de Veículo
	 7 – Troca por Adesivo
	 */
	@JsonProperty("MotivoId")
	private int motivoId;
	
	/*
	 PEDAGIO
	 ESTACIONAMENTO
	 */
	@JsonProperty("Bloquear")
	private boolean bloquear;
	
	public TagSubstituicaoIntegracaoVo() {
		this.motivoId = 1;
		this.bloquear = false;
	}

	public String getCodigoInternoParceiro() {
		return codigoInternoParceiro;
	}

	public void setCodigoInternoParceiro(String codigoInternoParceiro) {
		this.codigoInternoParceiro = codigoInternoParceiro;
	}

	public String getPlacaAtual() {
		return placaAtual;
	}

	public void setPlacaAtual(String placaAtual) {
		this.placaAtual = placaAtual;
	}

	public Long getNumeroSerieTagAtual() {
		return numeroSerieTagAtual;
	}

	public void setNumeroSerieTagAtual(Long numeroSerieTagAtual) {
		this.numeroSerieTagAtual = numeroSerieTagAtual;
	}

	public Long getNumeroSerieTagNovo() {
		return numeroSerieTagNovo;
	}

	public void setNumeroSerieTagNovo(Long numeroSerieTagNovo) {
		this.numeroSerieTagNovo = numeroSerieTagNovo;
	}

	public int getMotivoId() {
		return motivoId;
	}

	public void setMotivoId(int motivoId) {
		this.motivoId = motivoId;
	}

	public boolean isBloquear() {
		return bloquear;
	}

	public void setBloquear(boolean bloquear) {
		this.bloquear = bloquear;
	}
	
	
}
