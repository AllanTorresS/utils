package ipp.aci.boleia.dominio.vo.tarifador;

import ipp.aci.boleia.dominio.pesquisa.comum.FiltroBasePeriodoPaginado;
import ipp.aci.boleia.dominio.vo.EntidadeVo;

/**
 * Objeto que representa filtro da tela de exportação de relatório da Recolha Manual de NF
 */
public class FiltroPesquisaRelatorioTarifadorVo extends FiltroBasePeriodoPaginado {

    private EntidadeVo frota;

    public EntidadeVo getFrota() {
        return frota;
    }

    public void setFrota(EntidadeVo frota) {
        this.frota = frota;
    }
}