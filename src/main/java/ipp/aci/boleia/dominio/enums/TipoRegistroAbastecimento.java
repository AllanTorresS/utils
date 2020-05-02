package ipp.aci.boleia.dominio.enums;

/**
 * Enum com tipos de registro de abastecimento
 *
 */
public enum  TipoRegistroAbastecimento {

	ABASTECIMENTO_NORMAL(1),
	ABASTECIMENTO_EM_CONTINGENCIA(7);

    private Integer value;

    /**
     * Construtor da classe
     * @param value Valor
     */
    TipoRegistroAbastecimento(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
