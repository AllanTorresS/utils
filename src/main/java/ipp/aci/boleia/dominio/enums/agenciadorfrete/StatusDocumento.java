package ipp.aci.boleia.dominio.enums.agenciadorfrete;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Lista os campos de status de um documento
 */
public enum StatusDocumento implements IEnumComLabel<TipoCobranca> {

    NAO_INTEGRADO(0),
    EM_ABERTO(1),
    PAGO(2);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do tipo de cobrança
     */
    StatusDocumento(Integer value) {
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
    public static StatusDocumento obterPorValor(Integer value) {
        for(StatusDocumento status : StatusDocumento.values()) {
            if(status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
}
