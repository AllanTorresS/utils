package ipp.aci.boleia.util.validador.inscricaoestadual;

/**
 * Validador de inscrição estadual
 */
public class ValidadorInscricaoAlagoas extends ValidadorInscricaoEstadualGenerico {

    @Override
    public boolean validar(String ie) {
        if (!validarDigitos(ie,9)) {
            return false; 
        }

        //valida os dois primeiros dígitos - deve ser iguais a 24
        if (!"24".equals(ie.substring(0, 2))) {
            return false; 
        }

        //valida o terceiro dígito - deve ser 0,3,5,7,8
        int[] digits = { 0, 3, 5, 7, 8 };
        boolean check = false;
        for (int digit : digits) {
            if (Integer.parseInt(String.valueOf(ie.charAt(2))) == digit) {
                check = true;
                break;
            }
        }
        if (!check) {
            return false; 
        }

        //calcula o dígito verificador
        int soma = obterSomaPeso(ie);
        int d; //dígito verificador

        d = ((soma * 10) % 11);
        if (d == 10) {
            d = 0;
        }

        //valida o digito verificador
        String dv = Integer.toString(d);
        return ie.substring(ie.length() - 1, ie.length()).equals(dv);
    }
}