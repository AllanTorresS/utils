package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.NotaFiscal;
import ipp.aci.boleia.dominio.TransacaoConsolidada;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Classe responsável pelo Vo da entidade NotaFiscal.
 */
public class NotaFiscalVo {
    private Long idAutorizacaoPagamento;
    private Long idNota;
    private String numero;
    private BigDecimal valorTotal;
    private Date dataEmissao;
    private Long idConsolidado;

    /**
     * Construtor default de Nota Fiscal.
     * @param nota identificador da nota fiscal.
     * @param consolidada identificador da transação consolidada..
     * @param autorizacaoPagamento identificador da autorização de pagamento.
     */
    public NotaFiscalVo(NotaFiscal nota, TransacaoConsolidada consolidada, AutorizacaoPagamento autorizacaoPagamento) {
        this.idAutorizacaoPagamento = autorizacaoPagamento.getId();
        this.idNota = nota.getId();
        this.numero = nota.getNumero();
        this.valorTotal = nota.getValorTotal();
        this.dataEmissao = nota.getDataEmissao();
        this.idConsolidado = consolidada != null ? consolidada.getId() : null;
    }

    /**
     * Construtor padrão do VO.
     */
    public NotaFiscalVo() {
    }

    public Long getIdAutorizacaoPagamento() {
        return idAutorizacaoPagamento;
    }

    public void setIdAutorizacaoPagamento(Long idAutorizacaoPagamento) {
        this.idAutorizacaoPagamento = idAutorizacaoPagamento;
    }

    public Long getIdNota() { return idNota; }

    public void setIdNota(Long idNota) { this.idNota = idNota; }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Long getIdConsolidado() {
        return idConsolidado;
    }

    public void setIdConsolidado(Long idConsolidado) {
        this.idConsolidado = idConsolidado;
    }
}
