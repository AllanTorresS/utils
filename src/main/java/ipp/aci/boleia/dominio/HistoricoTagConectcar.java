package ipp.aci.boleia.dominio;

import java.util.Date;
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
import javax.validation.constraints.Size;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

/**
 * Representa a tabela de HistoricoTagConectcar
 */
@Entity
@Table(name = "HISTORICO_TAG_CONECTCAR")
public class HistoricoTagConectcar implements IPersistente {

	private static final long serialVersionUID = -8043835722308868542L;

	@Id
	@Column(name = "CD_HIST_TAG_CONECTCAR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIST_TAG_CONECTCAR")
    @SequenceGenerator(name = "SEQ_HIST_TAG_CONECTCAR", sequenceName = "SEQ_HIST_TAG_CONECTCAR", allocationSize = 1)
	private Long id;
	
	@Size(max = 8)
	@Column(name = "DS_PLACA")
	private String placa;
	
	@Column(name = "CD_TAG_CONECTCAR")
	private Long tag; 
	
	@Column(name = "DT_EXCLUSAO")
	private Date dataExclusao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_FROTA")
	private Frota frota;

	/**
	 * Construtor padrão da entidade.
	 */
	public HistoricoTagConectcar() {}

	/**
	 * Construtor por ID e data de ativação
	 * @param tag Identificador da tag
	 * @param dataExclusao Data de exclusão da tag
	 * @param placa Placa associada a tag
	 * @param frota Frota da tag
	 */
	public HistoricoTagConectcar(Long tag, Date dataExclusao, String placa, Frota frota) {
		this.tag = tag;
		this.dataExclusao = dataExclusao;
		this.placa = placa;
		this.frota = frota;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Long getTag() {
		return tag;
	}

	public void setTag(Long tag) {
		this.tag = tag;
	}

	public Date getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(Date dataExclusao) {
		this.dataExclusao = dataExclusao;
	}

	public Frota getFrota() {
		return frota;
	}

	public void setFrota(Frota frota) {
		this.frota = frota;
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
		HistoricoTagConectcar other = (HistoricoTagConectcar) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}