package ipp.aci.boleia.dominio;

import ipp.aci.boleia.util.UtilitarioLambda;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Reembolso Antecipado
 */
@Audited
@Entity
@Table(name = "REEMB_ANTECIP")
public class ReembolsoAntecipado extends ReembolsoBase {

    private static final long serialVersionUID = 7429764711772518241L;

    @Id
    @Column(name = "CD_REEMB_ANTECIP")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REEMB_ANTECIP")
    @SequenceGenerator(name = "SEQ_REEMB_ANTECIP", sequenceName = "SEQ_REEMB_ANTECIP", allocationSize = 1)
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
    private Date dataVencimentoPgto;

    @Column(name = "VR_REEMB_ANTECIP")
    private BigDecimal valorReembolso;

    @Column(name = "VR_DESC_ANTECIP")
    private BigDecimal valorDesconto;

    @Column(name = "VR_TOTAL_ANTECIP")
    private BigDecimal valorTotal;

    @Column(name = "DT_PGTO")
    private Date dataPagamento;

    @Column(name = "ID_STATUS")
    private Integer status;

    @Column(name = "DS_MSG_ERRO")
    private String mensagemErro;

    @Column(name = "ID_STATUS_INT_JDE")
    private Integer statusIntegracao;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TRANS_CONSOL")
    private TransacaoConsolidada transacaoConsolidada;

    @ManyToMany(fetch = FetchType.LAZY)
    @AuditJoinTable(name = "REEMB_ANTECIP_AUT_PAG_AUD")
    @JoinTable(name = "REEMB_ANTECIP_AUT_PAG",
            joinColumns = {@JoinColumn(name = "CD_REEMB_ANTECIP")},
            inverseJoinColumns={@JoinColumn(name = "CD_AUTORIZACAO_PAGAMENTO")})
    private List<AutorizacaoPagamento> autorizacoesPagamento;

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

    public Date getDataVencimentoPgto() {
        return dataVencimentoPgto;
    }

    public void setDataVencimentoPgto(Date dataVencimentoPgto) {
        this.dataVencimentoPgto = dataVencimentoPgto;
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

    public TransacaoConsolidada getTransacaoConsolidada() {
        return transacaoConsolidada;
    }

    public void setTransacaoConsolidada(TransacaoConsolidada transacaoConsolidada) {
        this.transacaoConsolidada = transacaoConsolidada;
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

    public List<TransacaoConsolidada> getTransacoesConsolidadas() {
        return Collections.singletonList(this.transacaoConsolidada);
    }

    public void setTransacoesConsolidadas(List<TransacaoConsolidada> transacoesConsolidadas) {
        this.transacaoConsolidada = UtilitarioLambda.obterPrimeiroObjetoDaLista(transacoesConsolidadas);
    }

    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return transacaoConsolidada != null ? transacaoConsolidada.getPontosDeVenda() : Collections.emptyList();
    }

    public Integer getStatusIntegracao() {
        return statusIntegracao;
    }

    public void setStatusIntegracao(Integer statusIntegracao) {
        this.statusIntegracao = statusIntegracao;
    }

    public List<AutorizacaoPagamento> getAutorizacoesPagamento() {
        return autorizacoesPagamento;
    }

    public void setAutorizacoesPagamento(List<AutorizacaoPagamento> autorizacoesPagamento) {
        this.autorizacoesPagamento = autorizacoesPagamento;
    }
}
