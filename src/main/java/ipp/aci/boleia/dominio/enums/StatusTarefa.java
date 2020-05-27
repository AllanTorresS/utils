package ipp.aci.boleia.dominio.enums;

/**
 * Indica os status de execução das tarefas
 */
public enum StatusTarefa {

    AGENDADA(0, 1),
    EM_EXECUCAO(1, 2),
    PAUSADA(2, 99),
    AGUARDANDO_CONCORRENCIA(3, 0),
    EXECUCAO_MANUAL(4, 0);

    private final Integer value;
    private final Integer prioridadeExecucao;

    StatusTarefa(Integer value, Integer prioridadeExecucao){
        this.value = value;
        this.prioridadeExecucao = prioridadeExecucao;
    }

    public Integer getValue(){
        return this.value;
    }

    public Integer getPrioridadeExecucao() {
        return prioridadeExecucao;
    }

    /**
     * Obtém status da tarefa por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static StatusTarefa obterPorValor(Integer value) {
        for (StatusTarefa status : StatusTarefa.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
