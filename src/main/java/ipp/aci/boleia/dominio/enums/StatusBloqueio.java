package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica se um dispositivo esta habilitado
 */
public enum StatusBloqueio implements IEnumComLabel<StatusBloqueio> {

    BLOQUEADO(1),
    DESBLOQUEADO(0);

    public static final String DECODE_FORMULA = "DECODE(ID_BLOQUEADO, 1, 'B', 0, 'D')";

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor do status
     */
    StatusBloqueio(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor do status
     * @return o valor do status
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static StatusBloqueio obterPorValor(Integer value) {
        for(StatusBloqueio status : StatusBloqueio.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }

    /**
     * Informa se o valor do enum é igual a BLOQUEADO.
     * @return true, caso seja.
     */
    public boolean isBloqueado() {
        return equals(BLOQUEADO);
    }
}
