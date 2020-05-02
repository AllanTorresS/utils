package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Representa a tabela de Transacao Consolidada Detalhe
 */
@Entity
@Audited
@Table(name = "TRANS_CONSOL_DETALHE")
public class TransacaoConsolidadaDetalhe implements IPersistente, IPertenceFrota, IPertenceRevendedor {

    private static final long serialVersionUID = -7711210011538047216L;
    @Id
    @Column(name = "CD_TRANS_CONSOL_DETALHE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANS_CONSOL_DETALHE")
    @SequenceGenerator(name = "SEQ_TRANS_CONSOL_DETALHE", sequenceName = "SEQ_TRANS_CONSOL_DETALHE", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TRANS_CONSOL")
    private TransacaoConsolidada transacaoConsolidada;

    @NotNull
    @Column(name = "CD_ITEM")
    private String codigoItem;

    @NotNull
    @Column(name = "NM_ITEM")
    private String nomeItem;

    @DecimalMin("-999999999999.9999")
    @DecimalMax("999999999999.9999")
    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_VALOR_UNIT")
    private BigDecimal valorUnitario;

    @DecimalMin("00.0000")
    @DecimalMax("999999999999.9999")
    @Digits(integer = 12, fraction = 4)
    @Column(name = "QT_QTD")
    private BigDecimal quantidade;

    @NotNull
    @DecimalMin("-999999999999.9999")
    @DecimalMax("999999999999.9999")
    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_TOTAL")
    private BigDecimal valorTotal;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    /**
     * Construtor
     */
    public TransacaoConsolidadaDetalhe() {
        this.quantidade = BigDecimal.ZERO;
        this.valorTotal = BigDecimal.ZERO;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public TransacaoConsolidada getTransacaoConsolidada() {
        return transacaoConsolidada;
    }

    public void setTransacaoConsolidada(TransacaoConsolidada transacaoConsolidada) {
        this.transacaoConsolidada = transacaoConsolidada;
    }

    public String getCodigoItem() {
        return codigoItem;
    }

    public void setCodigoItem(String codigoItem) {
        this.codigoItem = codigoItem;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Transient
    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return transacaoConsolidada != null ? transacaoConsolidada.getPontosDeVenda() : Collections.emptyList();
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return transacaoConsolidada != null ? transacaoConsolidada.getFrotas() : Collections.emptyList();
    }
}
