package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO com informações complementares de uma solicitacao de um lead ao sales force.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class SolicitacaoLeadExtRegistroVo {

	@JsonProperty("Name")
	private String name;

	/**
	 * Construtor padrão da classe.
	 */
	public SolicitacaoLeadExtRegistroVo() {
	}

	public SolicitacaoLeadExtRegistroVo(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
