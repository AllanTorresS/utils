package ipp.aci.boleia.util.validador.inscricaoestadual;

/**
 * Validador de inscrição estadual
 */
public class ValidadorInscricaoEspiritoSanto extends ValidadorInscricaoEstadualGenerico {

    @Override
    public boolean validar(String ie) {
        if (!validarDigitos(ie,9)) {
            return false; 
        }

        // Cálculo do dígito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        int resto = soma % 11;
        if (resto < 2) {
            d = 0;
        } else if (resto > 1) {
            d = 11 - resto;
        }

        //valida o digito verificador
        String dv = Integer.toString(d);
        return ie.substring(ie.length() - 1, ie.length()).equals(dv);
    }
}