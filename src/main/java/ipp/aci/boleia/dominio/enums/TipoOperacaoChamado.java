package ipp.aci.boleia.dominio.enums;

/**
 * Enumera os tipos de operação que o usuário interno pode realizar
 */
public enum TipoOperacaoChamado {
    INCLUSAO(1),
    CANCELAMENTO(2),
    ESTORNO(3),
    EDICAO(4);

    Integer value;

    /**
     * Construtor do enum
     * @param value valor do enum
     */
    TipoOperacaoChamado(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
