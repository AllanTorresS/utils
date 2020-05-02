package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa para busca parcial de frotas
 */
public class FiltroPesquisaParcialPtovVo extends BaseFiltroPaginado {

    private String termo;
    private Boolean apenasPVsHabilitados;
    private String idRede;
    private String idFrota;
    private String idUsuario;

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }

    public Boolean getApenasPVsHabilitados() {
        return apenasPVsHabilitados;
    }

    public void setApenasPVsHabilitados(Boolean apenasPVsHabilitados) {
        this.apenasPVsHabilitados = apenasPVsHabilitados;
    }

    public String getIdRede() {
        return idRede;
    }

    public void setIdRede(String idRede) {
        this.idRede = idRede;
    }

    public String getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(String idFrota) {
        this.idFrota = idFrota;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
