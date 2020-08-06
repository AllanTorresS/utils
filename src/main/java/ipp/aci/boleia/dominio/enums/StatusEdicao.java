package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o estado de edição de um abastecimento
 */
public enum StatusEdicao implements IEnumComLabel<StatusEdicao> {
    REJEITADO_VALIDACAO(-2),
    REJEITADO(-1),
    NAO_EDITADO(0),
    EDITADO(1),
    PENDENTE(2),
    EXPIRADO(3);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusEdicao(Integer value) {
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
    public static StatusEdicao obterPorValor(Integer value) {
        for(StatusEdicao statusEdicao : StatusEdicao.values()) {
            if(statusEdicao.value.equals(value)) {
                return statusEdicao;
            }
        }
        return null;
    }
}
