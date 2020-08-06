package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status de uma chave de API de Sistemas Externos.
 */
public enum StatusTokenModuloInterno implements IEnumComLabel<StatusTokenModuloInterno> {

    BLOQUEADA(0),
    ATIVA(1);

    public static final String DECODE_FORMULA = "DECODE(ID_STATUS, 0, 'BLOQ', 1, 'AT')";

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor da enumeracao
     */
    StatusTokenModuloInterno(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor da enumeracao
     * @return O valor da enumeracao
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
    public static StatusTokenModuloInterno obterPorValor(Integer value) {
        for (StatusTokenModuloInterno status : StatusTokenModuloInterno.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
