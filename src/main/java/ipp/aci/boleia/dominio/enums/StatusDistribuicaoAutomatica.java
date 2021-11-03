package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica se uma distribuição automática está ativa ou inativa
 */
public enum StatusDistribuicaoAutomatica implements IEnumComLabel<StatusDistribuicaoAutomatica>, IEnumComValor {

    ATIVA(1),
    INATIVA(0);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusDistribuicaoAutomatica(Integer value) {
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
    public static StatusDistribuicaoAutomatica obterPorValor(Integer value) {
        for(StatusDistribuicaoAutomatica status : StatusDistribuicaoAutomatica.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
