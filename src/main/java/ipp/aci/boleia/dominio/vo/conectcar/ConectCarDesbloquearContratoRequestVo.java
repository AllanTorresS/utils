package ipp.aci.boleia.dominio.vo.conectcar;

import java.util.List;

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

	public String getCodigoInternoParceiro() {
		return codigoInternoParceiro;
	}

	public void setCodigoInternoParceiro(String codigoInternoParceiro) {
		this.codigoInternoParceiro = codigoInternoParceiro;
	}

}
