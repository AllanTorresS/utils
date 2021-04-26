package ipp.aci.boleia.dominio.enums;

/**
 * Enumera os possiveis tipos de Postos em uma Rota Inteligente
 */
public enum TipoPostoRota implements IEnumComValor{
    PV (1),
    FROTA (2),
    UNIDADE (3);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor da enum
     */
    TipoPostoRota(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor da enumercao
     * @return o valor da enumeracao
     */
    public Integer getValue() {
        return value;
    }
}