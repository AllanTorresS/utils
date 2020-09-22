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
    private String gasStationName;
    private String cnpjGasStation;
    private BigDecimal unitPrice;
    private BigDecimal mdr;

    public TransacaoSaldoNddVo() {
        //Construtor default
    }

    public TransacaoSaldoNddVo(Transacao transacao) {
        this.fuelType = transacao.getAbastecimento().getCombustivel().getId();
        this.liters = transacao.getAbastecimento().getLitragem();
        this.orderNumber = transacao.getPedido().getNumero();
        this.unitPrice = transacao.getAbastecimento().getPrecoCombustivel();
        this.mdr = transacao.getAbastecimento().getMdr();
        this.cnpjGasStation = transacao.getPosto().getCnpj().toString();
        this.gasStationName = transacao.getPosto().getNome();
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

    public BigDecimal getMdr() {
        return mdr;
    }

    public void setMdr(BigDecimal mdr) {
        this.mdr = mdr;
    }

    public String getGasStationName() {
        return gasStationName;
    }

    public void setGasStationName(String gasStationName) {
        this.gasStationName = gasStationName;
    }

    public String getCnpjGasStation() {
        return cnpjGasStation;
    }

    public void setCnpjGasStation(String cnpjGasStation) {
        this.cnpjGasStation = cnpjGasStation;
    }
}
