package ipp.aci.boleia.dominio;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import org.hibernate.envers.Audited;

/**
 * Representa a tabela de PedidoTag
 */
@Entity
@Audited
@Table(name = "PEDIDO_TAG_PRAZO_ENTREGA")
public class PedidoTagPrazoEntrega implements Serializable {

	private static final long serialVersionUID = -5748495465053128787L;

	@Id
	@Column(name = "SG_UF")
	private String uf;

	@Digits(integer = 12, fraction = 4)
	@Column(name = "VR_FRETE")
	private BigDecimal valorFrete;

	@Column(name = "QT_DIAS_MIN")
	private BigDecimal quantidadeMinimaDias;

	@Column(name = "QT_DIAS_MAX")
	private BigDecimal quantidadeMaximoDias;

	/**
	 * Construtor padrão da entidade.
	 */
	public PedidoTagPrazoEntrega() {

	}

	public PedidoTagPrazoEntrega(String uf, BigDecimal valorFrete, BigDecimal quantidadeMinimaDias,
			BigDecimal quantidadeMaximoDias) {
		super();
		this.uf = uf;
		this.valorFrete = valorFrete;
		this.quantidadeMinimaDias = quantidadeMinimaDias;
		this.quantidadeMaximoDias = quantidadeMaximoDias;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	public BigDecimal getQuantidadeMinimaDias() {
		return quantidadeMinimaDias;
	}

	public void setQuantidadeMinimaDias(BigDecimal quantidadeMinimaDias) {
		this.quantidadeMinimaDias = quantidadeMinimaDias;
	}

	public BigDecimal getQuantidadeMaximoDias() {
		return quantidadeMaximoDias;
	}

	public void setQuantidadeMaximoDias(BigDecimal quantidadeMaximoDias) {
		this.quantidadeMaximoDias = quantidadeMaximoDias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoTagPrazoEntrega other = (PedidoTagPrazoEntrega) obj;
		if (uf == null) {
			if (other.uf != null)
				return false;
		} else if (!uf.equals(other.uf))
			return false;
		return true;
	}

}
