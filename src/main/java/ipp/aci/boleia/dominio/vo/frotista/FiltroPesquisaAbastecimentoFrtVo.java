package ipp.aci.boleia.dominio.vo.frotista;


import io.swagger.annotations.ApiModelProperty;
import ipp.aci.boleia.util.anotacoes.Numeric;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Filtro de abastecimento para API externa de Frotistas.
 */
public class FiltroPesquisaAbastecimentoFrtVo extends BaseFiltroPesquisaFrtVo {

    @ApiModelProperty(required = true, value = "${docs.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo.identificador}")
    private Long identificador;

    @ApiModelProperty(required = true, value = "${docs.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo.dataInicial}")
    private Date dataInicial;

    @ApiModelProperty(required = true, value = "${docs.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo.dataFinal}")
    private Date dataFinal;

    @Min(0)
    @Max(value = 99999999999999L)
    @ApiModelProperty(value = "${docs.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo.cnpjRevenda}",
            allowableValues = "range[0,99999999999999]")
    private Long cnpjRevenda;

    @ApiModelProperty(value = "${docs.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo.postoInterno}")
    private Boolean postoInterno;

    @Size(max = 8)
    @ApiModelProperty(value = "${docs.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo.placaVeiculo}")
    private String placaVeiculo;

    @Min(value = 1)
    @Max(value = 2)
    @ApiModelProperty(value = "${docs.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo.tipoVeiculo}",
            allowableValues = "range[1,2]")
    private Long tipoVeiculo;


    @Min(value = 1)
    @Max(value = 14)
    @ApiModelProperty(value = "${docs.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo.subtipoVeiculo}",
            allowableValues = "range[1,14]")
    private Long subtipoVeiculo;


    @Min(0)
    @Max(value = 99999999999L)
    @ApiModelProperty(value = "${docs.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo.cpfMotorista}",
            allowableValues = "range[0,99999999999]")
    private Long cpfMotorista;

    @Min(0)
    @Max(value = 99999999999999L)
    @ApiModelProperty(value = "${docs.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo.cnpjUnidade}",
            allowableValues = "range[0,99999999999999]")
    private Long cnpjUnidade;

    @Min(0)
    @Max(value = 99999999999999L)
    @ApiModelProperty(value = "${docs.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo.cnpjEmpresaAgregada}",
            allowableValues = "range[0,99999999999999]")
    private Long cnpjEmpresaAgregada;

    @Size(max = 9)
    @Numeric
    @ApiModelProperty(value = "${docs.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo.numeroNotaFiscal}")
    private String numeroNotaFiscal;

    @Min(value = 0)
    @Max(value = 2)
    @ApiModelProperty(value = "${docs.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo.statusEmissaoNotaFiscal}",
            allowableValues = "range[0,2]")
    private Integer statusEmissaoNotaFiscal;

    @Min(value = -1)
    @Max(value = 4)
    @ApiModelProperty(value = "${docs.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo.statusAutorizacaoPagamento}",
            allowableValues = "-1,0,1,4")
    private Integer statusAutorizacaoPagamento;

    @ApiModelProperty(value = "${docs.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo.pagamentoEmContingencia}")
    private Boolean pagamentoEmContingencia;

    @ApiModelProperty(value = "${docs.dominio.vo.frotista.FiltroPesquisaAbastecimentoFrtVo.pagamentoSemEstorno}")
    private Boolean pagamentoSemEstorno;

    /**
     * @return the dataInicial
     */
    public Date getDataInicial() {
        return dataInicial;
    }

    /**
     * @param dataInicial the dataInicial to set
     */
    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    /**
     * @return the dataFinal
     */
    public Date getDataFinal() {
        return dataFinal;
    }

    /**
     * @param dataFinal the dataFinal to set
     */
    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    /**
     * @return the cnpjRevenda
     */
    public Long getCnpjRevenda() {
        return cnpjRevenda;
    }

    /**
     * @param cnpjRevenda the cnpjRevenda to set
     */
    public void setCnpjRevenda(Long cnpjRevenda) {
        this.cnpjRevenda = cnpjRevenda;
    }

    /**
     * @return the postoInterno
     */
    public Boolean getPostoInterno() {
        return postoInterno;
    }

    /**
     * @param postoInterno the postoInterno to set
     */
    public void setPostoInterno(Boolean postoInterno) {
        this.postoInterno = postoInterno;
    }

    /**
     * @return the placaVeiculo
     */
    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    /**
     * @param placaVeiculo the placaVeiculo to set
     */
    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    /**
     * @return the tipoVeiculo
     */
    public Long getTipoVeiculo() {
        return tipoVeiculo;
    }

    /**
     * @param tipoVeiculo the tipoVeiculo to set
     */
    public void setTipoVeiculo(Long tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    /**
     * @return the subtipoVeiculo
     */
    public Long getSubtipoVeiculo() {
        return subtipoVeiculo;
    }

    /**
     * @param subtipoVeiculo the subtipoVeiculo to set
     */
    public void setSubtipoVeiculo(Long subtipoVeiculo) {
        this.subtipoVeiculo = subtipoVeiculo;
    }

    /**
     * @return the cpfMotorista
     */
    public Long getCpfMotorista() {
        return cpfMotorista;
    }

    /**
     * @param cpfMotorista the cpfMotorista to set
     */
    public void setCpfMotorista(Long cpfMotorista) {
        this.cpfMotorista = cpfMotorista;
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

    /**
     * @return the numeroNotaFiscal
     */
    public String getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }

    /**
     * @param numeroNotaFiscal the numeroNotaFiscal to set
     */
    public void setNumeroNotaFiscal(String numeroNotaFiscal) {
        this.numeroNotaFiscal = numeroNotaFiscal;
    }

    /**
     * @return the statusEmissaoNotaFiscal
     */
    public Integer getStatusEmissaoNotaFiscal() {
        return statusEmissaoNotaFiscal;
    }

    /**
     * @param statusEmissaoNotaFiscal the statusEmissaoNotaFiscal to set
     */
    public void setStatusEmissaoNotaFiscal(Integer statusEmissaoNotaFiscal) {
        this.statusEmissaoNotaFiscal = statusEmissaoNotaFiscal;
    }

    /**
     * @return the statusAutorizacaoPagamento
     */
    public Integer getStatusAutorizacaoPagamento() {
        return statusAutorizacaoPagamento;
    }

    /**
     * @param statusAutorizacaoPagamento the statusAutorizacaoPagamento to set
     */
    public void setStatusAutorizacaoPagamento(Integer statusAutorizacaoPagamento) {
        this.statusAutorizacaoPagamento = statusAutorizacaoPagamento;
    }

    /**
     * @return the pagamentoSemEstorno
     */
    public Boolean getPagamentoSemEstorno() {
        return pagamentoSemEstorno;
    }

    /**
     * @param pagamentoSemEstorno the pagamentoSemEstorno to set
     */
    public void setPagamentoSemEstorno(Boolean pagamentoSemEstorno) {
        this.pagamentoSemEstorno = pagamentoSemEstorno;
    }

    /**
     * @return the pagamentoEmContingencia
     */
    public Boolean getPagamentoEmContingencia() {
        return pagamentoEmContingencia;
    }

    /**
     * @param pagamentoEmContingencia the pagamentoEmContingencia to set
     */
    public void setPagamentoEmContingencia(Boolean pagamentoEmContingencia) {
        this.pagamentoEmContingencia = pagamentoEmContingencia;
    }

    public Long getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Long identificador) {
        this.identificador = identificador;
    }
}