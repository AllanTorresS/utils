package ipp.aci.boleia.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.envers.Audited;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

/**
 * Representa a tabela de Transacao Conectcar Consolidada
 */
@Entity
@Audited
@Table(name = "CATEGORIA_CONECTCAR")
public class CategoriaConectcar implements IPersistente {

	private static final long serialVersionUID = 8095939439819340567L;

	@Id
	@Column(name = "CD_CATEG_CONECTCAR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CATEG_CONECTCAR")
	@SequenceGenerator(name = "SEQ_CATEG_CONECTCAR", sequenceName = "SEQ_CATEG_CONECTCAR", allocationSize = 1)
	private Long id;

	@Column(name = "DS_CATEG_CONECTCAR")
	private String descricao;

	@Column(name = "QT_EIXOS")
	private Integer quantidadeEixos;

	@Version
	@Column(name = "NO_VERSAO")
	private Long versao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getQuantidadeEixos() {
		return quantidadeEixos;
	}

	public void setQuantidadeEixos(Integer quantidadeEixos) {
		this.quantidadeEixos = quantidadeEixos;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}

}