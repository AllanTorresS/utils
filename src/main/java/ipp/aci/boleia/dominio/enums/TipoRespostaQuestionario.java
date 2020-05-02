package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os possíveis tipos de resposta para um questionário
 */
public enum TipoRespostaQuestionario implements IEnumComLabel<TipoRespostaQuestionario> {

    RADIO(0),
    MULTISELECT(1),
    SELECT(2),
    CHECKLIST(3);

    private final Integer value;

    /**
     * Construtor
     * @param value O valor da enum
     */
    TipoRespostaQuestionario(Integer value) {
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
    public static TipoRespostaQuestionario obterPorValor(Integer value) {
        for(TipoRespostaQuestionario tipoProduto : TipoRespostaQuestionario.values()) {
            if(tipoProduto.value.equals(value)) {
                return tipoProduto;
            }
        }
        return null;
    }
}
