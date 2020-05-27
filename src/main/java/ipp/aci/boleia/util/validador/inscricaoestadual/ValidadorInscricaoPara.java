package ipp.aci.boleia.util.validador.inscricaoestadual;

/**
 * Validador de inscrição estadual
 */
public class ValidadorInscricaoPara extends ValidadorInscricaoEstadualGenerico  {

    @Override
    public boolean validar(String ie) {
        if (!validarDigitos(ie,9)) {
            return false; 
        }

        //valida os dois primeiros dígitos
        if (!"15".equals(ie.substring(0, 2))) {
            return false; 
        }

        //Calcula o dígito verificador
        int soma = 0;
        int peso = 9;
        int d; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1) {
            d = 0;
        }

        //valida o digito verificador
        String dv = Integer.toString(d);
        return ie.substring(ie.length() - 1, ie.length()).equals(dv);
    }
}