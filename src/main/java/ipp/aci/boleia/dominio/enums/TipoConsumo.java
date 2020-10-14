package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumerável de Enum com label
 */
public enum TipoConsumo implements IEnumComLabel<TipoConsumo> {

    HL(0),
    KML(1);

    private final Integer value;

    /**
     * Construtor
     * @param value O valor
     */
    TipoConsumo(Integer value) {
        this.value = value;
    }

    /**
     * Obtém valor do Tipo Consumo
     * @return Tipo Consumo.
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static TipoConsumo obterPorValor(Integer value) {
        for(TipoConsumo tipoConsumo : TipoConsumo.values()) {
            if(tipoConsumo.value.equals(value)) {
                return tipoConsumo;
            }
        }
        return null;
    }
}
