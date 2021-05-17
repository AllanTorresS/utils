package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera as opções de local de destino padrão da nota fiscal
 */
public enum LocalDestinoPadroNfe implements IEnumComLabel<LocalDestinoPadroNfe> {

	MATRIZ(1),
    VEICULO(2),
    ABASTECIMENTO(3);

    private final Integer value;

    /**
     * Construtor padrão
     * @param value O valor do status
     */
    LocalDestinoPadroNfe(Integer value) {
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
    public static LocalDestinoPadroNfe obterPorValor(Integer value) {
        for(LocalDestinoPadroNfe status : LocalDestinoPadroNfe.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
