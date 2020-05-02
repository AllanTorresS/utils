package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os possíveis tipos de um Produto
 */
public enum TipoProduto implements IEnumComLabel<TipoProduto> {

    PRODUTO(1),
    SERVICO(2);

    private final Integer value;

    /**
     * Construtor
     * @param value O valor da enum
     */
    TipoProduto(Integer value) {
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
    public static TipoProduto obterPorValor(Integer value) {
        for(TipoProduto tipoProduto : TipoProduto.values()) {
            if(tipoProduto.value.equals(value)) {
                return tipoProduto;
            }
        }
        return null;
    }
}
