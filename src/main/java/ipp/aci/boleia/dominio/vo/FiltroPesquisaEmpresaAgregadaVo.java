package ipp.aci.boleia.dominio.vo;


import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa de empresas agregadas.
 */
public class FiltroPesquisaEmpresaAgregadaVo extends BaseFiltroPaginado {

	private Long id;
	private String cnpj;
	private String nomeRazao;
	private String cidade;
	private EnumVo uf;
	private EntidadeVo unidade;
	private EnumVo status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeRazao() {
		return nomeRazao;
	}

	public void setNomeRazao(String nomeRazao) {
		this.nomeRazao = nomeRazao;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public EnumVo getUf() {
		return uf;
	}

	public void setUf(EnumVo uf) {
		this.uf = uf;
	}

	public EntidadeVo getUnidade() {
		return unidade;
	}

	public void setUnidade(EntidadeVo unidade) {
		this.unidade = unidade;
	}

	public EnumVo getStatus() {
		return status;
	}

	public void setStatus(EnumVo status) {
		this.status = status;
	}
}
