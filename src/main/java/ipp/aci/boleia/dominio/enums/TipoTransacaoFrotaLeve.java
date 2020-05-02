package ipp.aci.boleia.dominio.enums;

/**
 * Enumera os tipos de transações de uma frota leve.
 */
public enum TipoTransacaoFrotaLeve {
    AUTORIZAR(1),
    CONFIRMAR(2);

    private final Integer value;

    /**
     * Construtor privado do enum.
     *
     * @param value Valor numérico do enum.
     */
    TipoTransacaoFrotaLeve(Integer value) {
        this.value = value;
    }

    /**
    * Obtém o valor da enumeracao
    * @return O valor da enumeracao
    */
    public Integer getValue() {
        return this.value;
    }
}
