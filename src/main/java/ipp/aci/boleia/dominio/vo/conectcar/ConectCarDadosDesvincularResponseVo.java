package ipp.aci.boleia.dominio.vo.conectcar;

import java.util.List;

/**
 * Vo para obtenção da resposta das requisições da ConectCar
 */
public class ConectCarDadosDesvincularResponseVo {

	private Long ativacaoId;
	private String dataDesvinculo;

	private List<ConectCarErroResponseVo> erros;

	/**
	 * Construtor default da classe.
	 */
	public ConectCarDadosDesvincularResponseVo() {
	}

	public Long getAtivacaoId() {
		return ativacaoId;
	}

	public void setAtivacaoId(Long ativacaoId) {
		this.ativacaoId = ativacaoId;
	}

	public String getDataDesvinculo() {
		return dataDesvinculo;
	}

	public void setDataDesvinculo(String dataDesvinculo) {
		this.dataDesvinculo = dataDesvinculo;
	}

	public List<ConectCarErroResponseVo> getErros() {
		return erros;
	}

	public void setErros(List<ConectCarErroResponseVo> erros) {
		this.erros = erros;
	}

}
