package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa para busca parcial de frotas
 */
public class FiltroPesquisaParcialVeiculoVo extends BaseFiltroPaginado {

    private String termo;

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) { this.termo = termo; }
}