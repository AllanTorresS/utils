package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.List;

/**
 * Filtro para pesquisa de beneficiários 
 */
public class FiltroPesquisaBeneficiarioVo extends BaseFiltroPaginado {

    private EntidadeVo usuario;
    private List<Long> idsBeneficios;

    public EntidadeVo getUsuario() {
        return usuario;
    }

    public void setUsuario(EntidadeVo usuario) {
        this.usuario = usuario;
    }

    public List<Long> getIdsBeneficios() {
        return idsBeneficios;
    }

    public void setIdsBeneficios(List<Long> idsBeneficios) {
        this.idsBeneficios = idsBeneficios;
    }

}