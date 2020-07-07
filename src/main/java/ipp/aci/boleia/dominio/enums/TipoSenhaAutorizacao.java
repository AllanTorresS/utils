package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os possíveis tipos de senha para abastecimento no sistema
 */
public enum TipoSenhaAutorizacao implements IEnumComLabel<TipoSenhaAutorizacao> {

    SENHA_CONTINGENCIA(1),
    CODIGO_ABASTECIMENTO(2),
    CODIGO_ABASTECIMENTO_SMS(3),
    INCLUSAO_MANUAL(4),
    SENHA_MOTORISTA(5),
    CODIGO_ABASTECIMENTO_POS(6),
    SEM_SENHA_OU_CODIGO(7);

    private final Integer value;

    /**
     * Construtor
     * @param value O valor da enum
     */
    TipoSenhaAutorizacao(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor da enumercao
     * @return o valor da enumeracao
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static TipoSenhaAutorizacao obterPorValor(Integer value) {
        for(TipoSenhaAutorizacao tipoFormaPagamento : TipoSenhaAutorizacao.values()) {
            if(tipoFormaPagamento.value.equals(value)) {
                return tipoFormaPagamento;
            }
        }
        return null;
    }
}
