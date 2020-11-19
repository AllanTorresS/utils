package ipp.aci.boleia.dominio.vo;

import java.util.Date;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa de reembolso da conectar
 */
public class FiltroPesquisaReembolsoConectcarVo extends BaseFiltroPaginado {

	private Date de;
	private Date ate;
	private EnumVo statusPagamento;
	private EnumVo statusIntegracao;
	private String numeroDocumento;

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

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

}
