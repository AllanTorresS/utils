package ipp.aci.boleia.dominio.vo.conectcar;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Vo para obtenção da resposta das requisições da ConectCar
 */
public class ConectCarBloquearContratoRequestVo {

	@JsonProperty("CodigoInternoParceiro")
	private String codigoInternoParceiro;

	@JsonProperty("Motivo")
	private String motivo;

	/**
	 * Construtor default da classe.
	 */
	public ConectCarBloquearContratoRequestVo() {

	}

	public ConectCarBloquearContratoRequestVo(String codigoInternoParceiro, String motivo) {
		super();
		this.codigoInternoParceiro = codigoInternoParceiro;
		this.motivo = motivo;
	}

	public String getCodigoInternoParceiro() {
		return codigoInternoParceiro;
	}

	public void setCodigoInternoParceiro(String codigoInternoParceiro) {
		this.codigoInternoParceiro = codigoInternoParceiro;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

}
