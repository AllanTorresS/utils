package ipp.aci.boleia.dominio.enums;
import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os possíveis status de confirmação para uma transação de frota leve
 */
public enum StatusConfirmacaoTransacao implements IEnumComLabel<StatusConfirmacaoTransacao> {
    NAO_CONFIRMADO(-1),
    CONFIRMADO_FALHA(0),
    CONFIRMADO_SUCESSO(1);

    private final Integer value;

    StatusConfirmacaoTransacao(Integer value){
        this.value = value;
    }

    /**
     * Obtem o valor da enum
     * @return O valor da enum
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static StatusConfirmacaoTransacao obterPorValor(Integer value) {
        for(StatusConfirmacaoTransacao statusConfirmacaoTransacao : StatusConfirmacaoTransacao.values()) {
            if(statusConfirmacaoTransacao.value.equals(value)) {
                return statusConfirmacaoTransacao;
            }
        }
        return null;
    }
}
