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
import javax.validation.constraints.NotNull;

/**
 * Representa a tabela de Taxa do perfil.
 */
@Entity
@Audited
@Table(name = "CREDENCIAMENTO")
public class Credenciamento implements IPersistente {

	private static final long serialVersionUID = -1817940781584511198L;

	@Id
    @Column(name = "CD_CRED")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CRED")
    @SequenceGenerator(name = "SEQ_CRED", sequenceName = "SEQ_CRED", allocationSize = 1)
    private Long id;
	
	@NotNull
    @Column(name = "CD_CNPJ")
    private Long cnpj;
	
	@NotNull
	@Column(name = "CD_CPF")
    private Long cpf;
	
	@NotNull
	@Column(name = "CD_LEAD")
    private String lead;

	@NotNull
	@Column(name = "DS_TOKEN")
    private String tokenJWT;
	
	@Column(name = "ID_FINALIZADO")
    private Boolean finalizado;
	
	@Version
    @Column(name = "NO_VERSAO")
    private Long versao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCnpj() {
		return cnpj;
	}

	public void setCnpj(Long cnpj) {
		this.cnpj = cnpj;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public String getLead() {
		return lead;
	}

	public void setLead(String lead) {
		this.lead = lead;
	}

	public String getTokenJWT() {
		return tokenJWT;
	}

	public void setTokenJWT(String tokenJWT) {
		this.tokenJWT = tokenJWT;
	}

	public Boolean getFinalizado() {
		return finalizado;
	}

	public void setFinalizado(Boolean finalizado) {
		this.finalizado = finalizado;
	}

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}
    
}