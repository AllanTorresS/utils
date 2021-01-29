package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

import java.util.Arrays;

/**
 * Enumerado que representa os tipos de agrupamento da nota fiscal
 */
public enum TipoAgrupamentoNfe implements IEnumComLabel<TipoAgrupamentoNfe> {

    NOTA_UNICA_ABASTECIMENTO(1),
    NOTA_AGLOMERADA(2),
    NOTA_ATO_ABASTECIMENTO(3);

    private final Integer valor;

    /**
     * Construtor
     *
     * @param valor O valor do enumerado
     */
    TipoAgrupamentoNfe(Integer valor) {
        this.valor = valor;
    }

    public Integer getValor() {
        return valor;
    }

    /**
     * Obtem por valor
     *
     * @param valor O valor
     * @return Enum para o valor
     */
    public static TipoAgrupamentoNfe obterPorValor(Integer valor) {
        return Arrays.stream(TipoAgrupamentoNfe.values())
                .filter(s -> s.getValor().equals(valor))
                .findFirst().orElse(null);
    }

    /**
     * Obtem por nome
     *
     * @param nome O nome do enumerado
     * @return Enum para o nome
     */
    public static TipoAgrupamentoNfe obterPorNome(String nome) {
        return Arrays.stream(values())
                .filter(e -> e.name().equals(nome))
                .findAny()
                .orElse(null);
    }
}
