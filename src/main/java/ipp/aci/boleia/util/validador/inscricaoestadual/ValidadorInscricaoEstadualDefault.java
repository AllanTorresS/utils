package ipp.aci.boleia.util.validador.inscricaoestadual;

/**
 * Validador de inscrição estadual default para tratamento de logicas semelhantes ou igual
 * adotadas em diferentes estados da federacao.
 */
public class ValidadorInscricaoEstadualDefault extends ValidadorInscricaoEstadualGenerico {

    private static final int NUM_DIGITOS = 9;

    @Override
    public boolean validar(String inscricaoEstadual) {
        if (!validarDigitos(inscricaoEstadual, getNumeroDigitosEsperado())) {
            return false; 
        }
        int soma = obterSomaPeso(inscricaoEstadual);
        String digitoVerificador = calcularDigitoVerificador(soma);
        return inscricaoEstadual.substring(inscricaoEstadual.length() - 1, inscricaoEstadual.length()).equals(digitoVerificador);
    }

    /**
     * Retorna o numero de digitos esperado para a inscricao estadual
     * @return O numero de digitos esperado
     */
    protected int getNumeroDigitosEsperado() {
        return NUM_DIGITOS;
    }

    /**
     * Calcula o digito verificador da inscricao
     * @param soma A soma dos pesos calculada previamente
     * @return O digito verificador como string
     */
    protected String calcularDigitoVerificador(int soma) {
        int d = 11 - (soma % 11);
        if (d == 10 || d == 11) {
            d = 0;
        }
        return Integer.toString(d);
    }


}