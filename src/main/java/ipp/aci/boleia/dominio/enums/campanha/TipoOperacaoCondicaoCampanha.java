package ipp.aci.boleia.dominio.enums.campanha;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

import java.util.Arrays;

/**
 * Indica o tipo de operação a ser utilizado numa condição
 */
public enum TipoOperacaoCondicaoCampanha implements IEnumComLabel<TipoOperacaoCondicaoCampanha> {
    EXCLUSAO(0),
    INCLUSAO(1);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do tipo operacao
     */
    TipoOperacaoCondicaoCampanha(Integer value) {
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
     * Obtém o enumerado referente ao parâmetro fornecido
     *
     * @param nome nome do enum
     * @return enumerado ou nulo
     */
    public static TipoOperacaoCondicaoCampanha obterPorNome(String nome) {
        return Arrays.stream(TipoOperacaoCondicaoCampanha.values())
                .filter(t -> t.name().equalsIgnoreCase(nome))
                .findFirst().orElse(null);
    }
    /**
     * Obtem por valor
     *
     * @param value o valor numerico da enumeracao
     * @return Enum para o valor
     */
    public static TipoOperacaoCondicaoCampanha obterPorValor (Integer value) {
        return Arrays.stream(TipoOperacaoCondicaoCampanha.values())
                .filter(t -> t.getValue().equals(value))
                .findFirst().orElse(null);
    }
}
