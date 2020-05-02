package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o tipo de ponto da rota
 */
public enum TipoPontoRota implements IEnumComLabel<TipoPontoRota> {

    ORIGEM(0),
    DESTINO(2),
    INTERMEDIARIO(1);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor da enum
     */
    TipoPontoRota(Integer value) {
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
    public static TipoPontoRota obterPorValor(Integer value) {
        for(TipoPontoRota status : TipoPontoRota.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
