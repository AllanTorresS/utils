package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.enums.StatusTransacaoConsolidada;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.Date;

import static ipp.aci.boleia.util.UtilitarioCalculoData.diferencaEmDias;

/**
 * Mantém as informações agrupadas das transações consolidadas de um ponto de venda.
 */
public class AgrupamentoTransacaoConsolidadaPvVo {

    private Date dataInicioPeriodo;
    private Date dataFimPeriodo;
    private Date prazoEmissaoNotaFiscal;
    private Integer tamanhoPeriodo;
    private Integer statusConsolidacao;
    private BigDecimal valorFaturamento;
    private BigDecimal valorReembolso;
    private BigDecimal valorDesconto;
    private BigDecimal valorTotalNf;
    private BigDecimal valorEmitidoNf;

    /**
     * Construtor default.
     */
    public AgrupamentoTransacaoConsolidadaPvVo() {
    }

    /**
     * Construtor do vo.
     *
     * @param dataInicioPeriodo Data de início do período.
     * @param dataFimPeriodo Data final do período.
     * @param prazoEmissaoNotaFiscal Data limite de emissão de nota fiscal.
     * @param statusConsolidacao Status do ciclo.
     * @param valorFaturamento Valor de faturamento.
     * @param valorReembolso Valor de reembolso.
     * @param valorDesconto Valor de desconto.
     * @param valorTotalNf Valor total da nota fiscal.
     * @param valorEmitidoNf Valoro total emitido da nota fiscal.
     */
    public AgrupamentoTransacaoConsolidadaPvVo(Date dataInicioPeriodo, Date dataFimPeriodo, Date prazoEmissaoNotaFiscal, Integer statusConsolidacao, BigDecimal valorFaturamento, BigDecimal valorReembolso, BigDecimal valorDesconto, BigDecimal valorTotalNf, BigDecimal valorEmitidoNf) {
        this.dataInicioPeriodo = dataInicioPeriodo;
        this.dataFimPeriodo = dataFimPeriodo;
        this.prazoEmissaoNotaFiscal = prazoEmissaoNotaFiscal;
        this.tamanhoPeriodo = (int) diferencaEmDias(dataFimPeriodo, dataInicioPeriodo);
        this.statusConsolidacao = statusConsolidacao;
        this.valorFaturamento = valorFaturamento;
        this.valorReembolso = valorReembolso;
        this.valorDesconto = valorDesconto;
        this.valorTotalNf = valorTotalNf;
        this.valorEmitidoNf = valorEmitidoNf;
    }

    public Date getDataInicioPeriodo() {
        return dataInicioPeriodo;
    }

    public void setDataInicioPeriodo(Date dataInicioPeriodo) {
        this.dataInicioPeriodo = dataInicioPeriodo;
    }

    public Date getDataFimPeriodo() {
        return dataFimPeriodo;
    }

    public void setDataFimPeriodo(Date dataFimPeriodo) {
        this.dataFimPeriodo = dataFimPeriodo;
    }

    public Date getPrazoEmissaoNotaFiscal() {
        return prazoEmissaoNotaFiscal;
    }

    public void setPrazoEmissaoNotaFiscal(Date prazoEmissaoNotaFiscal) {
        this.prazoEmissaoNotaFiscal = prazoEmissaoNotaFiscal;
    }

    public Integer getTamanhoPeriodo() {
        return tamanhoPeriodo;
    }

    public void setTamanhoPeriodo(Integer tamanhoPeriodo) {
        this.tamanhoPeriodo = tamanhoPeriodo;
    }

    public Integer getStatusConsolidacao() {
        return statusConsolidacao;
    }

    public void setStatusConsolidacao(Integer statusConsolidacao) {
        this.statusConsolidacao = statusConsolidacao;
    }

    public BigDecimal getValorFaturamento() {
        return valorFaturamento;
    }

    public void setValorFaturamento(BigDecimal valorFaturamento) {
        this.valorFaturamento = valorFaturamento;
    }

    public BigDecimal getValorReembolso() {
        return valorReembolso;
    }

    public void setValorReembolso(BigDecimal valorReembolso) {
        this.valorReembolso = valorReembolso;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public BigDecimal getValorTotalNf() {
        return valorTotalNf;
    }

    public void setValorTotalNf(BigDecimal valorTotalNf) {
        this.valorTotalNf = valorTotalNf;
    }

    public BigDecimal getValorEmitidoNf() {
        return valorEmitidoNf;
    }

    public void setValorEmitidoNf(BigDecimal valorEmitidoNf) {
        this.valorEmitidoNf = valorEmitidoNf;
    }

    @Transient
    public boolean estaEmAberto() {
        return StatusTransacaoConsolidada.EM_ABERTO.getValue().equals(statusConsolidacao);
    }

    @Transient
    public boolean estaEmAjuste() {
        return StatusTransacaoConsolidada.EM_AJUSTE.getValue().equals(statusConsolidacao);
    }

    @Transient
    public boolean estaFechada() {
        return StatusTransacaoConsolidada.FECHADA.getValue().equals(statusConsolidacao);
    }

}
