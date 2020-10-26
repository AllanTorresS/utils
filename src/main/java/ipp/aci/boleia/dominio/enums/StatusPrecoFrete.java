package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status visual de um preço frete
 */
public enum StatusPrecoFrete implements IEnumComLabel<StatusPrecoFrete> {

    VIGENTE(1),
    HISTORICO(2);

    public static final String DECODE_FORMULA = "DECODE(ID_STATUS, 1, 'VIG', 2, 'HIST')";

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O label do status
     */
    StatusPrecoFrete(Integer value) {
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
    public static StatusPrecoFrete obterPorValor(Integer value) {
        for(StatusPrecoFrete status : StatusPrecoFrete.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }

}
