package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa de tag conectcar
 */
public class FiltroPesquisaTagConectcarVo extends BaseFiltroPaginado {

	private Long id;// tag
	private EntidadeVo frota;
	private String placa;
	private EnumVo status;
	private EnumVo tipoUtilizacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public EnumVo getStatus() {
		return status;
	}

	public void setStatus(EnumVo status) {
		this.status = status;
	}

	public EntidadeVo getFrota() {
		return frota;
	}

	public void setFrota(EntidadeVo frota) {
		this.frota = frota;
	}

	public EnumVo getTipoUtilizacao() {
		return tipoUtilizacao;
	}

	public void setTipoUtilizacao(EnumVo tipoUtilizacao) {
		this.tipoUtilizacao = tipoUtilizacao;
	}

}
