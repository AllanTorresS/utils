package ipp.aci.boleia.util.seguranca;

import ipp.aci.boleia.dominio.interfaces.IPossuiFingerprint;
import org.apache.commons.lang3.StringUtils;

/**
 * Utilitario para geracao e parsing de fingerprints de hardware
 */
public final class UtilitarioFingerprint {

    /**
     * Monta o fingerprint do hardware a partir dos campos que o compoem
     *
     * @param entidade O dispositivo cujo fingerprint deve ser montado
     * @return A string de fingerprint do dispositivo informado
     */
    public static String montarFingerprint(IPossuiFingerprint entidade) {
        StringBuilder str = new StringBuilder();
        String[] campos = entidade.getCamposFingerprint();
        for (String campo : campos) {
            if (campo != null) {
                if (str.length() > 0) {
                    str.append("|");
                }
                str.append(campo);
            }
        }
        return str.length() > 0 ? str.toString() : null;
    }

    /**
     * Dado um fingerprint de um dispositivo, obtem o campo numero de serie
     * @param fingerprint O fingerprint
     * @return O numero de serie caso exista no fingerprint
     */
    public static String extrairNumeroSerie(String fingerprint) {
        if(fingerprint != null && fingerprint.contains("|") && !fingerprint.endsWith("|")) {
            return StringUtils.split(fingerprint, '|')[1];
        }
        return null;
    }

    /**
     * Calcula o hash do fingerprint
     *
     * @param entidade A entidade que possui o fingerprint
     * @return O hash do fingerprint da entidade
     */
    public static String calcularHashFingerprint(IPossuiFingerprint entidade){
        String fingerprint = montarFingerprint(entidade);
        return fingerprint != null ? UtilitarioCriptografia.calcularHashSHA256(fingerprint) : null;
    }

    /**
     * Calcula o hash do fingerprint
     *
     * @param fingerprint O fingerprint
     * @return O hash do fingerprint
     */
    public static String calcularHashFingerprint(String fingerprint){
        if(fingerprint != null && fingerprint.endsWith("|")) {
            fingerprint = fingerprint.substring(0, fingerprint.length() - 1);
        }
        return fingerprint != null ? UtilitarioCriptografia.calcularHashSHA256(fingerprint) : null;
    }
}
