package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status de um contrato da Frota
 */
public enum StatusNotaFiscalAbastecimento implements IEnumComLabel<StatusNotaFiscalAbastecimento> {

    PENDENTE(0),
    EMITIDA(1),
    JUSTIFICADA(2);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor da enumeracao
     */
    StatusNotaFiscalAbastecimento(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor da enumeracao
     * @return O valor da enumeracao
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static StatusNotaFiscalAbastecimento obterPorValor(Integer value) {
        for (StatusNotaFiscalAbastecimento status : StatusNotaFiscalAbastecimento.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
