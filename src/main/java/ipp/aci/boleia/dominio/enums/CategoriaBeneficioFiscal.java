package ipp.aci.boleia.dominio.enums;


/**
 * Indica a categoria do benefício fiscal
 */
public enum CategoriaBeneficioFiscal {

    SIMPLES_NACIONAL("PJS");

    private final String value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    CategoriaBeneficioFiscal(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static CategoriaBeneficioFiscal obterPorValor(String value) {
        for(CategoriaBeneficioFiscal status : CategoriaBeneficioFiscal.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
