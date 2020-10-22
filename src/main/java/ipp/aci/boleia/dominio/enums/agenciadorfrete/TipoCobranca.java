package ipp.aci.boleia.dominio.enums.agenciadorfrete;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Representa o tipo de cobrança
 */
public enum TipoCobranca implements IEnumComLabel<TipoCobranca> {

    ABASTECIMENTO(1),
    SAQUE(2);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do tipo de cobrança
     */
    TipoCobranca(Integer value) {
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
    public static TipoCobranca obterPorValor(Integer value) {
        for(TipoCobranca status : TipoCobranca.values()) {
            if(status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
}
