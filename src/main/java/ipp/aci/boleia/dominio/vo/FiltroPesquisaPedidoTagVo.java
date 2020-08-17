package ipp.aci.boleia.dominio.vo;

import java.util.Date;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa de pedido de tag
 */
public class FiltroPesquisaPedidoTagVo extends BaseFiltroPaginado {

	private Long id;
	private EntidadeVo frota;
	private Date de;
	private Date ate;
	private EnumVo status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

}
