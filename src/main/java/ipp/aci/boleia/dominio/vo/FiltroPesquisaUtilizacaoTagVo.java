package ipp.aci.boleia.dominio.vo;

import java.util.Date;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa de utilizacao de tag
 */
public class FiltroPesquisaUtilizacaoTagVo extends BaseFiltroPaginado {

	private Long tag;
	private String placa;
	private Date de;
	private Date ate;
	private EnumVo statusTag;
	private EnumVo tipo;
	private EntidadeVo frota;
	private EntidadeVo reembolso;

	public Date getDe() {
		return de;
	}

	public void setDe(Date de) {
		this.de = de;
	}

	public Date getAte() {
		return ate;
	}

	public void setAte(Date ate) {
		this.ate = ate;
	}

	public EnumVo getStatusTag() {
		return statusTag;
	}

	public void setStatusTag(EnumVo statusTag) {
		this.statusTag = statusTag;
	}

	public EnumVo getTipo() {
		return tipo;
	}

	public void setTipo(EnumVo tipo) {
		this.tipo = tipo;
	}

	public Long getTag() {
		return tag;
	}

	public void setTag(Long tag) {
		this.tag = tag;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public EntidadeVo getFrota() {
		return frota;
	}

	public void setFrota(EntidadeVo frota) {
		this.frota = frota;
	}

	public EntidadeVo getReembolso() {
		return reembolso;
	}

	public void setReembolso(EntidadeVo reembolso) {
		this.reembolso = reembolso;
	}

}