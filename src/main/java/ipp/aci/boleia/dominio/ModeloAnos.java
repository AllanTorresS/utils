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
 * Representa a tabela de Modelo Anos
 */
@Audited
@Entity
@Table(name = "MODELO_ANOS")
public class ModeloAnos implements IPersistente {

    private static final long serialVersionUID = 970206463438304820L;

	@Id
	@Column(name = "CD_MODELO_ANOS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MODELO_ANOS")
	@SequenceGenerator(name = "SEQ_MODELO_ANOS", sequenceName = "SEQ_MODELO_ANOS", allocationSize = 1)
	private Long id;

	@Column(name = "NO_ANO_MODELO")
	private Integer anoModelo;

	@Column(name = "NO_ANO_FABRICACAO")
	private Integer anoFabricacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_MODELO")
	private ModeloVeiculo modelo;

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

	public Integer getAnoModelo() {
		return anoModelo;
	}

	public void setAnoModelo(Integer anoModelo) {
		this.anoModelo = anoModelo;
	}

	public Integer getAnoFabricacao() {
		return anoFabricacao;
	}

	public void setAnoFabricacao(Integer anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}

	public ModeloVeiculo getModelo() {
		return modelo;
	}

	public void setModelo(ModeloVeiculo modelo) {
		this.modelo = modelo;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}
}
