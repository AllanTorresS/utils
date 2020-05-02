package ipp.aci.boleia.util;

/**
 * Utilitario para coordenadas geográficas
 */
public final class UtilitarioCoordenadasGeograficas {

    private static final int EARTH_RADIUS = 6371;

    /**
     * Impede a instanciacao e a heranca
     */
    private UtilitarioCoordenadasGeograficas() {
    }

    /**
     * Converte a distancia em Km para o valor em graus relativo ao raio da terra
     * @param distanciaKm distancia em Km
     * @return valor em graus para ser somado a latitude
     */
    public static double converterDistanciaLatitude(double distanciaKm) {
        return (distanciaKm / EARTH_RADIUS) * (180 / Math.PI);
    }

    /**
     * Converte a distancia em Km para o valor em graus relativo ao raio da terra
     * @param distanciaKm distancia em Km
     * @param latitude a latitude do ponto
     * @return A distancia convertida
     */
    public static double converterDistanciaLongitude(double distanciaKm, double latitude) {
        return converterDistanciaLatitude(distanciaKm) / Math.cos(latitude * Math.PI/180);
    }
}