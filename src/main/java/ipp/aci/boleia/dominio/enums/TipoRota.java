package ipp.aci.boleia.dominio.enums;

/**
 * Indica o tipo de rota
 */
public enum TipoRota {

    MAIS_CURTA(0),
    MELHOR_CUSTO(1),
    PRE_CALCULO(2),
    CUSTOMIZADA(3);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor da enum
     */
    TipoRota(Integer value) {
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
    public static TipoRota obterPorValor(Integer value) {
        for(TipoRota status : TipoRota.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }

}
