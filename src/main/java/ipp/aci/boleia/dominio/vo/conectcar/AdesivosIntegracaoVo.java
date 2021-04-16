package ipp.aci.boleia.dominio.vo.conectcar;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Vo de integração dos adesivos com a conectcar 
 *
 */
public class AdesivosIntegracaoVo {

	@JsonProperty("Bloquear")
	private Boolean bloquear;
	
	@JsonProperty("ServicosBloqueio")
	private String[] servicosBloqueio;

	@JsonProperty("NumeroSerieTag")
	private Long numeroSerieTag;

	@JsonProperty("Veiculo")
	private VeiculoIntegracaoVo veiculo;

	public AdesivosIntegracaoVo() {

	}

	public AdesivosIntegracaoVo(Boolean bloquear, String[] servicosBloqueio, Long numeroSerieTag, VeiculoIntegracaoVo veiculo) {
		super();
		this.bloquear = bloquear;
		this.servicosBloqueio = servicosBloqueio;
		this.numeroSerieTag = numeroSerieTag;
		this.veiculo = veiculo;
	}

	public Boolean getBloquear() {
		return bloquear;
	}

	public void setBloquear(Boolean bloquear) {
		this.bloquear = bloquear;
	}

	public String[] getServicosBloqueio() {
		return servicosBloqueio;
	}

	public void setServicosBloqueio(String[] servicosBloqueio) {
		this.servicosBloqueio = servicosBloqueio;
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
