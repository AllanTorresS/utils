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
 * Representa a tabela de Motor Veiculo
 */
@Audited
@Entity
@Table(name = "MOTOR_VEICULO")
public class MotorVeiculo implements IPersistente {

    private static final long serialVersionUID = -6880325437521273416L;

	@Id
	@Column(name = "CD_MOTOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MOTOR_VEICULO")
	@SequenceGenerator(name = "SEQ_MOTOR_VEICULO", sequenceName = "SEQ_MOTOR_VEICULO", allocationSize = 1)
	private Long id;

	@Column(name = "CD_MOTOR_MTEC")
	private Long motorMtec;

	@Column(name = "DS_MOTOR")
	private String descricao;

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

	public Long getMotorMtec() {
		return motorMtec;
	}

	public void setMotorMtec(Long motorMtec) {
		this.motorMtec = motorMtec;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
