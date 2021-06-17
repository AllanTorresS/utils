package ipp.aci.boleia.dominio.vo;

import java.util.Date;

import ipp.aci.boleia.dominio.pesquisa.comum.FiltroBasePeriodoPaginado;

/**
 * Filtro para pesquisa de transação consolidada
 */
public class FiltroPesquisaExtratoValePedagioVo extends FiltroBasePeriodoPaginado {

    private EntidadeVo frota;
    private Date de;
    private Date ate;

    public EntidadeVo getFrota() {
        return frota;
    }

    public void setFrota(EntidadeVo frota) {
        this.frota = frota;
    }

    public Date getDe() {
        return de;
    }

    public void setDe(Date de) {
        this.de = de;
    }

    public Date getAte() {
        return ate;
    }

    public void setAte(Date ate) {
        this.ate = ate;
    }

}