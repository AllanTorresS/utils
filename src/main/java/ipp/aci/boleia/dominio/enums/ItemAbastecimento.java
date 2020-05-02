package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica os itens de abastecimento
 */
public enum ItemAbastecimento implements IEnumComLabel<ItemAbastecimento> {

   	DIESEL_COMUM("A"),
   	DIESEL_S500("A"),
   	DIESEL_S500_COMUM("A"),
   	GASOLINA("B"),
   	GASOLINA_COMUM("B"),
   	ETANOL_COMUM("C"),
   	ETANOL("C"),
   	GASOLINA_ADITIVADA("D"),
   	GASOLINA_ALTA_OCTANAGEM("F"),
   	ETANOL_ADITIVADO("E"),
   	GASOLINA_PREMIUM("F"),
   	GAS_METANO_VEICULAR("G"),
   	DIESEL_ADITIVADO("H"),
   	DIESEL_S500_ADITIVADO("H"),
   	DIESEL_S10("S"),
   	DIESEL_S10_COMUM("S"),
   	DIESEL_S10_ADITIVADO("T"),
   	ARLA_32("T");

    private final String value;

    /**
     * Construtor
     *
     * @param value O valor do item
     */
    ItemAbastecimento(String value) {
        this.value = value;
    }

    /**
     * Obtem o valor do item
     * @return O valor do item
     */
    public String getValue() {
        return value;
    }

    /**
     * Obtem por label
     * @param label label
     * @return Enum para o label
     */
    public static ItemAbastecimento obterPorLabel(String label) {
    	for (ItemAbastecimento itemAbastecimento : ItemAbastecimento.values()) {
            if(itemAbastecimento.getLabel().equals(label)) {
                return itemAbastecimento;
            }
    	}
    	return null;
    }

}