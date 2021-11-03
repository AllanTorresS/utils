
package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.List;

/**
 * Filtro para pesquisa de beneficiários 
 */
public class FiltroPesquisaBeneficiarioVo extends BaseFiltroPaginado {

    private EntidadeVo nomeBeneficiario;
    private EntidadeVo cpfBeneficiario;
    private List<EntidadeVo> beneficios;
    private EnumVo possuiSaldo;

    public EntidadeVo getNomeBeneficiario() {
        return nomeBeneficiario;
    }

    public void setNomeBeneficiario(EntidadeVo nomeBeneficiario) {
        this.nomeBeneficiario = nomeBeneficiario;
    }

    public EntidadeVo getCpfBeneficiario() {
        return cpfBeneficiario;
    }

    public void setCpfBeneficiario(EntidadeVo cpfBeneficiario) {
        this.cpfBeneficiario = cpfBeneficiario;
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