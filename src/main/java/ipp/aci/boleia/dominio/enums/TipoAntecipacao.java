package ipp.aci.boleia.dominio.enums;

public enum TipoAntecipacao {

    SOLUCAO(0),
    PARCEIRO_XP(1);

    private Integer value;

    /**
     * Construtor do enum.
     *
     * @param value Valor do enum.
     */
    TipoAntecipacao(Integer value) {
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

    /**
     * Obtem por value
     * @param value value
     * @return Enum para o value
     */
    public TipoAntecipacao obterPorValor(Integer value) {
        for(TipoAntecipacao tipo : TipoAntecipacao.values()) {
            if(tipo.getValue().equals(value)) {
                return tipo;
            }
        }
        return null;
    }
}
