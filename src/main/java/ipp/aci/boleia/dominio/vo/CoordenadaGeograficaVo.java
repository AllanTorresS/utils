package ipp.aci.boleia.dominio.vo;


import javax.validation.constraints.NotNull;

/**
 * Classe com informacoes referentes a coordenadas geograficas
 */
public class CoordenadaGeograficaVo {

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    public CoordenadaGeograficaVo() {
    }

    /**
     * Gera a coordenada geografica de acordo com a latitude e a longitude passadas
     * @param latitude A latitude do ponto
     * @param longitude A longitude do ponto
     */
    public CoordenadaGeograficaVo(Double latitude, Double longitude) {

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
