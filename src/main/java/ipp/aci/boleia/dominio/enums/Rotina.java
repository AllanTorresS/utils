package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

import java.util.Arrays;

/**
 * Indica as rotinas implementadas no sistema
 */
public enum Rotina implements IEnumComLabel<Rotina> {

    CONSOLIDACAO_CICLO(0),
    STATUS_NOTA_FISCAL(1),
    AVISO_DE_DEBITO(2),
    AVISO_DE_CREDITO(3),
    ATUALIZAR_PV_JDE(4),
    PRECOS_VIGENTES(5),
    CADASTRO_MOTORISTA_ALLOW_ME(6),
    REGISTRO_KMV_PENDENTE(7),
    CALCULO_COTA_SUGERIDA_VEICULOS(8),
    CALCULO_PRECO_MEDIO(9),
    CALCULO_PRECO_MAXIMO_SUGERIDO(10),
    RENOVACAO_COTA_VEICULOS(11),
    VERIFICACAO_EXPIRACAO_PROXIMA_TOKEN(12),
    VERIFICACAO_PAGAMENTO_MUNDIPAGG(13),
    INATIVACAO_FROTA_PRE_SEM_SALDO(14),
    ATUALIZACAO_DADOS_TELEMETRIA_SASCAR(15),
    ATUALIZACAO_VEICULOS_SASCAR(16),
    PROPAGACAO_DE_PRECOS_FROTA(17),
    ATUALIZACAO_DADOS_TELEMETRIA_ONIXSAT(18),
    ATUALIZACAO_VEICULOS_ONIXSAT(19),
    VIGENCIA_IMPORTACAO_PRECO_POSTO(20),
    SINCRONIZA_ABASTECIMENTO(21),
    CONCILIACAO_POS_ABASTECE_AI(22),
    ENVIO_REPASSE_JDE(23),
    CONCILIACAO_REPASSE(24),
    IMPORTACAO_CONCILIACAO_AUTOMATICA_NOTAS_FISCAIS(27),
    IMPORTACAO_EMAILS_NOTAS(28),
    EMAIL_MENSAL_KMV_DONO_FROTA(29),
    INTEGRACAO_APCO(30),
    VERIFICACAO_ABASTECIMENTO_PENDENTE_AUTORIZACAO(31),
    VERIFICACAO_FROTA_ATIVACAO_TEMPORARIA(32),
    VERIFICACAO_SALDO_FROTA(33),
    CONSOLIDACAO_CICLO_AG_FRETE(34),
    PUBLICACAO_DE_DOCUMENTOS(35),
    VERIFICACAO_STATUS_AG_FRETE(36),
	COBRANCA_CONECTCAR(37),
	REEMBOLSO_CONECTCAR(38),
	AVISO_DE_DEBITO_CONECTCAR(39),
	AVISO_DE_CREDITO_CONECTCAR(40),
	EXCLUSAO_DADOS_PESSOAIS_MOTORISTAS(41);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    Rotina(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static Rotina obterPorValor(Integer value) {
        for(Rotina status : Rotina.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }

    /**
     * Obtém o enumerado referente ao parâmetro fornecido
     *
     * @param nome nome da rotina
     * @return enumerado ou nulo
     */
    public static Rotina obterPorNome(String nome) {
        return Arrays.stream(Rotina.values())
                .filter(t -> t.name().equalsIgnoreCase(nome))
                .findFirst().orElse(null);
    }
}
