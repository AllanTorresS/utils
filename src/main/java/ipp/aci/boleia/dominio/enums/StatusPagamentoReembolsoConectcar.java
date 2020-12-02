package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status do pagamento do reembolso de uma transacao consolidada
 */
public enum StatusPagamentoReembolsoConectcar implements IEnumComLabel<StatusPagamentoReembolsoConectcar> {

    PAGO(1),
    EM_ABERTO(0),
    ATRASADO(2),    
    DEBITO_EM_ABERTO(3);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor do status
     */
    StatusPagamentoReembolsoConectcar(Integer value) {
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
    public static StatusPagamentoReembolsoConectcar obterPorValor(Integer value) {
        for(StatusPagamentoReembolsoConectcar status : StatusPagamentoReembolsoConectcar.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}