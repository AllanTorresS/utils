package ipp.aci.boleia.dominio.enums.agenciadorfrete;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica se uma entidade esta autorizada ou não autorizada
 */
public enum StatusAutorizacao implements IEnumComLabel<StatusAutorizacao> {

    AUTORIZADO(1),
    NAO_AUTORIZADO(0),
    PENDENTE_CONFIRMACAO(2);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusAutorizacao(Integer value) {
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
    public static StatusAutorizacao obterPorValor(Integer value) {
        for(StatusAutorizacao status : StatusAutorizacao.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
