package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.dominio.enums.campanha.IEnumComValorCampanha;

/**
 * Indica a classificacao de um veiculo
 */
public enum ClassificacaoAgregado implements IEnumComValorCampanha<ClassificacaoAgregado> {

    PROPRIO(0),
    AGREGADO(1);

    public static final String DECODE_FORMULA = "DECODE(ID_AGREGADO, 1, 'AGREG', 0, 'PROP')";

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor do enum
     */
    ClassificacaoAgregado(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor da enum
     * @return O valor da enum
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static ClassificacaoAgregado obterPorValor(Integer value) {
        for(ClassificacaoAgregado status : ClassificacaoAgregado.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static ClassificacaoAgregado obterPorValor(String value) {
        return obterPorValor(Integer.parseInt(value));
    }

    @Override
    public String obterValor() {
        return this.value.toString();
    }
}
