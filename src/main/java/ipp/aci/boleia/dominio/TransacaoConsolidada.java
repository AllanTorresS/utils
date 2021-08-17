package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.ModalidadePagamento;
import ipp.aci.boleia.dominio.enums.MotivoEstorno;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoReembolsoJde;
import ipp.aci.boleia.dominio.enums.StatusNotaFiscal;
import ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso;
import ipp.aci.boleia.dominio.enums.StatusTransacaoConsolidada;
import ipp.aci.boleia.dominio.enums.TipoAntecipacao;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.seguranca.UtilitarioCriptografia;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.annotations.Formula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Basic;
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
import javax.persistence.OneToOne;
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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso.AGUARDANDO_NF;
import static ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso.EM_ABERTO;
import static ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso.NF_ATRASADA;
import static ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso.PREVISTO;
import static ipp.aci.boleia.util.UtilitarioLambda.verificarTodosNaoNulos;

/**
 * Representa a tabela de Transacao Consolidada
 */
@Entity
@Audited
@Table(name = "TRANS_CONSOL")
public class TransacaoConsolidada implements IPersistente, IPertenceFrota, IPertenceRevendedor {

    private static final long serialVersionUID = 8095939439819340567L;

    private static final String QT_COMPLETA_ABASTECIMENTO_FORMULA = "(SELECT Q.QT_COMPLETA_ABASTECIMENTOS FROM BOLEIA_SCHEMA.V_T_CONSOL_QT_ABASTECIMENTO Q WHERE Q.CD_TRANS_CONSOL = CD_TRANS_CONSOL)";

    private static final String FORMULA_POSSUI_ANTECIPACAO_REALIZADA = "(SELECT CASE WHEN (SELECT COUNT(A.CD_REEMB_ANTECIP) FROM BOLEIA_SCHEMA.REEMB_ANTECIP A WHERE A.ID_STATUS_INT_JDE = 1 AND A.CD_TRANS_CONSOL = CD_TRANS_CONSOL) > 0 THEN 1 ELSE 0 END FROM dual)";
    private static final String FORMULA_POSSUI_ANTECIPACAO_COM_ERRO = "(SELECT CASE WHEN (SELECT COUNT(A.CD_REEMB_ANTECIP) FROM BOLEIA_SCHEMA.REEMB_ANTECIP A WHERE A.ID_STATUS_INT_JDE = 0 AND A.CD_TRANS_CONSOL = CD_TRANS_CONSOL) > 0 THEN 1 ELSE 0 END FROM dual)";

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

    @NotNull
    @Column(name = "DT_INI_PER")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicioPeriodo;

    @NotNull
    @Column(name = "DT_FIM_PER")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFimPeriodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_COBRANCA")
    private Cobranca cobranca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_REEMBOLSO")
    private Reembolso reembolso;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Column(name = "QT_ABASTECIMENTOS")
    private Long quantidadeAbastecimentos;

    @NotAudited
    @Formula(QT_COMPLETA_ABASTECIMENTO_FORMULA)
    @Basic(fetch = FetchType.LAZY)
    private Long quantidadeCompletaAbastecimentos;

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

    @OneToMany(mappedBy = "transacaoConsolidada", fetch = FetchType.LAZY)
    private List<AutorizacaoPagamento> autorizacaoPagamentos;

    @OneToMany(mappedBy = "transacaoConsolidadaPostergada", fetch = FetchType.LAZY)
    private List<AutorizacaoPagamento> autorizacoesPagamentoPostergadas;

    @DecimalMin("-999999999999.9999")
    @DecimalMax("999999999999.9999")
    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_TOTAL")
    private BigDecimal valorTotal;

    @Column(name = "VR_REEMB")
    private BigDecimal valorReembolso;

    @Column(name = "VR_DESC")
    private BigDecimal valorDesconto;

    @Column(name = "VR_FATURAMENTO")
    private BigDecimal valorFaturamento;

    @Column(name = "MDR")
    private BigDecimal mdr;

    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_TOTAL_NF")
    private BigDecimal valorTotalNotaFiscal;

    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_EMITIDO_NF")
    private BigDecimal valorEmitidoNotaFiscal;

    @Column(name = "DT_ULTIMA_EMISSAO_NF")
    private Date dataUltimaEmissaoNf;

    @DecimalMin("-999999999999.9999")
    @DecimalMax("999999999999.9999")
    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_DESCONTO_ABASTECIMENTOS")
    private BigDecimal valorDescontoAbastecimentos;

    @DecimalMin("-999999999999.9999")
    @DecimalMax("999999999999.9999")
    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_DESCONTO_NOTA_FISCAL")
    private BigDecimal valorDescontoNotaFiscal;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TRANS_CONSOL_PRAZOS")
    private TransacaoConsolidadaPrazos prazos;

    @NotNull
    @Column(name = "ID_PROCESSOU_POSTERGACAO")
    private boolean processouPostergacao;

    @NotNull
    @Column(name = "ID_FROTA_EXIGE_NF")
    private boolean frotaExigeNF;

    @Column(name="ID_FROTA_GERENCIA_NF")
    private Boolean frotaGerenciaNf;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "transacaoConsolidada")
    private List<ReembolsoAntecipado> antecipacoes;

    @NotAudited
    @Formula(FORMULA_POSSUI_ANTECIPACAO_REALIZADA)
    @Basic(fetch = FetchType.LAZY)
    private Boolean possuiAntecipacaoRealizada;

    @NotAudited
    @Formula(FORMULA_POSSUI_ANTECIPACAO_COM_ERRO)
    @Basic(fetch = FetchType.LAZY)
    private Boolean possuiAntecipacaoComErro;

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

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
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

    /**
     * Retorna a quantidade de abastecimentos da transação consolidada.
     * OBS.: Não considera os abastecimentos com valor negativo.
     *
     * @return A quantidade de abastecimentos.
     */
    public Long getQuantidadeAbastecimentos() {
        return quantidadeAbastecimentos;
    }

    public void setQuantidadeAbastecimentos(Long quantidadeAbastecimentos) {
        this.quantidadeAbastecimentos = quantidadeAbastecimentos;
    }

    /**
     * Retorna a quantidade completa de abastecimentos da transação consolidada.
     * OBS.: Considera os abastecimentos com valor negativo.
     *
     * @return A quantidade completa de abastecimentos.
     */
    public Long getQuantidadeCompletaAbastecimentos() {
        return quantidadeCompletaAbastecimentos;
    }

    public void setQuantidadeCompletaAbastecimentos(Long quantidadeCompletaAbastecimentos) {
        this.quantidadeCompletaAbastecimentos = quantidadeCompletaAbastecimentos;
    }

    public List<AutorizacaoPagamento> getAutorizacaoPagamentos() {
        return autorizacaoPagamentos;
    }

    @Transient
    public Stream<AutorizacaoPagamento> getAutorizacaoPagamentosStream() {
        return autorizacaoPagamentos != null ? autorizacaoPagamentos.stream() : Stream.empty();
    }

    public void setAutorizacaoPagamentos(List<AutorizacaoPagamento> autorizacaoPagamentos) {
        this.autorizacaoPagamentos = autorizacaoPagamentos;
    }

    public List<AutorizacaoPagamento> getAutorizacoesPagamentoPostergadas() {
        return autorizacoesPagamentoPostergadas;
    }

    @Transient
    public Stream<AutorizacaoPagamento> getAutorizacoesPagamentoPostergadasStream() {
        return autorizacoesPagamentoPostergadas != null ? autorizacoesPagamentoPostergadas.stream() : Stream.empty();
    }

    public void setAutorizacoesPagamentoPostergadas(List<AutorizacaoPagamento> autorizacoesPagamentoPostergadas) {
        this.autorizacoesPagamentoPostergadas = autorizacoesPagamentoPostergadas;
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

    public Date getDataUltimaEmissaoNf() {
        return dataUltimaEmissaoNf;
    }

    public void setDataUltimaEmissaoNf(Date dataUltimaEmissaoNf) {
        this.dataUltimaEmissaoNf = dataUltimaEmissaoNf;
    }

    /**
     * Obtem o status da consolidacao a partir da data atual
     *
     * @param dataCorrente A data atual
     * @return O status da consolidacao
     */
    public StatusNotaFiscal obterStatusNotaFiscal(Date dataCorrente) {
        if (statusNotaFiscal == null || statusNotaFiscal.equals(StatusNotaFiscal.PENDENTE.getValue())) {
            if (dataCorrente.after(prazos.getDataLimiteEmissaoNfe()) && isFrotaExigeNF()) {
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

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
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

    public BigDecimal getValorFaturamento() {
        return valorFaturamento;
    }

    public void setValorFaturamento(BigDecimal valorFaturamento) {
        this.valorFaturamento = valorFaturamento;
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

    public BigDecimal getValorDescontoAbastecimentos() {
        return valorDescontoAbastecimentos;
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

    public TransacaoConsolidadaPrazos getPrazos() {
        return prazos;
    }

    public void setPrazos(TransacaoConsolidadaPrazos prazos) {
        this.prazos = prazos;
    }

    public boolean isProcessouPostergacao() {
        return processouPostergacao;
    }

    public void setProcessouPostergacao(boolean processouPostergacao) {
        this.processouPostergacao = processouPostergacao;
    }

    public boolean isFrotaExigeNF() {
        return frotaExigeNF;
    }

    public void setFrotaExigeNF(boolean frotaExigeNF) {
        this.frotaExigeNF = frotaExigeNF;
    }

    public Boolean getFrotaGerenciaNf() {
        return frotaGerenciaNf;
    }

    public void setFrotaGerenciaNf(Boolean frotaGerenciaNf) {
        this.frotaGerenciaNf = frotaGerenciaNf;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    /**
     * Gera uma chave unica para cada TransacaoConsolidada, tendo o objetivo de garantir que cada ciclo seja unico no banco de dados.
     * Existe uma constraint (UQ_CHAVE_TRANS_CONSOL) no banco que valida a unicidade da chave.
     */
    public void preencherChave() {
        String key = frotaPtov.getId().toString() + "|"
                + UtilitarioFormatacaoData.formatarDataCurta(dataInicioPeriodo) + "|"
                + UtilitarioFormatacaoData.formatarDataCurta(dataFimPeriodo) + "|";
        StringBuilder keyBuilder = new StringBuilder(key);
        if (empresaAgregada != null && empresaAgregada.getId() != null) {
            keyBuilder.append(empresaAgregada.getId() + empresaAgregada.getCnpj());
        } else if(unidade != null && unidade.getId() != null) {
            keyBuilder.append(unidade.getId() + unidade.getCnpj());
        } else if (frotaExigeNF) {
            keyBuilder.append(frotaPtov.getFrota().getId() + frotaPtov.getFrota().getCnpj());
        } else if(frotaGerenciaNf != null) {
            keyBuilder.append(frotaGerenciaNf);
        }
        this.chave = UtilitarioCriptografia.calcularHashSHA256(keyBuilder.toString());
    }

    public List<ReembolsoAntecipado> getAntecipacoes() {
        return antecipacoes;
    }

    public void setAntecipacoes(List<ReembolsoAntecipado> antecipacoes) {
        this.antecipacoes = antecipacoes;
    }

    public Boolean getPossuiAntecipacaoRealizada() {
        return possuiAntecipacaoRealizada;
    }

    public void setPossuiAntecipacaoRealizada(Boolean possuiAntecipacaoRealizada) {
        this.possuiAntecipacaoRealizada = possuiAntecipacaoRealizada;
    }

    public Boolean getPossuiAntecipacaoComErro() {
        return possuiAntecipacaoComErro;
    }

    public void setPossuiAntecipacaoComErro(Boolean possuiAntecipacaoComErro) {
        this.possuiAntecipacaoComErro = possuiAntecipacaoComErro;
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
        return prazos.getDataLimiteEmissaoNfe().after(dataHoraCorrente);
    }

    /**
     * Retorna a data prevista de vencimento do pagamento do reembolso para esse ciclo.
     *
     * @return data prevista do vencimento.
     */
    @Transient
    public Date getPrevisaoVencimentoReembolso() {
        return UtilitarioCalculoData.adicionarDiasData(prazos.getDataLimiteEmissaoNfe(), 2);
    }

    /**
     * Informa se a transação consolidada exige emissão de nota fiscal.
     *
     * @return true, caso exija.
     */
    @Transient
    public boolean exigeEmissaoNF() {
        return frotaExigeNF || unidade != null || empresaAgregada != null;
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

    /**
     * Informa se é a transação consolidada é passível de emissão de nota fiscal.
     *
     * @return true, caso seja.
     */
    @Transient
    public boolean isPassivelDeEmissao() {
        return exigeEmissaoNF() || frotaGerenciaNf != null && frotaGerenciaNf;
    }

    /**
     * Retorna uma lista com todas as autorizações de pagamento associadas a transação consolidada.
     *
     * @return lista de autorizações de pagamento.
     */
    @Transient
    public List<AutorizacaoPagamento> getAutorizacoesPagamentoAssociadas() {
        return Stream.concat(getAutorizacaoPagamentosStream(), getAutorizacoesPagamentoPostergadasStream()).collect(Collectors.toList());
    }

    /**
     * Retorna uma lista com todas as autorizações de pagamento vigentes.
     *
     * @return lista de autorizações de pagamento.
     */
    @Transient
    public List<AutorizacaoPagamento> getAutorizacoesPagamentoVigentes() {
        return Stream.concat(this.getAutorizacaoPagamentosStream().filter(a -> a.getTransacaoConsolidadaPostergada() == null), getAutorizacoesPagamentoPostergadasStream()).collect(Collectors.toList());
    }

    /**
     * Obtém o valor antecipado pela revenda.
     * @return valor antecipado
     */
    @Transient
    public BigDecimal getValorAntecipadoRevenda() {
        return this.getAutorizacoesPagamentoVigentes().stream()
                    .filter(AutorizacaoPagamento::possuiAntecipacaoParceriaRealizada)
                    .reduce(BigDecimal.ZERO, (soma, a) -> soma.add(a.getValorAntecipadoRevenda()), BigDecimal::add);
    }

    /**
     * Obtém o valor antecipado pelo usuário interno.
     * @return valor antecipado
     */
    @Transient
    public BigDecimal getValorAntecipadoSolucao() {
        return this.getAntecipacaoSolucaoRealizada() != null
                ? this.getAntecipacaoSolucaoRealizada().getValorReembolso()
                : BigDecimal.ZERO;
    }

    /**
     * Obtém o valor total antecipado pela revenda e solução, após desconto das taxas aplicáveis.
     * @return valor antecipado líquido
     */
    @Transient
    public BigDecimal getValorAntecipadoLiquido() {
        return this.getValorAntecipadoSolucao().add(this.getValorAntecipadoRevenda());
    }

    /**
     *  Verifica se todos os abastecimentos do consolidados são negativos ou não
     * @return true, caso todos sejam negativos, ou false, caso contrário
     */
    @Transient
    public boolean todasTransacoesSaoNegativas(){
        if (getQuantidadeAbastecimentos() == 0){
            return getAutorizacoesPagamentoAssociadas().stream()
                    .filter(AutorizacaoPagamento::estaAutorizadaOuCancelada)
                    .noneMatch(autorizacaoPagamento -> autorizacaoPagamento.getValorTotal().compareTo(BigDecimal.ZERO) >= 0);
        }
        return false;
    }

    /**
     *  Verifica se todos os abastecimentos do consolidados são cancelados ou não
     * @return true, caso todos sejam cancelados, ou false, caso contrário
     */
    @Transient
    public boolean todasTransacoesSaoCanceladas(){
        boolean existeCancelado = getAutorizacoesPagamentoAssociadas().stream().anyMatch(AutorizacaoPagamento::estaCancelado);
        return getAutorizacoesPagamentoAssociadas().stream()
                .allMatch(autorizacaoPagamento -> (autorizacaoPagamento.estaCancelado() &&
                        MotivoEstorno.obterPorValor(autorizacaoPagamento.getMotivoEstorno()).getSemAlteracao()) ||
                        (autorizacaoPagamento.getValorTotal().compareTo(BigDecimal.ZERO) < 0 && existeCancelado));
    }

    /**
     *  Verifica se todos os abastecimentos do consolidados são estornados ou não
     * @return true, caso todos sejam estornados, ou false, caso contrário
     */
    @Transient
    public boolean todasTransacoesSaoEstornadas(){
        boolean existeCancelado = getAutorizacoesPagamentoAssociadas().stream().anyMatch(AutorizacaoPagamento::estaCancelado);
        return getAutorizacoesPagamentoAssociadas().stream()
                .allMatch(autorizacaoPagamento -> (autorizacaoPagamento.estaCancelado() &&
                        !MotivoEstorno.obterPorValor(autorizacaoPagamento.getMotivoEstorno()).getSemAlteracao()) ||
                        (autorizacaoPagamento.getValorTotal().compareTo(BigDecimal.ZERO) < 0 && existeCancelado));
    }

    /**
     *  Verifica se todos os abastecimentos do consolidados são cancelados ou estornados não
     * @return a lista de transações canceladas
     */
    @Transient
    public boolean todasTransacoesEstornadasOuCanceladas(){
        boolean existeCancelado = getAutorizacoesPagamentoAssociadas().stream().anyMatch( autorizacaoPagamento -> autorizacaoPagamento.estaCancelado()
                && MotivoEstorno.obterPorValor(autorizacaoPagamento.getMotivoEstorno()).getSemAlteracao());
        boolean existeEstornado = getAutorizacoesPagamentoAssociadas().stream().anyMatch( autorizacaoPagamento -> autorizacaoPagamento.estaCancelado()
                && !MotivoEstorno.obterPorValor(autorizacaoPagamento.getMotivoEstorno()).getSemAlteracao());

        return getAutorizacoesPagamentoAssociadas().stream()
                .allMatch(autorizacaoPagamento -> ((autorizacaoPagamento.estaCancelado() ||
                        (autorizacaoPagamento.getValorTotal().compareTo(BigDecimal.ZERO) < 0) && existeCancelado && existeEstornado)));
    }
    /**
     * Verifica se todos os abastecimentos do consolidado possuem pendência de nota fiscal
     * @return True caso todas estejam com pendência, false caso contrário
     */
    @Transient
    public boolean todasTransacoesPossuemPendenciaNF() {
        return getAutorizacoesPagamentoAssociadas().stream().allMatch(autorizacaoPagamento -> autorizacaoPagamento.isPendenteEmissaoNF(false));
    }

    /**
     * Retorna o percentual de emissão da transação consolidada.
     *
     * @return Porcentagem informando o quanto o ciclo já foi emitido.
     */
    @Transient
    public BigDecimal getPercentualEmissao() {
        if(StatusNotaFiscal.EMITIDA.getValue().equals(getStatusNotaFiscal())) {
            return BigDecimal.valueOf(100);
        } else if(verificarTodosNaoNulos(getValorTotalNotaFiscal(), getValorEmitidoNotaFiscal()) && getValorTotalNotaFiscal().compareTo(BigDecimal.ZERO) != 0) {
            return getValorEmitidoNotaFiscal().divide(getValorTotalNotaFiscal(), 2, BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100));
        }
        return null;
    }

    @Transient
    public ReembolsoAntecipado getAntecipacaoSolucaoRealizada() {
        if(antecipacoes != null) {
            return antecipacoes.stream().filter(antecipacao -> StatusIntegracaoReembolsoJde.REALIZADO.getValue()
                    .equals(antecipacao.getStatusIntegracao()) && antecipacao.getTipoAntecipacao().equals(TipoAntecipacao.SOLUCAO)).findFirst().orElse(null);
        }
        return null;
    }

    /**
     * Obtém as antecipações das revendas que foram realizadas.
     * @return antecipações
     */
    @Transient
    public List<ReembolsoAntecipado> getAntecipacoesParceriaRealizadas() {
        List<ReembolsoAntecipado> antecipacoesParceria = this.getAutorizacoesPagamentoVigentes().stream()
                .map(AutorizacaoPagamento::getAntecipacaoParceriaRealizada)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return !CollectionUtils.isEmpty(antecipacoesParceria) ? antecipacoesParceria : null;
    }

    @Transient
    public boolean possuiAntecipacaoRealizada() {
        return possuiAntecipacaoSolucaoRealizada() || possuiAntecipacaoParceriaRealizada();
    }

    @Transient
    public boolean possuiAntecipacaoSolucaoRealizada() {
        return getAntecipacaoSolucaoRealizada() != null;
    }

    @Transient
    public boolean possuiAntecipacaoParceriaRealizada() {
        return getAntecipacoesParceriaRealizadas() != null;
    }

    @Transient
    public boolean possuiAntecipacaoComErro() {
        if(antecipacoes != null) {
            return antecipacoes.stream().anyMatch(antecipacao -> StatusIntegracaoReembolsoJde.ERRO_ENVIO.getValue().equals(antecipacao.getStatusIntegracao()));
        }
        return false;
    }

    @Transient
    public ReembolsoAntecipado getUltimaAntecipacao() {
        if(antecipacoes != null) {
            return antecipacoes.stream().sorted(Comparator.comparing(ReembolsoAntecipado::getDataAntecipacao).reversed()).findFirst().orElse(null);
        }
        return null;
    }

    @Transient
    public StatusPagamentoReembolso getStatusReembolso() {
        if(reembolso != null && !reembolso.getStatus().equals(EM_ABERTO.getValue()) && !reembolso.getStatus().equals(AGUARDANDO_NF.getValue()) && !reembolso.getStatus().equals(NF_ATRASADA.getValue())) {
            return StatusPagamentoReembolso.obterPorValor(reembolso.getStatus());
        }
        return PREVISTO;
    }

    @Transient
    public Boolean getTransacaoCompletamenteAntecipada() {
        if(reembolso != null && antecipacoes!= null) {
            return reembolso.getValorDescontoAntecipacao() != null && reembolso.getValorDescontoAntecipacao().compareTo(BigDecimal.ZERO) > 0 && reembolso.getValorDescontoAntecipacao().equals(reembolso.getValorTotal());
        }
        return false;
    }

    /**
     * Obtém o CNPJ da empresa que realizou os abastecimentos do consolidado
     * @return O CNPJ da empresa
     */
    @Transient
    public Long getCnpjEmpresa() {
        return empresaAgregada != null ? empresaAgregada.getCnpj() : unidade != null ? unidade.getCnpj() : getFrota().getCnpj();
    }

    /**
     * Obtém a razão social da empresa que realizou os abastecimentos do consolidado
     * @return A razão social da empresa
     */
    @Transient
    public String getRazaoSocialEmpresa() {
        return empresaAgregada != null ? empresaAgregada.getRazaoSocial() : unidade != null ? unidade.getNome() : getFrota().getRazaoSocial();
    }
}