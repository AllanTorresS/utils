package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.enums.StatusIntegracaoJde;

import java.math.BigDecimal;
import java.util.Date;

/**
 * VO que agrupa transações consolidadas por frota
 */
public class AgrupamentoTransacaoConsolidadaFrotaVo {
    private Date dataInicioPeriodo;
    private Date dataFimPeriodo;
    private BigDecimal valorTotal;
    private BigDecimal valorDesconto;
    private Integer statusConsolidacao;
    private Integer statusPagamento;
    private Long quantidadeAbastecimentos;
    private Date dataVencimento;
    private Date dataPagamento;
    private Boolean exigeNota;
    private BigDecimal valorEmitidoNotaFiscal;
    private BigDecimal valorTotalNotaFiscal;
    private EnumVo statusIntegracao;
    private Long idCobranca;

    /**
     * Construtor default
     */
    public AgrupamentoTransacaoConsolidadaFrotaVo() {
    }

    /**
     * Construtor do VO
     * @param dataInicioPeriodo Data de início do ciclo
     * @param dataFimPeriodo Data de fim do ciclo
     * @param valorTotal O valor total do ciclo
     * @param valorDesconto O valor dos descontos aplicados aos abastecimentos (campanha)
     * @param statusConsolidacao O status do consolidado
     * @param statusPagamento O status de pagamento da cobrança
     * @param quantidadeAbastecimentos A quantidade de abastecimentos na cobrança
     * @param dataVencimento A data de vencimento da cobrança
     * @param statusIntegracao o status da integração da cobrança
     * @param idCobranca o id da cobrança
     * @param dataPagamento A data de pagamento da cobrança
     * @param exigeNota Se há exigência de nota para emissão da cobrança
     * @param valorEmitidoNotaFiscal Valor total emitido
     * @param valorTotalNotaFiscal Valor total a ser emitido
     */
    public AgrupamentoTransacaoConsolidadaFrotaVo(Date dataInicioPeriodo, Date dataFimPeriodo, BigDecimal valorTotal,
                                                  BigDecimal valorDesconto, Integer statusConsolidacao,
                                                  Integer statusPagamento, Long quantidadeAbastecimentos,
                                                  Date dataVencimento, Date dataPagamento, Integer statusIntegracao, Long idCobranca, Integer exigeNota, BigDecimal valorEmitidoNotaFiscal,
                                                  BigDecimal valorTotalNotaFiscal) {
        this.dataInicioPeriodo = dataInicioPeriodo;
        this.dataFimPeriodo = dataFimPeriodo;
        this.valorTotal = valorTotal;
        this.valorDesconto = valorDesconto;
        this.statusConsolidacao = statusConsolidacao;
        this.statusPagamento = statusPagamento;
        this.quantidadeAbastecimentos = quantidadeAbastecimentos;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.exigeNota = exigeNota == 1 ? true : false;
        this.valorEmitidoNotaFiscal = valorEmitidoNotaFiscal;
        this.valorTotalNotaFiscal = valorTotalNotaFiscal;
        this.idCobranca = idCobranca;
        if(statusIntegracao != null) {
            this.statusIntegracao = new EnumVo(StatusIntegracaoJde.obterPorValor(statusIntegracao));
        }
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

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public Integer getStatusConsolidacao() {
        return statusConsolidacao;
    }

    public void setStatusConsolidacao(Integer statusConsolidacao) {
        this.statusConsolidacao = statusConsolidacao;
    }

    public Integer getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(Integer statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public Long getQuantidadeAbastecimentos() {
        return quantidadeAbastecimentos;
    }

    public void setQuantidadeAbastecimentos(Long quantidadeAbastecimentos) {
        this.quantidadeAbastecimentos = quantidadeAbastecimentos;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Boolean getExigeNota() {
        return exigeNota;
    }

    public void setExigeNota(Boolean exigeNota) {
        this.exigeNota = exigeNota;
    }

    public BigDecimal getValorEmitidoNotaFiscal() {
        return valorEmitidoNotaFiscal;
    }

    public void setValorEmitidoNotaFiscal(BigDecimal valorEmitidoNotaFiscal) {
        this.valorEmitidoNotaFiscal = valorEmitidoNotaFiscal;
    }

    public BigDecimal getValorTotalNotaFiscal() {
        return valorTotalNotaFiscal;
    }

    public void setValorTotalNotaFiscal(BigDecimal valorTotalNotaFiscal) {
        this.valorTotalNotaFiscal = valorTotalNotaFiscal;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public EnumVo getStatusIntegracao() {
        return statusIntegracao;
    }

    public void setStatusIntegracao(EnumVo statusIntegracao) {
        this.statusIntegracao = statusIntegracao;
    }

    public Long getIdCobranca() {
        return idCobranca;
    }

    public void setIdCobranca(Long idCobranca) {
        this.idCobranca = idCobranca;
    }
}
