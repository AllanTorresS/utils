package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os periodos de vencimento de CNHs
 */
public enum PeriodoVencimentoCnh implements IEnumComLabel<PeriodoVencimentoCnh> {

    VENCIDO(0),
    EM_1_MES(1),
    EM_2_MESES(2),
    EM_3_MESES(3),
    EM_6_MESES(6);

    private final Integer meses;

    /**
     * Construtor
     *
     * @param meses O numero de meses contido no periodo
     */
    PeriodoVencimentoCnh(Integer meses) {
        this.meses = meses;
    }

    /**
     * Retorna o numero de meses contido no periodo
     * @return O numero de meses contido no periodo
     */
    public Integer getMeses() {
        return meses;
    }


}
