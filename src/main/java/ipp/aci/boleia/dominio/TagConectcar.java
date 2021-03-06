package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IExclusaoLogicaComData;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de AtivacaoTag
 */
@Entity
@Audited
@Table(name = "TAG_CONECTCAR")
public class TagConectcar implements IPersistente, IExclusaoLogicaComData{

	private static final long serialVersionUID = -8043835722308868542L;

	@Id
	@Column(name = "CD_TAG_CONECTCAR")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_FROTA")
	private Frota frota;

	@Size(max = 8)
	@Column(name = "DS_PLACA")
	private String placa;
	
	@Column(name = "DT_ATIVACAO")
	private Date dataAtivacao;
	
	@Column(name = "DT_BLOQUEIO")
	private Date dataBloqueio;

	@Column(name = "DT_EXCLUSAO")
	private Date dataExclusao;
	
	@NotAudited
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "DS_PLACA", insertable = false, updatable = false, referencedColumnName = "DS_PLACA")
	@Where(clause = "ID_EXCLUIDO = 0 AND ID_STATUS = 1")
	private List<Veiculo> veiculos;

	@NotNull
    @Column(name = "ID_TIPO_UTILIZACAO")
    private Integer tipoUtilizacao;

    @Column(name = "ID_TIPO_BLOQUEIO")
    private String tipoBloqueio;

	/**
	 * Construtor padrão da entidade.
	 */
	public TagConectcar() {}

	/**
	 * Construtor por ID e data de ativação
	 * @param id Identificador da tag
	 * @param dataExclusao Data de exclusão da tag
	 */
	public TagConectcar(Long id, Date dataExclusao) {
		this.id = id;
		this.dataExclusao = dataExclusao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Frota getFrota() {
		return frota;
	}

	public void setFrota(Frota frota) {
		this.frota = frota;
	}
	
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Date getDataAtivacao() {
		return dataAtivacao;
	}

	public void setDataAtivacao(Date dataAtivacao) {
		this.dataAtivacao = dataAtivacao;
	}

	public Date getDataBloqueio() {
		return dataBloqueio;
	}

	public void setDataBloqueio(Date dataBloqueio) {
		this.dataBloqueio = dataBloqueio;
	}

	@Override
	public Boolean getExcluido() {
		return this.dataExclusao != null;
	}

	public Date getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(Date dataExclusao) {
		this.dataExclusao = dataExclusao;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public boolean isAtivo(){
		return dataBloqueio == null;
	}
	
	public boolean isBloqueado(){
		return dataBloqueio != null;
	}
	
	public Integer getStatus() {
		//ativo
		if(isAtivo()){
			return 1;
		}
		
		//bloqueado
		return 0;
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
		TagConectcar other = (TagConectcar) obj;
		if (id == null) {
			return other.id == null;
		} else {
			return id.equals(other.id);
		}
	}

	public Integer getTipoUtilizacao() {
		return tipoUtilizacao;
	}

	public void setTipoUtilizacao(Integer tipoUtilizacao) {
		this.tipoUtilizacao = tipoUtilizacao;
	}

	public String getTipoBloqueio() {
		return tipoBloqueio;
	}

	public void setTipoBloqueio(String tipoBloqueio) {
		this.tipoBloqueio = tipoBloqueio;
	}

}