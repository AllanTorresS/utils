package ipp.aci.boleia.util.validador.inscricaoestadual;

/**
 * Validador de inscrição estadual
 */
public class ValidadorInscricaoMinasGerais extends ValidadorInscricaoEstadualGenerico {

    @Override
    public boolean validar(String ie) {
        if (!validarDigitos(ie,13)) {
            return false; 
        }

        //iguala a casas para o cálculo
        //em inserir o algarismo zero "0" imediatamente apís o nímero de cídigo do município,
        //desprezando-se os dígitos de controle.
        String str = "";
        for (int i = 0; i < ie.length() - 2; i++) {
            if (Character.isDigit(ie.charAt(i))) {
                if (i == 3) {
                    str = String.format("%s0", str);
                }
                str = str.concat(Character.toString(ie.charAt(i)));
            }
        }

        // Cálculo do primeiro dígito verificador
        int soma = 0;
        int pesoInicio = 1;
        int pesoFim = 2;
        int d1; //primeiro dígito verificador
        for (int i = 0; i < str.length(); i++) {
            if (i % 2 == 0) {
                int x = Integer.parseInt(String.valueOf(str.charAt(i))) * pesoInicio;
                String strX = Integer.toString(x);
                for (int j = 0; j < strX.length(); j++) {
                    soma += Integer.parseInt(String.valueOf(strX.charAt(j)));
                }
            } else {
                int y = Integer.parseInt(String.valueOf(str.charAt(i))) * pesoFim;
                String strY = Integer.toString(y);
                for (int j = 0; j < strY.length(); j++) {
                    soma += Integer.parseInt(String.valueOf(strY.charAt(j)));
                }
            }
        }

        int dezenaExata = soma;
        while (dezenaExata % 10 != 0) {
            dezenaExata++;
        }
        d1 = dezenaExata - soma; //resultado - primeiro dígito verificador

        // Cálculo do segundo dígito verificador
        soma = d1 * 2;
        pesoInicio = 3;
        pesoFim = 11;
        int d2;
        for (int i = 0; i < ie.length() - 2; i++) {
            if (i < 2) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d2 = 11 - (soma % 11); //resultado - segundo dígito verificador
        if ((soma % 11 == 0) || (soma % 11 == 1)) {
            d2 = 0;
        }

        //valida os digitos verificadores
        String dv = Integer.toString(d1) + Integer.toString(d2);
        return dv.equals(ie.substring(ie.length() - 2, ie.length()));
    }
}