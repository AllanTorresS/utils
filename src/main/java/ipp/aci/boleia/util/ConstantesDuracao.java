package ipp.aci.boleia.util;


/**
 * Constantes que representam duracoes de intervalos de tempo
 */
public final class ConstantesDuracao {

    public static final long UM_SEGUNDO          = 01_000L;
    public static final long UM_MINUTO           = 60_000L;
    public static final long UM_MINUTO_E_MEIO    = 90_000L;
    public static final long CINCO_MINUTO        = 300_000L;
    public static final long TRINTA_MINUTOS      = 1_800_000L;
    public static final long UMA_HORA            = 3_600_000L;
    public static final long DUAS_HORAS          = 7_200_000L;
    public static final long QUATRO_HORAS        = 14_400_000L;

    /**
     * Impede instanciacao e heranca
     */
    private ConstantesDuracao() {
        // Impede instanciacao e heranca
    }
}
