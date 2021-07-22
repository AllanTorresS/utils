package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.FiltroBasePeriodoPaginado;

/**
 * Filtro de pesquisa de antecipações
 */
public class FiltroPesquisaAntecipacaoVo extends FiltroBasePeriodoPaginado {
    private EnumVo statusAntecipacao;
    private String pontoDeVenda;

    public EnumVo getStatusAntecipacao() {
        return statusAntecipacao;
    }

    public void setStatusAntecipacao(EnumVo statusAntecipacao) {
        this.statusAntecipacao = statusAntecipacao;
    }

    public String getPontoDeVenda() {
        return pontoDeVenda;
    }

    public void setPontoDeVenda(String pontoDeVenda) {
        this.pontoDeVenda = pontoDeVenda;
    }
}
