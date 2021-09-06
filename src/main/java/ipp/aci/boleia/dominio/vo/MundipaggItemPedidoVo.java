package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;

/**
 * Classe com informacoes referentes ao item pedido na Mundipagg
 */
public class MundipaggItemPedidoVo {

    private static final String CREDITO_DESCRIPTION = "CREDITO";

    private int amount;
    private String description;
    private int quantity;

    public MundipaggItemPedidoVo() {

    }

    /**
     * Construtor do item pedido
     * @param valor O valor do pedido
     */
    public MundipaggItemPedidoVo(BigDecimal valor) {
        description = CREDITO_DESCRIPTION;
        quantity = 1;
        amount = valor.intValue();
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
 