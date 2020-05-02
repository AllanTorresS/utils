package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa para busca parcial de frotas
 */
public class FiltroPesquisaParcialFrotaVo extends BaseFiltroPaginado {

    private String termo;
    private Boolean apenasFrotasHabilitadas;
    private EntidadeVo pontoDeVenda;

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }

    public Boolean getApenasFrotasHabilitadas() {
        return apenasFrotasHabilitadas;
    }

    public void setApenasFrotasHabilitadas(Boolean apenasFrotasHabilitadas) {
        this.apenasFrotasHabilitadas = apenasFrotasHabilitadas;
    }

    public EntidadeVo getPontoDeVenda() {
        return pontoDeVenda;
    }

    public void setPontoDeVenda(EntidadeVo pontoDeVenda) {
        this.pontoDeVenda = pontoDeVenda;
    }
}
