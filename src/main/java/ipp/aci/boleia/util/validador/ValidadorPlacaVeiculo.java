package ipp.aci.boleia.util.validador;

import ipp.aci.boleia.util.UtilitarioFormatacao;

import java.util.regex.Pattern;

/**
 * Classe de validação de Placa de Veiculo.
 */
public final class ValidadorPlacaVeiculo {
    private static final Pattern PATTERN_PLACA_MERCOSUL = Pattern.compile("[A-Z]{3}[0-9][A-Z][0-9]{2}");
    private static final Pattern PATTERN_PLACA = Pattern.compile("[A-Z]{3}-[0-9]{4}");

    /**
     * Construtor privado do utilitário.
     */
    private ValidadorPlacaVeiculo() {
        //nada a fazer
    }

    /**
     * Método de validação.
     *
     * @param placa String a ser validada como placa de um veiculo valida no Brasil.
     * @return True caso válido.
     */
    public static Boolean validarPlacaVeiculo(String placa) {
        if(placa == null) {
            return false;
        }
        String placaFormatada = UtilitarioFormatacao.formatarPlacaVeiculo(placa);
        return placaFormatada != null && (placaFormatada.length() == 7 && PATTERN_PLACA_MERCOSUL.matcher(placaFormatada).matches()
                || placaFormatada.length() == 8 && PATTERN_PLACA.matcher(placaFormatada).matches());
    }
}
