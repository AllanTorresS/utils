package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status de um contrato da Frota
 */
public enum StatusNotaFiscal implements IEnumComLabel<StatusNotaFiscal>, IEnumComValor {

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
    StatusNotaFiscal(Integer value) {
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
    public static StatusNotaFiscal obterPorValor(Integer value) {
        for (StatusNotaFiscal status : StatusNotaFiscal.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
