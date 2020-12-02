package ipp.aci.boleia.dominio.vo.conectcar;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Vo de integração de adesivos da conectcar
 *
 */
public class TagDadosIntegracaoVo {

	@JsonProperty("CodigoInternoParceiro")
	private String codigoInternoParceiro;

	@JsonProperty("Placa")
	private String placa;

	public TagDadosIntegracaoVo() {

	}

	public TagDadosIntegracaoVo(String codigoInternoParceiro, String placa) {
		this.codigoInternoParceiro = codigoInternoParceiro;
		this.placa = placa;
	}

	public String getCodigoInternoParceiro() {
		return codigoInternoParceiro;
	}

	public void setCodigoInternoParceiro(String codigoInternoParceiro) {
		this.codigoInternoParceiro = codigoInternoParceiro;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

}
