package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os possíveis tipos de pagamento no sistema
 */
public enum TipoFormaPagamento implements IEnumComLabel<TipoFormaPagamento> {

    BOLEIA(1),
    OUTROS(2);

    private final Integer value;

    /**
     * Construtor
     * @param value O vlor da enum
     */
    TipoFormaPagamento(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor da enum
     * @return O valor da enum
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static TipoFormaPagamento obterPorValor(Integer value) {
        for(TipoFormaPagamento tipoFormaPagamento : TipoFormaPagamento.values()) {
            if(tipoFormaPagamento.value.equals(value)) {
                return tipoFormaPagamento;
            }
        }
        return null;
    }
}
