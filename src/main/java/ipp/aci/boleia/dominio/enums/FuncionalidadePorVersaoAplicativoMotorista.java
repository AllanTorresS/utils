package ipp.aci.boleia.dominio.enums;

/**
 * Relaciona funcionalidades presentes no aplicativo do Motorista com as versões
 * em que elas foram inicialmente disponibilizadas.
 */
public enum FuncionalidadePorVersaoAplicativoMotorista {

    NOTIFICACAO_AVALIAR_POSTO             ("2.5.0"),
    NOTIFICACAO_ACUMULO_KMV               ("2.6.0"),
    NOTIFICACAO_POR_TOPICO                ("3.0.0"),
    FLUXO_USUARIO_MOTORISTA               ("3.0.0"),
    ATIVACAO_OTP_VIA_BACKEND              ("3.3.8", "3.3.2"),
    MENSAGEM_HODOMETRO_HORIMETRO_INVALIDO ("2.9.3", "2.9.0");

    private String versaoAppAndroidIos;
    private String versaoAppAndroid;
    private String versaoAppIos;

    /**
     * Constutor utilizado quando a versão do aplicativo é igual para Android e iOS.
     *
     * @param versaoAppAndroidIos versão do aplicativo
     */
    FuncionalidadePorVersaoAplicativoMotorista(String versaoAppAndroidIos) {
        this.versaoAppAndroidIos = versaoAppAndroidIos;
        this.versaoAppAndroid = versaoAppAndroidIos;
        this.versaoAppIos = versaoAppAndroidIos;
    }

    /**
     * Constutor utilizado quando a versão do aplicativo é diferente para Android e iOS.
     *
     * @param versaoAppAndroid versão do aplicativo no sistema Android
     * @param versaoAppIos versão do aplicativo no sistema iOS
     */
    FuncionalidadePorVersaoAplicativoMotorista(String versaoAppAndroid, String versaoAppIos) {
        this.versaoAppAndroid = versaoAppAndroid;
        this.versaoAppIos = versaoAppIos;
    }

    public String getVersaoAppAndroidIos() {
        return versaoAppAndroidIos;
    }

    public String getVersaoAppAndroid() {
        return versaoAppAndroid;
    }

    public String getVersaoAppIos() {
        return versaoAppIos;
    }
}
