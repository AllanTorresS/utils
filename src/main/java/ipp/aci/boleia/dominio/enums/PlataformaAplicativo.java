package ipp.aci.boleia.dominio.enums;


import java.util.Arrays;

/**
 * Plataformas de aplicativos que são atendidos pelos aplicativos
 */
public enum PlataformaAplicativo {

    ANDROID,
    IOS;

    /**
     * Obtém o enumerado referente ao parâmetro fornecido
     *
     * @param nomePlataforma nome da plataforma
     * @return enumerado ou nulo
     */
    public static PlataformaAplicativo obterPorNome(String nomePlataforma) {
        return Arrays.stream(PlataformaAplicativo.values())
                .filter(t -> t.name().equalsIgnoreCase(nomePlataforma))
                .findFirst().orElse(null);
    }
}

