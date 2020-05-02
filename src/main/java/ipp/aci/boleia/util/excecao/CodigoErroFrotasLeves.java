package ipp.aci.boleia.util.excecao;

/**
 * Enumera os códigos de erro pertencentes aos fluxos do frotas leves.
 */
public enum CodigoErroFrotasLeves {
    SUCESSO(0, ""),
    CODIGO_ABASTECIMENTO_INVALIDO(1, "Erro.FROTAS_LEVES.CODIGO_ABASTECIMENTO_INVALIDO"),
    PEDIDO_INVALIDO(2, "Erro.FROTAS_LEVES.PEDIDO_INVALIDO"),
    PEDIDO_COMBUSTIVEL_INVALIDO(3, "Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    PONTO_VENDA_INVALIDO(4, "Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    PONTO_VENDA_INATIVO(5,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    VIP_AUTENTICACAO_INVALIDA(6,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    MOTORISTA_INVALIDO(7,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    MOTORISTA_INATIVO(8,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    ABASTECIMENTO_FINALIZADO(9,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    FROTA_INVALIDA(10,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    FROTA_INATIVA(11,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    VEICULO_INVALIDO(12,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    VEICULO_INATIVO(13,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    HODOMETRO_HORIMETRO_INVALIDO(14,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    NSU_ZACC_INVALIDO(30, "Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    TERMINAL_INVALIDO(31, "Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    CODIGO_VIP_INVALIDO(32, "Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    PRODUTO_INDISPONIVEL(33, "Erro.FROTAS_LEVES.PRODUTO_INDISPONIVEL"),
    VIOLACAO_RESTRICAO_USO(42, "Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    EXCECAO_GENERICA(43, "Erro.FROTAS_LEVES.NAO_AUTORIZADO"),

    VOLUME_ABASTECIDO(18,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    PRODUTO_ABASTECIMENTO(19,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    PRODUTOS_ADICIONAIS_PERMITIDOS(20,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    HORARIOS_ABASTECIMENTO(21,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    CONSUMO_ESTIMADO(22,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    PRECO_MAXIMO(23,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    HODOMETRO_HORIMETRO(24,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    COTA_VEICULO(25,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    INTERVALO_ABASTECIMENTO(26,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    POSTOS_AUTORIZADOS_ABASTECIMENTO(27,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    CREDITO_VEICULO_AGREGADO(28,"Erro.FROTAS_LEVES.NAO_AUTORIZADO"),
    LOCALIZACAO_ABASTECIMENTO(29,"Erro.FROTAS_LEVES.NAO_AUTORIZADO");

    private final Integer value;
    private final String label;

    /**
     * Construtor da classe.
     *
     * @param value Identificador numérico do enumerador.
     * @param label Label do código de erro.
     */
    CodigoErroFrotasLeves(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static CodigoErroFrotasLeves obterPorValor(Integer value) {
        for(CodigoErroFrotasLeves codigoErroFrotasLeves : CodigoErroFrotasLeves.values()) {
            if(codigoErroFrotasLeves.value.equals(value)) {
                return codigoErroFrotasLeves;
            }
        }
        return null;
    }

    /**
     * Obtem por nome
     * @param nome value
     * @return Enum para o nome
     */
    public static CodigoErroFrotasLeves obterPorNome(String nome) {
        for(CodigoErroFrotasLeves codigoErroFrotasLeves : CodigoErroFrotasLeves.values()) {
            if(codigoErroFrotasLeves.toString().equals(nome)) {
                return codigoErroFrotasLeves;
            }
        }
        return null;
    }
}
