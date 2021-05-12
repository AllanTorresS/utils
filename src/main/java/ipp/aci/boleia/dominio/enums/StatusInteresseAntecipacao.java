package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Lista os possíveis status de uma solicitação de interesse no programa de antecipação de recebíveis
 */
public enum StatusInteresseAntecipacao implements IEnumComLabel<StatusInteresseAntecipacao> {

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
