package ipp.aci.boleia.dominio.enums;

/**
 * Enum com status de existência de dados de rastreador na {@link ipp.aci.boleia.dominio.AutorizacaoPagamento}
 *
 */
public enum  StatusRastreador {

    DADOS_OBTIDOS(1),
    DADOS_NAO_ENCONTRADOS(0),
    PLACA_NAO_CADASTRADA(-1);

    private Integer value;

    /**
     * Construtor da classe
     * @param value Valor
     */
    StatusRastreador(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
