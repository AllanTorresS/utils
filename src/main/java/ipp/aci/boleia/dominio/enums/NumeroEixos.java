package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o numero de eixos do veiculo
 */
public enum NumeroEixos implements IEnumComLabel<NumeroEixos> {

    UM(1),
    DOIS(2),
    TRES(3),
    QUATRO(4),
    CINCO(5),
    SEIS(6),
    SETE(7),
    OITO(8),
    NOVE(9),
    DEZ(10),
    MAIS(11);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    NumeroEixos(Integer value) {
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
    public static NumeroEixos obterPorValor(Integer value) {
        for(NumeroEixos status : NumeroEixos.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
