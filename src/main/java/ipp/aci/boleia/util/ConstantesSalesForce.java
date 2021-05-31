package ipp.aci.boleia.util;

/**
 * Constantes utilizadas pela integração com o Sales Force
 */
public final class ConstantesSalesForce {

    public static final String PROSPECT = "Prospect";
    public static final String RECORD_TYPE_ID = "012f2000000ANFwAAO";
    public static final String ORIGIN = "Portal";
    public static final String SOLICITANTE_FROTA = "Frota";
    public static final String SOLICITANTE_REVENDA = "Posto";
    public static final String SOLICITANTE_INTERNO = "Interno";
    public static final String ACCOUNT_FROTA = "-CL";
    public static final String ACCOUNT_PV = "-PV";
    public static final String PRIORITY = "Média";

    public static final String CAMPO_STATUS = "Status";
    public static final String CAMPO_TIPO = "Type";
    public static final String CAMPO_SISTEMA_ORIGEM = "ClassificacaoPerfil__c";
    public static final String CAMPO_MOTIVO = "Motivo__c";
    public static final String CAMPO_MODULO = "MotivoSolicitacao__c";

    /**
     * Impede instanciacao
     */
    private ConstantesSalesForce() {
        // Impede instanciacao
    }
}
