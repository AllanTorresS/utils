package ipp.aci.boleia.dominio.vo.conectcar;

import java.util.List;

/**
 * Vo para obtenção da resposta das requisições da ConectCar
 */
public class ConectCarDadosResponseVo {

	private Boolean ativado;
	private String numeroSerieTag;
	private String placa;

	private List<ConectCarErroResponseVo> erros;

	/**
	 * Construtor default da classe.
	 */
	public ConectCarDadosResponseVo() {
	}

	public Boolean getAtivado() {
		return ativado;
	}

	public void setAtivado(Boolean ativado) {
		this.ativado = ativado;
	}

	public String getNumeroSerieTag() {
		return numeroSerieTag;
	}

	public void setNumeroSerieTag(String numeroSerieTag) {
		this.numeroSerieTag = numeroSerieTag;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public List<ConectCarErroResponseVo> getErros() {
		return erros;
	}

	public void setErros(List<ConectCarErroResponseVo> erros) {
		this.erros = erros;
	}

}
