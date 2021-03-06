package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.ConstantesDuracao;

/**
 * Enumeração para controle dos locks de agendamento da solução
 */
public enum LockAgendamento {

    CADASTRO_MOTORISTA_ALLOW_ME                     (ConstantesDuracao.UM_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    CONSOLIDACAO_CICLO                              (ConstantesDuracao.DUAS_HORAS, ConstantesDuracao.DUAS_HORAS),
    CONSOLIDACAO_CICLO_AG_FRETE                     (ConstantesDuracao.DUAS_HORAS, ConstantesDuracao.DUAS_HORAS),
    STATUS_NOTA_FISCAL                              (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    ATUALIZAR_PV_JDE                                (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    PRECOS_VIGENTES                                 (ConstantesDuracao.UM_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    REGISTRO_KMV_PENDENTE                           (ConstantesDuracao.QUINZE_MINUTOS, ConstantesDuracao.UM_SEGUNDO),
    AVISO_DE_DEBITO                                 (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    AVISO_DE_CREDITO                                (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    VERIFICACAO_STATUS_FROTA                        (ConstantesDuracao.UM_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    VERIFICACAO_SALDO_FROTA                         (ConstantesDuracao.UM_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    CALCULO_PRECO_MEDIO                             (ConstantesDuracao.DUAS_HORAS, ConstantesDuracao.UM_SEGUNDO),
    CALCULO_PRECO_MAXIMO_SUGERIDO                   (ConstantesDuracao.UM_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    VERIFICACAO_ABASTECIMENTO_PENDENTE_AUTORIZACAO  (ConstantesDuracao.UM_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    CALCULO_COTA_SUGERIDA_VEICULOS                  (ConstantesDuracao.QUATRO_HORAS, ConstantesDuracao.UM_SEGUNDO),
    RENOVACAO_COTA_VEICULOS                         (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    VERIFICACAO_EXPIRACAO_PROXIMA_TOKEN             (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    VERIFICACAO_PAGAMENTO_MUNDIPAGG                 (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    INATIVACAO_FROTA_PRE_SEM_SALDO                  (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    ATUALIZACAO_DADOS_TELEMETRIA_SASCAR             (ConstantesDuracao.CINCO_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    ATUALIZACAO_VEICULOS_SASCAR                     (ConstantesDuracao.CINCO_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    PROPAGACAO_DE_PRECOS_FROTA                      (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    ATUALIZACAO_DADOS_TELEMETRIA_ONIXSAT            (ConstantesDuracao.CINCO_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    ATUALIZACAO_VEICULOS_ONIXSAT                    (ConstantesDuracao.CINCO_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    VIGENCIA_IMPORTACAO_PRECO_POSTO                 (ConstantesDuracao.UM_MINUTO_E_MEIO, ConstantesDuracao.UM_SEGUNDO),
    SINCRONIZA_ABASTECIMENTO                        (ConstantesDuracao.CINCO_MINUTO,ConstantesDuracao.UM_SEGUNDO),
    CONCILIACAO_POS_ABASTECE_AI                     (ConstantesDuracao.CINCO_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    ENVIO_REPASSE_JDE                               (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    CONCILIACAO_REPASSE                             (ConstantesDuracao.CINCO_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    ATUALIZACAO_STATUS_CAMPANHA                     (ConstantesDuracao.CINCO_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    PUBLICACAO_DE_DOCUMENTOS                        (ConstantesDuracao.DUAS_HORAS, ConstantesDuracao.UM_SEGUNDO),
    IMPORTACAO_EMAILS_NOTAS                         (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    IMPORTACAO_CONCILIACAO_AUTOMATICA_NOTAS_FISCAIS (ConstantesDuracao.DUAS_HORAS, ConstantesDuracao.DUAS_HORAS),
    EMAIL_MENSAL_KMV_DONO_FROTA                     (ConstantesDuracao.CINCO_MINUTO,ConstantesDuracao.UM_SEGUNDO),
    INTEGRACAO_APCO                                 (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    VERIFICACAO_STATUS_AG_FRETE                     (ConstantesDuracao.DUAS_HORAS, ConstantesDuracao.DUAS_HORAS),
	COBRANCA_CONECTCAR                              (ConstantesDuracao.DUAS_HORAS, ConstantesDuracao.DUAS_HORAS),
	REEMBOLSO_CONECTCAR                             (ConstantesDuracao.DUAS_HORAS, ConstantesDuracao.DUAS_HORAS),
	AVISO_DE_DEBITO_CONECTCAR                       (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
	AVISO_DE_CREDITO_CONECTCAR                      (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
	EXCLUSAO_DADOS_PESSOAIS_MOTORISTAS              (ConstantesDuracao.CINCO_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    AVISO_MOTORISTAS_INAVAVOS_E_SEM_ABASTECIMENTO   (ConstantesDuracao.CINCO_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    EXCLUSAO_DADOS_MOTORISTAS                       (ConstantesDuracao.CINCO_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    VALE_PEDAGIO                                    (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    DISTRIBUICAO_AUTOMATICA_DE_BENEFICIOS           (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    CANCELAMENTO_CHAMADOS_PENDENTES                 (ConstantesDuracao.QUINZE_MINUTOS, ConstantesDuracao.UM_SEGUNDO),
    ATUALIZACAO_PEDIDO_CREDITO_BENEFICIOS           (ConstantesDuracao.DUAS_HORAS, ConstantesDuracao.UM_SEGUNDO);

	private final long duracao;
    private final long duracaoMinima;
    /**
     * Construtor da enum
     *
     * @param duracao duracao do lock
     * @param duracaoMinima o valor mínimo para a duração
     */
    LockAgendamento(Long duracao, Long duracaoMinima) {
        this.duracao = duracao;
        this.duracaoMinima = duracaoMinima;
    }

    /**
     * Retorna a duracao do lock em milissegundos
     *
     * @return A duracao do lock
     */
    public long getDuracao() {
        return duracao;
    }

    /**
     * Retorna a duracao mínima do lock em milissegundos
     *
     * @return A duracao mínima do lock
     */
    public long getDuracaoMinima() {
        return duracaoMinima;
    }
}
