package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.StatusLiberacaoReembolsoJde;
import ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Classe abstrata de generalização para o domínio de Reembolso
 */
public abstract class ReembolsoBase implements IPersistente, IPertenceRevendedor {

    private static final long serialVersionUID = 1574595991544792091L;

    private Long id;
    private List<PontoDeVenda> pontosDeVenda;
    private BigDecimal valorTotal;
    private BigDecimal valorDesconto;
    private BigDecimal valorReembolso;
    private Date dataVencimentoPgto;
    private List<TransacaoConsolidada> transacoesConsolidadas;
    private Long numeroDocumento;
    private String tipoDocumento;
    private String ciaDocumento;
    private Integer quantidadeParcelas;
    private Integer statusLiberacaoPagamento;
    private String mensagemErro;
    private Integer status;
    private Integer statusIntegracao;
    private Integer numeroTentativasEnvio;
    private Date dataPagamento;

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

    public BigDecimal getValorReembolso() {
        return valorReembolso;
    }

    public void setValorReembolso(BigDecimal valorReembolso) {
        this.valorReembolso = valorReembolso;
    }

    public Date getDataVencimentoPgto() {
        return dataVencimentoPgto;
    }

    public void setDataVencimentoPgto(Date dataVencimentoPgto) {
        this.dataVencimentoPgto = dataVencimentoPgto;
    }

    public List<TransacaoConsolidada> getTransacoesConsolidadas() {
        return transacoesConsolidadas;
    }

    public void setTransacoesConsolidadas(List<TransacaoConsolidada> transacoesConsolidadas) {
        this.transacoesConsolidadas = transacoesConsolidadas;
    }

    public Long getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(Long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getCiaDocumento() {
        return ciaDocumento;
    }

    public void setCiaDocumento(String ciaDocumento) {
        this.ciaDocumento = ciaDocumento;
    }

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public Integer getStatusLiberacaoPagamento() {
        return statusLiberacaoPagamento;
    }

    public void setStatusLiberacaoPagamento(Integer statusLiberacaoPagamento) {
        this.statusLiberacaoPagamento = statusLiberacaoPagamento;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatusIntegracao() {
        return statusIntegracao;
    }

    public void setStatusIntegracao(Integer statusIntegracao) {
        this.statusIntegracao = statusIntegracao;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Integer getNumeroTentativasEnvio() {
        return numeroTentativasEnvio;
    }

    public void setNumeroTentativasEnvio(Integer numeroTentativasEnvio) {
        this.numeroTentativasEnvio = numeroTentativasEnvio;
    }

    /**
     * Informa se o reembolso está liberado para a realização do seu pagamento.
     *
     * @return true, caso esteja liberado.
     */
    @Transient
    public boolean estaAprovadoParaPagamento() {
        return StatusLiberacaoReembolsoJde.APROVADO_PAGAMENTO.getValue() == statusLiberacaoPagamento;
    }

    /**
     * Informa se o reembolso já foi pago.
     *
     * @return True, caso esteja pago.
     */
    @Transient
    public boolean isPago() {
        return StatusPagamentoReembolso.PAGO.getValue().equals(status);
    }
}
