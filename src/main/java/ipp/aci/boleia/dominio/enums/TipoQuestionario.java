package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os possíveis tipos de questionários aplicados
 */
public enum TipoQuestionario implements IEnumComLabel<TipoQuestionario> {

    PONTOVENDA(0);

    private final Integer value;

    /**
     * Construtor
     * @param value O valor da enum
     */
    TipoQuestionario(Integer value) {
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
    public static TipoQuestionario obterPorValor(Integer value) {
        for(TipoQuestionario tipoProduto : TipoQuestionario.values()) {
            if(tipoProduto.value.equals(value)) {
                return tipoProduto;
            }
        }
        return null;
    }
}
