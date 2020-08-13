package ipp.aci.boleia.dominio.vo.agenciadorfrete.ndd;

import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;

import java.math.BigDecimal;


/**
 * Classe para armazenar informações da transação do motorista autonomo para o serviço da Ndd
 */
public class TransacaoSaldoNddVo {
    private Long fuelType;
    private BigDecimal liters;
    private String orderNumber;
    private BigDecimal unitPrice;

    public TransacaoSaldoNddVo() {
        //Construtor default
    }

    public TransacaoSaldoNddVo(Transacao transacao) {
        this.fuelType = transacao.getAbastecimento().getCombustivel().getId();
        this.liters = transacao.getAbastecimento().getLitragem();
        this.orderNumber = transacao.getPedido().getNumero();
        this.unitPrice = transacao.getAbastecimento().getPrecoCombustivel();
    }

    public Long getFuelType() {
        return fuelType;
    }

    public void setFuelType(Long fuelType) {
        this.fuelType = fuelType;
    }

    public BigDecimal getLiters() {
        return liters;
    }

    public void setLiters(BigDecimal liters) {
        this.liters = liters;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
