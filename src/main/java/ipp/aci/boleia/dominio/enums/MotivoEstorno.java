package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica os motivos de um estorno de transação
 */
public enum MotivoEstorno implements IEnumComLabel<MotivoEstorno> {

    DUPLICIDADE(0, true),
    FRAUDE(1, true),
    NAO_RECONHECIMENTO(2, true),
    ERRO_VOLUME_QUANTIDADE(3, false),
    ERRO_PRODUTO(4, false),
    ERRO_VALOR(5, false),
    OUTROS(6, false);

    private final Integer value;
    private final Boolean semAlteracao;

    /**
     * Construtor
     *
     * @param value O value do status
     * @param  semAlteracao verifica se existe alteracao
     */
    MotivoEstorno(Integer value, Boolean semAlteracao) {
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
    public static MotivoEstorno obterPorValor(Integer value) {
        for(MotivoEstorno status : MotivoEstorno.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
