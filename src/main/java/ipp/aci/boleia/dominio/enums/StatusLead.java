package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status da situacao do lead
 */
public enum StatusLead implements IEnumComLabel<StatusLead> {

    REPROVADO(0),
    APROVADO(1);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusLead(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static StatusLead obterPorValor(Integer value) {
        for(StatusLead status : StatusLead.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}