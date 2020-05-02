package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica os motivos de um estorno de transação
 */
public enum MotivoCancelamento implements IEnumComLabel<MotivoCancelamento> {

    DUPLICIDADE(0, true),
    NAO_RECONHECIMENTO(2, true),
    OUTROS(6, false);

    private final Integer value;
    private final Boolean semAlteracao;

    /**
     * Construtor
     *
     * @param value O value do status
     * @param  semAlteracao verifica se existe alteracao
     */
    MotivoCancelamento(Integer value, Boolean semAlteracao) {
        this.value = value;
        this.semAlteracao = semAlteracao;
    }

    public Integer getValue() {
        return value;
    }

    public Boolean getSemAlteracao() {
        return semAlteracao;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static MotivoCancelamento obterPorValor(Integer value) {
        for(MotivoCancelamento status : MotivoCancelamento.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
