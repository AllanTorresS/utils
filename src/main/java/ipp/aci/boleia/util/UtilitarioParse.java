package ipp.aci.boleia.util;

/**
 * Utilitario para simplicar o tratamento de parse e conversoes
 */
public final class UtilitarioParse {

    private static final String TRUE = "1";

    /**
     * Impede instanciação
     */
    private UtilitarioParse() {
    }

    /**
     * Converte uma String para Long e caso a string esteja fora de formato é retornado um valor default.
     *
     * @param valor        String a ser convertida para Long
     * @param valorDefault Valor a ser retornado em caso de string fora de formato
     * @return O valor Long convertido
     */
    public static Long tryParseLong(String valor, Long valorDefault) {
        try {
            return Long.parseLong(valor);
        } catch (NumberFormatException e) {
            return valorDefault;
        }
    }

    /**
     * Converte uma String para Long e caso a string esteja fora de formato é retornado null.
     *
     * @param valor String a ser convertida para Long
     * @return O valor Long convertido
     */
    public static Long tryParseLong(String valor) {
        return tryParseLong(valor, null);
    }

    /**
     * Converte uma String no formato numerico (1 = true ou false) em um valor booleano
     *
     * @param parametro String a ser convertida
     * @return O valor booleano resultante
     */
    public static Boolean numericoStringParaBooleano(String parametro) {
        return TRUE.equals(parametro);
    }

}
