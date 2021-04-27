package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;

/**
 * VO usado para obter pontos de rotas calculadas
 */
public class PontoRotaVo {

    private BigDecimal latitude;
    private BigDecimal longitude;
    private Integer ordem;
    private String nome;
    private Integer tipo;

    /**
     * Construtor padrão
     */
    public PontoRotaVo() { }

    /**
     * Constroi um ponto geográfico para requisição ao roteirizador
     * @param latitude Latitude do ponto da rota
     * @param longitude Longitude do ponto da rota
     * @param ordem Ordem do ponto na rota
     * @param nome Nome do ponto na rota
     * @param tipo Tipo do ponto na rota
     */
    public PontoRotaVo(BigDecimal latitude, BigDecimal longitude, Integer ordem, String nome, Integer tipo) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.ordem = ordem;
        this.nome = nome;
        this.tipo = tipo;
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

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }
}
