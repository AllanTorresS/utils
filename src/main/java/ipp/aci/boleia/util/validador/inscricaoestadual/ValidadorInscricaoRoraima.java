package ipp.aci.boleia.util.validador.inscricaoestadual;

/**
 * Validador de inscrição estadual
 */
public class ValidadorInscricaoRoraima extends ValidadorInscricaoEstadualGenerico {

    @Override
    public boolean validar(String ie) {
        if (!validarDigitos(ie,9)) {
            return false; 
        }

        //valida os dois primeiros dígitos
        if (!"24".equals(ie.substring(0, 2))) {
            return false; 
        }

        int soma = 0;
        int peso = 1;
        int d; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso++;
        }

        d = soma % 9;

        //valida o digito verificador
        String dv = Integer.toString(d);
        return ie.substring(ie.length() - 1, ie.length()).equals(dv);
    }
}