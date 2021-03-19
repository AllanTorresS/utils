package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.dominio.parametros.ITratadorViolacoesParametros;
import ipp.aci.boleia.dominio.parametros.TratadorViolacoesParametrosAbastecimento;
import ipp.aci.boleia.dominio.parametros.TratadorViolacoesParametrosPreAutorizacao;

/**
 * Enumera os possiveis grupos de execucao de parametros de sistema
 */
public enum GrupoExecucaoParametroSistema {

    AUTORIZACAO_PAGAMENTO(
        TratadorViolacoesParametrosAbastecimento.class,
        ParametroSistema.VOLUME_ABASTECIDO,
        ParametroSistema.PRODUTO_ABASTECIMENTO,
        ParametroSistema.PRODUTOS_ADICIONAIS_PERMITIDOS,
        ParametroSistema.HORARIOS_ABASTECIMENTO,
        ParametroSistema.CONSUMO_ESTIMADO,
        ParametroSistema.PRECO_MAXIMO,
        ParametroSistema.HODOMETRO_HORIMETRO,
        ParametroSistema.COTA_VEICULO,
        ParametroSistema.INTERVALO_ABASTECIMENTO,
        ParametroSistema.POSTOS_AUTORIZADOS_ABASTECIMENTO,
        ParametroSistema.CREDITO_VEICULO_AGREGADO,
        ParametroSistema.LOCALIZACAO_ABASTECIMENTO),

    AUTORIZACAO_ABASTECIMENTO(
        TratadorViolacoesParametrosAbastecimento.class,
        ParametroSistema.VOLUME_ABASTECIDO,
        ParametroSistema.PRODUTO_ABASTECIMENTO,
        ParametroSistema.CONSUMO_ESTIMADO,
        ParametroSistema.HODOMETRO_HORIMETRO),

    AUTORIZACAO_PAGAMENTO_MANUAL(TratadorViolacoesParametrosAbastecimento.class),

    PRE_AUTORIZACAO_FROTA(
        TratadorViolacoesParametrosPreAutorizacao.class,
        ParametroSistema.POSTOS_AUTORIZADOS_ABASTECIMENTO),

    PRE_AUTORIZACAO_PLACA(
        TratadorViolacoesParametrosPreAutorizacao.class,
        ParametroSistema.HORARIOS_ABASTECIMENTO,
        ParametroSistema.INTERVALO_ABASTECIMENTO,
        ParametroSistema.COTA_VEICULO,
        ParametroSistema.CREDITO_VEICULO_AGREGADO),

    PRE_AUTORIZACAO_HODOMETRO_HORIMETRO(
        TratadorViolacoesParametrosPreAutorizacao.class,
        ParametroSistema.HODOMETRO_HORIMETRO,
        ParametroSistema.INTERVALO_ABASTECIMENTO),

    PRE_AUTORIZACAO_LITRAGEM(
        TratadorViolacoesParametrosPreAutorizacao.class,
        ParametroSistema.CONSUMO_ESTIMADO,
        ParametroSistema.VOLUME_ABASTECIDO);


    private final Class<ITratadorViolacoesParametros<?>> tratadorViolacoes;
    private final ParametroSistema[] parametros;

    /**
     * Construtor do Enum
     * @param tratadorViolacoes Classe tratadora de violações de parâmetro
     * @param parametros Parâmetros do sistema
     */
    GrupoExecucaoParametroSistema(Class tratadorViolacoes, ParametroSistema... parametros) {
        this.tratadorViolacoes = tratadorViolacoes;
        this.parametros = parametros;
    }

    public ParametroSistema[] getParametrosOrdenadosPorCriticidade() {
        return parametros;
    }

    public Class<ITratadorViolacoesParametros<?>> getTratadorViolacoes() {
        return tratadorViolacoes;
    }

    /**
     * Verifica se o grupo em questao contem o parametro informado
     *
     * @param param O parametro desejado
     * @return true caso contenha o parametro
     */
    public boolean contem(ParametroSistema param) {
        for (ParametroSistema parametro : parametros) {
            if (parametro.equals(param)) {
                return true;
            }
        }
        return false;
    }
}