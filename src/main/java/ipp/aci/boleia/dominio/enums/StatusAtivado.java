package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica se uma entidade esta ativado ou desativado
 */
public enum StatusAtivado implements IEnumComLabel<StatusAtivado> {

    ATIVADO(1),
    DESATIVADO(0);

    public static final String DECODE_FORMULA = "DECODE(ID_STATUS, 1, 'AT', 0, 'DE')";

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusAtivado(Integer value) {
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
    public static StatusAtivado obterPorValor(Integer value) {
        for(StatusAtivado status : StatusAtivado.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
