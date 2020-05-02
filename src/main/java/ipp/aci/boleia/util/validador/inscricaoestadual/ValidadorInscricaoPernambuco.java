package ipp.aci.boleia.util.validador.inscricaoestadual;

/**
 * Validador de inscrição estadual
 */
public class ValidadorInscricaoPernambuco extends ValidadorInscricaoEstadualGenerico {

    @Override
    public boolean validar(String ie) {

        if (validarDigitos(ie,14)) {
            // Cálculo do dígito verificador
            int soma = 0;
            int pesoInicio = 5;
            int pesoFim = 9;
            int d; //dígito verificador

            for (int i = 0; i < ie.length() - 1; i++) {
                if (i < 5) {
                    soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                    pesoInicio--;
                } else {
                    soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                    pesoFim--;
                }
            }

            d = 11 - (soma % 11);
            if (d > 9) {
                d -= 10;
            }

            //valida o digito verificador
            String dv = Integer.toString(d);
            return ie.substring(ie.length() - 1, ie.length()).equals(dv);
        } else if(validarDigitos(ie,9)) {
            long[] numero = new long[9];
            String nuDigitoVerificador = ie.substring(7,9);

            for (int i = 0; i < 7; i++) {
                numero[i] = (ie.charAt(i) - 48);
            }
            long soma1 = 0;
            for (int i = 0; i < 7; i++) {
                soma1 += numero[i] * (8 - i);
            }
            long resto1 = soma1 % 11;
            if (resto1 == 0 || resto1 == 1) {
                numero[7] = 0;
            } else {
                numero[7] = 11 - resto1;
            }

            long soma2 = (numero[7] * 2);
            for (int i = 0; i < 7; i++) {
                soma2 += numero[i] * (9 - i);
            }

            long resto2 = soma2 % 11;
            if (resto2 == 0 || resto2 == 1) {
                numero[8] = 0;
            } else {
                numero[8] = 11 - resto2;
            }

            return nuDigitoVerificador.contentEquals("" + numero[7] + numero[8]);
        }
        return false;
    }
}