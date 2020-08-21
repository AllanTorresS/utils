package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.visao.dto.PontoRotaDTO;

import java.math.BigDecimal;

/**
 * VO usado para obter pontos de rotas calculadas
 */
public class PontoRotaVo {

    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer ordem;

    /**
     * Construtor padrão
     */
    public PontoRotaVo() { }

    /**
     * Cria um ponto a partir de {@link PontoRotaDTO}
     * @param dto {@link PontoRotaDTO} DTO Utilizado para criar a representação do ponto
     */
    public PontoRotaVo(PontoRotaDTO dto) {
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
        this.ordem = dto.getOrdem();
    }

    public PontoRotaVo(BigDecimal latitude, BigDecimal longitude, Integer ordem) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.ordem = ordem;
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

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

}
