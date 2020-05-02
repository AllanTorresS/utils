package ipp.aci.boleia.util.validador;

import ipp.aci.boleia.util.UtilitarioFormatacao;

/**
 * Validador de cpf.
 */
public final class ValidadorCpf {

    private static final int[] PESO_CPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    public static final int TAMANHO_CPF = 11;

    /**
     * Impede instanciacao e heranca
     */
    private ValidadorCpf() {
        // Impede instanciacao e heranca
    }

    /**
     * Verifica se o @param valor é  um cpf válido.
     *
     * @param valor cpf
     * @return true para cpf invalido
     */
    public static boolean invalidCPF(String valor) {
        String valorFormatado = UtilitarioFormatacao.obterDigitosMascara(valor);
        String cpf = "";
        if (valorFormatado.length() == 14) {
            cpf = valorFormatado.substring(3, 14);
        } else if (valorFormatado.length() == 11) {
            cpf = valorFormatado;
        } else if (valorFormatado.length() < 11) {
            cpf = UtilitarioFormatacao.formatarNumeroZerosEsquerda(valorFormatado, 11);
        }

        if ((cpf.isEmpty()) || (cpf.length() != 11) || cpf.matches(cpf.charAt(0) + "{11}")) {
            return true;
        }
        Integer digito1 = calcularDigito(cpf.substring(0, 9), PESO_CPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, PESO_CPF);

        return !cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    /**
     * Calcula digito de acordo com algoritmo de validade de CPF
     *
     * @param str digitos cpf
     * @param peso pesos do algoritmo
     * @return digito calculado
     */
    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1; indice >= 0; indice--) {
            int digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

}
