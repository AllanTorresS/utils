package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status de uma antecipação de reembolso
 */
public enum StatusAntecipacao implements IEnumComLabel<StatusAntecipacao> {

    ANTECIPADO(1),
    AGUARDANDO_ACEITE(2),
    CANCELADO_CLIENTE(3),
    CANCELADO_SEM_RESPOSTA(4),
    EM_ANDAMENTO(5),
    PENDENTE(6);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusAntecipacao(Integer value) {
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
    public static StatusAntecipacao obterPorValor(Integer value) {
        for(StatusAntecipacao status : StatusAntecipacao.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
