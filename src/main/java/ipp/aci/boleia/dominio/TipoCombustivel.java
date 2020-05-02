package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.List;

/**
 * Representa a tabela de Tipo de Combustivel
 */
@Audited
@Entity
@Table(name = "TIPO_COMBUSTIVEL")
public class TipoCombustivel implements IPersistente {

    private static final long serialVersionUID = -973828644188222555L;

	@Id
	@Column(name = "CD_TIPO_COMBUSTIVEL")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPO_COMBUSTIVEL")
	@SequenceGenerator(name = "SEQ_TIPO_COMBUSTIVEL", sequenceName = "SEQ_TIPO_COMBUSTIVEL", allocationSize = 1)
	private Long id;

	@Column(name = "DS_TIPO_COMBUSTIVEL")
	private String descricao;

	@Column(name = "DS_TIPO_COMBUSTIVEL_MTEC")
	private String tipoCombustivelMtec;

	@Version
	@Column(name = "NO_VERSAO")
	private Long versao;

	@Column(name = "DS_COR_COMBUSTIVEL")
	private String corCombustivel;

	@NotAudited
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="TIPO_COMB_COMB", joinColumns={@JoinColumn(name="CD_TIPO_COMBUSTIVEL")}, inverseJoinColumns={@JoinColumn(name="CD_COMBUSTIVEL_MOTOR")})
	private List<CombustivelMotor> combustiveisMotor;

	@OneToMany(mappedBy = "tipoCombustivel", fetch = FetchType.LAZY)
	private List<DispositivoMotoristaPedido> dispositivosMotoristaPedido;

	@Column(name = "CD_CONNECTCTA")
	private Long codigoConnectCTA;

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

	public String getTipoCombustivelMtec() {
		return tipoCombustivelMtec;
	}

	public void setTipoCombustivelMtec(String tipoCombustivelMtec) {
		this.tipoCombustivelMtec = tipoCombustivelMtec;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}

	public List<CombustivelMotor> getCombustiveisMotor() {
		return combustiveisMotor;
	}

	public void setCombustiveisMotor(List<CombustivelMotor> combustiveisMotor) {
		this.combustiveisMotor = combustiveisMotor;
	}

	public List<DispositivoMotoristaPedido> getDispositivosMotoristaPedido() {
		return dispositivosMotoristaPedido;
	}

	public void setDispositivosMotoristaPedido(List<DispositivoMotoristaPedido> dispositivosMotoristaPedido) {
		this.dispositivosMotoristaPedido = dispositivosMotoristaPedido;
	}

	public String getCorCombustivel() {
		return corCombustivel;
	}

	public void setCorCombustivel(String corCombustivel) {
		this.corCombustivel = corCombustivel;
	}

	public Long getCodigoConnectCTA() {
		return codigoConnectCTA;
	}

	public void setCodigoConnectCTA(Long codigoConnectCTA) {
		this.codigoConnectCTA = codigoConnectCTA;
	}
}
