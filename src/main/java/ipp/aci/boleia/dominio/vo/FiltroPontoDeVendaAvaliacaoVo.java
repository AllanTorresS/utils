package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa de ponto de venda avaliação
 */
public class FiltroPontoDeVendaAvaliacaoVo extends BaseFiltroPaginado {

	private String id;
	private EntidadeVo pontoDeVenda;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EntidadeVo getPontoDeVenda() {
		return pontoDeVenda;
	}

	public void setPontoDeVenda(EntidadeVo pontoDeVenda) {
		this.pontoDeVenda = pontoDeVenda;
	}
}
