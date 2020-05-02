package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os possíveis tipos de realização de um pedido de abastecimento
 */
public enum TipoRealizacaoPedido implements IEnumComLabel<TipoRealizacaoPedido> {

    ONLINE(1),
    OFFLINE(2);

    private final Integer value;

    /**
     * Construtor
     * @param value O valor da enum
     */
    TipoRealizacaoPedido(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor da enumercao
     * @return o valor da enumeracao
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static TipoRealizacaoPedido obterPorValor(Integer value) {
        for(TipoRealizacaoPedido tipoRealizacaoPedido  : TipoRealizacaoPedido.values()) {
            if(tipoRealizacaoPedido.value.equals(value)) {
                return tipoRealizacaoPedido;
            }
        }
        return null;
    }
}