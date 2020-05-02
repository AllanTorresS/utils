package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status de alteração do preco por PV
 */
public enum StatusAlteracaoPrecoPosto implements IEnumComLabel<StatusAlteracaoPrecoPosto> {

    ACEITE_PENDENTE_REVENDA(1),
    ACEITE_PENDENTE_INTERNO(2),
    VIGENTE(3),
    ACEITO(4),
    RECUSADO(5),
    EXPIRADO(6),
    AGENDADO_SEM_ACEITE_REVENDA(7),
    AGENDADO_COM_ACEITE_REVENDA(8);

    public static final String DECODE_FORMULA = "DECODE(ID_STATUS, 1, 'ACEPENDREV', 2, 'ACEPENDINT', 3, 'VIG', 4, 'ACE', 5, 'REC', 6, 'EXP', 7, 'AGES', 8, 'AGEC')";

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O label do status
     */
    StatusAlteracaoPrecoPosto(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor do status
     * @return O valor do status
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static StatusAlteracaoPrecoPosto obterPorValor(Integer value) {
        for(StatusAlteracaoPrecoPosto status : StatusAlteracaoPrecoPosto.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }


}
