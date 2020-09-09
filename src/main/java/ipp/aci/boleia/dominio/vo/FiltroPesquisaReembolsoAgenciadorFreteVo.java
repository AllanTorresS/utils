package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa o reembolso do agenciador de frete
 */
public class FiltroPesquisaReembolsoAgenciadorFreteVo extends BaseFiltroPaginado {
    private String de;
    private String ate;
    private EnumVo tipoEntidade;

    public FiltroPesquisaReembolsoAgenciadorFreteVo() {
        //Construtor default
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getAte() {
        return ate;
    }

    public void setAte(String ate) {
        this.ate = ate;
    }

    public EnumVo getTipoEntidade() {
        return tipoEntidade;
    }

    public void setTipoEntidade(EnumVo tipoEntidade) {
        this.tipoEntidade = tipoEntidade;
    }
}
