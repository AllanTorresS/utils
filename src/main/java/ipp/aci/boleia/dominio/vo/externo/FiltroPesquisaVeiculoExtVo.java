package ipp.aci.boleia.dominio.vo.externo;


import com.fasterxml.jackson.annotation.JsonClassDescription;
import io.swagger.annotations.ApiModelProperty;
import ipp.aci.boleia.dominio.vo.frotista.FiltroPesquisaVeiculoFrtVo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

/**
 * Filtro de Veículo para APIs externas. *
 */

@JsonClassDescription(value="Filtro de pesquisa para veículos.")
public class FiltroPesquisaVeiculoExtVo extends FiltroPesquisaVeiculoFrtVo {

    @ApiModelProperty(value = "${docs.visao.externo.VeiculoExtController.pesquisar.dataUltimaAtualizacao}")
    private Date dataUltimaAtualizacao;

    @Min(0)
    @Max(value = 99999999999999L)
    @ApiModelProperty(value="${docs.visao.externo.VeiculoExtController.pesquisar.cnpjFrota}",
            allowableValues="range[0,99999999999999]")
    private Long cnpjFrota;

    public FiltroPesquisaVeiculoExtVo() {
        //Construtor default
    }

    public Date getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
    }

    public Long getCnpjFrota() {
        return cnpjFrota;
    }

    public void setCnpjFrota(Long cnpjFrota) {
        this.cnpjFrota = cnpjFrota;
    }
}