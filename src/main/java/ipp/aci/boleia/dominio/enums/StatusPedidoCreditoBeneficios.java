package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status de um pedido de crédito de benefícios.
 */
public enum StatusPedidoCreditoBeneficios implements IEnumComLabel<StatusPedidoCreditoBeneficios> {

    PENDENTE(0),
    PAGO(1),
    VENCIDO(2);

    private final Integer value;

    /**
     * Construtor.
     *
     * @param value O valor do status.
     */
    StatusPedidoCreditoBeneficios(Integer value) {
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
    public static StatusPedidoCreditoBeneficios obterPorValor(Integer value){
        for(StatusPedidoCreditoBeneficios status : StatusPedidoCreditoBeneficios.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
