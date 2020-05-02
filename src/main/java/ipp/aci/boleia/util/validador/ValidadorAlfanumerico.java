package ipp.aci.boleia.util.validador;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validador de string alfanumerica ou numerica.
 */
public final class ValidadorAlfanumerico {

    /**
     * Impede instanciacao e heranca
     */
    private ValidadorAlfanumerico() {
        // Impede instanciacao e heranca
    }

    /**
     * Verifica se a string passada possui apenas letras e numeros
     * @param string string a validar
     * @return true para valida
     */
    public static boolean isAlfanumerico(String string) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]+");
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    /**
     * Verifica se a string passada possui apenas numeros
     * @param string string a validar
     * @return true para valida
     */
    public static boolean isNumerico(String string) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    /**
     * Verifica se a string passada possui apenas numeros
     * @param string string a validar
     * @return true para valida
     */
    public static boolean isDecimal(String string) {
        try {
            BigDecimal decimal = new BigDecimal(string);
            return decimal.scale()>0 || decimal.precision()>0;
        }catch (NumberFormatException ex){
            //NADA A FAZER
        }
        return false;
    }

}
