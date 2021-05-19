package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

import java.util.Objects;

/**
 * Lista os possíveis status de uma solicitação de interesse no programa de antecipação de recebíveis
 */
public enum StatusInteresseAntecipacao implements IEnumComLabel<StatusInteresseAntecipacao> {

    NAO_SOLICITADO(0, null),
    APROVADO(1, "aprovado"),
    REJEITADO(2, "rejeitado"),
    EM_ANALISE(3, "analise"),
    CANCELADO(4, "cancelado");

    private final Integer value;
    private final String labelSalesforce;

    /**
     * Construtor padrão
     * @param value O valor do status
     */
    StatusInteresseAntecipacao(Integer value, String labelSalesforce) {
        this.value = value;
        this.labelSalesforce = labelSalesforce;
    }

    public Integer getValue() {
        return value;
    }

    public String getLabelSalesforce() {
        return labelSalesforce;
    }

    /**
     * Obtém o status do contrato a partir do label equivalente utilizado pelo Salesforce
     *
     * @param labelSalesforce o label utilizado pelo Salesforce
     * @return o status do contrato, ou null caso não exista equivalência
     */
    public static StatusInteresseAntecipacao obterPorLabelSalesforce(String labelSalesforce) {
        for(StatusInteresseAntecipacao status : StatusInteresseAntecipacao.values()) {
            if(Objects.equals(labelSalesforce, status.labelSalesforce)) {
                return status;
            }
        }
        return null;
    }
}
