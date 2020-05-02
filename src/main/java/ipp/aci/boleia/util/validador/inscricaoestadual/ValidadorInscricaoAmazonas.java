package ipp.aci.boleia.util.validador.inscricaoestadual;

/**
 * Validador de inscrição estadual
 */
public class ValidadorInscricaoAmazonas extends ValidadorInscricaoEstadualDefault {

    @Override
    protected String calcularDigitoVerificador(int soma) {
        int d;
        if (soma < 11) {
            d = 11 - soma;
        } else if ((soma % 11) <= 1) {
            d = 0;
        } else {
            d = 11 - (soma % 11);
        }
        return Integer.toString(d);
    }
}