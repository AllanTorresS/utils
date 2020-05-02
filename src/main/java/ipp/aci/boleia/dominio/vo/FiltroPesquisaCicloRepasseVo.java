package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa de Ciclos de Repasse
 */
public class FiltroPesquisaCicloRepasseVo extends BaseFiltroPaginado {

	private String de;
	private String ate;
	private EnumVo statusPagamento;
	private EnumVo statusIntegracao;
	private String numeroDocumento;
	private EntidadeVo frota;

	public String getDe() {
		return de;
	}

	public void setDe(String de) {
		this.de = de;
	}

	public String getAte() {
		return ate;
	}

	public void setAte(String ate) {
		this.ate = ate;
	}

	public EnumVo getStatusPagamento() {
		return statusPagamento;
	}

	public void setStatusPagamento(EnumVo statusPagamento) {
		this.statusPagamento = statusPagamento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public EntidadeVo getFrota() {
		return frota;
	}

	public void setFrota(EntidadeVo frota) {
		this.frota = frota;
	}

	public EnumVo getStatusIntegracao() {
		return statusIntegracao;
	}

	public void setStatusIntegracao(EnumVo statusIntegracao) {
		this.statusIntegracao = statusIntegracao;
	}
}
