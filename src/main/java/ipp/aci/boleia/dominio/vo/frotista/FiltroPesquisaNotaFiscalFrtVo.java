package ipp.aci.boleia.dominio.vo.frotista;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.YearMonth;

/**
 * Filtro de pesquisa para Nota Fiscal no contexto da API de Frotista.
 * 
 */
public class FiltroPesquisaNotaFiscalFrtVo extends BaseFiltroPesquisaFrtVo {

    @NotNull
    @DateTimeFormat(pattern="Y-M")
    @ApiModelProperty(required=true, value="${docs.dominio.vo.frotista.FiltroPesquisaNotaFiscalFrtVo.mesAnoPeriodo}", dataType="java.util.Date")
    private YearMonth mesAnoPeriodo;

    @Min(0)
    @Max(99999999999999L)
    @ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaNotaFiscalFrtVo.cnpjRevenda}",
        allowableValues="range[0,99999999999999]")
    private Long cnpjRevenda;

    @Min(0)
    @Max(2)
    @ApiModelProperty(
        value="${docs.dominio.vo.frotista.FiltroPesquisaNotaFiscalFrtVo.statusEmissaoNotaFiscal}",
        allowableValues="range[0,2]"
    )
    private Integer statusEmissaoNotaFiscal;

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
     * @return the mesAnoPeriodo
     */
    public YearMonth getMesAnoPeriodo() {
        return mesAnoPeriodo;
    }

    /**
     * @param mesAnoPeriodo the mesAnoPeriodo to set
     */
    public void setMesAnoPeriodo(YearMonth mesAnoPeriodo) {
        this.mesAnoPeriodo = mesAnoPeriodo;
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

}