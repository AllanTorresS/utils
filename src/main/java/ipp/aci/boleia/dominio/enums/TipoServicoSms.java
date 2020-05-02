package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica qual servico de SMS foi selecionado
 */
public enum TipoServicoSms implements IEnumComLabel<TipoServicoSms> {

    AWS("0"),
    IBM("1");

    private final String value;

    /**
     * Construtor
     *
     * @param value O valor da enum
     */
    TipoServicoSms(String value) {
        this.value = value;
    }

    /**
     * Obtem o valor da enumercao
     * @return o valor da enumeracao
     */
    public String getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static TipoServicoSms obterPorValor(String value) {
        for(TipoServicoSms status : TipoServicoSms.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
