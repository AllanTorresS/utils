package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os possíveis tipos de rastreadores
 */
public enum TipoRastreador implements IEnumComLabel<TipoRastreador> {

    NAO(0),
    SASCAR(1),
    ONIXSAT(2),
    GOLSAT(3);

    private final Integer value;

    /**
     * Construtor da enumeracao com o valor
     * @param value valor que representa a enumeracao
     */
    TipoRastreador(Integer value) {
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
    public static TipoRastreador obterPorValor(Integer value) {
        for(TipoRastreador tipoRastreador  : TipoRastreador.values()) {
            if(tipoRastreador.value.equals(value)) {
                return tipoRastreador;
            }
        }
        return null;
    }
}