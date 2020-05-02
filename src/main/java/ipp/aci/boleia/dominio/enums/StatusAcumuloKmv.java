package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica se pontos do kmv foram acumulados
 */
public enum StatusAcumuloKmv implements IEnumComLabel<StatusAcumuloKmv> {

    ACUMULADO(1),
    PENDENTE(0),
    NAO_ACUMULADO(-1);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusAcumuloKmv(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static StatusAcumuloKmv obterPorValor(Integer value) {
        for(StatusAcumuloKmv status : StatusAcumuloKmv.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
