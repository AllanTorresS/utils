package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.Date;

/**
 * Filtro para pesquisa de documento
 */
public class FiltroPesquisaDocumentoVo extends BaseFiltroPaginado {
    private Date de;
    private Date ate;
    private EnumVo tipo;
    private String versao;
    private EnumVo status;
    private EnumVo tipoPerfil;

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

    public EnumVo getTipo() {
        return tipo;
    }

    public void setTipo(EnumVo tipo) {
        this.tipo = tipo;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public EnumVo getStatus() {
        return status;
    }

    public void setStatus(EnumVo status) {
        this.status = status;
    }

    public EnumVo getTipoPerfil() { return tipoPerfil; }

    public void setTipoPerfil(EnumVo tipoPerfil) { this.tipoPerfil = tipoPerfil; }
}
