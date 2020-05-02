package ipp.aci.boleia.dominio.vo.frotista;


import com.fasterxml.jackson.annotation.JsonClassDescription;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * Filtro de Veículo para API externa de Frotistas.
 *
 */

@JsonClassDescription(value="Filtro de pesquisa para veículos.")
public class FiltroPesquisaVeiculoFrtVo extends BaseFiltroPesquisaFrtVo {

    @Size(max=8)
    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaVeiculoFrtVo.placa}")
    private String placa;

    @Min(value=0)
    @Max(value=1)
    @ApiModelProperty(
            value="${docs.dominio.vo.frotista.FiltroPesquisaVeiculoFrtVo.status}",
            allowableValues="range[0,1]")
    private Integer status;

    @Min(value=1)
    @Max(value=2)
    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaVeiculoFrtVo.tipo}",
            allowableValues="range[1,2]")
    private Long tipo;

    @Min(value=1)
    @Max(value=14)
    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaVeiculoFrtVo.subtipo}",
            allowableValues="range[1,14]")
    private Long subtipo;

    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaVeiculoFrtVo.marca}")
    private Long marca;

    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaVeiculoFrtVo.modelo}")
    private Long modelo;

    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaVeiculoFrtVo.anoModelo}")
    private Integer anoModelo;

    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaVeiculoFrtVo.anoFabricacao}")
    private Integer anoFabricacao;

    @Min(value=1)
    @Max(value=5)
    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaVeiculoFrtVo.tipoCombustivel}",
            allowableValues="range[1,10]")
    private Long tipoCombustivel;


    @Min(0)
    @Max(1)
    @ApiModelProperty(
            value="${docs.dominio.vo.frotista.FiltroPesquisaVeiculoFrtVo.classificacao}",
            allowableValues="range[0,1]")
    private Integer classificacao;

    @Min(0)
    @Max(value = 99999999999999L)
    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaVeiculoFrtVo.cnpjUnidade}",
            allowableValues="range[0,99999999999999]")
    private Long cnpjUnidade;

    @Size(max=50)
    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaVeiculoFrtVo.codigoGrupoOperacional}")
    private String codigoGrupoOperacional;

    @Min(0)
    @Max(value = 99999999999999L)
    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaVeiculoFrtVo.cnpjEmpresaAgregada}",
            allowableValues="range[0,99999999999999]")
    private Long cnpjEmpresaAgregada;

    /**
     * @return the placa
     */
    public String getPlaca() {
        return placa;
    }
    /**
     * @param placa the placa to set
     */
    public void setPlaca(String placa) {
        this.placa = placa;
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
     * @return the tipo
     */
    public Long getTipo() {
        return tipo;
    }
    /**
     * @param tipo the tipo to set
     */
    public void setTipo(Long tipo) {
        this.tipo = tipo;
    }
    /**
     * @return the subtipo
     */
    public Long getSubtipo() {
        return subtipo;
    }
    /**
     * @param subtipo the subtipo to set
     */
    public void setSubtipo(Long subtipo) {
        this.subtipo = subtipo;
    }
    /**
     * @return the marca
     */
    public Long getMarca() {
        return marca;
    }
    /**
     * @param marca the marca to set
     */
    public void setMarca(Long marca) {
        this.marca = marca;
    }
    /**
     * @return the modelo
     */
    public Long getModelo() {
        return modelo;
    }
    /**
     * @param modelo the modelo to set
     */
    public void setModelo(Long modelo) {
        this.modelo = modelo;
    }
    /**
     * @return the anoModelo
     */
    public Integer getAnoModelo() {
        return anoModelo;
    }
    /**
     * @param anoModelo the anoModelo to set
     */
    public void setAnoModelo(Integer anoModelo) {
        this.anoModelo = anoModelo;
    }
    /**
     * @return the anoFabricacao
     */
    public Integer getAnoFabricacao() {
        return anoFabricacao;
    }
    /**
     * @param anoFabricacao the anoFabricacao to set
     */
    public void setAnoFabricacao(Integer anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }
    /**
     * @return the tipoCombustivel
     */
    public Long getTipoCombustivel() {
        return tipoCombustivel;
    }
    /**
     * @param tipoCombustivel the tipoCombustivel to set
     */
    public void setTipoCombustivel(Long tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
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