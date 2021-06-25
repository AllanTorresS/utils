package ipp.aci.boleia.dominio.vo.conectcar;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Vo de integração dos lotes de adesivos com a conectcar 
 *
 */
public class LoteAdesivosVo {

	@JsonProperty("CodigoInternoParceiro")
	private String codigoInternoParceiro;

	@JsonProperty("Placa")
	private String[] placas;

	@JsonProperty("Motivo")
	private String motivo;

	@JsonProperty("ServicosBloqueio")
	private String[] servicosBloqueio;

	public String getCodigoInternoParceiro() {
		return codigoInternoParceiro;
	}

	public void setCodigoInternoParceiro(String codigoInternoParceiro) {
		this.codigoInternoParceiro = codigoInternoParceiro;
	}

	public String[] getPlacas() {
		return placas;
	}

	public void setPlacas(String[] placas) {
		this.placas = placas;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String[] getServicosBloqueio() {
		return servicosBloqueio;
	}

	public void setServicosBloqueio(String[] servicosBloqueio) {
		this.servicosBloqueio = servicosBloqueio;
	}

}