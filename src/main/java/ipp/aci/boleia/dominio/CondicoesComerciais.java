package ipp.aci.boleia.dominio;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONDICOES_COMERCIAIS")
	@SequenceGenerator(name = "SEQ_CONDICOES_COMERCIAIS", sequenceName = "SEQ_CONDICOES_COMERCIAIS", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_FROTA")
	private Frota frota;

	@Digits(integer = 12, fraction = 4)
	@Column(name = "VR_TOTAL_CREDITO")
	private BigDecimal creditoTotal;

	@Column(name = "QT_PRAZO_CICLO")
	private Integer tamanhoCiclo;

	@Column(name = "QT_PRAZO_PAGAMENTO")
	private Integer prazoPagamento;
	
	@Column(name = "QT_PRAZO_CONTRATO")
	private Integer prazoContrato;

	@Column(name = "DS_LINK_CONTRATO")
	private String linkContrato;

	@Digits(integer = 12, fraction = 4)
	@Column(name = "VR_ADESAO")
	private BigDecimal valorAdesao;

	@Digits(integer = 12, fraction = 4)
	@Column(name = "VR_MENSALIDADE")
	private BigDecimal valorMensalidade;

	@Column(name = "QT_TEMPO_ISENCAO_MENSALIDADE")
	private Integer mesesIsencao;
	
	@Column(name = "NO_DIA_REEMBOLSO")
	private Integer diaReembolso;

	@Column(name = "NO_VERSAO")
	private Integer versao;

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

	public BigDecimal getCreditoTotal() {
		return creditoTotal;
	}

	public void setCreditoTotal(BigDecimal creditoTotal) {
		this.creditoTotal = creditoTotal;
	}

	public Integer getTamanhoCiclo() {
		return tamanhoCiclo;
	}

	public void setTamanhoCiclo(Integer tamanhoCiclo) {
		this.tamanhoCiclo = tamanhoCiclo;
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

	public String getLinkContrato() {
		return linkContrato;
	}

	public void setLinkContrato(String linkContrato) {
		this.linkContrato = linkContrato;
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

	public Integer getMesesIsencao() {
		return mesesIsencao;
	}

	public void setMesesIsencao(Integer mesesIsencao) {
		this.mesesIsencao = mesesIsencao;
	}

	public Integer getDiaReembolso() {
		return diaReembolso;
	}

	public void setDiaReembolso(Integer diaReembolso) {
		this.diaReembolso = diaReembolso;
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