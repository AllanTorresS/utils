package ipp.aci.boleia.util;

/**
 * Constantes utilizadas pela integração com o CTA
 */
public final class ConstantesCta {

    public static final String SUCESSO = "001";
    public static final String ERRO = "-1";
    public static final String TEXTO_SUCESSO_CONNECT = "SUCESSO";
    public static final String TEXTO_FALHA_CONNECT = "ERRO";

    public static final String ERRO_VEICULO_FROTA = "Veículo não pertence a Frota do Abastecimento";
    public static final String ERRO_VEICULO_NAO_ENCONTRADO = "Veiculo (%s) não encontrado";
    public static final String ERRO_FRENTISTA_NAO_ENCONTRADO = "Frentista não encontrado";
    public static final String ERRO_FRENTISTA_CPF_INVALIDO = "Frentista não possui CPF válido";
    public static final String ERRO_FROTA_NAO_ENCONTRADA = "Frota não encontrada";
    public static final String ERRO_UNIDADE_EMPR_AGREG_NAO_ENCONTRADA = "Frota, Unidade e Empresa Agregada não encontradas";
    public static final String ERRO_TIPO_COMBUSTIVEL_NAO_ENCONTRADO = "Tipo Combustível não encontrado";
    public static final String ERRO_VEICULO_INVALIDO = "Veiculo inválido";
    public static final String ERRO_EMPRESA_INVALIDA = "Empresa inválida";
    public static final String ERRO_TIPO_COMBUSTIVEL_INVALIDO = "Tipo Combustível inválido";
    public static final String ERRO_MOTORISTA_INVALIDO = "Motorista inválido";
    public static final String ERRO_ID_ABASTECIMENTO_INVALIDO = "ID Abastecimento IpirangaConnect inválido";
    public static final String ERRO_COMBUSTIVEL_INVALIDO = "Abastecimento não é de COMBUSTIVEL, Categoria Produto = %s";
    public static final String ERRO_ABASTECIMENTO_IMPORTADO = "Abastecimento já foi importado";


    public static final String FORMATO_RESPOSTA = "json";
    public static final String QUANTIDADE_MAX_ABASTECIMENTOS = "100";
    public static final String PREFIXO_ERRO_LOG = "CTA Plus: Sincronizar Abastecimentos - Error:";

    /**
     * Impede instanciacao
     */
    private ConstantesCta() {
        // Impede instanciacao
    }
}
