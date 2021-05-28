package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.List;

/**
 * Filtro para pesquisa de beneficiários 
 */
public class FiltroPesquisaBeneficiarioVo extends BaseFiltroPaginado {

    private EntidadeVo beneficiario;
    private List<Long> idsBeneficios;

    public EntidadeVo getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(EntidadeVo beneficiario) {
        this.beneficiario = beneficiario;
    }

    public List<Long> getIdsBeneficios() {
        return idsBeneficios;
    }

    public void setIdsBeneficios(List<Long> idsBeneficios) {
        this.idsBeneficios = idsBeneficios;
    }

}