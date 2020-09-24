package ipp.aci.boleia.dominio.vo.conectcar;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VeiculoIntegracaoVo {

	@JsonProperty("Placa")
	private String placa;

	public VeiculoIntegracaoVo() {

	}

	public VeiculoIntegracaoVo(String placa) {		
		this.placa = placa;
	}


	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

}
