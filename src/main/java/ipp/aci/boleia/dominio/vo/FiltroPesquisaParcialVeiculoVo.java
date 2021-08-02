package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa para busca parcial de frotas
 */
public class FiltroPesquisaParcialVeiculoVo extends BaseFiltroPaginado {

    private String termo;
    private Boolean apenasClimatizador;
    private Boolean apenasHabilitadoAbastecerDuasPlacas;

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) { this.termo = termo; }

    public Boolean getApenasClimatizador() {
        return apenasClimatizador;
    }

    public void setApenasClimatizador(Boolean apenasClimatizador) {
        this.apenasClimatizador = apenasClimatizador;
    }

    public Boolean getApenasHabilitadoAbastecerDuasPlacas() {
        return apenasHabilitadoAbastecerDuasPlacas;
    }

    public void setApenasHabilitadoAbastecerDuasPlacas(Boolean apenasHabilitadoAbastecerDuasPlacas) {
        this.apenasHabilitadoAbastecerDuasPlacas = apenasHabilitadoAbastecerDuasPlacas;
    }
}