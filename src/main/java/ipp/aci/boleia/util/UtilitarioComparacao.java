package ipp.aci.boleia.util;

/**
 * Utilitario para comparar versões
 */
public final class UtilitarioComparacao {

    /**
     * Evita instanciacao e heranca
     */
    private UtilitarioComparacao() {
        // Evita instanciacao e heranca
    }

    /**
     * Verifica se a versao2 é maior ou igual à versao1.
     *
     * @param versao1 A versao 1 para comparacao
     * @param versao2 A versao 2 para comparacao
     *
     * @return True caso a versao1 seja maior ou igual a versao2 e false caso contrario
     */
    public static Boolean compararVersoes(String versao1, String versao2){
        String[] vr1 = versao1.split("\\.");
        String[] vr2 = versao2.split("\\.");

        int i = 0;
        //Define o index como o primeiro ordinal diferente ou o tamanho da string de versão mais curta
        while(i < vr1.length && i < vr2.length && vr1[i].equals(vr2[i])){
            i++;
        }
        //Compara ordinais diferentes
        if(i < vr1.length && i < vr2.length){
            int diff = Integer.valueOf(vr1[i]).compareTo(Integer.valueOf(vr2[i]));
            if(Integer.signum(diff) >= 0) {
                return true;
            } else {
                return false;
            }
        }

        if(Integer.signum(vr1.length - vr2.length) >= 0){
            return true;
        }

        return false;
    }
}
