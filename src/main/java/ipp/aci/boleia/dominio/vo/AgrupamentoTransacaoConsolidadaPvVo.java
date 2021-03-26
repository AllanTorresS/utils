package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso;
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
    private Long quantidadeAbastecimentos;
    private Integer statusPagamentoReembolso;
    private Boolean passivelDeEmissao; 

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
     * @param passivelDeEmissao indica se ao menos um ciclo do agrupamaneto é passível de emissão de NF
     */
    public AgrupamentoTransacaoConsolidadaPvVo(Date dataInicioPeriodo, Date dataFimPeriodo, Date prazoEmissaoNotaFiscal, Integer statusConsolidacao, BigDecimal valorFaturamento, BigDecimal valorReembolso, BigDecimal valorDesconto, BigDecimal valorTotalNf, BigDecimal valorEmitidoNf, Long quantidadeAbastecimentos, Integer statusPagamentoReembolso, Integer passivelDeEmissao) {
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
        this.quantidadeAbastecimentos = quantidadeAbastecimentos;
        this.statusPagamentoReembolso = statusPagamentoReembolso;
        this.passivelDeEmissao = passivelDeEmissao == 1;
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

    public Long getQuantidadeAbastecimentos() {
        return quantidadeAbastecimentos;
    }

    public void setQuantidadeAbastecimentos(Long quantidadeAbastecimentos) {
        this.quantidadeAbastecimentos = quantidadeAbastecimentos;
    }

    public Integer getStatusPagamentoReembolso() {
        return statusPagamentoReembolso;
    }

    public void setStatusPagamentoReembolso(Integer statusPagamentoReembolso) {
        this.statusPagamentoReembolso = statusPagamentoReembolso;
    }


    public Boolean getPassivelDeEmissao() { 
        return passivelDeEmissao; 
    }

    public void setPassivelDeEmissao(Boolean passivelDeEmissao) { 
        this.passivelDeEmissao = passivelDeEmissao;
    }


    /**
     * Indica se uma transação consolidada está em aberto.
     * @return true caso esteja em aberto e false caso contrário.
     */
    @Transient
    public boolean estaEmAberto() {
        return StatusTransacaoConsolidada.EM_ABERTO.getValue().equals(statusConsolidacao);
    }

    /**
     * Indica se uma transação consolidada está em ajuste.
     * @return true caso esteja em ajuste e false caso contrário.
     */
    @Transient
    public boolean estaEmAjuste() {
        return StatusTransacaoConsolidada.EM_AJUSTE.getValue().equals(statusConsolidacao);
    }

    /**
     * Indica se uma transação consolidada está fechada.
     * @return true caso esteja fechada e false caso contrário.
     */
    @Transient
    public boolean estaFechada() {
        return StatusTransacaoConsolidada.FECHADA.getValue().equals(statusConsolidacao);
    }

    /**
     * Informa se o pagamento do reembolso está em aberto.
     * @return True caso esteja em aberto.
     */
    @Transient
    public boolean isReembolsoEmAberto() {
        return StatusPagamentoReembolso.EM_ABERTO.getValue().equals(statusPagamentoReembolso);
    }

    /**
     * Informa se o pagamento do reembolso está previsto.
     * @return True caso esteja previsto.
     */
    @Transient
    public boolean isReembolsoPrevisto() {
        return StatusPagamentoReembolso.PREVISTO.getValue().equals(statusPagamentoReembolso);
    }
}
