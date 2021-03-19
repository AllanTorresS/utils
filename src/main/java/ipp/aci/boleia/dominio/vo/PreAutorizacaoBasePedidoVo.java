package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Modela os dados basicos de utilizados na pre-autorizacao
 */
public abstract class PreAutorizacaoBasePedidoVo {
    
    protected BigDecimal litragem;

    protected Long hodometro;
    protected BigDecimal horimetro;

    protected BigDecimal latitude;
    protected BigDecimal longitude;
    protected BigDecimal precisaoGPS;

    protected Date dataCriacao;

    public BigDecimal getLitragem() {
        return litragem;
    }

    public void setLitragem(BigDecimal litragem) {
        this.litragem = litragem;
    }

    public Long getHodometro() {
        return hodometro;
    }

    public void setHodometro(Long hodometro) {
        this.hodometro = hodometro;
    }

    public BigDecimal getHorimetro() {
        return horimetro;
    }

    public void setHorimetro(BigDecimal horimetro) {
        this.horimetro = horimetro;
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

    public BigDecimal getPrecisaoGPS() {
        return precisaoGPS;
    }

    public void setPrecisaoGPS(BigDecimal precisaoGPS) {
        this.precisaoGPS = precisaoGPS;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
