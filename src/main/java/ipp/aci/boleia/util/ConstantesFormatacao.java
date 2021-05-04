package ipp.aci.boleia.util;

/**
 * Constantes que representam os formatos do sistema.
 */
public final class ConstantesFormatacao {

    public static final String FORMATO_MOEDA_REAL = "R$ ###,##0.";
    public static final String FORMATO_MOEDA_REAL_SEM_PREFIXO = "###,##0.";
    public static final String FORMATO_MOEDA_REAL_COM_SINAL = "+R$ ###,##0.00;-R$ ";
    public static final String FORMATO_PERCENTUAL = "##0.00%";
    public static final String FORMATO_KILOMETROS = "###,### Km";
    public static final String FORMATO_HORAS = "###,##0.00 H";
    public static final String FORMATO_LITROS = "###,##0.000 L";
    public static final String FORMATO_LITROS_DUAS_CASAS = "###,##0.00 L";
    public static final String FORMATO_DUAS_CASAS = "###,##0.00";
    public static final String FORMATO_TRES_CASAS = "##0.000";
    public static final String FORMATO_MDR = "###,##0.00";
    public static final String FORMATO_TELEFONE_DEZ = "(##) ####-####";
    public static final String FORMATO_TELEFONE_ONZE = "(##) #####-####";
    public static final String FORMATO_NUMERO_NOTA = "###.###.###";
    public static final String FORMATO_INTEIRO = "###,###";
    public static final String FORMATO_CPF = "###.###.###-##";
    public static final String FORMATO_CPF_OCULTO = "'*'*'*.'*'*'*.'*'*#-##";
    public static final String FORMATO_CNPJ = "##.###.###/####-##";
    public static final String FORMATO_CEP = "#####-###";
    public static final String FORMATO_CHAVE_ACESSO = "#### #### #### #### #### #### #### #### #### #### ####";
    public static final String FORMATO_SERIE_NOTA_FISCAL = "###";
    public static final String FORMATO_PREFIXO_VALOR_EXTENSO = "****";
    public static final String FORMATO_SUFIXO_VALOR_EXTENSO = "******";
    public static final String FORMATO_DATA_CURTA = "dd/MM/yyyy";
    public static final String PADRAO_DATA_CURTA_HIFEN = "dd-MM-yyyy";
    public static final String FORMATO_DATA_CURTA_ANO_CURTO = "dd/MM/yy";
    public static final String FORMATO_DATA_HORA = "dd/MM/yyyy HH:mm";
    public static final String FORMATO_DATA_CURTA_HORA = "dd/MM/yy HH:mm";
    public static final String FORMATO_DATA_HORA_MINUTOS_SEGUNDOS = "dd/MM/yyyy HH:mm:ss";
    public static final String FORMATO_HORA_MINUTOS = "HH:mm";
    public static final String FORMATO_HORA_MINUTOS_SEGUNDOS = "HH:mm:ss";
    public static final String FORMATO_DATA_JSON = "yyyy-MM-dd";
    public static final String FORMATO_DIA_MES = "dd/MM";
    public static final String FORMATO_MES_ANO = "MM/yyyy";
    public static final String FORMATO_MES_ANO_ISO = "yyyy-MM";
    public static final String FORMATO_DIA_MES_HORA_MINUTO = "dd/MM à's' HH:mm";
    public static final String FORMATO_DIA = "dd";
    public static final String FORMATO_MES = "MM";
    public static final String FORMATO_ANO = "yyyy";
    public static final String FORMATO_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ssXXX";
    public static final String FORMATO_ISO_8601_COM_MILLIS = "yyyy-MM-dd'T'HH:mm:ss.sssXXX";
    public static final String FORMATO_ISO_8601_SEM_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String FORMATO_ISO_8601_COM_MILLIS_SEM_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String FORMATO_ISO_8601_COM_MILLIS_E_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String FORMATO_TIMEZONE_GMT_3 = "GMT-3";
    public static final String SEPARADOR_CHAVE_IDENTIFICADORA_AGRUPAMENTO_CICLOS = "|";
    public static final String REGEX_FORMATO_HORA_MINUTO = "((?:(?:0|1)\\d|2[0-3])):([0-5]\\d)";

    /**
     * Impede instanciacao
     */
    private ConstantesFormatacao() {
        // Impede instanciacao
    }
}
