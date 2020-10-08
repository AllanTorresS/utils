package ipp.aci.boleia.dominio.vo.agenciadorfrete.ndd;

import ipp.aci.boleia.dominio.agenciadorfrete.Pedido;
import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * Classe para armazenar informações da transação do motorista autonomo para o serviço da Ndd
 */
public class TransacaoNddVo {
    private String orderNumber;
    private BigDecimal supplyValue;
    private BigDecimal withdrawalAmount;

    public TransacaoNddVo() {
        //Construtor default
    }

    public TransacaoNddVo(Transacao transacao) {
        this.orderNumber = transacao.getPedido().getNumero();
        this.supplyValue = transacao.getAbastecimento().getPrecoCombustivel().multiply(transacao.getAbastecimento().getLitragem()).setScale(2, RoundingMode.HALF_UP);
        this.withdrawalAmount = transacao.getSaque() != null ? transacao.getSaque().getValorSolicitado() : null;
    }

    public TransacaoNddVo(Pedido pedido) {
        this.orderNumber = pedido.getNumero();
        this.supplyValue = pedido.getPrecoUnitario().multiply(pedido.getLitragem());
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getSupplyValue() {
        return supplyValue;
    }

    public void setSupplyValue(BigDecimal supplyValue) {
        this.supplyValue = supplyValue;
    }

    public BigDecimal getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(BigDecimal withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }
}
