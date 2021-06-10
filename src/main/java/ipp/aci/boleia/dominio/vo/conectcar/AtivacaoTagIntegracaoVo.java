package ipp.aci.boleia.dominio.vo.conectcar;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import ipp.aci.boleia.dominio.enums.TipoUtilizacao;

/**
 * Vo de integração de adesivos da conectcar
 *
 */
public class AtivacaoTagIntegracaoVo {

	@JsonProperty("CodigoInternoParceiro")
	private String codigoInternoParceiro;

	@JsonProperty("Placa")
	private String placa;

	@JsonProperty("ServicosBloqueio")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String[] servicosBloqueio;
	
	public AtivacaoTagIntegracaoVo() {

	}

	public AtivacaoTagIntegracaoVo(String codigoInternoParceiro, String placa, Integer tipoUtilizacaoTag) {
		this.codigoInternoParceiro = codigoInternoParceiro;
		if(placa != null && !placa.contains("-")) {
			this.placa = placa.substring(0, 3) + "-" + placa.substring(3);
		} else {
			this.placa = placa;
		}
		if(TipoUtilizacao.ESTACIONAMENTO.getValue().equals(tipoUtilizacaoTag)) {
			this.servicosBloqueio = new String[1];
			this.servicosBloqueio[0] = TipoUtilizacao.PEDAGIO.name();
		} else if(TipoUtilizacao.PEDAGIO.getValue().equals(tipoUtilizacaoTag)) {
			this.servicosBloqueio = new String[1];
			this.servicosBloqueio[0] = TipoUtilizacao.ESTACIONAMENTO.name();
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

	public String[] getServicosBloqueio() {
		return servicosBloqueio;
	}

	public void setServicosBloqueio(String[] servicosBloqueio) {
		this.servicosBloqueio = servicosBloqueio;
	}

}
