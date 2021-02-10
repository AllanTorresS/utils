package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

import java.util.Arrays;

/**
 * Enumerado que representa os tipos de documento
 */
public enum TipoEntidadeEmpresaAbastecedora implements IEnumComLabel<TipoEntidadeEmpresaAbastecedora> {

    FROTA(1),
    AGENCIADOR_FRETE(2);

    private final Integer valor;

    /**
     * Construtor
     *
     * @param valor O valor do enumerado
     */
    TipoEntidadeEmpresaAbastecedora(Integer valor) {
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
    public static TipoEntidadeEmpresaAbastecedora obterPorValor(Integer valor) {
        return Arrays.stream(TipoEntidadeEmpresaAbastecedora.values())
                .filter(s -> s.getValor().equals(valor))
                .findFirst().orElse(null);
    }

    /**
     * Obtem por nome
     *
     * @param nome O nome do enumerado
     * @return Enum para o nome
     */
    public static TipoEntidadeEmpresaAbastecedora obterPorNome(String nome) {
        return Arrays.stream(values())
                .filter(e -> e.name().equals(nome))
                .findAny()
                .orElse(null);
    }
}
