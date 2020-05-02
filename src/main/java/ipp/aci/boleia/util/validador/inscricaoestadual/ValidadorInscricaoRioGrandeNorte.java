package ipp.aci.boleia.util.validador.inscricaoestadual;

/**
 * Validador de inscrição estadual
 */
public class ValidadorInscricaoRioGrandeNorte extends ValidadorInscricaoEstadualGenerico {

    @Override
    public boolean validar(String ie) {
        if (!validarDigitos(ie,10) && !validarDigitos(ie,9)) {
            return false; 
        }

        //valida os dois primeiros dígitos
        if (!"20".equals(ie.substring(0, 2))) {
            return false; 
        }

        //calcula o dígito para inscrição de 9 dígitos
        if (ie.length() == 9) {
            int d; //dígito verificador
            int soma = obterSomaPeso(ie);

            d = (soma * 10) % 11;
            if (d == 10) {
                d = 0;
            }

            //valida o digito verificador
            String dv = Integer.toString(d);
            if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
                return false; 
            }
        } else {
            int soma = 0;
            int peso = 10;
            int d; //dígito verificador
            for (int i = 0; i < ie.length() - 1; i++) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                peso--;
            }
            d = (soma * 10) % 11;
            if (d == 10) {
                d = 0;
            }

            //valida o digito verificador
            String dv = Integer.toString(d);
            if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
                return false; 
            }
        }
        return true;
    }

}