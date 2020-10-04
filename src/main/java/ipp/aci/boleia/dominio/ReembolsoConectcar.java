package ipp.aci.boleia.dominio;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.envers.Audited;

import ipp.aci.boleia.dominio.enums.StatusLiberacaoReembolsoJde;
import ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso;
import ipp.aci.boleia.dominio.interfaces.IPersistente;

/**
 * Representa a tabela de Reembolso Conectcar
 */
@Audited
@Entity
@Table(name = "REEMBOLSO_CONECTCAR")
public class ReembolsoConectcar implements IPersistente {

    private static final long serialVersionUID = -5283245531897409405L;

    @Id
    @Column(name = "CD_REEMBOLSO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REEMBOLSO_CONECTCAR")
    @SequenceGenerator(name = "SEQ_REEMBOLSO_CONECTCAR", sequenceName = "SEQ_REEMBOLSO_CONECTCAR", allocationSize = 1)
    private Long id;

    @Column(name = "NO_DOC_JDE")
    private Long numeroDocumento;

    @Column(name = "ID_TIPO_DOC")
    private String tipoDocumento;

    @Column(name = "NM_CIA_DOC")
    private String ciaDocumento;

    @Column(name = "QT_PARCELAS")
    private Integer quantidadeParcelas;

    @Column(name = "DT_VENC_PGTO")
    private Date dataVencimentoPagto;

    @Column(name = "VR_REEMB")
    private BigDecimal valorReembolso;

    @Column(name = "VR_DESC")
    private BigDecimal valorDesconto;

    @Column(name = "VR_TOTAL")
    private BigDecimal valorTotal;

    @Column(name = "DT_PGTO")
    private Date dataPagamento;

    @Column(name = "ID_STATUS")
    private Integer status;

    @Column(name = "DS_MSG_ERRO")
    private String mensagemErro;

    @Column(name = "ID_STATUS_INT_JDE")
    private Integer statusIntegracao;

    @Column(name = "DS_MSG_ERRO_LIB_JDE")
    private String mensagemErroLiberacaoPagamento;

    @Column(name = "ID_STATUS_LIB_JDE")
    private Integer statusLiberacaoPagamento;

    @Column(name = "VR_DESC_CRED")
    private BigDecimal valorDescontoCredito;

    @Column(name = "NO_TENTATIVAS_ENVIO")
    private Integer numeroTentativasEnvio;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reembolso")
    private List<TransacaoConectcar> transacoesConsolidadas;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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

    public Date getDataVencimentoPagto() {
        return dataVencimentoPagto;
    }

    public void setDataVencimentoPagto(Date dataVencimentoPagto) {
        this.dataVencimentoPagto = dataVencimentoPagto;
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

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
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

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public List<TransacaoConectcar> getTransacoesConsolidadas() {
        return transacoesConsolidadas;
    }

    public void setTransacoesConsolidadas(List<TransacaoConectcar> transacoesConsolidadas) {
        this.transacoesConsolidadas = transacoesConsolidadas;
    }

    public Integer getStatusIntegracao() {
        return statusIntegracao;
    }

    public void setStatusIntegracao(Integer statusIntegracao) {
        this.statusIntegracao = statusIntegracao;
    }

    public String getMensagemErroLiberacaoPagamento() {
        return mensagemErroLiberacaoPagamento;
    }

    public void setMensagemErroLiberacaoPagamento(String mensagemErroLiberacaoPagamento) {
        this.mensagemErroLiberacaoPagamento = mensagemErroLiberacaoPagamento;
    }

    public Integer getStatusLiberacaoPagamento() {
        return statusLiberacaoPagamento;
    }

    public void setStatusLiberacaoPagamento(Integer statusLiberacaoPagamento) {
        this.statusLiberacaoPagamento = statusLiberacaoPagamento;
    }

    public BigDecimal getValorDescontoCredito() {
        return valorDescontoCredito;
    }

    public void setValorDescontoCredito(BigDecimal valorDescontoCredito) {
        this.valorDescontoCredito = valorDescontoCredito;
    }

    public Integer getNumeroTentativasEnvio() {
        return numeroTentativasEnvio;
    }

    public void setNumeroTentativasEnvio(Integer numeroTentativasEnvio) {
        this.numeroTentativasEnvio = numeroTentativasEnvio;
    }

    @Transient
    public StatusLiberacaoReembolsoJde getStatusLiberacaoPagamentoEnum() {
        return StatusLiberacaoReembolsoJde.obterPorValor(statusLiberacaoPagamento);
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

    /**
     * Informa se o reembolso está liberado para a realização do seu pagamento.
     *
     * @return true, caso esteja liberado.
     */
    @Transient
    public boolean estaAprovadoParaPagamento() {
        return StatusLiberacaoReembolsoJde.APROVADO_PAGAMENTO.getValue() == statusLiberacaoPagamento;
    }

}