package ipp.aci.boleia.util.validador.inscricaoestadual;

/**
 * Validador de inscrição estadual
 */
public class ValidadorInscricaoTocantins extends ValidadorInscricaoEstadualGenerico  {

    @Override
    public boolean validar(String ie) {
        //valida quantida de dígitos
        if (!validarDigitos(ie,9) && !validarDigitos(ie,11)) {
            return false; //throw new Exception("Quantidade de dígitos invílida.");
        } else if (validarDigitos(ie,9)) {
            ie = ie.substring(0, 2) + "02" + ie.substring(2);
        }

        int soma = 0;
        int peso = 9;
        int d; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            if (i != 2 && i != 3) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                peso--;
            }
        }
        d = 11 - (soma % 11);
        if ((soma % 11) < 2) {
            d = 0;
        }

        //valida o digito verificador
        String dv = Integer.toString(d);
        return ie.substring(ie.length() - 1, ie.length()).equals(dv);
    }
}