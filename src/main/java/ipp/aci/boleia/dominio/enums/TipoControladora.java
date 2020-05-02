package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Tipo de controladora enviada pelo Integrador
 */
public enum TipoControladora implements IEnumComLabel<TipoControladora> {

    HORUSTECH(1),
    EZTECHFORECOURT(2),
    FUSIONWAYNE(3),
    XPERT(4),
    GENERICA(5),
    SOFTPLUS(6);

    private final Integer value;

    /**
     * Construtor
     * @param value O valor da enum
     */
    TipoControladora(Integer value) {
        this.value = value;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static TipoControladora obterPorValor(Integer value) {
        for (TipoControladora tipoCategoriaNotificacao : TipoControladora.values()) {
            if (tipoCategoriaNotificacao.value.equals(value)) {
                return tipoCategoriaNotificacao;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }
}
