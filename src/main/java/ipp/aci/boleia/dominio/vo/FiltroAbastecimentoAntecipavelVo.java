package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.List;

/**
 * Filtro para pesquisa de abastecimentos antecipáveis
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FiltroAbastecimentoAntecipavelVo extends FiltroPesquisaDetalheCicloVo {

    private List<Long> idsPontoVenda;
    private Date dataVencimento;

    /**
     * Serialização JSON
     */
    public FiltroAbastecimentoAntecipavelVo() {}

    public List<Long> getIdsPontoVenda() {
        return idsPontoVenda;
    }

    public void setIdsPontoVenda(List<Long> idsPontoVenda) {
        this.idsPontoVenda = idsPontoVenda;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }
}
