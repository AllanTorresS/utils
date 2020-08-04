package ipp.aci.boleia.dominio.vo.agenciadorfrete.ndd;

import java.math.BigDecimal;

/**
 * Classe para armazenar informações do saldo de motorista autonomo para o serviço da Ndd
 */
public class SaldoNddVo {

    private String orderNumber;
    private BigDecimal amountAvailable;
    private BigDecimal withdrawalFee;

    public SaldoNddVo(){

    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(BigDecimal amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public BigDecimal getWithdrawalFee() {
        return withdrawalFee;
    }

    public void setWithdrawalFee(BigDecimal withdrawalFee) {
        this.withdrawalFee = withdrawalFee;
    }
}
