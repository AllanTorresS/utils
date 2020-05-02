package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.util.ConstantesTipoVeiculo;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Representa a tabela de Tipo Veiculo
 */
@Audited
@Entity
@Table(name = "TIPO_VEICULO")
public class TipoVeiculo implements IPersistente {

    private static final long serialVersionUID = 4162019888872310370L;

	@Id
	@Column(name = "CD_TIPO_VEICULO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPO_VEICULO")
	@SequenceGenerator(name = "SEQ_TIPO_VEICULO", sequenceName = "SEQ_TIPO_VEICULO", allocationSize = 1)
	private Long id;

	@Column(name = "DS_TIPO_VEICULO")
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

	@Transient
	public boolean isLeve() {
		return ConstantesTipoVeiculo.LEVE.equalsIgnoreCase(descricao);
	}

	@Transient
	public boolean isPesado() {
		return ConstantesTipoVeiculo.PESADO.equalsIgnoreCase(descricao);
	}

	/**
	 * Monta o nome de apresentacao de um Tipo de veiculo,
	 * @return O nome de apresentacao do Tipo do Veiulo
	 */
	@Transient
	public String getNomeApresentacao() {
		return getDescricao();
	}
}
