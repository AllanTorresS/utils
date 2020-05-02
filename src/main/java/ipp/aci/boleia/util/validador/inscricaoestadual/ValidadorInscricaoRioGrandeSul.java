package ipp.aci.boleia.util.validador.inscricaoestadual;

/**
 * Validador de inscrição estadual
 */
public class ValidadorInscricaoRioGrandeSul extends ValidadorInscricaoEstadualDefault {

    private static final int NUMERO_DIGITOS = 10;

    @Override
    protected int getNumeroDigitosEsperado() {
        return NUMERO_DIGITOS;
    }

    @Override
    protected int obterSomaPeso(String inscricaoEstadual) {
        int soma = Integer.parseInt(String.valueOf(inscricaoEstadual.charAt(0))) * 2;
        int peso = 9;
        for (int i = 1; i < inscricaoEstadual.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(inscricaoEstadual.charAt(i))) * peso;
            peso--;
        }
        return soma;
    }
}