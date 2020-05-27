package ipp.aci.boleia.dominio.enums;

/**
 * Indica as unidades de medida de tempo utilizadas no agendamento de execução das tarefas
 */
public enum UnidadeRepeticao {

    SEGUNDO(1),
    MINUTO(2),
    HORA(3);

    private final Integer value;

    UnidadeRepeticao(Integer value){
        this.value = value;
    }
    
    public Integer getValue() {
        return value;
    }

    /**
     * Obtém a unidade de medida de repetição por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static UnidadeRepeticao obterPorValor(Integer value) {
        for (UnidadeRepeticao p : UnidadeRepeticao.values()) {
            if (p.value.equals(value)) {
                return p;
            }
        }
        return null;
    }
}
