
package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.List;

/**
 * Filtro para pesquisa de beneficiários 
 */
public class FiltroPesquisaBeneficiarioVo extends BaseFiltroPaginado {

    private EntidadeVo beneficiario;
    private List<EntidadeVo> beneficios;
    private EnumVo possuiSaldo;

    public EntidadeVo getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(EntidadeVo beneficiario) {
        this.beneficiario = beneficiario;
    }

    public List<EntidadeVo> getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(List<EntidadeVo> beneficios) {
        this.beneficios = beneficios;
    }

    public EnumVo getPossuiSaldo() {
        return possuiSaldo;
    }

    public void setPossuiSaldo(EnumVo possuiSaldo) {
        this.possuiSaldo = possuiSaldo;
    }
}