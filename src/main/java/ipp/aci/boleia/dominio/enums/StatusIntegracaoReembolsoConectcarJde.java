package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status da integração de uma entidade de reembolso
 */
public enum StatusIntegracaoReembolsoConectcarJde implements IEnumComLabel<StatusAtivacao> {

    ERRO_ENVIO(0),
    REALIZADO(1),
    PENDENTE(2),
    AGUARDANDO_LIBERACAO(3),
    ERRO_LIBERACAO(4);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusIntegracaoReembolsoConectcarJde(Integer value) {
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
    public static StatusIntegracaoReembolsoConectcarJde obterPorValor(Integer value) {
        for(StatusIntegracaoReembolsoConectcarJde status : StatusIntegracaoReembolsoConectcarJde.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}