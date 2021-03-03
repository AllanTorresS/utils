package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Digits;

import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa a tabela de Lead
 */
@Entity
@Audited
@Table(name="LEAD")
public class Lead implements IPersistente {

	private static final long serialVersionUID = 6734001856560196834L;

	@Id
    @Column(name = "CD_LEAD")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LEAD")
    @SequenceGenerator(name = "SEQ_LEAD", sequenceName = "SEQ_LEAD", allocationSize = 1)
    private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;
	
	@Column(name = "QT_VEICULO_LEVE")
    private Integer qtdadeVeiculoLeve;
	
	@Column(name = "QT_VEICULO_PESADO")
    private Integer qtdadeVeiculoPesado;
	
	@Column(name = "QT_VEICULO_TAG")
    private Integer qtdadeVeiculoTag;
	
    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_GASTO_MENSAL")
    private BigDecimal valorGastoMensal;
	
	@Version
    @Column(name = "NO_VERSAO")
    private Long versao;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="DT_INCL")
    private Date dataInclusao;
	
	@Column(name = "ID_APROVADO")
    private Integer statusAprovacao;

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="DT_ALTER")
    private Date dataAlteracao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

	public Frota getFrota() {
		return frota;
	}

	public void setFrota(Frota frota) {
		this.frota = frota;
	}

	public Integer getQtdadeVeiculoLeve() {
		return qtdadeVeiculoLeve;
	}

	public void setQtdadeVeiculoLeve(Integer qtdadeVeiculoLeve) {
		this.qtdadeVeiculoLeve = qtdadeVeiculoLeve;
	}

	public Integer getQtdadeVeiculoPesado() {
		return qtdadeVeiculoPesado;
	}

	public void setQtdadeVeiculoPesado(Integer qtdadeVeiculoPesado) {
		this.qtdadeVeiculoPesado = qtdadeVeiculoPesado;
	}

	public Integer getQtdadeVeiculoTag() {
		return qtdadeVeiculoTag;
	}

	public void setQtdadeVeiculoTag(Integer qtdadeVeiculoTag) {
		this.qtdadeVeiculoTag = qtdadeVeiculoTag;
	}

	public BigDecimal getValorGastoMensal() {
		return valorGastoMensal;
	}

	public void setValorGastoMensal(BigDecimal valorGastoMensal) {
		this.valorGastoMensal = valorGastoMensal;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Integer getStatusAprovacao() {
		return statusAprovacao;
	}

	public void setStatusAprovacao(Integer statusAprovacao) {
		this.statusAprovacao = statusAprovacao;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
    
}