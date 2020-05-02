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
  * Representa a tabela de Motor Combustivel
  */
@Audited
@Entity
@Table(name = "MOTOR_COMBUSTIVEL")
public class MotorCombustivel implements IPersistente {

    private static final long serialVersionUID = -3700543742320750572L;

	@Id
	@Column(name = "CD_MOTOR_COMBUSTIVEL")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MOTOR_COMBUSTIVEL")
	@SequenceGenerator(name = "SEQ_MOTOR_COMBUSTIVEL", sequenceName = "SEQ_MOTOR_COMBUSTIVEL", allocationSize = 1)
	private Long id;

	@Column(name = "DS_TIPO_COMBUSTIVEL_MTEC")
	private String tipoCombustivelMtec;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_MOTOR")
	private MotorVeiculo motor;

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

	public String getTipoCombustivelMtec() {
		return tipoCombustivelMtec;
	}

	public void setTipoCombustivelMtec(String tipoCombustivelMtec) {
		this.tipoCombustivelMtec = tipoCombustivelMtec;
	}

	public MotorVeiculo getMotor() {
		return motor;
	}

	public void setMotor(MotorVeiculo motor) {
		this.motor = motor;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}
}
