package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.util.Collections;
import java.util.List;

/**
 * Representa a tabela de Grupo Operacional
 */
@Audited
@Entity
@Table(name = "GRUPO_OPERACIONAL")
public class GrupoOperacional implements IPersistente, IExclusaoLogica, IPertenceFrota {

    private static final long serialVersionUID = 7098029541255870216L;

	@Id
	@Column(name = "CD_GRUPO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GRUPO_OPERACIONAL")
	@SequenceGenerator(name = "SEQ_GRUPO_OPERACIONAL", sequenceName = "SEQ_GRUPO_OPERACIONAL", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_FROTA")
	private Frota frota;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "grupo")
	private List<Motorista> motorista;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "grupo")
	private List<Veiculo> veiculo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "grupoOperacional")
	private List<Usuario> usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_UNIDADE")
	private Unidade unidade;

	@Column(name = "CD_CODIGO_CC")
	private String codigoCC;

	@Column(name = "DS_GRUPO")
	private String nome;

	@Column(name = "ID_EXCLUIDO")
	private Boolean excluido;

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

	public Frota getFrota() {
		return frota;
	}

	public void setFrota(Frota frota) {
		this.frota = frota;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public String getCodigoCC() {
		return codigoCC;
	}

	public void setCodigoCC(String codigoCC) {
		this.codigoCC = codigoCC;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public Boolean getExcluido() {
		return excluido;
	}

	@Override
	public void setExcluido(Boolean excluido) {
		this.excluido = excluido;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}

	public List<Motorista> getMotorista() {
		return motorista;
	}

	public void setMotorista(List<Motorista> motorista) {
		this.motorista = motorista;
	}

	public List<Veiculo> getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(List<Veiculo> veiculo) {
		this.veiculo = veiculo;
	}

	public List<Usuario> getUsuario() {
		return usuario;
	}

	public void setUsuario(List<Usuario> usuario) {
		this.usuario = usuario;
	}

	@Transient
	@Override
	public List<Frota> getFrotas() {
		return Collections.singletonList(frota);
	}


	/**
	 * Monta o nome de apresentacao de um Grupo Operacional,
	 * @return O nome de apresentacao do Grupo Operacional em questão
	 */
	@Transient
	public String getNomeApresentacao() {
		String nomeGrupo = getNome();
		String nomeFrota = getFrota().getNomeFantasia();
		nomeGrupo += " - ";
		return nomeGrupo + nomeFrota;
	}
}



