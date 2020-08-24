package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

import java.util.Arrays;

/**
 * Enumerado que representa os tipos de documento
 */
public enum DocumentoTipo implements IEnumComLabel<DocumentoTipo> {

    TERMO_DE_USO(0),
    POLITICA_DE_PRIVACIDADE(1),
    INTEGRACAO_KMV(2);

    private final Integer valor;

    /**
     * Construtor
     *
     * @param valor O valor do enumerado
     */
    DocumentoTipo(Integer valor) {
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
    public static DocumentoTipo obterPorValor(Integer valor) {
        return Arrays.stream(DocumentoTipo.values())
                .filter(s -> s.getValor().equals(valor))
                .findFirst().orElse(null);
    }

    /**
     * Obtem por nome
     *
     * @param nome O nome do enumerado
     * @return Enum para o nome
     */
    public static DocumentoTipo obterPorNome(String nome) {
        return Arrays.stream(values())
                .filter(e -> e.name().equals(nome))
                .findAny()
                .orElse(null);
    }
}
