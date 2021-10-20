package ipp.aci.boleia.dominio.vo.beneficios;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;
import ipp.aci.boleia.dominio.pesquisa.comum.FiltroBasePeriodoPaginado;

/**
 * Vo com as informações usadas para buscar as operações relacionadas à conta de um beneficiário.
 *
 * @author luiz.viana
 */
public class FiltroPesquisaExtratoBeneficiariosVo extends FiltroBasePeriodoPaginado {
    private String cpfBeneficiario;

    public FiltroPesquisaExtratoBeneficiariosVo() {
    }

    public String getCpfBeneficiario() {
        return cpfBeneficiario;
    }

    public void setCpfBeneficiario(String cpfBeneficiario) {
        this.cpfBeneficiario = cpfBeneficiario;
    }
}
