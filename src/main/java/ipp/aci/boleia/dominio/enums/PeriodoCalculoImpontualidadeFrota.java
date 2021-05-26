package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enum que determina o período que será usado para o cálculo da pontualidade da frota
 */
public enum PeriodoCalculoImpontualidadeFrota implements IEnumComLabel<PeriodoCalculoImpontualidadeFrota> {

    TRIMESTRAL(1),
    ANUAL(2);

    private final Integer value;

    /**
     * Construtor
     * @param value O valor do enum
     */
    PeriodoCalculoImpontualidadeFrota(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
