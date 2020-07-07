package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.ConstantesDuracao;

/**
 * Enumeração para controle dos locks de agendamento da solução
 */
public enum LockAgendamento {

    CADASTRO_ALLOW_ME_DISPOSITIVO_MOTORISTA         (ConstantesDuracao.UM_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    CONSOLIDACAO_NOTAS_FISCAIS                      (ConstantesDuracao.DUAS_HORAS, ConstantesDuracao.DUAS_HORAS),
    VERIFICACAO_STATUS_NOTAS_FISCAIS                (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    ATUALIZACAO_PV_JDE                              (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    VIGENCIA_AUTOMATICA_PRECOS                      (ConstantesDuracao.UM_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    REGISTRO_KMV                                    (ConstantesDuracao.UM_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    VERIFICACAO_AVISO_DEBITO                        (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    VERIFICACAO_AVISO_CREDITO                       (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    VERIFICACAO_FROTA_ATIVACAO_TEMPORARIA           (ConstantesDuracao.TRINTA_MINUTOS, ConstantesDuracao.UM_SEGUNDO),
    VERIFICACAO_SALDO_FROTA                         (ConstantesDuracao.UM_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    CONSOLIDACAO_PRECO_MEDIO_FROTA                  (ConstantesDuracao.DUAS_HORAS, ConstantesDuracao.UM_SEGUNDO),
    CONSOLIDACAO_PRECO_MAXIMO_FROTA                 (ConstantesDuracao.UM_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    VERIFICACAO_ABASTECIMENTO_PENDENTE_AUTORIZACAO  (ConstantesDuracao.UM_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    ATUALIZACAO_COTA_VEICULO                        (ConstantesDuracao.QUATRO_HORAS, ConstantesDuracao.UM_SEGUNDO),
    RENOVACAO_COTA_VEICULO                          (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    VERIFICACAO_EXPIRACAO_PROXIMA_TOKEN             (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    VERIFICACAO_PAGAMENTO_CREDITO_PREPAGO           (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    INATIVACAO_FROTA_SALDO_PRE_ZERADO               (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    ATUALIZACAO_DADOS_TELEMETRIA_SASCAR             (ConstantesDuracao.CINCO_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    ATUALIZACAO_VEICULOS_SASCAR                     (ConstantesDuracao.CINCO_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    PROPAGACAO_DE_PRECOS_FROTA                      (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    ATUALIZACAO_DADOS_TELEMETRIA_ONIXSAT            (ConstantesDuracao.CINCO_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    ATUALIZACAO_VEICULOS_ONIXSAT                    (ConstantesDuracao.CINCO_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    VIGENCIA_IMPORTACAO_PRECO_POSTO                 (ConstantesDuracao.UM_MINUTO_E_MEIO, ConstantesDuracao.UM_SEGUNDO),
    BUSCA_DADOS_CONNECTCTA                          (ConstantesDuracao.CINCO_MINUTO,ConstantesDuracao.UM_SEGUNDO),
    CONCILIACAO_POS_ABASTECE_AI                     (ConstantesDuracao.CINCO_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    ENVIO_REPASSE_JDE                               (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    CONCILIACAO_REPASSE                             (ConstantesDuracao.CINCO_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    ATUALIZACAO_STATUS_CAMPANHA                     (ConstantesDuracao.CINCO_MINUTO, ConstantesDuracao.UM_SEGUNDO),
    IMPORTACAO_EMAILS_NOTAS                         (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO),
    IMPORTACAO_CONCILIACAO_AUTOMATICA_NOTAS_FISCAIS (ConstantesDuracao.DUAS_HORAS, ConstantesDuracao.DUAS_HORAS),
    EMAIL_MENSAL_KMV_DONO_FROTA                     (ConstantesDuracao.CINCO_MINUTO,ConstantesDuracao.UM_SEGUNDO),
    INTEGRACAO_APCO                                 (ConstantesDuracao.UMA_HORA, ConstantesDuracao.UM_SEGUNDO);

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
