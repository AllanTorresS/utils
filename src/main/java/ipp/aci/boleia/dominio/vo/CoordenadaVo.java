package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;

/**
 * VO para coordenadas com latitude e longitude
 */
public class CoordenadaVo {

    private BigDecimal latitude;
    private BigDecimal longitude;

    /**
     * Construtor default, necessario para a serializacao JSON
     */
    public CoordenadaVo() {
        // necessario para a serializacao JSON
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}
