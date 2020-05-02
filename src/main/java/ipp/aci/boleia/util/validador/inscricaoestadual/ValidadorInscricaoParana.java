package ipp.aci.boleia.util.validador.inscricaoestadual;

/**
 * Validador de inscrição estadual
 */
public class ValidadorInscricaoParana extends ValidadorInscricaoEstadualGenerico  {

    @Override
    public boolean validar(String ie) {
        if (!validarDigitos(ie,10)) {
            return false; 
        }

        // Cálculo do primeiro dígito
        int soma = 0;
        int pesoInicio = 3;
        int pesoFim = 7;
        int d1; //dígito verificador
        for (int i = 0; i < ie.length() - 2; i++) {
            if (i < 2) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d1 = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1) {
            d1 = 0;
        }

        //cálculo do segundo dígito
        soma = d1 * 2;
        pesoInicio = 4;
        pesoFim = 7;
        int d2; //segundo dígito
        for (int i = 0; i < ie.length() - 2; i++) {
            if (i < 3) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d2 = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1) {
            d2 = 0;
        }

        //valida os digitos verificadores
        String dv = Integer.toString(d1) + Integer.toString(d2);
        return dv.equals(ie.substring(ie.length() - 2, ie.length()));
    }
}