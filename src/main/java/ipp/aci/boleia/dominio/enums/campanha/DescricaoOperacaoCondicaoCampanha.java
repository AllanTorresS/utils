package ipp.aci.boleia.dominio.enums.campanha;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

import java.util.Arrays;

/**
 * Indica a semântica da operação de uma condição de campanha
 */
public enum DescricaoOperacaoCondicaoCampanha implements IEnumComLabel<DescricaoOperacaoCondicaoCampanha> {
    IGUAL(0),
    CONTEM(1);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do tipo operacao
     */
    DescricaoOperacaoCondicaoCampanha(Integer value) {
        this.value = value;
    }

    /**
     * Obtém valor da Descrição da Operação
     *
     * @return Descrição da Operação.
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     *
     * @param value o valor numerico da enumeracao
     * @return Enum para o valor
     */
    public static DescricaoOperacaoCondicaoCampanha obterPorValor(Integer value) {
        return Arrays.stream(DescricaoOperacaoCondicaoCampanha.values())
                .filter(d -> d.getValue().equals(value))
                .findFirst().orElse(null);
    }
}
