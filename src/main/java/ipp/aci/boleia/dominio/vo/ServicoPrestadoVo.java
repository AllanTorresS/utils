package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Classe com informações referentes à um serviço prestado
 */
public class ServicoPrestadoVo {

	private String periodo;
	private String descricao;
	private String valor;

	/**
	 * Construtor padrão
	 */
	public ServicoPrestadoVo() {}

	/**
	 * Constrói o VO a partir dos valores informados
	 * @param dataInicio data inicial do período
	 * @param dataFim data final do período
	 * @param descricao descrição do serviço prestado
	 * @param valor valor do serviço prestado
	 */
	public ServicoPrestadoVo(Date dataInicio, Date dataFim, String descricao, BigDecimal valor) {
		this.periodo = UtilitarioFormatacaoData.formatarDataPeriodoDataCurta(dataInicio, dataFim);
		this.descricao = descricao;
		this.valor = UtilitarioFormatacao.formatarDecimal(valor);
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}