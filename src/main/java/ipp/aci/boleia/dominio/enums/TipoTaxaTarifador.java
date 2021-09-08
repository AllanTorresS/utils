package ipp.aci.boleia.dominio.enums.tarifador;

import ipp.aci.boleia.dominio.enums.IEnumComValor;

import java.util.Arrays;

/**
 * Representa os tipos possíveis de intervalos de cobrança das taxas do tarifador da Recolha Manual de NF
 */
public enum TipoTaxaTarifador implements IEnumComValor {
    REAIS(1),
    LITRAGEM(2),
    FIXO_REAIS(3);

    private final Integer value;

    /**
     * Construtor
     * @param value O valor do enumerado
     */
    TipoTaxaTarifador(Integer value) {
        this.value = value;
    }

    /**
     * Obtem por valor
     *
     * @param valor O valor
     * @return Enum para o valor
     */
    public static TipoTaxaTarifador obterPorValor(Integer valor) {
        return Arrays.stream(TipoTaxaTarifador.values())
                .filter(s -> s.getValue().equals(valor))
                .findFirst().orElse(null);
    }

    /**
     * Obtem por nome
     *
     * @param nome O nome do enumerado
     * @return Enum para o nome
     */
    public static TipoTaxaTarifador obterPorNome(String nome) {
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
