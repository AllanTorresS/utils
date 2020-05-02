package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os itens de abastecimentos de autorizações oriundas do connect cta.
 */
public enum TipoItemAbastecimentoCta implements IEnumComLabel<TipoItemAbastecimentoCta> {

    COMBUSTIVEL(1),
    ADITIVO(2),
    OLEO_LUBRIFICANTE(3);

    private final Integer value;

    /**
     * Construtor do enum.
     *
     * @param value Valor numérico do enum.
     */
    TipoItemAbastecimentoCta(Integer value) {
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
    public static TipoItemAbastecimentoCta obterPorValor(Integer value) {
        for (TipoItemAbastecimentoCta tipoFormaPagamento : TipoItemAbastecimentoCta.values()) {
            if(tipoFormaPagamento.value.equals(value)) {
                return tipoFormaPagamento;
            }
        }
        return null;
    }

    /**
     * Verifica se o valor corresponde a Combustivel
     * @param value valor do enumerado
     * @return true se o valor fornecido representar Combustivel
     */
    public static boolean isCombustivel(Integer value) {
        return value != null && value.equals(COMBUSTIVEL.getValue());
    }

    /**
     * Verifica se o valor corresponde a Aditivo
     * @param value valor do enumerado
     * @return true se o valor fornecido representar Aditivo
     */
    public static boolean isAditivo(Integer value) {
        return value != null && value.equals(ADITIVO.getValue());
    }

    /**
     * Verifica se o valor corresponde a Oleo Lubrificante
     * @param value valor do enumerado
     * @return true se o valor fornecido representar Oleo Lubrificante
     */
    public static boolean isOleoLubrificante(Integer value) {
        return value != null && value.equals(OLEO_LUBRIFICANTE.getValue());
    }
}
