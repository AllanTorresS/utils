package ipp.aci.boleia.dominio.enums;

/**
 * Enumera os tipos de acesso feitos ao sistema.
 *
 * @author pedro.silva
 */
public enum TipoAcesso {
    PORTAL(1),
    PDV(2);

    private Integer value;

    /**
     * Construtor do enum.
     *
     * @param value Valor do enum.
     */
    TipoAcesso(Integer value) {
        this.value = value;
    }

    /**
     * Retorna o valor do enum.
     *
     * @return valor do enum.
     */
    public Integer getValue() {
        return value;
    }
}
