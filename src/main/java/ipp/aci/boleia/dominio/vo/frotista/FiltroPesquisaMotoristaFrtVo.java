package ipp.aci.boleia.dominio.vo.frotista;


import com.fasterxml.jackson.annotation.JsonClassDescription;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Filtro de Motorista para API externa de Frotistas.
 *
 */

@JsonClassDescription(value="Filtro de pesquisa para motoristas.")
public class FiltroPesquisaMotoristaFrtVo extends BaseFiltroPesquisaFrtVo {

	
	@Min(0)
	@Max(value = 99999999999L)
	@ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaMotoristaFrtVo.cpf}",
		allowableValues="range[0,99999999999]")
    private Long cpf;

	@Size(max=250)
    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaMotoristaFrtVo.nomeOuApelido}")
    private String nomeOuApelido;

	@Min(value=0)
	@Max(value=1)
    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaMotoristaFrtVo.status}")
	private Integer status;

	@Size(max=25)
    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaMotoristaFrtVo.matricula}")
	private String matricula;

	@Min(value=0)
	@Max(value=1)
    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaMotoristaFrtVo.classificacao}")
	private Integer classificacao;

	@Min(value=0)
	@Max(value=6)
    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaMotoristaFrtVo.vencimentoCNH}")
	private Integer vencimentoCNH;

	
	@Min(0)
	@Max(value = 99999999999999L)
    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaMotoristaFrtVo.cnpjUnidade}",
	allowableValues="range[0,99999999999999]")
	private Long cnpjUnidade;

	@Size(max=50)
    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaMotoristaFrtVo.codigoGrupoOperacional}")
	private String codigoGrupoOperacional;

	
	@Min(0)
	@Max(value = 99999999999999L)
    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaMotoristaFrtVo.cnpjEmpresaAgregada}",
	allowableValues="range[0,99999999999999]")
	private Long cnpjEmpresaAgregada;

	/**
	 * @return the cpf
	 */
	public Long getCpf() {
		return cpf;
	}
	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}
	/**
	 * @return the nomeOuApelido
	 */
	public String getNomeOuApelido() {
		return nomeOuApelido;
	}
	/**
	 * @param nomeOuApelido the nomeOuApelido to set
	 */
	public void setNomeOuApelido(String nomeOuApelido) {
		this.nomeOuApelido = nomeOuApelido;
	}
	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}
	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	/**
	 * @return the classificacao
	 */
	public Integer getClassificacao() {
		return classificacao;
	}
	/**
	 * @param classificacao the classificacao to set
	 */
	public void setClassificacao(Integer classificacao) {
		this.classificacao = classificacao;
	}
	/**
	 * @return the vencimentoCNH
	 */
	public Integer getVencimentoCNH() {
		return vencimentoCNH;
	}
	/**
	 * @param vencimentoCNH the vencimentoCNH to set
	 */
	public void setVencimentoCNH(Integer vencimentoCNH) {
		this.vencimentoCNH = vencimentoCNH;
	}
	/**
	 * @return the cnpjUnidade
	 */
	public Long getCnpjUnidade() {
		return cnpjUnidade;
	}
	/**
	 * @param cnpjUnidade the cnpjUnidade to set
	 */
	public void setCnpjUnidade(Long cnpjUnidade) {
		this.cnpjUnidade = cnpjUnidade;
	}
	/**
	 * @return the codigoGrupoOperacional
	 */
	public String getCodigoGrupoOperacional() {
		return codigoGrupoOperacional;
	}
	/**
	 * @param codigoGrupoOperacional the codigoGrupoOperacional to set
	 */
	public void setCodigoGrupoOperacional(String codigoGrupoOperacional) {
		this.codigoGrupoOperacional = codigoGrupoOperacional;
	}
	/**
	 * @return the cnpjEmpresaAgregada
	 */
	public Long getCnpjEmpresaAgregada() {
		return cnpjEmpresaAgregada;
	}
	/**
	 * @param cnpjEmpresaAgregada the cnpjEmpresaAgregada to set
	 */
	public void setCnpjEmpresaAgregada(Long cnpjEmpresaAgregada) {
		this.cnpjEmpresaAgregada = cnpjEmpresaAgregada;
	}

}