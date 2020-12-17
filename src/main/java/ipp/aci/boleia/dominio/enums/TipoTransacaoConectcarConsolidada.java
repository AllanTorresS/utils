package ipp.aci.boleia.dominio.enums;

/**
 * Enumera os tipos de transação da conectcar.
 *
 */
public enum TipoTransacaoConectcarConsolidada {

    PEDIDO_TAG(1),
	PEDAGIO(2),
	ESTACIONAMENTO(3);

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

    /**
     * Obtem por value
     * @param value value
     * @return Enum para o value
     */
    public static TipoTransacaoConectcarConsolidada obterPorValue(Integer value) {
    	for (TipoTransacaoConectcarConsolidada tipo : TipoTransacaoConectcarConsolidada.values()) {
            if(tipo.getValue().equals(value)) {
                return tipo;
            }
    	}
    	return null;
    }

}