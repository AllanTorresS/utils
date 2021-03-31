package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.StatusLiberacaoReembolsoJde;
import org.hibernate.envers.Audited;

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
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Reembolso
 */
@Audited
@Entity
@Table(name = "REEMBOLSO")
public class Reembolso extends ReembolsoBase {

    private static final long serialVersionUID = -5283245531897409405L;

    @Id
    @Column(name = "CD_REEMBOLSO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REEMBOLSO")
    @SequenceGenerator(name = "SEQ_REEMBOLSO", sequenceName = "SEQ_REEMBOLSO", allocationSize = 1)
    private Long id;

    @Column(name = "VR_REEMB")
    private BigDecimal valorReembolso;

    @Column(name = "VR_DESC")
    private BigDecimal valorDesconto;

    @Column(name = "VR_TOTAL")
    private BigDecimal valorTotal;

    @Column(name = "DS_MSG_ERRO_LIB_JDE")
    private String mensagemErroLiberacaoPagamento;

    @Column(name = "ID_STATUS_LIB_JDE")
    private Integer statusLiberacaoPagamento;

    @Column(name = "VR_DESC_CRED")
    private BigDecimal valorDescontoCredito;

    @Column(name = "VR_DESC_ANTECIP")
    private BigDecimal valorDescontoAntecipacao;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reembolso")
    private List<TransacaoConsolidada> transacoesConsolidadas;

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
    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    @Override
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
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
    public BigDecimal getValorDescontoVoucher() {
        BigDecimal valorDesconto = getValorDesconto();
        if(getValorDescontoAntecipacao() != null) {
            valorDesconto = valorDesconto.add(getValorDescontoAntecipacao());
        }
        return valorDesconto;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Override
    public List<TransacaoConsolidada> getTransacoesConsolidadas() {
        return transacoesConsolidadas;
    }

    public void setTransacoesConsolidadas(List<TransacaoConsolidada> transacoesConsolidadas) {
        this.transacoesConsolidadas = transacoesConsolidadas;
    }

    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return transacoesConsolidadas != null ? transacoesConsolidadas.get(0).getPontosDeVenda() : Collections.emptyList();
    }

    public String getMensagemErroLiberacaoPagamento() {
        return mensagemErroLiberacaoPagamento;
    }

    public void setMensagemErroLiberacaoPagamento(String mensagemErroLiberacaoPagamento) {
        this.mensagemErroLiberacaoPagamento = mensagemErroLiberacaoPagamento;
    }

    @Override
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

    public BigDecimal getValorDescontoAntecipacao() {
        return valorDescontoAntecipacao;
    }

    public void setValorDescontoAntecipacao(BigDecimal valorDescontoAntecipacao) {
        this.valorDescontoAntecipacao = valorDescontoAntecipacao;
    }

    @Transient
    public StatusLiberacaoReembolsoJde getStatusLiberacaoPagamentoEnum() {
        return StatusLiberacaoReembolsoJde.obterPorValor(statusLiberacaoPagamento);
    }

    /**
     * Informa se o reembolso possui alguma transação consolidada com
     * pendência de emissão de nota fiscal.
     *
     * @return True, caso possua pendencia.
     */
    @Transient
    public boolean possuiPendenciaNotaFiscal() {
        return transacoesConsolidadas.stream().anyMatch(TransacaoConsolidada::pendenteNotaFiscal);
    }

    /**
     * Informa se o reembolso está liberado para a realização do seu pagamento.
     *
     * @return true, caso esteja liberado.
     */
    @Transient
    @Override
    public boolean estaAprovadoParaPagamento() {
        return StatusLiberacaoReembolsoJde.APROVADO_PAGAMENTO.getValue() == statusLiberacaoPagamento;
    }

}
