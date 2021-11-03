package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera as possíveis origens de um beneficiário
 */
public enum OrigemBeneficiario implements IEnumComLabel<TipoConsumo> {

    MOTORISTA(1);

    private final Integer value;

    /**
     * Construtor
     * @param value O valor
     */
    OrigemBeneficiario(Integer value) {
        this.value = value;
    }

    /**
     * Obtém valor da origem do beneficiário
     * @return Origem beneficiário.
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static OrigemBeneficiario obterPorValor(Integer value) {
        for(OrigemBeneficiario origem : OrigemBeneficiario.values()) {
            if(origem.value.equals(value)) {
                return origem;
            }
        }
        return null;
    }
}

