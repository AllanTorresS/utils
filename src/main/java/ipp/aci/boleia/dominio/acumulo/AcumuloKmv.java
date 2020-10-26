package ipp.aci.boleia.dominio.acumulo;


import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

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
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Representa a tabela de de acumulos de KMV do sistema.
 */

@Entity
@Audited
@Table(name = "ACUMULO_KMV")
public class AcumuloKmv implements IPersistente  {

	private static final long serialVersionUID = -3133116557021017037L;

	@Id
	@Column(name = "CD_ACUMULO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ACUMULO_KMV")
	@SequenceGenerator(name = "SEQ_ACUMULO_KMV", sequenceName = "SEQ_ACUMULO_KMV", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_FROTA")
	private Frota frota;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_AUTORIZACAO_PAGAMENTO")
	private AutorizacaoPagamento autorizacaoPagamento;

	@Column(name = "DT_ENVIO")
	@NotNull
	private Date dataEnvio;

	@Column(name = "VALOR")
	private Integer valorAcumulo;

	@Column(name = "STATUS_ACUMULO")
	private Integer statusAcumulo;

	@Column(name = "TIPO_ACUMULO_KMV")
	private Integer tipoAcumulo;

	@Column(name = "DS_MOTIVO_FALHA")
	private String motivoFalha;

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

	public AutorizacaoPagamento getAutorizacaoPagamento() {
		return autorizacaoPagamento;
	}

	public void setAutorizacaoPagamento(AutorizacaoPagamento autorizacaoPagamento) {
		this.autorizacaoPagamento = autorizacaoPagamento;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public Integer getValorAcumulo() {
		return valorAcumulo;
	}

	public void setValorAcumulo(Integer valorAcumulo) {
		this.valorAcumulo = valorAcumulo;
	}

	public Integer getStatusAcumulo() {
		return statusAcumulo;
	}

	public void setStatusAcumulo(Integer statusAcumulo) {
		this.statusAcumulo = statusAcumulo;
	}

	public Integer getTipoAcumulo() {
		return tipoAcumulo;
	}

	public void setTipoAcumulo(Integer tipoAcumulo) {
		this.tipoAcumulo = tipoAcumulo;
	}

	public String getMotivoFalha() {
		return motivoFalha;
	}

	public void setMotivoFalha(String motivoFalha) {
		this.motivoFalha = motivoFalha;
	}
}
