package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.enums.ModalidadePagamento;
import ipp.aci.boleia.dominio.enums.StatusNotaFiscal;
import ipp.aci.boleia.dominio.enums.StatusTransacaoConsolidada;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.seguranca.UtilitarioCriptografia;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
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
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

/**
 * Representa a tabela de Transacao Consolidada
 */
@Entity
@Audited
@Table(name = "TRANS_CONSOL")
public class TransacaoConsolidada implements IPersistente, IPertenceFrota, IPertenceRevendedor {

    private static final long serialVersionUID = 8095939439819340567L;

    @Id
    @Column(name = "CD_TRANS_CONSOL")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANS_CONSOL")
    @SequenceGenerator(name = "SEQ_TRANS_CONSOL", sequenceName = "SEQ_TRANS_CONSOL", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PTOV")
    private FrotaPontoVenda frotaPtov;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "transacaoConsolidada", cascade = CascadeType.ALL)
    private List<TransacaoConsolidadaDetalhe> produtos;

    @DecimalMin("-999999999999.9999")
    @DecimalMax("999999999999.9999")
    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_TOTAL")
    private BigDecimal valorTotal;

    @NotNull
    @Column(name = "DT_INI_PER")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicioPeriodo;

    @NotNull
    @Column(name = "DT_FIM_PER")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFimPeriodo;

    @NotNull
    @Column(name = "DT_PRAZO_EMIS_NFE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPrazoEmissaoNfe;

    @NotNull
    @Column(name = "QT_PRAZO_PGTO_DIAS")
    private Long prazoPagtoDias;

    @NotNull
    @Column(name = "QT_PRAZO_REEMB_DIAS")
    private Long prazoReembolsoDias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_COBRANCA")
    private Cobranca cobranca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_REEMBOLSO")
    private Reembolso reembolso;

    @Column(name = "VR_REEMB")
    private BigDecimal valorReembolso;

    @Column(name = "VR_DESC")
    private BigDecimal valorDesconto;

    @Column(name = "MDR")
    private BigDecimal mdr;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_TOTAL_NF")
    private BigDecimal valorTotalNotaFiscal;

    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_EMITIDO_NF")
    private BigDecimal valorEmitidoNotaFiscal;

    @Column(name = "QT_ABASTECIMENTOS")
    private Long quantidadeAbastecimentos;

    @Column(name = "ID_STATUS_NF")
    private Integer statusNotaFiscal;

    @Column(name = "DT_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @NotNull
    @Column(name = "CD_MOD_PGTO")
    private Integer modalidadePagamento;

    @NotNull
    @Column(name = "ID_STATUS_CONSOLIDACAO")
    private Integer statusConsolidacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_EMPR_AGREGADA")
    private EmpresaAgregada empresaAgregada;

    @NotNull
    @Column(name = "CD_CHAVE")
    private String chave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_UNIDADE")
    private Unidade unidade;

    @DecimalMin("-999999999999.9999")
    @DecimalMax("999999999999.9999")
    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_DESCONTO_ABASTECIMENTOS")
    private BigDecimal valorDescontoAbastecimentos;

    @OneToMany(mappedBy = "transacaoConsolidada", fetch = FetchType.LAZY)
    private List<AutorizacaoPagamento> autorizacaoPagamentos;

    @DecimalMin("-999999999999.9999")
    @DecimalMax("999999999999.9999")
    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_DESCONTO_NOTA_FISCAL")
    private BigDecimal valorDescontoNotaFiscal;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public FrotaPontoVenda getFrotaPtov() {
        return frotaPtov;
    }

    public void setFrotaPtov(FrotaPontoVenda frotaPtov) {
        this.frotaPtov = frotaPtov;
    }

    public List<TransacaoConsolidadaDetalhe> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<TransacaoConsolidadaDetalhe> produtos) {
        this.produtos = produtos;
    }

    public Date getDataInicioPeriodo() {
        return dataInicioPeriodo;
    }

    public void setDataInicioPeriodo(Date dataInicioPeriodo) {
        this.dataInicioPeriodo = dataInicioPeriodo;
    }

    public Date getDataFimPeriodo() {
        return dataFimPeriodo;
    }

    public void setDataFimPeriodo(Date dataFimPeriodo) {
        this.dataFimPeriodo = dataFimPeriodo;
    }

    public Date getDataPrazoEmissaoNfe() {
        return dataPrazoEmissaoNfe;
    }

    public void setDataPrazoEmissaoNfe(Date dataPrazoEmissaoNfe) {
        this.dataPrazoEmissaoNfe = dataPrazoEmissaoNfe;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Long getPrazoPagtoDias() {
        return prazoPagtoDias;
    }

    public void setPrazoPagtoDias(Long prazoPagtoDias) {
        this.prazoPagtoDias = prazoPagtoDias;
    }

    public Long getPrazoReembolsoDias() {
        return prazoReembolsoDias;
    }

    public void setPrazoReembolsoDias(Long prazoReembolsoDias) {
        this.prazoReembolsoDias = prazoReembolsoDias;
    }

    public Cobranca getCobranca() {
        return cobranca;
    }

    public void setCobranca(Cobranca cobranca) {
        this.cobranca = cobranca;
    }

    public Reembolso getReembolso() {
        return reembolso;
    }

    public void setReembolso(Reembolso reembolso) {
        this.reembolso = reembolso;
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

    public BigDecimal getMdr() {
        return mdr;
    }

    public void setMdr(BigDecimal mdr) {
        this.mdr = mdr;
    }

    public BigDecimal getValorTotalNotaFiscal() {
        return valorTotalNotaFiscal;
    }

    public void setValorTotalNotaFiscal(BigDecimal valorTotalNotaFiscal) {
        this.valorTotalNotaFiscal = valorTotalNotaFiscal;
    }

    public BigDecimal getValorEmitidoNotaFiscal() {
        return valorEmitidoNotaFiscal;
    }

    public void setValorEmitidoNotaFiscal(BigDecimal valorEmitidoNotaFiscal) {
        this.valorEmitidoNotaFiscal = valorEmitidoNotaFiscal;
    }

    public Long getQuantidadeAbastecimentos() {
        return quantidadeAbastecimentos;
    }

    public void setQuantidadeAbastecimentos(Long quantidadeAbastecimentos) {
        this.quantidadeAbastecimentos = quantidadeAbastecimentos;
    }

    public List<AutorizacaoPagamento> getAutorizacaoPagamentos() {
        return autorizacaoPagamentos;
    }

    public Stream<AutorizacaoPagamento> getAutorizacaoPagamentosStream() {
        return autorizacaoPagamentos.stream();
    }

    public void setAutorizacaoPagamentos(List<AutorizacaoPagamento> autorizacaoPagamentos) {
        this.autorizacaoPagamentos = autorizacaoPagamentos;
    }

    public Integer getStatusNotaFiscal() {
        return statusNotaFiscal;
    }

    public void setStatusNotaFiscal(Integer statusNotaFiscal) {
        this.statusNotaFiscal = statusNotaFiscal;
    }

    @Transient
    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return frotaPtov != null ? Collections.singletonList(frotaPtov.getPontoVenda()) : Collections.emptyList();
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return frotaPtov != null ? Collections.singletonList(frotaPtov.getFrota()) : Collections.emptyList();
    }

    @Transient
    public Frota getFrota() {
        return frotaPtov != null ? frotaPtov.getFrota() : null;
    }

    @Transient
    public PontoDeVenda getPontoVenda() {
        return frotaPtov != null ? frotaPtov.getPontoVenda() : null;
    }

    /**
     * Obtem o a data da última NF
     *
     * @return A data da última NF
     */
    @Transient
    public Date getDataUltimaSubidaNF() {
       return emptyIfNull(this.getAutorizacaoPagamentos()).stream().map(a ->
               a.getNotasFiscais().stream().map(n -> n.getDataEmissao()).max(Date::compareTo)
                       .orElse(null)).filter(Objects::nonNull).max(Date::compareTo).orElse(null);
    }

     /**
     * Obtem o status da consolidacao a partir da data atual
     *
     * @param dataCorrente A data atual
     * @return O status da consolidacao
     */
    public StatusNotaFiscal obterStatusNotaFiscal(Date dataCorrente) {
        if (statusNotaFiscal == null || statusNotaFiscal.equals(StatusNotaFiscal.PENDENTE.getValue())) {
            if (dataCorrente.after(getDataPrazoEmissaoNfe())) {
                return StatusNotaFiscal.ATRASADA;
            } else {
                return StatusNotaFiscal.PENDENTE;
            }
        } else {
            return StatusNotaFiscal.obterPorValor(statusNotaFiscal);
        }
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Integer getModalidadePagamento() {
        return modalidadePagamento;
    }

    public void setModalidadePagamento(Integer modalidadePagamento) {
        this.modalidadePagamento = modalidadePagamento;
    }

    public Integer getStatusConsolidacao() {
        return statusConsolidacao;
    }

    public void setStatusConsolidacao(Integer statusConsolidacao) {
        this.statusConsolidacao = statusConsolidacao;
    }

    public EmpresaAgregada getEmpresaAgregada() {
        return empresaAgregada;
    }

    public void setEmpresaAgregada(EmpresaAgregada empresaAgregada) {
        this.empresaAgregada = empresaAgregada;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public BigDecimal getValorDescontoAbastecimentos() {
        return valorDescontoAbastecimentos != null ? valorDescontoAbastecimentos : BigDecimal.ZERO;
    }

    public void setValorDescontoAbastecimentos(BigDecimal valorDescontoAbastecimentos) {
        this.valorDescontoAbastecimentos = valorDescontoAbastecimentos;
    }

    public BigDecimal getValorDescontoNotaFiscal() {
        return valorDescontoNotaFiscal;
    }

    public void setValorDescontoNotaFiscal(BigDecimal valorDescontoNotaFiscal) {
        this.valorDescontoNotaFiscal = valorDescontoNotaFiscal;
    }


    /**
     * Gera uma chave unica para cada TransacaoConsolidada, tendo o objetivo de garantir que cada ciclo seja unico no banco de dados.
     * Existe uma constraint (UQ_CHAVE_TRANS_CONSOL) no banco que valida a unicidade da chave.
     */
    public void preencherChave() {
        String key = frotaPtov.getId().toString() + "|"
                + UtilitarioFormatacaoData.formatarDataCurta(dataInicioPeriodo) + "|"
                + UtilitarioFormatacaoData.formatarDataCurta(dataFimPeriodo) + "|";
        if (empresaAgregada != null && empresaAgregada.getId() != null) {
            key = key + empresaAgregada.getId();
        } else if(unidade != null && unidade.getId() != null) {
            key = key + unidade.getId();
        }
        this.chave = UtilitarioCriptografia.calcularHashSHA256(key);
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    /**
     * Verifica se a transação consolidada é pre-paga
     * @return se é pre paga ou não
     */
    @Transient
    public Boolean isPrePaga() {
        return ModalidadePagamento.PRE_PAGO.getValue().equals(modalidadePagamento);
    }

    /**
     * Verifica o status da transacao
     *
     * @param status Status a ser verificado
     * @return True, caso o status da transação seja igual ao verificado
     */
    @Transient
    public boolean esta(StatusTransacaoConsolidada status) {
        return status.getValue().equals(getStatusConsolidacao());
    }

    /**
     * Informa se o status de nota fiscal da transação consolida é EMITIDA.
     *
     * @return True, caso seja status EMITIDA.
     */
    @Transient
    public boolean isNFEmitida() {
        return StatusNotaFiscal.EMITIDA.getValue().equals(statusNotaFiscal);
    }

    /**
     * Informa se o prazo para emissão de nota fiscal do ciclo venceu.
     *
     * @param dataHoraCorrente Data e hora atual
     * @return true, caso tenha vencido.
     */
    @Transient
    public boolean isPrazoEmissaoNotaVencido(Date dataHoraCorrente) {
        return dataPrazoEmissaoNfe.after(dataHoraCorrente);
    }

    /**
     * Retorna a data prevista de vencimento do pagamento do reembolso para esse ciclo.
     *
     * @return data prevista do vencimento.
     */
    @Transient
    public Date getPrevisaoVencimentoReembolso() {
        return UtilitarioCalculoData.adicionarDiasData(dataPrazoEmissaoNfe, 2);
    }

    /**
     * Informa se a transação consolidada exige emissão de nota fiscal.
     *
     * @return true, caso exija.
     */
    @Transient
    public boolean exigeEmissaoNF() {
        return frotaPtov.getFrota().exigeNotaFiscal() || unidade != null || empresaAgregada != null;
    }

    /**
     * Informa se a transação consolidada possui pendencia de nota fiscal.
     *
     * @return true, caso possua pendencia.
     */
    @Transient
    public boolean pendenteNotaFiscal() {
        return exigeEmissaoNF() && !isNFEmitida() &&
                !this.getStatusNotaFiscal().equals(StatusNotaFiscal.SEM_EMISSAO.getValue());
    }
}