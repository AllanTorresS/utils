package ipp.aci.boleia.dominio.enums;

/**
 * Contem os valores absolutos dos status das entidades PrecoBase
 */
public enum ValorStatusPrecoBase {

    NOVO(0),
    VIGENTE(1),
    PENDENTE_INTERNO(2),
    PENDENTE_REVENDEDOR(3),
    HISTORICO(4);

    private Integer valor;

    /**
     * Construtor privado
     * @param valor O valor do status
     */
    ValorStatusPrecoBase(Integer valor) {
        this.valor = valor;
    }

    /**
     * Obtem o valor do status
     * @return O valor do status
     */
    public Integer getValor() {
        return valor;
    }
}
