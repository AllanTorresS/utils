package ipp.aci.boleia.dominio.enums;

/**
 * Lista os possíveis status de uma solicitação de interesse no programa de antecipação de recebíveis
 */
public enum StatusInteresseAntecipacao {

    NAO_SOLICITADO(0),
    APROVADO(1),
    REJEITADO(2),
    EM_ANALISE(3),
    CANCELADO(4);

    private final Integer value;

    /**
     * Construtor padrão
     * @param value O valor do status
     */
    StatusInteresseAntecipacao(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
