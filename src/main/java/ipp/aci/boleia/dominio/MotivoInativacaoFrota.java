package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.StatusFrota;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Representa a tabela de Motivo Inativacao Frota
 */
@Entity
@Audited
@Table(name = "MOTIVO_INATIV_FROTA")
public class MotivoInativacaoFrota implements IPersistente {

    private static final long serialVersionUID = -1173019737404734970L;

	@Id
	@Column(name = "CD_MOTIV_INATIV")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MOTIVO_INATIV_FROTA")
	@SequenceGenerator(name = "SEQ_MOTIVO_INATIV_FROTA", sequenceName = "SEQ_MOTIVO_INATIV_FROTA", allocationSize = 1)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_FROTA")
	private Frota frota;

	@NotNull
	@Max(99)
	@Column(name="TP_MOTIV_INATIV")
	private Integer tipoMotivo;

	@NotNull
	@Column(name="DT_INATIV")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInativacao;

	@Size(max=1000)
	@Column(name="DS_MOTIV_INATIV")
	private String descricaoInativacao;

	@Column(name="DT_REATIV")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataReativacao;

	@Size(max=1000)
	@Column(name="DS_MOTIV_REATIV")
	private String descricaoReativacao;

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

	public Integer getTipoMotivo() {
		return tipoMotivo;
	}

	public void setTipoMotivo(Integer tipoMotivo) {
		this.tipoMotivo = tipoMotivo;
	}

	public Date getDataInativacao() {
		return dataInativacao;
	}

	public void setDataInativacao(Date dataInativacao) {
		this.dataInativacao = dataInativacao;
	}

	public String getDescricaoInativacao() {
		return descricaoInativacao;
	}

	public void setDescricaoInativacao(String descricaoInativacao) {
		this.descricaoInativacao = descricaoInativacao;
	}

	public Date getDataReativacao() {
		return dataReativacao;
	}

	public void setDataReativacao(Date dataReativacao) {
		this.dataReativacao = dataReativacao;
	}

	public String getDescricaoReativacao() {
		return descricaoReativacao;
	}

	public void setDescricaoReativacao(String descricaoReativacao) {
		this.descricaoReativacao = descricaoReativacao;
	}

	/**
	 * Verifica se os debitos da frota em questao encontram-se pagos
	 * @return True caso os debitos da frota em questao encontrem-se pagos.
	 */
	@Transient
	public boolean isDebitoPago() {
		return
			getFrota() != null
			&& StatusFrota.ATIVO.getValue().equals(getFrota().getStatus())
			&& getFrota().getFimAtivacaoTemporaria() == null;
	}
}
