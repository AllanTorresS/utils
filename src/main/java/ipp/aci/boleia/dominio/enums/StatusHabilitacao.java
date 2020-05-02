package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica se um dispositivo esta habilitado
 */
public enum StatusHabilitacao implements IEnumComLabel<StatusHabilitacao> {

    HABILITADO(1),
    EXPIRADO(2),
    REGERADO(3),
    NAO_HABILITADO(0);

    public static final String DECODE_FORMULA = "CASE WHEN DT_EXP_TOKEN < (SELECT SYSDATE FROM DUAL) THEN 1 ELSE 0 END";

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusHabilitacao(Integer value) {
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
    public static StatusHabilitacao obterPorValor(Integer value) {
        for(StatusHabilitacao status : StatusHabilitacao.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
