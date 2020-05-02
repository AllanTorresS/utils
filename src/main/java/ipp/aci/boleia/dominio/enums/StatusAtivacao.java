package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica se uma entidade esta ativa ou inativa
 */
public enum StatusAtivacao implements IEnumComLabel<StatusAtivacao> {

    ATIVO(1),
    INATIVO(0);

    public static final String DECODE_FORMULA = "DECODE(ID_STATUS, 1, 'AT', 0, 'IN')";

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusAtivacao(Integer value) {
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
    public static StatusAtivacao obterPorValor(Integer value) {
        for(StatusAtivacao status : StatusAtivacao.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
