package ipp.aci.boleia.dominio.vo.conectcar;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Vo para obtenção da resposta das requisições da ConectCar
 */
public class ConectCarDesbloquearContratoRequestVo {

	@JsonProperty("CodigoInternoParceiro")
	private String codigoInternoParceiro;

	/**
	 * Construtor default da classe.
	 */
	public ConectCarDesbloquearContratoRequestVo() {

	}

	public ConectCarDesbloquearContratoRequestVo(String codigoInternoParceiro) {	
		this.codigoInternoParceiro = codigoInternoParceiro;
	}

	public String getCodigoInternoParceiro() {
		return codigoInternoParceiro;
	}

	public void setCodigoInternoParceiro(String codigoInternoParceiro) {
		this.codigoInternoParceiro = codigoInternoParceiro;
	}

}
