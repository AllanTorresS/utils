package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.Frota;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Classe com informacoes relacionadas ao pedido de credito junto a Mundipagg
 */
public class MundipaggPedidoVo {

    private List<MundipaggItemPedidoVo> items;
    private MundipaggClientePedidoVo customer;
    private List<MundipaggPagamentoVo> payments;

    public MundipaggPedidoVo() {

    }

    /**
     * Construtor do pedido Mundipagg
     * @param frota A frota que realiza o pedido
     * @param valor O valor do pedido
     * @param vencimentoBoleto A data de vencimento do boleto
     * @param code Flag para utilização do ambiente de testes da mundipagg
     */
    public MundipaggPedidoVo(Frota frota, BigDecimal valor, Date vencimentoBoleto, String code) {
        customer = new MundipaggClientePedidoVo(frota);
        payments = Collections.singletonList(new MundipaggPagamentoVo(frota, valor, vencimentoBoleto, code));
        items = Collections.singletonList(new MundipaggItemPedidoVo(valor));
    }

    public List<MundipaggItemPedidoVo> getItems() {
        return items;
    }

    public void setItems(List<MundipaggItemPedidoVo> items) {
        this.items = items;
    }

    public MundipaggClientePedidoVo getCustomer() {
        return customer;
    }

    public void setCustomer(MundipaggClientePedidoVo customer) {
        this.customer = customer;
    }

    public List<MundipaggPagamentoVo> getPayments() {
        return payments;
    }

    public void setPayments(List<MundipaggPagamentoVo> payments) {
        this.payments = payments;
    }
}
 