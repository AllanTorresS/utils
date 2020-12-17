package ipp.aci.boleia.dominio.vo.conectcar;

import java.math.BigDecimal;

/**
 * Vo de retorno do serviço do sales force
 *
 */
public class RespostaLeadSalesforceVo {

	private String cnpj;
	private Boolean aprovado;
	private Integer tempoIsencaoMensalidade;
	private Integer prazoCiclo;
	private Integer prazoPagamento;
	private Integer prazoContrato;
	private BigDecimal valorAdesao;
	private BigDecimal valorMensalidade;
	private BigDecimal creditoTotal;
	private String linkContrato;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Boolean getAprovado() {
		return aprovado;
	}

	public void setAprovado(Boolean aprovado) {
		this.aprovado = aprovado;
	}

	public Integer getTempoIsencaoMensalidade() {
		return tempoIsencaoMensalidade;
	}

	public void setTempoIsencaoMensalidade(Integer tempoIsencaoMensalidade) {
		this.tempoIsencaoMensalidade = tempoIsencaoMensalidade;
	}

	public Integer getPrazoCiclo() {
		return prazoCiclo;
	}

	public void setPrazoCiclo(Integer prazoCiclo) {
		this.prazoCiclo = prazoCiclo;
	}

	public Integer getPrazoPagamento() {
		return prazoPagamento;
	}

	public void setPrazoPagamento(Integer prazoPagamento) {
		this.prazoPagamento = prazoPagamento;
	}

	public Integer getPrazoContrato() {
		return prazoContrato;
	}

	public void setPrazoContrato(Integer prazoContrato) {
		this.prazoContrato = prazoContrato;
	}

	public BigDecimal getValorAdesao() {
		return valorAdesao;
	}

	public void setValorAdesao(BigDecimal valorAdesao) {
		this.valorAdesao = valorAdesao;
	}

	public BigDecimal getValorMensalidade() {
		return valorMensalidade;
	}

	public void setValorMensalidade(BigDecimal valorMensalidade) {
		this.valorMensalidade = valorMensalidade;
	}

	public String getLinkContrato() {
		return linkContrato;
	}

	public void setLinkContrato(String linkContrato) {
		this.linkContrato = linkContrato;
	}

	public BigDecimal getCreditoTotal() {
		return creditoTotal;
	}

	public void setCreditoTotal(BigDecimal creditoTotal) {
		this.creditoTotal = creditoTotal;
	}

}
