package ipp.aci.boleia.util;

/**
 * Constantes utilizadas pela integração com a NDD
 */
public final class ConstantesNdd {

    public static final String SALDO_API_ENDPOINT = "/Supplies/{orderNumber}/AmountAvailable";
    public static final int DIAS_CICLO = 1;
    public static final String CONFIRMAR_TRANSACAO = "/Supplies/Confirm";
    public static final String CANCELAR_TRANSACAO = "/Supplies/Cancel";
    public static final String ATUALIZAR_TRANSACAO = "/Supplies";
    public static final String TIPO_AUTENTICACAO_ATUALIZACAO = "refresh_token";
    public static final String TIPO_AUTENTICACAO_CRIACAO = "client_credentials";

    public static final int MINUTOS_EXPIRACAO_TOKEN = 20;

    /**
     * Impede instanciacao
     */
    private ConstantesNdd() {
        // Impede instanciacao
    }
}
