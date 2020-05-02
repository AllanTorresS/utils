package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

public enum StatusAnexo implements IEnumComLabel<StatusAnexo> {
    NAO_PROCESSADO(0),
    PROCESSADO(1),
    NAO_IMPORTADO(2),
    IMPORTADO(3);

    private final Integer value;

    /**
     * Construtor padrão
     * @param value O valor do status
     */
    StatusAnexo(Integer value) {
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
    public static StatusAnexo obterPorValor(Integer value) {
        for(StatusAnexo status : StatusAnexo.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
