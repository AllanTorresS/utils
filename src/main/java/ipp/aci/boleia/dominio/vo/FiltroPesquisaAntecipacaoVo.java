package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.FiltroBasePeriodoPaginado;

/**
 * Filtro de pesquisa de antecipações
 */
public class FiltroPesquisaAntecipacaoVo extends FiltroBasePeriodoPaginado {
    private EnumVo statusAntecipacao;
    private EntidadeVo pontoDeVenda;

    public EnumVo getStatusAntecipacao() {
        return statusAntecipacao;
    }

    public void setStatusAntecipacao(EnumVo statusAntecipacao) {
        this.statusAntecipacao = statusAntecipacao;
    }

    public EntidadeVo getPontoDeVenda() {
        return pontoDeVenda;
    }

    public void setPontoDeVenda(EntidadeVo pontoDeVenda) {
        this.pontoDeVenda = pontoDeVenda;
    }
}
