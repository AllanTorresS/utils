package ipp.aci.boleia.dominio.enums;

/**
 * Enumera os tipos de transação da conectcar.
 *
 */
public enum TipoTransacaoConectcarConsolidada {
    PEDIDO_TAG(1);

    private Integer value;

    /**
     * Construtor do enum.
     *
     * @param value Valor do enum.
     */
    TipoTransacaoConectcarConsolidada(Integer value) {
        this.value = value;
    }

    /**
     * Retorna o valor do enum.
     *
     * @return valor do enum.
     */
    public Integer getValue() {
        return value;
    }
}
