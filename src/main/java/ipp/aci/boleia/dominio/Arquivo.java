package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Representa a tabela de Arquivo
 */
@Audited
@Entity
@Table(name = "ARQUIVO")
public class Arquivo implements IPersistente {

	private static final long serialVersionUID = -6210916822120297060L;

	@Id
	@Column(name = "CD_ARQUIVO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ARQUIVO")
	@SequenceGenerator(name = "SEQ_ARQUIVO", sequenceName = "SEQ_ARQUIVO", allocationSize = 1)
	private Long id;

	@Column(name = "CD_S3")
	private String cdS3;

	@Column(name = "DS_MIME_TYPE")
	private String mimeType;

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

	public String getCdS3() {
		return cdS3;
	}

	public void setCdS3(String cdS3) {
		this.cdS3 = cdS3;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}
}
