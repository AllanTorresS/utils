package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status de uma nota fiscal do consolidado do Financeiro novo
 */
public enum StatusNotaFiscalFinanceiro implements IEnumComLabel<StatusNotaFiscalFinanceiro> {

    PENDENTE(0),
    EMITIDA(1),
    ATRASADA(2),
    SEM_EMISSAO(3),
    PARCIALMENTE_EMITIDA(4);

    private final Integer value;

    /**
     * Construtor
     * @param value O valor da enumeracao
     */
    StatusNotaFiscalFinanceiro(Integer value) {
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
     *
     * @param value value
     * @return Enum para o valor
     */
    public static StatusNotaFiscalFinanceiro obterPorValor(Integer value) {
        for (StatusNotaFiscalFinanceiro status : StatusNotaFiscalFinanceiro.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
