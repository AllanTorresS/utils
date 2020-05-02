package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Representa a tabela de Versao do Token JWT
 */
@Entity
@Table(name = "VERSAO_TOKEN_JWT")
public class VersaoTokenJwt implements IPersistente {

    private static final long serialVersionUID = -1441326598362571729L;

	@Id
	@Column(name = "CD_VERSAO_TOKEN_JWT")
	private Long id;

	@Column(name = "CD_TIPO_TOKEN")
	private String tipo;

	@Column(name = "VA_VERSAO_CORRENTE")
	private Long versaoCorrente;

	@Column(name = "DT_VERSAO_ANTERIOR_VALIDA_ATE")
	private Date versaoAnteriorValidaAte;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getVersaoCorrente() {
		return versaoCorrente;
	}

	public void setVersaoCorrente(Long versaoCorrente) {
		this.versaoCorrente = versaoCorrente;
	}

	public Date getVersaoAnteriorValidaAte() {
		return versaoAnteriorValidaAte;
	}

	public void setVersaoAnteriorValidaAte(Date versaoAnteriorValidaAte) {
		this.versaoAnteriorValidaAte = versaoAnteriorValidaAte;
	}
}
