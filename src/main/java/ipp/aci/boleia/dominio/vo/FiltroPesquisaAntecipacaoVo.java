package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.FiltroBasePeriodoPaginado;

/**
 * Filtro de pesquisa de antecipações
 */
public class FiltroPesquisaAntecipacaoVo extends FiltroBasePeriodoPaginado {
    private EnumVo statusAntecipacao;
    private EnumVo statusIntegracao;
    private EntidadeVo pontoDeVenda;

    public EnumVo getStatusAntecipacao() {
        return statusAntecipacao;
    }

    public void setStatusAntecipacao(EnumVo statusAntecipacao) {
        this.statusAntecipacao = statusAntecipacao;
    }

    public EnumVo getStatusIntegracao() {
        return statusIntegracao;
    }

    public void setStatusIntegracao(EnumVo statusIntegracao) {
        this.statusIntegracao = statusIntegracao;
    }

    public EntidadeVo getPontoDeVenda() {
        return pontoDeVenda;
    }

    public void setPontoDeVenda(EntidadeVo pontoDeVenda) {
        this.pontoDeVenda = pontoDeVenda;
    }
}
