package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.FiltroBasePeriodoPaginado;

/**
 * Filtro para pesquisa de transação consolidada
 */
public class FiltroPesquisaExtratoValePedagioVo extends FiltroBasePeriodoPaginado {

    private EntidadeVo frota;

    public EntidadeVo getFrota() {
        return frota;
    }

    public void setFrota(EntidadeVo frota) {
        this.frota = frota;
    }

}