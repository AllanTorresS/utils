package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.PedidoTag;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Classe com informações referentes à um pedido de tag.
 */
public class PedidoTagVo {

	private Long id;
	private BigDecimal quantidade;
	private BigDecimal valorTotal;
	private BigDecimal valorFrete;
	private BigDecimal valorUnitario;
	private BigDecimal quantidadeMinDiasFrete;
	private BigDecimal quantidadeMaxDiasFrete;
	private Date dataPedido;

	public PedidoTagVo(PedidoTag entidade) {
		this.id = entidade.getId();
		this.quantidade = entidade.getQuantidadeTag();
		this.valorFrete = entidade.getValorFrete();
		this.valorTotal = entidade.getValorTotal();
		this.valorUnitario = entidade.getValorUnitario();
		this.dataPedido = entidade.getDataPedido();
		this.quantidadeMaxDiasFrete = entidade.getQuantidadeMaxDiasFrete();
		this.quantidadeMinDiasFrete = entidade.getQuantidadeMinDiasFrete();
	}

	public PedidoTagVo() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public BigDecimal getQuantidadeMinDiasFrete() {
		return quantidadeMinDiasFrete;
	}

	public void setQuantidadeMinDiasFrete(BigDecimal quantidadeMinDiasFrete) {
		this.quantidadeMinDiasFrete = quantidadeMinDiasFrete;
	}

	public BigDecimal getQuantidadeMaxDiasFrete() {
		return quantidadeMaxDiasFrete;
	}

	public void setQuantidadeMaxDiasFrete(BigDecimal quantidadeMaxDiasFrete) {
		this.quantidadeMaxDiasFrete = quantidadeMaxDiasFrete;
	}

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

}