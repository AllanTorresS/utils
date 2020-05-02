package ipp.aci.boleia.dominio.vo.frotista;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;

/**
 * Classe base para implementação de filtros de pesquisa para
 * frotistas.
 * 
 */
public abstract class BaseFiltroPesquisaFrtVo {

	@ApiModelProperty(value="${docs.dominio.vo.frotista.BaseFiltroPesquisaFrtVo.pagina}")
	@Min(1)
    private Integer pagina;

	/**
	 * @return the pagina
	 */
	public Integer getPagina() {
		return pagina;
	}

	/**
	 * @param pagina the pagina to set
	 */
	public void setPagina(Integer pagina) {
		this.pagina = pagina;
	}

}