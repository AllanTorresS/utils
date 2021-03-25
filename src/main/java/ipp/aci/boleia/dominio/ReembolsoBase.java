package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.StatusLiberacaoReembolsoJde;
import ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Classe abstrata de generalização para o domínio de Reembolso
 */
@MappedSuperclass
public abstract class ReembolsoBase implements IPersistente, IPertenceRevendedor {

    private static final long serialVersionUID = 1574595991544792091L;

    @Column(name = "NO_DOC_JDE")
    private Long numeroDocumento;
    @Column(name = "ID_TIPO_DOC")
    private String tipoDocumento;
    @Column(name = "NM_CIA_DOC")
    private String ciaDocumento;
    @Column(name = "QT_PARCELAS")
    private Integer quantidadeParcelas;
    @Column(name = "DT_VENC_PGTO")
    private Date dataVencimentoPgto;
    @Column(name = "DT_PGTO")
    private Date dataPagamento;
    @Column(name = "ID_STATUS")
    private Integer status;
    @Column(name = "DS_MSG_ERRO")
    private String mensagemErro;
    @Column(name = "ID_STATUS_INT_JDE")
    private Integer statusIntegracao;
    @Column(name = "NO_TENTATIVAS_ENVIO")
    private Integer numeroTentativasEnvio;

    public abstract Long getId();

    public abstract void setId(Long id);

    public abstract BigDecimal getValorTotal();

    public abstract void setValorTotal(BigDecimal valorTotal);

    public abstract BigDecimal getValorDesconto();

    public abstract void setValorDesconto(BigDecimal valorDesconto);

    public abstract BigDecimal getValorReembolso();

    public abstract void setValorReembolso(BigDecimal valorReembolso);

    public abstract BigDecimal getValorDescontoVoucher();

    public abstract List<PontoDeVenda> getPontosDeVenda();

    public abstract Integer getStatusLiberacaoPagamento();

    public abstract List<TransacaoConsolidada> getTransacoesConsolidadas();

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

    public Date getDataVencimentoPgto() {
        return dataVencimentoPgto;
    }

    public void setDataVencimentoPgto(Date dataVencimentoPgto) {
        this.dataVencimentoPgto = dataVencimentoPgto;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public Integer getStatusIntegracao() {
        return statusIntegracao;
    }

    public void setStatusIntegracao(Integer statusIntegracao) {
        this.statusIntegracao = statusIntegracao;
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
    public abstract boolean estaAprovadoParaPagamento();

    /**
     * Informa se o reembolso já foi pago.
     *
     * @return True, caso esteja pago.
     */
    @Transient
    public boolean isPago() {
        return StatusPagamentoReembolso.PAGO.getValue().equals(status);
    }

    @Transient
    public BigDecimal getValorLiquidoVoucher() {
        return getValorTotal().subtract(getValorDescontoVoucher());
    }
}
