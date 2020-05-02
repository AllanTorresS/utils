package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica se uma entidade esta autorizada ou não autorizada
 */
public enum StatusAutorizacao implements IEnumComLabel<StatusAutorizacao> {

    AUTORIZADO(1),
    NAO_AUTORIZADO(0),
    AGUARDANDO_SALDO(2),
    AGUARDANDO_APROVACAO_SOLUCAO(3),
    PENDENTE_AUTORIZACAO(4),
    CANCELADO(-1);

    public static final String DECODE_FORMULA = "DECODE(ID_STATUS, 1, 'AUTORIZ', 0, 'NAO_AUTORIZ', 2, 'AGUARD_SALDO', 3, 'AGUARD_AUT', 4, 'PEND_AUT', -1, 'CANCEL')";

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
