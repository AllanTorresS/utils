package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera as unidades de medida do sistema
 */
public enum UnidadeMedida implements IEnumComLabel<UnidadeMedida> {

    NENHUMA(1),
    LITRO(2);

    private final Integer value;

    /**
     * Construtor
     * @param value O valor da enum
     */
    UnidadeMedida(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor da enumercao
     * @return o valor da enumeracao
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static UnidadeMedida obterPorValor(Integer value) {
        for(UnidadeMedida unidadeMedida : UnidadeMedida.values()) {
            if(unidadeMedida.value.equals(value)) {
                return unidadeMedida;
            }
        }
        return null;
    }
}
