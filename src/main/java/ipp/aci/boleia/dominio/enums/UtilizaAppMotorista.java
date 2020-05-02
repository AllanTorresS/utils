package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica se um motorista utiliza ou não o aplicativo motorista
 */
public enum UtilizaAppMotorista implements IEnumComLabel<UtilizaAppMotorista> {

    NAO(0),
    SIM(1);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor do status
     */
    UtilizaAppMotorista(Integer value) {
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
    public static UtilizaAppMotorista obterPorValor(Integer value) {
        for(UtilizaAppMotorista status : UtilizaAppMotorista.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }

    /**
     * Obtém o enum por label
     * @param label label
     * @return Enum para a label
     */
    public static UtilizaAppMotorista obterPorLabel(String label){
        UtilizaAppMotorista enumEncontrado;
        try {
            enumEncontrado = UtilizaAppMotorista.valueOf(label);
        } catch (Exception e){
            return null;
        }
        return enumEncontrado;
    }
}
