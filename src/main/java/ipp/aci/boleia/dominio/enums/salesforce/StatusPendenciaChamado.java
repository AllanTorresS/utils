package ipp.aci.boleia.dominio.enums.salesforce;

import ipp.aci.boleia.dominio.enums.IEnumComValor;

/**
 * Enum com os status da pendencia de um usuário com um chamado salesforce.
 *
 * @author pedro.silva
 */
public enum StatusPendenciaChamado implements IEnumComValor {
    EM_ABERTO(0),
    RESOLVIDA(1);

    private final Integer value;

    /**
     * Construtor do enum.
     *
     * @param value Valor do enum.
     */
    StatusPendenciaChamado(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
