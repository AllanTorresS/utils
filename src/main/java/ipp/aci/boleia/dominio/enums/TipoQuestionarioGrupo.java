package ipp.aci.boleia.dominio.enums;

/**
 * Enumera os possíveis grupos de questionario
 */
public enum TipoQuestionarioGrupo {

    CONVENIENCIA(1),
    RESTAURANTE(2),
    SANITARIOS(3),
    ESTACIONAMENTO(4),
    TROCA_OLEO(5),
    ARLA(6),
    INTERNET(7),
    OUTROS_SERVICOS(8);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor da enum
     */
    TipoQuestionarioGrupo(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor da enumercao
     *
     * @return o valor da enumeracao
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static TipoQuestionarioGrupo obterPorValor(Integer value) {
        for (TipoQuestionarioGrupo tipoProduto : TipoQuestionarioGrupo.values()) {
            if (tipoProduto.value.equals(value)) {
                return tipoProduto;
            }
        }
        return null;
    }
}
