package ipp.aci.boleia.dominio.enums;

/**
 * Enum com meios de pagamento
 *
 */
public enum  MeioPagamento {

	CONTINGENCIA_PORTAL(2),
	INCLUSAO_MANUAL(3);

    private Integer value;

    /**
     * Construtor da classe
     * @param value Valor
     */
    MeioPagamento(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
