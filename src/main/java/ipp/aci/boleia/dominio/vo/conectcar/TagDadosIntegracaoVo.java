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

	@JsonProperty("Id")
	private Long id;
	
	public TagDadosIntegracaoVo() {

	}

	public TagDadosIntegracaoVo(String codigoInternoParceiro, String placa) {
		this.codigoInternoParceiro = codigoInternoParceiro;
		if(placa != null && !placa.contains("-")) {
			this.placa = placa.substring(0, 3) + "-" + placa.substring(3);
		} else {
			this.placa = placa;
		}
	}

	public TagDadosIntegracaoVo(String codigoInternoParceiro, Long id, String placa) {
		this.codigoInternoParceiro = codigoInternoParceiro;
		this.id = id;
		if(placa != null && !placa.contains("-")) {
			this.placa = placa.substring(0, 3) + "-" + placa.substring(3);
		} else {
			this.placa = placa;
		}
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
