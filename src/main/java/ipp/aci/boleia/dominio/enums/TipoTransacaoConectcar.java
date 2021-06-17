package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os status de pedido tag
 */
public enum TipoTransacaoConectcar implements IEnumComLabel<TipoTransacaoConectcar> {

	PEDAGIO(1),
    ESTACIONAMENTO(2),
    ESTORNO_PEDAGIO(3),
    ESTORNO_ESTACIONAMENTO(4),
    AJUSTE_PEDAGIO(5),
    AJUSTE_ESTACIONAMENTO(6),
    RECARGA_VALE_PEDAGIO(7),
    PASSAGEM_VALE_PEDAGIO(8),
    ESTORNO_VALE_PEDAGIO(9);

    private final Integer value;

    /**
     * Construtor padrão
     * @param value O valor do status
     */
    TipoTransacaoConectcar(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static TipoTransacaoConectcar obterPorValor(Integer value) {
        for(TipoTransacaoConectcar status : TipoTransacaoConectcar.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
