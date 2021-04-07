package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.StatusLiberacaoReembolsoJde;
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
import javax.persistence.Transient;
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

    @Column(name = "VR_REEMB_ANTECIP")
    private BigDecimal valorReembolso;

    @Column(name = "VR_DESC_ANTECIP")
    private BigDecimal valorDesconto;

    @Column(name = "VR_TOTAL_ANTECIP")
    private BigDecimal valorTotal;

    @Column(name = "DT_ANTECIPACAO")
    private Date dataAntecipacao;

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

    @Override
    public BigDecimal getValorReembolso() {
        return valorReembolso;
    }

    @Override
    public void setValorReembolso(BigDecimal valorReembolso) {
        this.valorReembolso = valorReembolso;
    }

    @Override
    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    @Override
    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    @Override
    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    @Override
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getDataAntecipacao() {
        return dataAntecipacao;
    }

    public void setDataAntecipacao(Date dataAntecipacao) {
        this.dataAntecipacao = dataAntecipacao;
    }

    @Override
    public BigDecimal getValorDescontoVoucher() {
        return getValorDesconto();
    }

    public TransacaoConsolidada getTransacaoConsolidada() {
        return transacaoConsolidada;
    }

    public void setTransacaoConsolidada(TransacaoConsolidada transacaoConsolidada) {
        this.transacaoConsolidada = transacaoConsolidada;
    }

    @Override
    public List<TransacaoConsolidada> getTransacoesConsolidadas() {
        return Collections.singletonList(this.transacaoConsolidada);
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return transacaoConsolidada != null ? transacaoConsolidada.getPontosDeVenda() : Collections.emptyList();
    }

    public List<AutorizacaoPagamento> getAutorizacoesPagamento() {
        return autorizacoesPagamento;
    }

    public void setAutorizacoesPagamento(List<AutorizacaoPagamento> autorizacoesPagamento) {
        this.autorizacoesPagamento = autorizacoesPagamento;
    }

    @Override
    public Integer getStatusLiberacaoPagamento() {
        return StatusLiberacaoReembolsoJde.APROVADO_PAGAMENTO.getValue();
    }

    @Override
    @Transient
    public boolean estaAprovadoParaPagamento() {
        return true;
    }
}
