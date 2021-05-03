package ipp.aci.boleia.dominio.enums;

/**
 * Lista os possíveis status de uma solicitação de interesse no programa de antecipação de reembolsos
 */
public enum StatusContratoAntecipacao {

    NAO_SOLICITADO(0),
    APROVADO(1),
    REJEITADO(2),
    EM_ANALISE(3);

    private final Integer value;

    /**
     * Construtor padrão
     * @param value O valor do status
     */
    StatusContratoAntecipacao(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
