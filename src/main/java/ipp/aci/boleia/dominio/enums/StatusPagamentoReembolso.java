package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status do pagamento do reembolso de uma transacao consolidada
 */
public enum StatusPagamentoReembolso implements IEnumComLabel<StatusPagamentoReembolso> {

    PAGO(1),
    EM_ABERTO(0),
    ATRASADO(2),
    AGUARDANDO_NF(3),
    NF_ATRASADA(4),
    A_DESCONTAR(5),
    PREVISTO(6);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor do status
     */
    StatusPagamentoReembolso(Integer value) {
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
    public static StatusPagamentoReembolso obterPorValor(Integer value) {
        for(StatusPagamentoReembolso status : StatusPagamentoReembolso.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}