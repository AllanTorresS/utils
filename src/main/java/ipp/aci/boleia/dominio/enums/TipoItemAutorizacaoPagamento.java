package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os possíveis tipos de itens adicionais em um pagamento
 */
public enum TipoItemAutorizacaoPagamento implements IEnumComLabel<TipoItemAutorizacaoPagamento> {

    ABASTECIMENTO(1),
    PRODUTO_SERVICO(2);

    private final Integer value;

    /**
     * Construtor
     * @param value O valor da enum
     */
    TipoItemAutorizacaoPagamento(Integer value) {
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
    public static TipoItemAutorizacaoPagamento obterPorValor(Integer value) {
        for(TipoItemAutorizacaoPagamento tipoFormaPagamento : TipoItemAutorizacaoPagamento.values()) {
            if(tipoFormaPagamento.value.equals(value)) {
                return tipoFormaPagamento;
            }
        }
        return null;
    }

    /**
     * Verifica se o valor corresponde a Abastecimento
     * @param value valor do enumerado
     * @return true se o valor fornecido representar abastecimento
     */
    public static boolean isAbastecimento(Integer value) {
        return value != null && value.equals(ABASTECIMENTO.getValue());
    }

    /**
     * Verifica se o valor corresponde a Produto/Serviço
     * @param value valor do enumerado
     * @return true se o valor fornecido representar produto ou serviço
     */
    public static boolean isProdutoServico(Integer value) {
        return value != null && value.equals(PRODUTO_SERVICO.getValue());
    }
}
