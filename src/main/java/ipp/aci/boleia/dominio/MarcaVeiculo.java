package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Representa a tabela de Marca Veiculo
 */
@Audited
@Entity
@Table(name = "MARCA_VEICULO")
public class MarcaVeiculo implements IPersistente {

    private static final long serialVersionUID = -3862377394753078009L;

	@Id
	@Column(name = "CD_MARCA")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MARCA_VEICULO")
	@SequenceGenerator(name = "SEQ_MARCA_VEICULO", sequenceName = "SEQ_MARCA_VEICULO", allocationSize = 1)
	private Long id;

	@Column(name = "CD_MARCA_MTEC")
	private Long marcaMtec;

	@Column(name = "DS_MARCA")
	private String descricao;

	@Version
	@Column(name = "NO_VERSAO")
	private Long versao;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Long getMarcaMtec() {
		return marcaMtec;
	}

	public void setMarcaMtec(Long marcaMtec) {
		this.marcaMtec = marcaMtec;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}
}
