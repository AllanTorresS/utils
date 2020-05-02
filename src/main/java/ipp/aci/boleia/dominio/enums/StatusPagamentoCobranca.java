package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status do pagamento da cobranca de uma transacao consolidada
 */
public enum StatusPagamentoCobranca implements IEnumComLabel<StatusPagamentoCobranca> {

    PAGO(1),
    EM_ABERTO(0),
    VENCIDO(2);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor do status
     */
    StatusPagamentoCobranca(Integer value) {
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
    public static StatusPagamentoCobranca obterPorValor(Integer value) {
        for(StatusPagamentoCobranca status : StatusPagamentoCobranca.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}