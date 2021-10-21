package ipp.aci.boleia.dominio.enums.salesforce;

import ipp.aci.boleia.dominio.enums.IEnumComValor;

/**
 * Enumera os tipos de solicitantes que podem abrir um chamado.
 */
public enum TipoSolicitanteChamado implements IEnumComValor {
    FROTA(0),
    REVENDA(1),
    INTERNO(2);

    private final Integer value;

    /**
     * Construtor do enum.
     *
     * @param value Valor do enum.
     */
    TipoSolicitanteChamado(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
