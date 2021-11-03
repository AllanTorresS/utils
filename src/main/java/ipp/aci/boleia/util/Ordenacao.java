package ipp.aci.boleia.util;

/**
 * Enumera os sentidos de ordenacao possiveis para as pesquisas do sistema
 */
public enum Ordenacao {
    CRESCENTE("ASC"),
    DECRESCENTE("DESC");

    private String termoSql;

    /**
     * Construtor do enum.
     *
     * @param termoSql termo sql equivalente a ordenação.
     */
    private Ordenacao(String termoSql) {
        this.termoSql = termoSql;
    }

    public String getTermoSql() {
        return termoSql;
    }
}
