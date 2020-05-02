package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status dos avisos de credito
 */
public enum StatusAvisoCredito implements IEnumComLabel<StatusAvisoCredito> {

    ERRO_ENVIO( -1),
    ENVIADO_JDE(1);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusAvisoCredito(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static StatusAvisoCredito obterPorValor(Integer value) {
        for (StatusAvisoCredito status : StatusAvisoCredito.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
