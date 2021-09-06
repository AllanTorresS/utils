package ipp.aci.boleia.dominio.enums.tarifador;

import ipp.aci.boleia.dominio.enums.IEnumComValor;

import java.util.Arrays;

/**
 * Indica se uma faixa de uma taxa do Tarifador é escolhida por valor monetário ou valor percentual
 */
public enum TipoFaixaTarifador implements IEnumComValor {
    REAIS(1),
    PERCENTUAL(2);

    private final Integer value;

    /**
     * Construtor
     * @param value O valor do enum
     */
    TipoFaixaTarifador(Integer value) {
        this.value = value;
    }

    /**
     * Obtem por valor
     *
     * @param valor O valor
     * @return Enum para o valor
     */
    public static TipoFaixaTarifador obterPorValor(Integer valor) {
        return Arrays.stream(TipoFaixaTarifador.values())
                .filter(s -> s.getValue().equals(valor))
                .findFirst().orElse(null);
    }

    /**
     * Obtem por nome
     *
     * @param nome O nome do enumerado
     * @return Enum para o nome
     */
    public static TipoFaixaTarifador obterPorNome(String nome) {
        return Arrays.stream(values())
                .filter(e -> e.name().equals(nome))
                .findAny()
                .orElse(null);
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
