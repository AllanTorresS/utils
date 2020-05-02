package ipp.aci.boleia.dominio.vo.frotista;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.YearMonth;


/**
 * Filtro de pesquisa para Cobranças na APi do Frotista.
 * 
 */
public class FiltroPesquisaCobrancaFrtVo extends BaseFiltroPesquisaFrtVo {

    @NotNull
    @DateTimeFormat(pattern="Y-M")
    @ApiModelProperty(required=true, value="${docs.dominio.vo.frotista.FiltroPesquisaCobrancaFrtVo.mesInicial}", dataType="java.lang.String")
    private YearMonth mesInicial;

    @NotNull
    @DateTimeFormat(pattern="Y-M")
    @ApiModelProperty(required=true, value="${docs.dominio.vo.frotista.FiltroPesquisaCobrancaFrtVo.mesFinal}", dataType="java.lang.String")
    private YearMonth mesFinal;

    @Min(0)
    @Max(2)
	@ApiModelProperty(value="${docs.dominio.vo.frotista.FiltroPesquisaCobrancaFrtVo.statusPagamento}",
    allowableValues="range[0,2]")
    private Integer statusPagamento;

    /**
     * @return O mês inicial
     */
    public YearMonth getMesInicial() {
        return mesInicial;
    }

    /**
     * @param mesInicial O mes inicial
     */
    public void setMesInicial(YearMonth mesInicial) {
        this.mesInicial = mesInicial;
    }

    /**
     * @return O mês final
     */
    public YearMonth getMesFinal() {
        return mesFinal;
    }

    /**
     * @param mesFinal O mês final
     */
    public void setMesFinal(YearMonth mesFinal) {
        this.mesFinal = mesFinal;
    }

    /**
     * @return O status de pagamento
     */
    public Integer getStatusPagamento() {
        return statusPagamento;
    }

    /**
     * @param statusPagamento O status de pagamento.
     */
    public void setStatusPagamento(Integer statusPagamento) {
        this.statusPagamento = statusPagamento;
    }


}