package ipp.aci.boleia.util.validador;

import org.apache.commons.lang3.StringUtils;

/**
 * Realiza a validacao de numeros de CNPJ
 */
public final class ValidadorCnpj {

    /**
     * Impede instanciacao e heranca
     */
    private ValidadorCnpj() {
        // Impede instanciacao e heranca
    }

    /**
     * Verifica se um CNPJ e valido
     * @param cnpj o CNPJ a ser validado
     * @return true caso valido
     */
    public static boolean isValidCNPJ(String cnpj) {
        if (cnpj.length() < 14) {
            cnpj = StringUtils.leftPad(cnpj,14,"0");
        }
        return validacaoBasica(cnpj) && conferirDigitosVerificadores(cnpj);
    }

    /**
     * Confere os dígitos verificadores do CNPJ
     * @param cnpj o CNPJ a ser validado
     * @return true caso os digitos verificadores estejam corretos
     */
    private static boolean conferirDigitosVerificadores(String cnpj) {
        char primeiroVerificador = calcularDigitoVerificador(cnpj, 11);
        char segundoVerificador = calcularDigitoVerificador(cnpj, 12);
        return (primeiroVerificador == cnpj.charAt(12)) && (segundoVerificador == cnpj.charAt(13));
    }

    /**
     * Calcula um digito verificador a partir do modulo previamente calculado
     * @param cnpj         O cnpj
     * @param posicaoFinal A ultima posicao do CNPJ a ser considerada no calculo
     * @return O digito verificador esperado
     */
    private static char calcularDigitoVerificador(String cnpj, int posicaoFinal) {

        int soma = 0;
        int peso = 2;

        for (int i = posicaoFinal; i >= 0; i--) {
            int num = (cnpj.charAt(i) - 48);
            soma = soma + (num * peso);
            peso = peso + 1;
            if (peso == 10) {
                peso = 2;
            }
        }

        int modulo = soma % 11;
        if ((modulo == 0) || (modulo == 1)) {
            return '0';
        } else {
            return (char) ((11 - modulo) + 48);
        }
    }

    /**
     * Valida o tamanho da string e se nao e composta por digitos iguais
     *
     * @param cnpj String de CNPJ a validar
     * @return false caso seja um cnpj invalido
     */
    private static boolean validacaoBasica(String cnpj) {

        if (cnpj.length() != 14) {
            return false;
        }

        char primeiro = cnpj.charAt(0);
        for (int i = 1; i < cnpj.length(); i++) {
            if(cnpj.charAt(i) != primeiro) {
                return true;
            }
        }

        return false;
    }
}
