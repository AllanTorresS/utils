package ipp.aci.boleia.util;

/**
 * Constantes utilizadas pela integração com o Sales Force
 */
public final class ConstantesSalesForce {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_TIPO_CONCESSAO = "grant_type";
    public static final String AUTHORIZATION_CLIENTE_ID = "client_id";
    public static final String AUTHORIZATION_CLIENTE_SEGREDO = "client_secret";
    public static final String AUTHORIZATION_USUARIO = "username";
    public static final String AUTHORIZATION_SENHA = "password";

    public static final String CAMPO_TIPO_TOKEN = "token_type";
    public static final String CAMPO_TOKEN_ACESSO = "access_token";
    public static final String CAMPO_INSTANCIA_URL = "instance_url";
    
    public static final String CODIGO_ERRO_SESSAO_EXPIRADA = "INVALID_SESSION_ID";

    public static final String PROSPECT = "Prospect";
    public static final String RECORD_TYPE_ID = "012f2000000ANFwAAO";
    public static final String ORIGIN = "Portal";
    public static final String SOLICITANTE_FROTA = "Frota";
    public static final String SOLICITANTE_REVENDA = "Posto";
    public static final String SOLICITANTE_INTERNO = "Interno";
    public static final String ACCOUNT_FROTA = "-CL";
    public static final String ACCOUNT_PV = "-PV";
    public static final String STATUS_CANCELAMENTO = "Cancelado";
    public static final String STATUS_NOVO = "Novo";
    public static final String STATUS_EM_ATENDIMENTO = "Em atendimento";
    public static final String STATUS_AGUARDANDO_ATENDIMENTO = "Aguardando atendimento";
    public static final String STATUS_PENDENTE_EXTERNO = "Pendente Externo";
    public static final String LOCALIZACAO_CONTEUDO_ANEXO = "S";
    public static final String VALOR_CAMPO_VISIBILIDADE_ANEXO = "AllUsers";

    public static final String CAMPO_ID = "id";
    public static final String CAMPO_STATUS = "Status";
    public static final String CAMPO_TIPO = "Type";
    public static final String CAMPO_SISTEMA_ORIGEM = "ClassificacaoPerfil__c";
    public static final String CAMPO_MOTIVO = "Motivo__c";
    public static final String CAMPO_MODULO = "MotivoSolicitacao__c";
    public static final String CAMPO_MOTIVO_CANCELAMENTO = "MotivoCancelamento__c";
    public static final String CAMPO_MOTIVO_CANCELAMENTO_VALOR_DUPLICADO = "Duplicado";
    public static final String CAMPO_MOTIVO_CANCELAMENTO_SEM_RETORNO= "Sem Retorno";
    public static final String CAMPO_CORPO_COMENTARIO = "CommentBody";
    public static final String CAMPO_CHAMADO_COMENTARIO = "ParentId";
    public static final String CAMPO_TOTAL_ITEMS = "totalSize";
    public static final String CAMPO_REGISTROS = "records";
    public static final String CAMPO_ID_DOCUMENTO = "ContentDocumentId";
    public static final String CAMPO_ID_CHAMADO_ANEXO = "LinkedEntityId";
    public static final String CAMPO_VISIBILIDADE_ANEXO = "Visibility";

    /**
     * Impede instanciacao
     */
    private ConstantesSalesForce() {
        // Impede instanciacao
    }
}
