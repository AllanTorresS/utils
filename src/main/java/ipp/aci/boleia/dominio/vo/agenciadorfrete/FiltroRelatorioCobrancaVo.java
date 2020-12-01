package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import ipp.aci.boleia.dominio.pesquisa.comum.FiltroBasePeriodoPaginado;

/**
 * Filtro de relatorio de Cobrança de Agenciador de Frete
 */
public class FiltroRelatorioCobrancaVo extends FiltroBasePeriodoPaginado {

    private String deMesAno;
    private String ateMesAno;

    public FiltroRelatorioCobrancaVo () {
        //Construtor default
    }

    public String getDeMesAno() {
        return deMesAno;
    }

    public void setDeMesAno(String deMesAno) {
        this.deMesAno = deMesAno;
    }

    public String getAteMesAno() {
        return ateMesAno;
    }

    public void setAteMesAno(String ateMesAno) {
        this.ateMesAno = ateMesAno;
    }
}
