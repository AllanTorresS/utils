package ipp.aci.boleia.dominio.campanha.parametros;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Representa a tabela de Parâmetro de Campanha
 */

@Audited
@Entity
@Table(name="PARAMETRO_CAMPANHA", schema = "BOLEIA_CAMPANHA_SCHEMA")
public class ParametroCampanha implements IPersistente {

	@Id
	@Column(name = "CD_PARAM")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARAMETRO_CAMPANHA")
	@SequenceGenerator(name = "SEQ_PARAMETRO_CAMPANHA", sequenceName = "SEQ_PARAMETRO_CAMPANHA", allocationSize = 1)
	private Long id;

	@NotNull
	@Column(name = "ID_TIPO")
	private Integer tipo;

	@NotNull
	@Column(name = "ID_CRITERIO")
	private Integer criterio;


	@Column(name="ID_PRIORIDADE")
	private Integer prioridade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getCriterio() {
		return criterio;
	}

	public void setCriterio(Integer criterio) {
		this.criterio = criterio;
	}

	public Integer getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Integer prioridade) {
		this.prioridade = prioridade;
	}
}
