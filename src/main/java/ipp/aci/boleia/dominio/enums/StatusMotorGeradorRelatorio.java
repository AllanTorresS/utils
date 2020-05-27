package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica so status de relatório emitido pelo motor de geração de relatórios
 */
public enum StatusMotorGeradorRelatorio implements IEnumComLabel<StatusMotorGeradorRelatorio> {

    EM_ANDAMENTO_AGUARDANDO(0),
    CONCLUIDO(1),
    CANCELADO(2),
    EXCLUIDO(3),
    ERRO(4),
    REGISTROS_NAO_ENCONTRADOS(5),
    EM_ANDAMENTO_PROCESSANDO(6);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusMotorGeradorRelatorio(Integer value) {
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
    public static StatusMotorGeradorRelatorio obterPorValor(Integer value) {
        for(StatusMotorGeradorRelatorio status : StatusMotorGeradorRelatorio.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
