package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa de sistemas externos de integração.
 */
public class FiltroPesquisaSistemaExternoVo extends BaseFiltroPaginado {

    private EntidadeVo nomeSistema;
    private EnumVo status;


    public EntidadeVo getNomeSistema() {
        return nomeSistema;
    }

    public void setNomeSistema(EntidadeVo nomeSistema) {
        this.nomeSistema = nomeSistema;
    }

    public EnumVo getStatus() {
        return status;
    }

    public void setStatus(EnumVo status) {
        this.status = status;
    }
}
