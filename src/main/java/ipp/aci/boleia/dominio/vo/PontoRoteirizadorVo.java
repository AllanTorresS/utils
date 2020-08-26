package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;

/**
 * Representa o posto recebido dentro da Resposta da API Gateway do Python (dentro de RespostaRoteirizadorVo.java)
 */
public class PontoRoteirizadorVo extends PontoRotaVo {

    private Long pv;
    private BigDecimal valor;
    private BigDecimal litros;
    private BigDecimal valor_litro;
    private String dt_atualizacao;
    private String nm_ptov;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public PontoRoteirizadorVo() { }

    public Long getPv() {
        return this.pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }

    public String getDtAtualizacao() {
        return dt_atualizacao;
    }

    public void setDtAtualizacao(String dt_atualizacao) {
        this.dt_atualizacao = dt_atualizacao;
    }

    public String getNmPtov() {
        return nm_ptov;
    }

    public void setNmPtov(String nm_ptov) {
        this.nm_ptov = nm_ptov;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getLitros() {
        return litros;
    }

    public void setLitros(BigDecimal litros) {
        this.litros = litros;
    }

    public BigDecimal getValorLitro() {
        return valor_litro;
    }

    public void setValorLitro(BigDecimal valor_litro) {
        this.valor_litro = valor_litro;
    }

    @Override
    public BigDecimal getLatitude() {
        return latitude;
    }

    @Override
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    @Override
    public BigDecimal getLongitude() {
        return longitude;
    }

    @Override
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}
