package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.util.List;

/**
 * Representa a tabela de Combustivel Motor
 */
@Audited
@Entity
@Table(name = "COMBUSTIVEL_MOTOR")
public class CombustivelMotor implements IPersistente {

	private static final String OLEO_DIESEL = "OLEO DIESEL";
	private static final long serialVersionUID = -2203729497290626984L;

	@Id
	@Column(name = "CD_COMBUSTIVEL_MOTOR")
	private Long id;

	@Column(name = "DS_TIPO_COMBUSTIVEL_MTEC")
	private String tipoCombustivelMtec;

	@Column(name = "DS_COMBUSTIVEL_MOTOR")
	private String descricao;

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="TIPO_COMB_COMB", joinColumns={@JoinColumn(name="CD_COMBUSTIVEL_MOTOR")}, inverseJoinColumns={@JoinColumn(name="CD_TIPO_COMBUSTIVEL")})
    private List<TipoCombustivel> tiposCombustivel;

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

	public List<TipoCombustivel> getTiposCombustivel() {
		return tiposCombustivel;
	}

	public void setTiposCombustivel(List<TipoCombustivel> tiposCombustivel) {
		this.tiposCombustivel = tiposCombustivel;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}

	public String getTipoCombustivelMtec() {
		return tipoCombustivelMtec;
	}

	public void setTipoCombustivelMtec(String tipoCombustivelMtec) {
		this.tipoCombustivelMtec = tipoCombustivelMtec;
	}

	/**
	 * Metodo que verifica se o tipoCombustivelMtec eh do tipo Oleo diesel
	 * @return true se o tipo for oleo diesel
	 */
	@Transient
	public boolean isOleoDiesel() {
		return CombustivelMotor.isOleoDiesel(this.tipoCombustivelMtec);
	}

	/**
	 * Metodo estatico que verifica se o tipo combustivel eh do tipo oleo diesel
	 * @param tipoCombustivelMtec o tipo combustivel a ser verificado
	 * @return true se o tipo for oleo diesel
	 */
	@Transient
	public static boolean isOleoDiesel(String tipoCombustivelMtec) {
		return tipoCombustivelMtec != null && tipoCombustivelMtec.equalsIgnoreCase(OLEO_DIESEL);
	}
}
