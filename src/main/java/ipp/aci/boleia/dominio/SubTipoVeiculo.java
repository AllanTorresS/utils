
package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.HodometroHorimetro;
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
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Representa a tabela de Subtipo do Veiculo
 */
@Audited
@Entity
@Table(name = "SUB_TIPO_VEICULO")
public class SubTipoVeiculo implements IPersistente {

    private static final long serialVersionUID = 1132732396114709295L;

	private static final String CLIMATIZADOR = "CLIMATIZADOR";

	@Id
	@Column(name = "CD_SUB_TIPO_VEICULO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SUB_TIPO_VEICULO")
	@SequenceGenerator(name = "SEQ_SUB_TIPO_VEICULO", sequenceName = "SEQ_SUB_TIPO_VEICULO", allocationSize = 1)
	private Long id;

	@Column(name = "DS_SUB_TIPO_VEICULO")
	private String descricao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_TIPO_VEICULO")
	private TipoVeiculo tipoVeiculo;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoVeiculo getTipoVeiculo() {
		return tipoVeiculo;
	}

	public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
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

	/**
	 * Método que retorna a descrição do tipo de Veículo
	 *
	 * @return A descrição
	 */
	public String getTipoVeiculoDescricao() {
		return tipoVeiculo != null ? tipoVeiculo.getDescricao() : null;
	}

	/**
	 * Metodo que determina se o veiculo utiliza horimetro
	 *
	 * @return true se utiliza horimetro, false caso contrario
	 */
	@Transient
	public boolean utilizaHorimetro() {
		return descricao != null && CLIMATIZADOR.equalsIgnoreCase(descricao);
	}

	/**
	 * Meéodo que determina se o veículo utiliza hodômetro ou horímetro
	 *
	 * @return enumerado {@link HodometroHorimetro} indicando a utilização de hodômetro ou horímetro
	 */
	@Transient
	public HodometroHorimetro obterHodometroHorimetro() {
		return utilizaHorimetro() ? HodometroHorimetro.HORIMETRO : HodometroHorimetro.HODOMETRO;
	}

	/**
	 * Monta o nome de apresentacao do SubTipo de veiculo,
	 * @return O nome de apresentacao do SubTipo de Veiulo
	 */
	@Transient
	public String getNomeApresentacao() {
		return getDescricao();
	}
}
