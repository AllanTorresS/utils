package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status de uma consolidacao de transacoes (ciclo de fatura de abastecimentos)
 */
public enum StatusTransacaoConsolidada implements IEnumComLabel<StatusTransacaoConsolidada>, IEnumComValor {

    EM_ABERTO(0),
    FECHADA(1),
    EM_AJUSTE(2);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor do status
     */
    StatusTransacaoConsolidada(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem o tipo enumerado a partir de seu valor numerico
     *
     * @param value O valor
     * @return O tipo enumerado
     */
    public static StatusTransacaoConsolidada obterPorValor(Integer value) {
        for (StatusTransacaoConsolidada status : StatusTransacaoConsolidada.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
