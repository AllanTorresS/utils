package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status da integração de uma entidade
 */
public enum StatusIntegracaoJde implements IEnumComLabel<StatusAtivacao>, IEnumComValor {

    ERRO_ENVIO(0),
    REALIZADO(1);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusIntegracaoJde(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static StatusIntegracaoJde obterPorValor(Integer value) {
        for(StatusIntegracaoJde status : StatusIntegracaoJde.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}