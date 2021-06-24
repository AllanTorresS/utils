package ipp.aci.boleia.dominio.enums;


/**
 * Indica o status de uma proposta de empréstimo da XP
 */
public enum StatusPropostaXP {

    REQUESTED(1),
    CREATED(2),
    APPROVED(4),
    CANCELED(5);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor da enumeração
     */
    StatusPropostaXP(Integer value) {
        this.value = value;
    }

    /**
     * Obtém o valor da enumeração
     * @return O valor da enumeração
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtém por valor
     *
     * @param value value
     * @return Enum associado ao valor
     */
    public static StatusPropostaXP obterPorValor(Integer value) {
        for (StatusPropostaXP status : StatusPropostaXP.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
