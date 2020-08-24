package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

import java.util.Arrays;

/**
 * Enumerado que representa o status de vigencia de um documento
 */
public enum DocumentoStatus implements IEnumComLabel<DocumentoStatus> {

    AGUARDANDO_PUBLICACAO(0),
    VIGENTE(1),
    REVOGADA(-1);

    private final Integer valor;

    /**
     * Construtor
     *
     * @param valor O valor do enumerado
     */
    DocumentoStatus(Integer valor) {
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
    public static DocumentoStatus obterPorValor(Integer valor) {
        return Arrays.stream(DocumentoStatus.values())
                .filter(s -> s.getValor().equals(valor))
                .findFirst().orElse(null);
    }

    /**
     * Obtem por nome
     *
     * @param nome O nome do enumerado
     * @return Enum para o nome
     */
    public static DocumentoStatus obterPorNome(String nome) {
        return Arrays.stream(values())
                .filter(e -> e.name().equals(nome))
                .findAny()
                .orElse(null);
    }
}
