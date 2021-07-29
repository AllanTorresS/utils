package ipp.aci.boleia.dominio.vo.beneficios;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;
import ipp.aci.boleia.dominio.pesquisa.comum.FiltroBasePeriodoPaginado;

import java.util.Date;

public class FiltroPesquisaExtratoBeneficiariosVo extends BaseFiltroPaginado {
    private String cpfBeneficiario;
    private FiltroBasePeriodoPaginado dataBeneficio;

    public FiltroPesquisaExtratoBeneficiariosVo() {
    }

    public String getCpfBeneficiario() {
        return cpfBeneficiario;
    }

    public void setCpfBeneficiario(String cpfBeneficiario) {
        this.cpfBeneficiario = cpfBeneficiario;
    }

    public FiltroBasePeriodoPaginado getDataBeneficio() {
        return dataBeneficio;
    }

    public void setDataBeneficio(FiltroBasePeriodoPaginado dataBeneficio) {
        this.dataBeneficio = dataBeneficio;
    }
}
