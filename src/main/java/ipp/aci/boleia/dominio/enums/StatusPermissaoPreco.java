package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica se um ponto de venda tem premissao para alteracao de preco
 */
public enum StatusPermissaoPreco implements IEnumComLabel<StatusPermissaoPreco> {

    SIM(1),
    NAO(0);

    public static final String DECODE_FORMULA = "DECODE(ID_PERMISSAO_PRECO_POSTO, 1, 'SIM', 0, 'NAO')";

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusPermissaoPreco(Integer value) {
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
    public static StatusPermissaoPreco obterPorValor(Integer value) {
        for(StatusPermissaoPreco status : StatusPermissaoPreco.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }

    /**
     * Obtem por valor booleano
     * @param value value
     * @return Enum para o valor (booleano)
     */
    public static StatusPermissaoPreco obterPorValorBoolean(Boolean value) {
        for(StatusPermissaoPreco status : StatusPermissaoPreco.values()) {
            if(value && status.value.equals(StatusPermissaoPreco.SIM.getValue()) || !value && status.value.equals(StatusPermissaoPreco.NAO.getValue())) {
                return status;
            }
        }
        return null;
    }

}
