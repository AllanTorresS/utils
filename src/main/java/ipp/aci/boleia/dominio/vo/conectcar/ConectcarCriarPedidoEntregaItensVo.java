package ipp.aci.boleia.dominio.vo.conectcar;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConectcarCriarPedidoEntregaItensVo {

	@JsonProperty("Item")
	private String item;

	@JsonProperty("Quantidade")
	private Integer quantidade;

	@JsonProperty("ValorUnitario")
	private BigDecimal valorUnitario;

	public ConectcarCriarPedidoEntregaItensVo() {

	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

}
