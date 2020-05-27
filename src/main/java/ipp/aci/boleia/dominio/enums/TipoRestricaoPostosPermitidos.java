package ipp.aci.boleia.dominio.enums;

/**
 * Enumera os diferentes tipos de restrição de um posto
 * no parametro de uso Postos Permitidos.
 *
 * @author pedro.silva
 */
public enum TipoRestricaoPostosPermitidos {

    VALOR(1),
    LITRAGEM(2);

    private Integer valor;

    /**
     * Construtor do enum.
     *
     * @param valor Valor da enumeração.
     */
    TipoRestricaoPostosPermitidos(Integer valor) {
        this.valor = valor;
    }

    public Integer getValor() {
        return valor;
    }

    /**
     * Informa se o enum é do tipo VALOR.
     *
     * @return True, caso seja.
     */
    public boolean isValor() {
        return equals(VALOR);
    }

    /**
     * Obtém um enum a partir do valor.
     *
     * @param valor Valor do enum
     * @return Enum obtido
     */
    public static TipoRestricaoPostosPermitidos obterPorValor(Integer valor) {
        for (TipoRestricaoPostosPermitidos tipoRestricao : values()) {
            if(tipoRestricao.valor.equals(valor)) {
                return tipoRestricao;
            }
        }
        return null;
    }
}