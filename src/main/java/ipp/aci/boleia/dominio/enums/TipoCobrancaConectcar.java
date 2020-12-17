package ipp.aci.boleia.dominio.enums;

/**
 * Enumera os tipos de cobrança da conectcar.
 *
 */
public enum TipoCobrancaConectcar {

    PEDIDO(1),
	CICLO(2);

    private Integer value;

	/**
     * Construtor do enum.
     *
     * @param value Valor do enum.
     */
    TipoCobrancaConectcar(Integer value) {
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
    public static TipoCobrancaConectcar obterPorValue(Integer value) {
    	for (TipoCobrancaConectcar tipo : TipoCobrancaConectcar.values()) {
            if(tipo.getValue().equals(value)) {
                return tipo;
            }
    	}
    	return null;
    }

}