package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

import java.util.Arrays;

/**
 * Enumerado que representa o sistema no qual foi realizado o aceite de um documento
 */
public enum DocumentoSistema implements IEnumComLabel<DocumentoStatus> {

    PORTAL(0),
    APP_MOTORISTA(1),
    APP_FROTISTA(2),
    PDV(3),
    PDV_WEB(4);

    public static final String DECODE_FORMULA = "DECODE(ID_DOC_SISTEMA, 0, 'PORTAL', 1, 'APP_MOTORISTA', 2, 'APP_FROTISTA', 3, 'PDV', 4, 'PDV_WEB')";

    private final Integer valor;

    /**
     * Construtor
     *
     * @param valor O valor do enumerado
     */
    DocumentoSistema(Integer valor) {
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
    public static DocumentoSistema obterPorValor(Integer valor) {
        return Arrays.stream(DocumentoSistema.values())
                .filter(s -> s.getValor().equals(valor))
                .findFirst().orElse(null);
    }

    /**
     * Obtem por nome
     *
     * @param nome O nome do enumerado
     * @return Enum para o nome
     */
    public static DocumentoSistema obterPorNome(String nome) {
        return Arrays.stream(values())
                .filter(e -> e.name().equals(nome))
                .findAny()
                .orElse(null);
    }
}