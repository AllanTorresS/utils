package ipp.aci.boleia.dominio;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import org.hibernate.envers.Audited;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

/**
 * Representa a tabela de Condicoes Comerciais
 */
@Entity
@Audited
@Table(name = "CONDICOES_COMERCIAIS")
public class CondicoesComerciais implements IPersistente {

	private static final long serialVersionUID = 6138727610068518914L;

	@Id
	@Column(name = "CD_CONDICOES_COMERCIAIS")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_FROTA")
	private Frota frota;

	@Digits(integer = 12, fraction = 4)
	@Column(name = "VR_TOTAL_CREDITO")
	private BigDecimal valorTotalCredito;

	@Digits(integer = 12, fraction = 4)
	@Column(name = "VR_ADESAO")
	private BigDecimal valorAdesao;

	@Column(name = "QT_TEMPO_ISENCAO_MENSALIDADE")
	private BigDecimal quantidadeTempoIsencaoMensalidade;

	@Column(name = "QT_PRAZO_CICLO")
	private BigDecimal quantidadePrazoCiclo;

	@Column(name = "NO_VERSAO")
	private Integer versao;

	/**
	 * Construtor padrão da entidade.
	 */
	public CondicoesComerciais() {

	}

	public CondicoesComerciais(Long id, Frota frota, BigDecimal valorTotalCredito, BigDecimal valorAdesao,
			BigDecimal quantidadeTempoIsencaoMensalidade, BigDecimal quantidadePrazoCiclo, Integer versao) {
		super();
		this.id = id;
		this.frota = frota;
		this.valorTotalCredito = valorTotalCredito;
		this.valorAdesao = valorAdesao;
		this.quantidadeTempoIsencaoMensalidade = quantidadeTempoIsencaoMensalidade;
		this.quantidadePrazoCiclo = quantidadePrazoCiclo;
		this.versao = versao;
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

	public BigDecimal getValorTotalCredito() {
		return valorTotalCredito;
	}

	public void setValorTotalCredito(BigDecimal valorTotalCredito) {
		this.valorTotalCredito = valorTotalCredito;
	}

	public BigDecimal getValorAdesao() {
		return valorAdesao;
	}

	public void setValorAdesao(BigDecimal valorAdesao) {
		this.valorAdesao = valorAdesao;
	}

	public BigDecimal getQuantidadeTempoIsencaoMensalidade() {
		return quantidadeTempoIsencaoMensalidade;
	}

	public void setQuantidadeTempoIsencaoMensalidade(BigDecimal quantidadeTempoIsencaoMensalidade) {
		this.quantidadeTempoIsencaoMensalidade = quantidadeTempoIsencaoMensalidade;
	}

	public BigDecimal getQuantidadePrazoCiclo() {
		return quantidadePrazoCiclo;
	}

	public void setQuantidadePrazoCiclo(BigDecimal quantidadePrazoCiclo) {
		this.quantidadePrazoCiclo = quantidadePrazoCiclo;
	}

	public Integer getVersao() {
		return versao;
	}

	public void setVersao(Integer versao) {
		this.versao = versao;
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
		CondicoesComerciais other = (CondicoesComerciais) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
