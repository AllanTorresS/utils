package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status da chave de acesso à api
 */
public enum StatusApiToken implements IEnumComLabel<StatusApiToken> {

    ATIVA(1),
    BLOQUEADA(0),
    EXPIRADA(2),
    NAO_GERADA(3);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusApiToken(Integer value) {
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
    public static StatusApiToken obterPorValor(Integer value) {
        for(StatusApiToken status : StatusApiToken.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
