package ipp.aci.boleia.dominio.enums;

/**
 * Indica os tipos de frequência de execução das tarefas
 */
public enum TipoFrequencia {

    DIARIA(1),
    SEMANAL(2),
    MENSAL(3);

    private final Integer value;

    TipoFrequencia(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Obtém o tipo de frequência por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static TipoFrequencia obterPorValor(Integer value) {
        for (TipoFrequencia tipoFrequencia : TipoFrequencia.values()) {
            if (tipoFrequencia.value.equals(value)) {
                return tipoFrequencia;
            }
        }
        return null;
    }
}
