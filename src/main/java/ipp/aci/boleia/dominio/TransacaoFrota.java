package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Transacao Frota
 */
@Entity
@Audited
@Table(name = "TRANSACAO_FROTA")
public class TransacaoFrota implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = -7534051067613640276L;

    @Id
    @Column(name = "CD_TRANSACAO_FROTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANSACAO_FROTA")
    @SequenceGenerator(name = "SEQ_TRANSACAO_FROTA", sequenceName = "SEQ_TRANSACAO_FROTA", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @NotNull
    @Column(name = "ID_TIPO")
    private Integer tipoTransacao;

    @NotNull
    @Column(name = "DT_TRANSACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataTransacao;

    @NotNull
    @Digits(integer = 10, fraction = 12)
    @Column(name = "VA_TOTAL")
    private BigDecimal valorTotal;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "transacaoFrota")
    private AutorizacaoPagamento autorizacaoPagamento;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "transacaoFrota")
    @AuditJoinTable(name = "EXTRATO_PEDIDO_TRANSACAO_AUD")
    private List<ExtratoPedidoTransacao> extratosTransacao;

    @Column(name = "ID_CONSUMIU_CREDITO_PRE")
    private Boolean consumiuCreditoPrePago;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public Integer getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(Integer tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public Date getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(Date dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public AutorizacaoPagamento getAutorizacaoPagamento() {
        return autorizacaoPagamento;
    }

    public void setAutorizacaoPagamento(AutorizacaoPagamento autorizacaoPagamento) {
        this.autorizacaoPagamento = autorizacaoPagamento;
    }

    public List<ExtratoPedidoTransacao> getExtratosTransacao() {
        return extratosTransacao;
    }

    public void setExtratosTransacao(List<ExtratoPedidoTransacao> extratosTransacao) {
        this.extratosTransacao = extratosTransacao;
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }

    public Boolean getConsumiuCreditoPrePago() {
        return consumiuCreditoPrePago;
    }

    public void setConsumiuCreditoPrePago(Boolean consumiuCreditoPrePago) {
        this.consumiuCreditoPrePago = consumiuCreditoPrePago;
    }
}
