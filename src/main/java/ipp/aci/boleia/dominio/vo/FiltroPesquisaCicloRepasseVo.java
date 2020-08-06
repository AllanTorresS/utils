package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.List;

/**
 * Filtro para pesquisa de Ciclos de Repasse
 */
public class FiltroPesquisaCicloRepasseVo extends BaseFiltroPaginado {

	private String de;
	private String ate;
	private EntidadeVo pontoDeVenda;
	private String numeroDocumento;
	private List<EnumVo> produto;
	private EnumVo statusPagamento;
	private EnumVo statusIntegracao;

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

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public EntidadeVo getPontoDeVenda() {
		return pontoDeVenda;
	}

	public void setPontoDeVenda(EntidadeVo pontoDeVenda) {
		this.pontoDeVenda = pontoDeVenda;
	}

	public List<EnumVo> getProduto() {
		return produto;
	}

	public void setProduto(List<EnumVo> produto) {
		this.produto = produto;
	}

	public EnumVo getStatusPagamento() {
		return statusPagamento;
	}

	public void setStatusPagamento(EnumVo statusPagamento) {
		this.statusPagamento = statusPagamento;
	}

	public EnumVo getStatusIntegracao() {
		return statusIntegracao;
	}

	public void setStatusIntegracao(EnumVo statusIntegracao) {
		this.statusIntegracao = statusIntegracao;
	}
}
