package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;

/**
 * Representa uma entidade de acumulo de pontos para integração com o Km de Vantagens (KMV)
 */
public class KmvAcumuloPedidoVo {

    private String cpf;
    private String codigoPedido;
    private Integer tipoAcumulo;
    private BigDecimal valorCompra;
    private BigDecimal valorPago;
    private String dataPedido;
    private Integer km;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public Integer getTipoAcumulo() {
        return tipoAcumulo;
    }

    public void setTipoAcumulo(Integer tipoAcumulo) {
        this.tipoAcumulo = tipoAcumulo;
    }

    public BigDecimal getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(BigDecimal valorCompra) {
        this.valorCompra = valorCompra;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Integer getKm() {
        return km;
    }

    public void setKm(Integer km) {
        this.km = km;
    }
}
