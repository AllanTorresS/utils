package ipp.aci.boleia.dominio.enums.campanha;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o tipo de um Campanha
 */
public enum TipoDesconto implements IEnumComLabel<TipoDesconto> {
    PERCENTUAL(0),
    MOEDA(1);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do tipo
     */
    TipoDesconto(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static TipoDesconto obterPorValor(Integer value) {
        for (TipoDesconto tipo : TipoDesconto.values()) {
            if (tipo.value.equals(value)) {
                return tipo;
            }
        }
        return null;
    }
}