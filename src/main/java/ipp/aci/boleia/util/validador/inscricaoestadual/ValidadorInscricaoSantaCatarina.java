package ipp.aci.boleia.util.validador.inscricaoestadual;

/**
 * Validador de inscrição estadual
 */
public class ValidadorInscricaoSantaCatarina extends ValidadorInscricaoEstadualDefault {

    @Override
    protected String calcularDigitoVerificador(int soma) {
        int d = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1) {
            d = 0;
        }
        return Integer.toString(d);
    }
}