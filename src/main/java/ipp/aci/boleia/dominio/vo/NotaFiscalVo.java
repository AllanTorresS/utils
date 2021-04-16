package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.NotaFiscal;
import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.util.UtilitarioFormatacao;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Classe responsável pelo Vo da entidade NotaFiscal.
 */
public class NotaFiscalVo {
    private Long idAutorizacaoPagamento;
    private Long idNota;
    private String numero;
    private String numeroSerie;
    private BigDecimal valorTotal;
    private String valorTotalFormatado;
    private Date dataEmissao;
    private Long idConsolidado;
    private Date dataAbastecimento;

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
        this.numeroSerie = nota.getNumeroSerie();
        this.valorTotal = nota.getValorTotal();
        this.valorTotalFormatado = UtilitarioFormatacao.formatarDecimalMoedaReal(nota.getValorTotal());
        this.dataEmissao = nota.getDataEmissao();
        this.idConsolidado = consolidada != null ? consolidada.getId() : null;
        this.dataAbastecimento = autorizacaoPagamento.getDataRequisicao();
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

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getValorTotalFormatado() {
        return valorTotalFormatado;
    }

    public void setValorTotalFormatado(String valorTotalFormatado) {
        this.valorTotalFormatado = valorTotalFormatado;
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

    public Date getDataAbastecimento() {
        return dataAbastecimento;
    }

    public void setDataAbastecimento(Date dataAbastecimento) {
        this.dataAbastecimento = dataAbastecimento;
    }

    public String getNumeroCompleto() {
        return this.numero+"-"+this.numeroSerie;
    }
}
