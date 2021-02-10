package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.FiltroBasePeriodoPaginado;

/**
 * Filtro para pesquisa dos ultimos abastecimentos
 */
public class FiltroPesquisaUltimosAbastecimentosVo extends FiltroBasePeriodoPaginado {

    private String placa;
    private Long pontoDeVenda;
    private Long empresa;
    private Integer tipo;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Long getPontoDeVenda() {
        return pontoDeVenda;
    }

    public void setPontoDeVenda(Long pontoDeVenda) {
        this.pontoDeVenda = pontoDeVenda;
    }

    public Long getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Long empresa) {
        this.empresa = empresa;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }
}
