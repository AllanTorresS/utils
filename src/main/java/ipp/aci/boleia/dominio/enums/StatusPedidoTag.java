package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os status de pedido tag
 */
public enum StatusPedidoTag implements IEnumComLabel<StatusPedidoTag> {

    CONFIRMADO(1),
    EXPIRADO(2),
    PENDENTE(3);	

    private final Integer value;

    /**
     * Construtor padrão
     * @param value O valor do status
     */
    StatusPedidoTag(Integer value) {
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
    public static StatusPedidoTag obterPorValor(Integer value) {
        for(StatusPedidoTag status : StatusPedidoTag.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
