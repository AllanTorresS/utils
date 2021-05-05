package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;

/**
 * VO usado para obter pontos de rotas calculadas
 */
public class PontoRotaPostoVo {

    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer ordem;
    private String nome;
    private Long litros;

    /**
     * Construtor padrão
     */
    public PontoRotaPostoVo() { }

    /**
     * Constroi um ponto geográfico para requisição ao roteirizador
     * @param latitude Latitude do ponto da rota
     * @param longitude Longitude do ponto da rota
     * @param ordem Ordem do ponto na rota
     * @param nome Nome do ponto na rota
     * @param litros Quantos litros serão abastecidos
     */
    public PontoRotaPostoVo(BigDecimal latitude, BigDecimal longitude, Integer ordem, String nome, Long litros) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.ordem = ordem;
        this.nome = nome;
        this.litros = litros;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getLitros() {
        return litros;
    }

    public void setLitros(Long litros) {
        this.litros = litros;
    }
}
