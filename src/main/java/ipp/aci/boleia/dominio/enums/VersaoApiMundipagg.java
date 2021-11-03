package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica qual versão da api da mundipagg foi usado para comprar de credito de uma frota pré-paga
 */
public enum VersaoApiMundipagg implements IEnumComLabel<VersaoApiMundipagg>, IEnumComValor {

    PRIMEIRA(1),
    SECUNDA(2);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    VersaoApiMundipagg(Integer value) {
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
    public static VersaoApiMundipagg obterPorValor(Integer value) {
        for(VersaoApiMundipagg status : VersaoApiMundipagg.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
