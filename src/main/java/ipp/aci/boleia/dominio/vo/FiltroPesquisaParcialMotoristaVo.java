package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa para busca parcial de motoristas
 */
public class FiltroPesquisaParcialMotoristaVo extends BaseFiltroPaginado {

    private String termo;
    private Boolean apenasMotoristasHabilitados;

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }

    public Boolean getApenasMotoristasHabilitados() {
        return apenasMotoristasHabilitados;
    }

    public void setApenasMotoristasHabilitados(Boolean apenasMotoristasHabilitados) {
        this.apenasMotoristasHabilitados = apenasMotoristasHabilitados;
    }
}
