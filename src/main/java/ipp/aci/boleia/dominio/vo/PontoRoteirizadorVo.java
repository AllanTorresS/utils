package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Representa o posto recebido dentro da Resposta da API Gateway do Python (dentro de RespostaRoteirizadorVo.java)
 */
public class PontoRoteirizadorVo extends PontoRotaVo {

    private Long pv;

    private BigDecimal valor;

    private BigDecimal litros;

    @JsonProperty("valor_litro")
    private BigDecimal valorLitro;

    @JsonProperty("dt_atualizacao")
    private String dtAtualizacao;

    @JsonProperty("nm_ptov")
    private String nomePtov;

    @JsonProperty("preco_negociado")
    private Boolean precoNegociado;

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
        return dtAtualizacao;
    }

    public void setDtAtualizacao(String dtAtualizacao) {
        this.dtAtualizacao = dtAtualizacao;
    }

    public String getNomePtov() {
        return nomePtov;
    }

    public void setNomePtov(String nomePtov) {
        this.nomePtov = nomePtov;
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
        return valorLitro;
    }

    public void setValorLitro(BigDecimal valorLitro) {
        this.valorLitro = valorLitro;
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

    public Boolean isPrecoNegociado() {
        return precoNegociado;
    }

    public void setPrecoNegociado(Boolean precoNegociado) {
        this.precoNegociado = precoNegociado;
    }
}
