package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status dos termos de aceite da frota
 */
public enum StatusTermoFrota implements IEnumComLabel<StatusTermoFrota> {

    ACEITO(1),
    NAO_ACEITO(0);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor do status
     */
    StatusTermoFrota(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor do status
     * @return O valor do status
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static StatusTermoFrota obterPorValor(Integer value) {
        for (StatusTermoFrota status : StatusTermoFrota.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
