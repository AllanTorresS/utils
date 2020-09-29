package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa para busca parcial de frotas
 */
public class FiltroPesquisaParcialVeiculoVo extends BaseFiltroPaginado {

    private String identificadorInterno;




    public String getIdentificadorInterno() {
        return identificadorInterno;
    }

    public void setIdentificadorInterno(String identificadorInterno) {
        this.identificadorInterno = identificadorInterno;
    }
}
