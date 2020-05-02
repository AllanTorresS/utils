package ipp.aci.boleia.dominio.enums;


/**
 * Indica o status de uma consolidacao de transacoes (ciclo de fatura de abastecimentos)
 */
public enum StatusTransacaoConsolidada {

    ABERTA(0),
    FECHADA(1);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor do status
     */
    StatusTransacaoConsolidada(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Obtem o tipo enumerado a partir de seu valor numerico
     *
     * @param value O valor
     * @return O tipo enumerado
     */
    public static StatusTransacaoConsolidada obterPorValor(Integer value) {
        for (StatusTransacaoConsolidada status : StatusTransacaoConsolidada.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
