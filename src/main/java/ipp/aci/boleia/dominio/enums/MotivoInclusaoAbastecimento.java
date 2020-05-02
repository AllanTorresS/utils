package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica os motivos de uma inclusão de abastecimento
 */
public enum MotivoInclusaoAbastecimento implements IEnumComLabel<MotivoInclusaoAbastecimento> {

    FRAUDE("Fraude", 0),
    ERRO_VOLUME_QUANTIDADE("Volume ou quantidade errados", 1),
    ERRO_PRODUTO("Produto errado", 2),
    ERRO_VALOR("Valor errado",3),
    OUTROS("Outros", 4);

    private final String label;
    private final Integer value;

    /**
     * Construtor
     *
     * @param label O label do status
     */
    MotivoInclusaoAbastecimento(String label, Integer value) {
        this.label = label;
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
    public static MotivoInclusaoAbastecimento obterPorValor(Integer value) {
        for(MotivoInclusaoAbastecimento status : MotivoInclusaoAbastecimento.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
