package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status do pagamento de um ciclo de repasse.
 */
public enum StatusCicloRepasse implements IEnumComLabel<StatusCicloRepasse> {

    PAGO(1),
    EM_ABERTO(0),
    VENCIDO(2);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor do status
     */
    StatusCicloRepasse(Integer value) {
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
     * @param value value
     * @return Enum para o valor
     */
    public static StatusCicloRepasse obterPorValor(Integer value) {
        for(StatusCicloRepasse status : StatusCicloRepasse.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}