package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica se um veículo deve registrar hodometro ou horimetro no aplicativo motorista
 */
public enum HodometroHorimetro implements IEnumComLabel<HodometroHorimetro> {

    HODOMETRO(0),
    HORIMETRO(1);

    public static final String DECODE_FORMULA = "DECODE(ID_STATUS, 1, 'HORIMETRO', 0, 'HODOMETRO')";

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    HodometroHorimetro(Integer value) {
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
    public static HodometroHorimetro obterPorValor(Integer value) {
        for(HodometroHorimetro status : HodometroHorimetro.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
