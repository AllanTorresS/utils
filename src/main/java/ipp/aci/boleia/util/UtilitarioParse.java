package ipp.aci.boleia.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * Utilitario para simplicar o tratamento de parse e conversoes
 */
public final class UtilitarioParse {

    private static final String TRUE = "1";
    private static final String SEPARADOR_HORA_MINUTO = ":";
    private static final Integer DIA_EM_HORAS = 24;
    private static final Integer HORA_EM_MINUTOS = 60;
    private static final Integer DIA_EM_SEGUNDOS = 3600;
    private static final Integer MINUTO_EM_SEGUNDOS = 60;

    /**
     * Impede instanciação
     */
    private UtilitarioParse() { }

    /**
     * Converte uma String para Long e caso a string esteja fora de formato é retornado um valor default.
     *
     * @param valor String a ser convertida para Long
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
    public static Long tryParseLong(String valor){
        return tryParseLong(valor, null);
    }

    /**
     * Converte uma String no formato numerico (1 = true ou false) em um valor booleano
     * @param parametro String a ser convertida
     * @return O valor booleano resultante
     */
    public static Boolean numericoStringParaBooleano(String parametro) {
        return TRUE.equals(parametro);
    }

    /**
     * Converte um horário no formato "HH24:MM" para segundos
     *
     * @param horario o horário a ser transformado
     * @return o horário em segundos
     */
    public static Integer horaMinutoParaSegundos(String horario) {
        if (horario != null) {
            String[] horaMinuto = horario.split(SEPARADOR_HORA_MINUTO);
            if (horaMinuto.length == 2 && Arrays.stream(horaMinuto).allMatch(StringUtils::isNumeric)) {
                int horas = Integer.parseInt(horaMinuto[0]);
                int minutos = Integer.parseInt(horaMinuto[1]);

                if (horas < DIA_EM_HORAS && minutos < HORA_EM_MINUTOS) {
                    return horas * DIA_EM_SEGUNDOS + minutos * MINUTO_EM_SEGUNDOS;
                }
            }
        }

        return null;
    }
}
