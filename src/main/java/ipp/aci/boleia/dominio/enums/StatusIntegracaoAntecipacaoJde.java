package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status da integração de uma proposta da XP
 */
public enum StatusIntegracaoAntecipacaoJde implements IEnumComLabel<StatusIntegracaoAntecipacaoJde> {

    PREVISTO(0),
    ANTECIPADO(1),
    ERRO_ENVIO_F7(2),
    ERRO_ENVIO_PV(3),
    ERRO_ENVIO_F7_PV(4);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusIntegracaoAntecipacaoJde(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}