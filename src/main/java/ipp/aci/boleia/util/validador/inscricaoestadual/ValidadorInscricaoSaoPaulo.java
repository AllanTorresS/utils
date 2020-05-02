package ipp.aci.boleia.util.validador.inscricaoestadual;

/**
 * Validador de inscrição estadual
 */
public class ValidadorInscricaoSaoPaulo extends ValidadorInscricaoEstadualGenerico  {

    @Override
    public boolean validar(String ie) {
        if (validarDigitos(ie,12)) {
            int soma = 0;
            int peso = 1;
            int d1; //primeiro dígito verificador
            //cálculo do primeiro dígito verificador (nona posição)
            for (int i = 0; i < ie.length() - 4; i++) {
                if (i == 1 || i == 7) {
                    soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * ++peso;
                    peso++;
                } else {
                    soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                    peso++;
                }
            }

            d1 = soma % 11;
            String strD1 = Integer.toString(d1); //O dígito í igual ao algarismo mais a direita do resultado de (soma % 11)
            d1 = Integer.parseInt(String.valueOf(strD1.charAt(strD1.length() - 1)));

            //cálculo do segunfo dígito
            soma = 0;
            int pesoInicio = 3;
            int pesoFim = 10;
            int d2; //segundo dígito verificador
            for (int i = 0; i < ie.length() - 1; i++) {
                if (i < 2) {
                    soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                    pesoInicio--;
                } else {
                    soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                    pesoFim--;
                }
            }

            d2 = soma % 11;
            String strD2 = Integer.toString(d2); //O dígito í igual ao algarismo mais a direita do resultado de (soma % 11)
            d2 = Integer.parseInt(String.valueOf(strD2.charAt(strD2.length() - 1)));

            //valida os dígitos verificadores
            return ie.substring(8, 9).equals(d1 + "") && ie.substring(11, 12).equals(d2 + "");
        } else if(validarDigitos(ie,14)) {
            //valida o primeiro caracter
            if (ie.charAt(0) != 'P') {
                return false; 
            }

            String strIE = ie.substring(1, 10); //Obtím somente os dígitos utilizados no cálculo do dígito verificador
            int soma = 0;
            int peso = 1;
            int d1; //primeiro dígito verificador
            //cálculo do primeiro dígito verificador (nona posição)
            for (int i = 0; i < strIE.length() - 1; i++) {
                if (i == 1 || i == 7) {
                    soma += Integer.parseInt(String.valueOf(strIE.charAt(i))) * ++peso;
                    peso++;
                } else {
                    soma += Integer.parseInt(String.valueOf(strIE.charAt(i))) * peso;
                    peso++;
                }
            }

            d1 = soma % 11;
            String strD1 = Integer.toString(d1); //O dígito í igual ao algarismo mais a direita do resultado de (soma % 11)
            d1 = Integer.parseInt(String.valueOf(strD1.charAt(strD1.length() - 1)));

            //valida o dígito verificador
            return ie.substring(9, 10).equals(d1 + "");
        }
        return false;
    }
}