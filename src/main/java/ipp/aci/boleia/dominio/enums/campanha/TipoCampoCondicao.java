package ipp.aci.boleia.dominio.enums.campanha;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o tipo de entrada de uma condição de campanha
 */
public enum TipoCampoCondicao implements IEnumComLabel<TipoCampoCondicao> {

    AUTO_COMPLETE(0),
    COMBOBOX(1),
    CHECKBOX(2),
    TEXTO_PLACA(3),
    TEXTO_CICLO(4);

    private Integer value;

    /**
     * Construtor
     *
     * @param value O value do tipo
     */
    TipoCampoCondicao(Integer value) {
        this.value = value;
    }

    /**
     * Obtém valor do Tipo do campo
     *
     * @return Tipo campo.
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
    public static TipoCampoCondicao obterPorValor(Integer value) {
        for (TipoCampoCondicao tipo : TipoCampoCondicao.values()) {
            if (tipo.value.equals(value)) {
                return tipo;
            }
        }
        return null;
    }
}
