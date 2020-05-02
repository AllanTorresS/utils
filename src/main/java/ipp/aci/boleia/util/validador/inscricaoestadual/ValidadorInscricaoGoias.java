package ipp.aci.boleia.util.validador.inscricaoestadual;

/**
 * Validador de inscrição estadual
 */
public class ValidadorInscricaoGoias extends ValidadorInscricaoEstadualGenerico {

    @Override
    public boolean validar(String ie) {
        if (!validarDigitos(ie,9)) {
            return false; 
        }

        //vílida os dois primeiros dígito
        if (!"10".equals(ie.substring(0, 2)) && !"11".equals(ie.substring(0, 2)) && !"15".equals(ie.substring(0, 2))) {
            return false; 
        }

        if ("11094402".equals(ie.substring(0, ie.length() - 1))
                && !"0".equals(ie.substring(ie.length() - 1, ie.length()))
                && !"1".equals(ie.substring(ie.length() - 1, ie.length()))) {
            return false; 
        } else {

            // Cálculo do dígito verificador
            int soma = 0;
            int peso = 9;
            int d; //dígito verificador
            for (int i = 0; i < ie.length() - 1; i++) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                peso--;
            }

            int resto = soma % 11;
            long faixaInicio = 10103105;
            long faixaFim = 10119997;
            long insc = Long.parseLong(ie.substring(0, ie.length() - 1));
            if (resto == 0) {
                d = 0;
            } else if (resto == 1) {
                if (insc >= faixaInicio && insc <= faixaFim) {
                    d = 1;
                } else {
                    d = 0;
                }
            } else {
                d = 11 - resto;
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