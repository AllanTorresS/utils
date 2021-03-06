package ipp.aci.boleia.dominio.enums;


import java.util.Arrays;

import static ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema.ULTIMA_VERSAO_APP_FROTISTA_ANDROID;
import static ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema.ULTIMA_VERSAO_APP_FROTISTA_IOS;
import static ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema.ULTIMA_VERSAO_APP_MOTORISTA_ANDROID;
import static ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema.ULTIMA_VERSAO_APP_MOTORISTA_IOS;
import static ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema.VERSAO_MINIMA_APP_FROTISTA_ANDROID;
import static ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema.VERSAO_MINIMA_APP_FROTISTA_IOS;
import static ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema.VERSAO_MINIMA_APP_MOTORISTA_ANDROID;
import static ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema.VERSAO_MINIMA_APP_MOTORISTA_IOS;
import static ipp.aci.boleia.dominio.enums.PlataformaAplicativo.ANDROID;
import static ipp.aci.boleia.dominio.enums.PlataformaAplicativo.IOS;
import static ipp.aci.boleia.dominio.enums.TipoAplicativo.FROTISTA;
import static ipp.aci.boleia.dominio.enums.TipoAplicativo.MOTORISTA;

/**
 * Tipos de aplicativos que consomem a API, agrupados por plataforma (sistema operacional)
 */
public enum TipoAplicativoPorPlataforma {

    MOTORISTA_ANDROID(MOTORISTA, ANDROID, VERSAO_MINIMA_APP_MOTORISTA_ANDROID, ULTIMA_VERSAO_APP_MOTORISTA_ANDROID),
    MOTORISTA_IOS    (MOTORISTA, IOS,     VERSAO_MINIMA_APP_MOTORISTA_IOS,     ULTIMA_VERSAO_APP_MOTORISTA_IOS),
    FROTISTA_ANDROID (FROTISTA,  ANDROID, VERSAO_MINIMA_APP_FROTISTA_ANDROID, ULTIMA_VERSAO_APP_FROTISTA_ANDROID),
    FROTISTA_IOS     (FROTISTA,  IOS,     VERSAO_MINIMA_APP_FROTISTA_IOS,     ULTIMA_VERSAO_APP_FROTISTA_IOS),;

    private final TipoAplicativo tipoApp;
    private final PlataformaAplicativo plataformaApp;
    private final ChaveConfiguracaoSistema chaveMinimaVersaoApp;
    private final ChaveConfiguracaoSistema chaveUltimaVersaoApp;

    /**
     * Construtor
     *
     * @param tipoAplicativo tipo do aplicativo
     * @param plataformaAplicativo plataforma do aplicativo
     * @param chaveMinimaVersaoApp chave de configura????o que identifica a vers??o m??nima do aplicativo
     * @param chaveUltimaVersaoApp chave de configura????o que identifica a ??ltima vers??o do aplicativo
     */
    TipoAplicativoPorPlataforma(TipoAplicativo tipoAplicativo, PlataformaAplicativo plataformaAplicativo, ChaveConfiguracaoSistema chaveMinimaVersaoApp, ChaveConfiguracaoSistema chaveUltimaVersaoApp) {
        this.tipoApp = tipoAplicativo;
        this.plataformaApp = plataformaAplicativo;
        this.chaveMinimaVersaoApp = chaveMinimaVersaoApp;
        this.chaveUltimaVersaoApp = chaveUltimaVersaoApp;
    }

    /**
     * Obt??m o enumerado condizente com os par??metros fornecidos
     *
     * @param tipoAplicativo tipo do aplicativo
     * @param plataformaAplicativo plataforma do aplicativo
     * @return enumerado equivalente aos par??metros fornecidos, ou nulo
     */
    public static TipoAplicativoPorPlataforma obterPorTipoPlataforma(TipoAplicativo tipoAplicativo, PlataformaAplicativo plataformaAplicativo) {
        return Arrays.stream(TipoAplicativoPorPlataforma.values())
                .filter(t -> t.tipoApp.equals(tipoAplicativo) && t.plataformaApp.equals(plataformaAplicativo))
                .findFirst().orElse(null);
    }

    /**
     * @return tipo do aplicativo
     */
    public TipoAplicativo getTipoApp() {
        return tipoApp;
    }

    /**
     * @return plataforma do aplicativo
     */
    public PlataformaAplicativo getPlataformaApp() {
        return plataformaApp;
    }

    /**
     * @return chave de configura????o que identifica a vers??o m??nima do aplicativo
     */
    public ChaveConfiguracaoSistema getChaveMinimaVersaoApp() {
        return chaveMinimaVersaoApp;
    }

    /**
     * @return chave de configura????o que identifica a ??ltima vers??o do aplicativo
     */
    public ChaveConfiguracaoSistema getChaveUltimaVersaoApp() {
        return chaveUltimaVersaoApp;
    }
}

