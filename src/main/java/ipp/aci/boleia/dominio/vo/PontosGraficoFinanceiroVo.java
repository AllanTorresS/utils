package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Classe responsável pelo Vo dos pontos de gráficos da tela do financeiro
 */
public class PontosGraficoFinanceiroVo {

    private Date dataPagamento;
    private BigDecimal valorPagamento;
    private String status;

    /**
     * Construtor padrão do VO.
     */
    public PontosGraficoFinanceiroVo(){
        //serialização
    }

    /** Construtor padrão da entidade dos pontos de gráfico com parâmetros
     *
     * @param dataPagamento Data de pagamento do reembolso
     * @param valorPagamento Valor do reembolso
     * @param status Status do reembolso conforme vem da consulta
     */
    public PontosGraficoFinanceiroVo(Date dataPagamento, BigDecimal valorPagamento, String status) {
        this.dataPagamento = dataPagamento;
        this.valorPagamento = valorPagamento;
        this.status = status;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public BigDecimal getValorPagamento() {
        return valorPagamento;
    }

    public void setValorPagamento(BigDecimal valorPagamento) {
        this.valorPagamento = valorPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
