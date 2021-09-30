package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os status de pedido tag
 */
public enum TipoBloqueioConectcar implements IEnumComLabel<TipoBloqueioConectcar> {

	INDIVIDUAL("I"),
    LOTE("L"),
    CONTRATO("C");

    private final String value;

    /**
     * Construtor padrão
     * @param value O valor do status
     */
    TipoBloqueioConectcar(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static TipoBloqueioConectcar obterPorValor(String value) {
        for(TipoBloqueioConectcar status : TipoBloqueioConectcar.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
