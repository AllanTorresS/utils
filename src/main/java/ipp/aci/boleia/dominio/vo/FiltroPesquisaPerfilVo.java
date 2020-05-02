package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Classe com informacoes relacionadas ao filtro de pesquisa por perfil
 */
public class FiltroPesquisaPerfilVo extends BaseFiltroPaginado {

    private EntidadeVo tipoPerfil;

    private Boolean gestor;

    private Boolean template;

    private Long idFrota;

    private Long idRede;

    public Boolean getTemplate() {
        return template;
    }

    public void setTemplate(Boolean template) {
        this.template = template;
    }

    public EntidadeVo getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(EntidadeVo tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public Boolean getGestor() {
        return gestor;
    }

    public void setGestor(Boolean gestor) {
        this.gestor = gestor;
    }

    public Long getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(Long idFrota) {
        this.idFrota = idFrota;
    }

    public Long getIdRede() {
        return idRede;
    }

    public void setIdRede(Long idRede) {
        this.idRede = idRede;
    }
}
