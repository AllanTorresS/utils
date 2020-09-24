package ipp.aci.boleia.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utilitário para formatar e fazer parsing de configurações do sistema
 */
public class UtilitarioConfiguracaoSistema {
    private static final String SEPARADOR_PARAMETROS = ",";

    /**
     * Impede a instanciacao e a heranca
     */
    private UtilitarioConfiguracaoSistema() {
        // Impede a instanciacao e a heranca
    }

    /**
     * Formata lista de parâmetros
     * @param parametros os parâmetros de configuração
     * @return os parâmetros formatados
     */
    public static String formatarParametros(List<String> parametros) {
        if (parametros != null) {
            return String.join(SEPARADOR_PARAMETROS, parametros);
        }
        return StringUtils.EMPTY;
    }

    /**
     * Obtém lista de parâmetros a partir de parâmetros formatados como string
     * @param parametros os parâmetros em formato de string
     * @return lista com os parâmetros presentes na string de entrada
     */
    public static List<String> lerParametros(String parametros) {
        if (StringUtils.isNotEmpty(StringUtils.strip(parametros))) {
            return Arrays.asList(StringUtils.split(parametros, SEPARADOR_PARAMETROS)).stream()
                    .map(StringUtils::strip)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
