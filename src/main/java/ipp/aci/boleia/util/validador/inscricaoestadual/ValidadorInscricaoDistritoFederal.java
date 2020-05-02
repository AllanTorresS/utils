package ipp.aci.boleia.util.validador.inscricaoestadual;

/**
 * Validador de inscrição estadual
 */
public class ValidadorInscricaoDistritoFederal extends ValidadorInscricaoEstadualGenerico {

    @Override
    public boolean validar(String ie) {
        if (!validarDigitos(ie,13)) {
            return false; 
        }

        //cálculo do primeiro dígito verificador
        int soma = 0;
        int pesoInicio = 4;
        int pesoFim = 9;
        int d1; //primeiro dígito verificador
        for (int i = 0; i < ie.length() - 2; i++) {
            if (i < 3) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d1 = 11 - (soma % 11);
        if (d1 == 11 || d1 == 10) {
            d1 = 0;
        }

        //cálculo do segundo dígito verificador
        soma = d1 * 2;
        pesoInicio = 5;
        pesoFim = 9;
        int d2; //segundo dígito verificador
        for (int i = 0; i < ie.length() - 2; i++) {
            if (i < 4) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d2 = 11 - (soma % 11);
        if (d2 == 11 || d2 == 10) {
            d2 = 0;
        }

        //valida os digitos verificadores
        String dv = Integer.toString(d1) + Integer.toString(d2);
        return dv.equals(ie.substring(ie.length() - 2, ie.length()));
    }
}