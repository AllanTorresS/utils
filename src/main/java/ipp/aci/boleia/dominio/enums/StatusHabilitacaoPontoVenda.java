package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica se um ponto de venda esta habilitado
 */
public enum StatusHabilitacaoPontoVenda implements IEnumComLabel<StatusHabilitacaoPontoVenda> {

    HABILITADO(1),
    PENDENTE_ACEITE(2),
    DESABILITADO(0);

    public static final String DECODE_FORMULA = "DECODE(ID_HABIL, 1, 'HAB', 2, 'PEND', 0, 'DES')";

    private final Integer value;

    /**
     * Construtor
     *
     * @param value o valor numerico da enumeracao
     */
    StatusHabilitacaoPontoVenda(Integer value) {
        this.value = value;
    }


    public Integer getValue() {
        return value;
    }

    /**
     * Obtem a constante enumerada correspondente ao valor informado
     * @param value O valor inteiro
     * @return O tipo enumerado correspondente ao valor
     */
    public static StatusHabilitacaoPontoVenda obterPorValor(Integer value) {
        for(StatusHabilitacaoPontoVenda status : StatusHabilitacaoPontoVenda.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
