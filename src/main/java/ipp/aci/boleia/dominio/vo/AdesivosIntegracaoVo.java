package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdesivosIntegracaoVo {

	@JsonProperty("Bloquear")
	private Boolean bloquear;

	@JsonProperty("NumeroSerieTag")
	private Long numeroSerieTag;

	@JsonProperty("Veiculo")
	private VeiculoIntegracaoVo veiculo;

	public AdesivosIntegracaoVo() {

	}

	public AdesivosIntegracaoVo(Boolean bloquear, Long numeroSerieTag, VeiculoIntegracaoVo veiculo) {
		super();
		this.bloquear = bloquear;
		this.numeroSerieTag = numeroSerieTag;
		this.veiculo = veiculo;
	}

	public Boolean getBloquear() {
		return bloquear;
	}

	public void setBloquear(Boolean bloquear) {
		this.bloquear = bloquear;
	}

	public Long getNumeroSerieTag() {
		return numeroSerieTag;
	}

	public void setNumeroSerieTag(Long numeroSerieTag) {
		this.numeroSerieTag = numeroSerieTag;
	}

	public VeiculoIntegracaoVo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(VeiculoIntegracaoVo veiculo) {
		this.veiculo = veiculo;
	}

}
