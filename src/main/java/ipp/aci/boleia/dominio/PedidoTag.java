package ipp.aci.boleia.dominio;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

/**
 * Representa a tabela de PedidoTag
 */
@Entity
@Audited
@Table(name = "PEDIDO_TAG")
public class PedidoTag implements IPersistente {

	private static final long serialVersionUID = -8043835722308868542L;

	@Id
	@Column(name = "CD_PEDIDO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PEDIDO_TAG")
	@SequenceGenerator(name = "SEQ_PEDIDO_TAG", sequenceName = "SEQ_PEDIDO_TAG", allocationSize = 1)
	private Long id;

	@Column(name = "NO_DOC_PEDIDO_CONECTCAR")
	private Integer numeroPedidoConectcar;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_FROTA")
	private Frota frota;

	@Column(name = "ID_STATUS")
	private Integer status;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_COBRANCA")
	private CobrancaConectcar cobranca;

	@Digits(integer = 12, fraction = 4)
	@Column(name = "VR_TOTAL")
	private BigDecimal valorTotal;

	@Digits(integer = 12, fraction = 4)
	@Column(name = "VR_UNIT")
	private BigDecimal valorUnitario;

	@Digits(integer = 12, fraction = 4)
	@Column(name = "VR_FRETE")
	private BigDecimal valorFrete;

	@Column(name = "QT_TAG")
	private BigDecimal quantidadeTag;

	@Column(name = "DT_PEDIDO")
	private Date dataPedido;

	@Column(name = "QT_DIAS_MIN_FRETE")
	private BigDecimal quantidadeMinDiasFrete;

	@Column(name = "QT_DIAS_MAX_FRETE")
	private BigDecimal quantidadeMaxDiasFrete;

	@Size(max = 9)
	@Column(name = "NO_CEP")
	private String cep;

	@Size(max = 150)
	@Column(name = "DS_END")
	private String logradouroEndereco;

	@Min(0)
	@Max(99999999L)
	@Column(name = "NO_END")
	private Integer numeroEndereco;

	@Size(max = 20)
	@Column(name = "DS_COMPL")
	private String complementoEndereco;

	@Size(max = 150)
	@Column(name = "NM_BAIRRO")
	private String bairro;

	@Size(max = 50)
	@Column(name = "NM_MUNIC")
	private String municipio;

	@Column(name = "SG_UF")
	private String uf;
	
	@Column(name = "DS_STATUS_RASTREIO")
	private String statusRastreio;

	/**
	 * Construtor padrão da entidade.
	 */
	public PedidoTag() {

	}

	public PedidoTag(Long id, Frota frota, Integer status, BigDecimal valorTotal, BigDecimal valorUnitario,
			BigDecimal valorFrete, BigDecimal quantidadeTag, Date dataPedido, BigDecimal quantidadeMinDiasFrete,
			BigDecimal quantidadeMaxDiasFrete, String cep, String logradouroEndereco, Integer numeroEndereco,
			String complementoEndereco, String bairro, String municipio, String uf) {
		super();
		this.id = id;
		this.frota = frota;
		this.status = status;
		this.valorTotal = valorTotal;
		this.valorUnitario = valorUnitario;
		this.valorFrete = valorFrete;
		this.quantidadeTag = quantidadeTag;
		this.dataPedido = dataPedido;
		this.quantidadeMinDiasFrete = quantidadeMinDiasFrete;
		this.quantidadeMaxDiasFrete = quantidadeMaxDiasFrete;
		this.cep = cep;
		this.logradouroEndereco = logradouroEndereco;
		this.numeroEndereco = numeroEndereco;
		this.complementoEndereco = complementoEndereco;
		this.bairro = bairro;
		this.municipio = municipio;
		this.uf = uf;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Frota getFrota() {
		return frota;
	}

	public void setFrota(Frota frota) {
		this.frota = frota;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public BigDecimal getQuantidadeTag() {
		return quantidadeTag;
	}

	public void setQuantidadeTag(BigDecimal quantidadeTag) {
		this.quantidadeTag = quantidadeTag;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouroEndereco() {
		return logradouroEndereco;
	}

	public void setLogradouroEndereco(String logradouroEndereco) {
		this.logradouroEndereco = logradouroEndereco;
	}

	public Integer getNumeroEndereco() {
		return numeroEndereco;
	}

	public void setNumeroEndereco(Integer numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}

	public String getComplementoEndereco() {
		return complementoEndereco;
	}

	public void setComplementoEndereco(String complementoEndereco) {
		this.complementoEndereco = complementoEndereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	public CobrancaConectcar getCobranca() {
		return cobranca;
	}

	public void setCobranca(CobrancaConectcar cobranca) {
		this.cobranca = cobranca;
	}

	public Integer getNumeroPedidoConectcar() {
		return numeroPedidoConectcar;
	}

	public void setNumeroPedidoConectcar(Integer numeroPedidoConectcar) {
		this.numeroPedidoConectcar = numeroPedidoConectcar;
	}
	
	public String getStatusRastreio() {
		return statusRastreio;
	}

	public void setStatusRastreio(String statusRastreio) {
		this.statusRastreio = statusRastreio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		PedidoTag other = (PedidoTag) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
