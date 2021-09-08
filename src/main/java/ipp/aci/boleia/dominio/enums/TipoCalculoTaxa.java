package ipp.aci.boleia.dominio.enums.tarifador;

import ipp.aci.boleia.dominio.enums.IEnumComValor;

import java.util.Arrays;

/**
 * Indica a forma como o valor da taxa é calculada
 */
public enum TipoCalculoTaxa implements IEnumComValor {
    REAIS(1),
    PERCENTUAL(2);

    private final Integer value;

    /**
     * Construtor
     * @param value O valor do enum
     */
    TipoCalculoTaxa(Integer value) {
        this.value = value;
    }

    /**
     * Obtem por valor
     *
     * @param valor O valor
     * @return Enum para o valor
     */
    public static TipoCalculoTaxa obterPorValor(Integer valor) {
        return Arrays.stream(TipoCalculoTaxa.values())
                .filter(s -> s.getValue().equals(valor))
                .findFirst().orElse(null);
    }

    /**
     * Obtem por nome
     *
     * @param nome O nome do enumerado
     * @return Enum para o nome
     */
    public static TipoCalculoTaxa obterPorNome(String nome) {
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
