package ipp.aci.boleia.dominio.enums;

/**
 * Enumera os tipos de atividade que um componente de um ponto de venda pode ter.
 */
public enum TipoAtividadeComponente {
    AREA_ABASTECIMENTO("1"),
    CONVENIENCIA_MERCADOS("2"),
    AREA_ABASTECIMENTO_OUTRA("99");

    private String codigoCorporativo;

    /**
     * Construtor do enum.
     *
     * @param codigoCorporativo Código corporativo da atividade
     */
    TipoAtividadeComponente(String codigoCorporativo) {
        this.codigoCorporativo = codigoCorporativo;
    }

    public String getCodigoCorporativo() {
        return codigoCorporativo;
    }
}
