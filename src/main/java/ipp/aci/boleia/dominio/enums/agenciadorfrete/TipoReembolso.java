package ipp.aci.boleia.dominio.enums.agenciadorfrete;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Representa o tipo de cobrança
 */
public enum TipoReembolso implements IEnumComLabel<TipoReembolso> {

    ABASTECIMENTO(1),
    SAQUE(2);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do tipo de cobrança
     */
    TipoReembolso(Integer value) {
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
    public static TipoReembolso obterPorValor(Integer value) {
        for(TipoReembolso status : TipoReembolso.values()) {
            if(status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
}
