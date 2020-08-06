package ipp.aci.boleia.dominio;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Representa um ciclo de repasses para o Pró-frotas
 */
@Audited
@Entity
@Table(name="CICLO_REPASSE")
public class CicloRepasse implements IPersistente {

    private static final long serialVersionUID = -4937560330471108329L;

    @Id
    @Column(name = "CD_CICLO_REPASSE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CICLO_REPASSE")
    @SequenceGenerator(name = "SEQ_CICLO_REPASSE", sequenceName = "SEQ_CICLO_REPASSE", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_CONFIGURACAO_REPASSE")
    private ConfiguracaoRepasse configuracaoRepasse;

    /**
     * O valor total de abastecimentos realizados no ciclo
     */
    @Column(name="VA_TOTAL")
    private BigDecimal valorTotal;

    /**
     * O percentual de repasse aplicado
     */
    @Column(name="VA_PERCENTUAL_REPASSE")
    private BigDecimal valorPercentualRepasse;

    /**
     * O valor a ser repassado ao Pró-frotas
     */
    @Column(name="VA_NOMINAL_REPASSE")
    private BigDecimal valorNominalRepasse;

    @Column(name = "DT_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicio;

    @Column(name = "DT_FIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFim;

    @Column(name = "DT_VENC_PGTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataVencimento;

    @Column(name = "DT_PGTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPagamento;

    @Column(name = "NO_DOC_JDE")
    private Integer numeroDocumento;

    @Column(name = "ID_STATUS")
    private Integer status;

    /**
     * O valor de repasse remanescente do ciclo anterior, caso o mesmo tenha tido um valor negativo
     */
    @Column(name="VA_CREDITO")
    private BigDecimal valorCredito;

    @OneToMany(mappedBy = "cicloRepasse", fetch = FetchType.LAZY)
    private List<AutorizacaoPagamento> autorizacaoPagamentos;

    @Column(name="DS_MSG_ERRO_JDE")
    private String mensagemErroJDE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda pontoDeVenda;

    @Column(name="ID_STATUS_INT_JDE")
    private Integer statusIntegracaoJDE;

    @Column(name="NM_CIA_DOC")
    private String ciaDocumento;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public ConfiguracaoRepasse getConfiguracaoRepasse() {
        return configuracaoRepasse;
    }

    public void setConfiguracaoRepasse(ConfiguracaoRepasse configuracaoRepasse) {
        this.configuracaoRepasse = configuracaoRepasse;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorPercentualRepasse() {
        return valorPercentualRepasse;
    }

    public void setValorPercentualRepasse(BigDecimal valorPercentualRepasse) {
        this.valorPercentualRepasse = valorPercentualRepasse;
    }

    public BigDecimal getValorNominalRepasse() {
        return valorNominalRepasse;
    }

    public void setValorNominalRepasse(BigDecimal valorNominalRepasse) {
        this.valorNominalRepasse = valorNominalRepasse;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Integer getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(Integer numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getValorCredito() {
        return valorCredito;
    }

    public void setValorCredito(BigDecimal valorCredito) {
        this.valorCredito = valorCredito;
    }

    public List<AutorizacaoPagamento> getAutorizacaoPagamentos() {
        return autorizacaoPagamentos;
    }

    public void setAutorizacaoPagamentos(List<AutorizacaoPagamento> autorizacaoPagamentos) {
        this.autorizacaoPagamentos = autorizacaoPagamentos;
    }

    public String getMensagemErroJDE() {
        return mensagemErroJDE;
    }

    public void setMensagemErroJDE(String mensagemErroJDE) {
        this.mensagemErroJDE = mensagemErroJDE;
    }

    public PontoDeVenda getPontoDeVenda() {
        return pontoDeVenda;
    }

    public void setPontoDeVenda(PontoDeVenda pontoDeVenda) {
        this.pontoDeVenda = pontoDeVenda;
    }

    public Integer getStatusIntegracaoJDE() {
        return statusIntegracaoJDE;
    }

    public void setStatusIntegracaoJDE(Integer statusIntegracaoJDE) {
        this.statusIntegracaoJDE = statusIntegracaoJDE;
    }

    public String getCiaDocumento() {
        return ciaDocumento;
    }

    public void setCiaDocumento(String ciaDocumento) {
        this.ciaDocumento = ciaDocumento;
    }
}
