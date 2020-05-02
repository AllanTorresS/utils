package ipp.aci.boleia.dominio;

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
import javax.persistence.Version;

/**
 * Representa a tabela de Modelo Veiculo
 */
@Audited
@Entity
@Table(name = "MODELO_VEICULO")
public class ModeloVeiculo implements IPersistente {

    private static final long serialVersionUID = 8894731644002627046L;

	@Id
	@Column(name = "CD_MODELO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MODELO_VEICULO")
	@SequenceGenerator(name = "SEQ_MODELO_VEICULO", sequenceName = "SEQ_MODELO_VEICULO", allocationSize = 1)
	private Long id;

	@Column(name = "CD_MODELO_MTEC")
	private Long modeloMtec;

	@Column(name = "DS_MODELO")
	private String descricao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_MARCA")
	private MarcaVeiculo marca;

	@Column(name = "CD_TIPO_VEICULO_MTEC")
	private Long tipoVeiculoMtec;

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

	public Long getModeloMtec() {
		return modeloMtec;
	}

	public void setModeloMtec(Long modeloMtec) {
		this.modeloMtec = modeloMtec;
	}

	public MarcaVeiculo getMarca() {
		return marca;
	}

	public void setMarca(MarcaVeiculo marca) {
		this.marca = marca;
	}

	public Long getTipoVeiculoMtec() {
		return tipoVeiculoMtec;
	}

	public void setTipoVeiculoMtec(Long tipoVeiculoMtec) {
		this.tipoVeiculoMtec = tipoVeiculoMtec;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
