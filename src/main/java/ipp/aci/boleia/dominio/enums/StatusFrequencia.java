package ipp.aci.boleia.dominio.enums;

/**
 * Indica os status de frequência de execução das tarefas
 */
public enum  StatusFrequencia {

    ATIVO(1),
    EM_PAUSA(0);

    private final Integer value;

    StatusFrequencia(Integer value){
        this.value = value;
    }

    public Integer getValue(){return this.value;}
}
