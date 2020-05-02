package ipp.aci.boleia.util.validador.inscricaoestadual;

/**
 * Validador de inscrição estadual
 */
public class ValidadorInscricaoAmapa extends ValidadorInscricaoEstadualGenerico {

    @Override
    public boolean validar(String ie) {
        if (!validarDigitos(ie,9)) {
            return false;
        }

        //verifica os dois primeiros dígitos - deve ser igual 03
        if (!"03".equals(ie.substring(0, 2))) {
            return false; 
        }

        //calcula o dígito verificador
        int d1 = -1;
        int soma = -1;
        int peso = 9;

        //configura o valor do dígito verificador e da soma de acordo com faixa das inscriçães
        long x = Long.parseLong(ie.substring(0, ie.length() - 1)); //x = inscrição estadual sem o dígito verificador
        if (x >= 3017001L && x <= 3019022L) {
            d1 = 1;
            soma = 9;
        } else if (x >= 3000001L && x <= 3017000L) {
            d1 = 0;
            soma = 5;
        } else if (x >= 3019023L) {
            d1 = 0;
            soma = 0;
        }

        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        int d = 11 - (soma % 11); //d = armazena o dígito verificador apís cálculo
        if (d == 10) {
            d = 0;
        } else if (d == 11) {
            d = d1;
        }

        //valida o digito verificador
        String dv = Integer.toString(d);
        return ie.substring(ie.length() - 1, ie.length()).equals(dv);
    }
}