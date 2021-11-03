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

	/**
	 * Devem ser informados os serviços que serão desbloqueados. Embora o nome do campo seja 'servicosBloqueio' funciona como 'servicosDesbloqueio'
	 */
	@JsonProperty("ServicosBloqueio")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String[] servicosBloqueio;

	/**
     * Construtor padrão
     */
	public AtivacaoTagIntegracaoVo() { }

	/**
	 * Constroi um AtivacaoTagIntegracaoVo representando uma Ativação de Tag para a ConectCar
	 * @param codigoInternoParceiro Código do parceiro
	 * @param placa Placa do veículo
	 * @param tipoUtilizacaoTag Inteiro representando o tipo de utilização para o qual a Tag deve ser ativada
	 */
	public AtivacaoTagIntegracaoVo(String codigoInternoParceiro, String placa, Integer tipoUtilizacaoTag) {
		this.codigoInternoParceiro = codigoInternoParceiro;
		if(placa != null && !placa.contains("-")) {
			this.placa = placa.substring(0, 3) + "-" + placa.substring(3);
		} else {
			this.placa = placa;
		}
		if(TipoUtilizacao.ESTACIONAMENTO.getValue().equals(tipoUtilizacaoTag)) {
			this.servicosBloqueio = new String[1];
			this.servicosBloqueio[0] = TipoUtilizacao.ESTACIONAMENTO.name();
		} else if(TipoUtilizacao.PEDAGIO.getValue().equals(tipoUtilizacaoTag)) {
			this.servicosBloqueio = new String[1];
			this.servicosBloqueio[0] = TipoUtilizacao.PEDAGIO.name();
		} else if(TipoUtilizacao.ESTACIONAMENTO_PEDAGIO.getValue().equals(tipoUtilizacaoTag)) {
			this.servicosBloqueio = new String[2];
			this.servicosBloqueio[0] = TipoUtilizacao.ESTACIONAMENTO.name();
			this.servicosBloqueio[1] = TipoUtilizacao.PEDAGIO.name();
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
