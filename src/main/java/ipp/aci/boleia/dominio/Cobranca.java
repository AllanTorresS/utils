package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import org.hibernate.annotations.Formula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Basic;
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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Representa a tabela de Cobranca
 */
@Audited
@Entity
@Table(name = "COBRANCA")
public class Cobranca implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = -2656595902166817661L;

    /**
     * Utilizado para possibilitar a ordenação paginada pela data de vencimento vigente.
     */
    private static final String FORMULA_DATA_VENCIMENTO_VIGENTE = "CASE WHEN DT_VENC_PGTO_AJUSTADA IS NOT NULL THEN DT_VENC_PGTO_AJUSTADA ELSE DT_VENC_PGTO END";

    /**
     * Utilizados para facilitar a obtenção das informações de ajuste da cobrança.
     */
    private static final String FORMULA_USUARIO_ULTIMO_AJUSTE_VALOR = "(SELECT U.NM_USUARIO FROM BOLEIA_SCHEMA.AJUSTE_COBRANCA AC JOIN BOLEIA_SCHEMA.USUARIO U ON AC.CD_USUARIO = U.CD_USUARIO WHERE AC.CD_COBRANCA = CD_COBRANCA AND AC.VR_TOTAL_AJUSTE <> 0 AND AC.DT_AJUSTE = (SELECT MAX(ACI.DT_AJUSTE) FROM BOLEIA_SCHEMA.AJUSTE_COBRANCA ACI WHERE ACI.CD_COBRANCA = CD_COBRANCA AND ACI.VR_TOTAL_AJUSTE <> 0))";
    private static final String FORMULA_DATA_ULTIMO_AJUSTE_VALOR = "(SELECT AC.DT_AJUSTE FROM BOLEIA_SCHEMA.AJUSTE_COBRANCA AC WHERE AC.CD_COBRANCA = CD_COBRANCA AND AC.VR_TOTAL_AJUSTE <> 0 AND AC.DT_AJUSTE = (SELECT MAX(ACI.DT_AJUSTE) FROM BOLEIA_SCHEMA.AJUSTE_COBRANCA ACI WHERE ACI.CD_COBRANCA = CD_COBRANCA AND ACI.VR_TOTAL_AJUSTE <> 0))";
    private static final String FORMULA_USUARIO_ULTIMO_AJUSTE_VENCIMENTO = "(SELECT U.NM_USUARIO FROM BOLEIA_SCHEMA.AJUSTE_COBRANCA AC JOIN BOLEIA_SCHEMA.USUARIO U ON AC.CD_USUARIO = U.CD_USUARIO WHERE AC.CD_COBRANCA = CD_COBRANCA AND AC.DT_VENC_AJUSTE IS NOT NULL AND AC.DT_AJUSTE = (SELECT MAX(ACI.DT_AJUSTE) FROM BOLEIA_SCHEMA.AJUSTE_COBRANCA ACI WHERE ACI.CD_COBRANCA = CD_COBRANCA AND ACI.DT_VENC_AJUSTE IS NOT NULL))";
    private static final String FORMULA_DATA_ULTIMO_AJUSTE_VENCIMENTO = "(SELECT AC.DT_AJUSTE FROM BOLEIA_SCHEMA.AJUSTE_COBRANCA AC WHERE AC.CD_COBRANCA = CD_COBRANCA AND AC.DT_VENC_AJUSTE IS NOT NULL AND AC.DT_AJUSTE = (SELECT MAX(ACI.DT_AJUSTE) FROM BOLEIA_SCHEMA.AJUSTE_COBRANCA ACI WHERE ACI.CD_COBRANCA = CD_COBRANCA AND ACI.DT_VENC_AJUSTE IS NOT NULL))";

    @Id
    @Column(name = "CD_COBRANCA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COBRANCA")
    @SequenceGenerator(name = "SEQ_COBRANCA", sequenceName = "SEQ_COBRANCA", allocationSize = 1)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cobranca")
    private List<TransacaoConsolidada> transacoesConsolidadas;

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

    @Column(name = "DT_VENC_PGTO_AJUSTADA")
    private Date dataVencimentoPagtoAjustada;

    @NotAudited
    @Formula(FORMULA_DATA_VENCIMENTO_VIGENTE)
    private Date dataVencimentoVigente;

    @Column(name = "DT_PGTO")
    private Date dataPagamento;

    @Column(name = "VR_TOTAL")
    private BigDecimal valorTotal;

    @Column(name = "VR_TOTAL_AJUSTADO")
    private BigDecimal valorTotalAjustado;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cobranca")
    private List<AjusteCobranca> ajustes;

    @Column(name = "ID_STATUS")
    private Integer status;

    @Column(name = "ID_STATUS_INT_JDE")
    private Integer statusIntegracaoJDE;

    @Column(name = "DS_MSG_ERRO")
    private String mensagemErro;

    @Column(name = "VR_DESC_CRED")
    private BigDecimal valorDescontoCredito;

    @Column(name = "DT_INI_PER")
    private Date dataInicioPeriodo;

    @Column(name = "DT_FIM_PER")
    private Date dataFimPeriodo;

    @Column(name = "NO_TENTATIVAS_ENVIO")
    private Integer numeroTentativasEnvio;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Column(name = "VR_DESCONTO_ABASTECIMENTOS")
    private BigDecimal valorDescontoAbastecimentos;

    @NotAudited
    @Basic(fetch = FetchType.LAZY)
    @Formula(FORMULA_USUARIO_ULTIMO_AJUSTE_VALOR)
    private String usuarioUltimoAjusteValor;

    @NotAudited
    @Basic(fetch = FetchType.LAZY)
    @Formula(FORMULA_DATA_ULTIMO_AJUSTE_VALOR)
    private Date dataUltimoAjusteValor;

    @NotAudited
    @Basic(fetch = FetchType.LAZY)
    @Formula(FORMULA_USUARIO_ULTIMO_AJUSTE_VENCIMENTO)
    private String usuarioUltimoAjusteVencimento;

    @NotAudited
    @Basic(fetch = FetchType.LAZY)
    @Formula(FORMULA_DATA_ULTIMO_AJUSTE_VENCIMENTO)
    private Date dataUltimoAjusteVencimento;

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

    public Date getDataVencimentoPagtoAjustada() {
        return dataVencimentoPagtoAjustada;
    }

    public void setDataVencimentoPagtoAjustada(Date dataVencimentoPagtoAjustada) {
        this.dataVencimentoPagtoAjustada = dataVencimentoPagtoAjustada;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorTotalAjustado() {
        return valorTotalAjustado;
    }

    public void setValorTotalAjustado(BigDecimal valorTotalAjustado) {
        this.valorTotalAjustado = valorTotalAjustado;
    }

    public List<AjusteCobranca> getAjustes() {
        return ajustes;
    }

    public void setAjustes(List<AjusteCobranca> ajustes) {
        this.ajustes = ajustes;
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

    public Integer getStatusIntegracaoJDE() {
        return statusIntegracaoJDE;
    }

    public void setStatusIntegracaoJDE(Integer statusIntegracaoJDE) {
        this.statusIntegracaoJDE = statusIntegracaoJDE;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public List<TransacaoConsolidada> getTransacoesConsolidadas() {
        return transacoesConsolidadas;
    }

    public void setTransacoesConsolidadas(List<TransacaoConsolidada> transacoesConsolidadas) {
        this.transacoesConsolidadas = transacoesConsolidadas;
    }

    public BigDecimal getValorDescontoCredito() {
        return valorDescontoCredito;
    }

    public void setValorDescontoCredito(BigDecimal valorDescontoCredito) {
        this.valorDescontoCredito = valorDescontoCredito;
    }

    @Override
    public List<Frota> getFrotas() {
        return transacoesConsolidadas != null ? transacoesConsolidadas.get(0).getFrotas() : Collections.emptyList();
    }

    @Transient
    public Frota getFrota() {
        return !getFrotas().isEmpty() ? getFrotas().get(0) : null;
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

    public BigDecimal getValorDescontoAbastecimentos() {
        return valorDescontoAbastecimentos != null?valorDescontoAbastecimentos:BigDecimal.ZERO;
    }

    public void setValorDescontoAbastecimentos(BigDecimal valorDescontoAbastecimentos) {
        this.valorDescontoAbastecimentos = valorDescontoAbastecimentos;
    }

    public Integer getNumeroTentativasEnvio() {
        return numeroTentativasEnvio;
    }

    public void setNumeroTentativasEnvio(Integer numeroTentativasEnvio) {
        this.numeroTentativasEnvio = numeroTentativasEnvio;
    }

    public String getUsuarioUltimoAjusteValor() {
        return usuarioUltimoAjusteValor;
    }

    public void setUsuarioUltimoAjusteValor(String usuarioUltimoAjusteValor) {
        this.usuarioUltimoAjusteValor = usuarioUltimoAjusteValor;
    }

    public Date getDataUltimoAjusteValor() {
        return dataUltimoAjusteValor;
    }

    public void setDataUltimoAjusteValor(Date dataUltimoAjusteValor) {
        this.dataUltimoAjusteValor = dataUltimoAjusteValor;
    }

    public String getUsuarioUltimoAjusteVencimento() {
        return usuarioUltimoAjusteVencimento;
    }

    public void setUsuarioUltimoAjusteVencimento(String usuarioUltimoAjusteVencimento) {
        this.usuarioUltimoAjusteVencimento = usuarioUltimoAjusteVencimento;
    }

    public Date getDataUltimoAjusteVencimento() {
        return dataUltimoAjusteVencimento;
    }

    public void setDataUltimoAjusteVencimento(Date dataUltimoAjusteVencimento) {
        this.dataUltimoAjusteVencimento = dataUltimoAjusteVencimento;
    }

    /**
     * Retorna o último ajuste efetuado para a cobrança.
     * @return o último ajuste encontrado.
     */
    @Transient
    public AjusteCobranca getUltimoAjuste() {
        if(ajustes != null && !ajustes.isEmpty()) {
            return ajustes.stream().max(Comparator.comparing(AjusteCobranca::getId)).get();
        }
        return null;
    }

    /**
     * Retorna o último ajuste de valor efetuado para a cobrança.
     *
     * @return o último ajuste de valor encontrado.
     */
    @Transient
    public AjusteCobranca getUltimoAjusteValor() {
        if (ajustes != null && !ajustes.isEmpty()) {
            return ajustes.stream()
                            .filter(AjusteCobranca::isAjusteValor)
                            .max(Comparator.comparing(AjusteCobranca::getId))
                            .orElse(null);
        }
        return null;
    }

    /**
     * Retorna o último ajuste efetuado com alteração da data de vencimento.
     * @return ajuste de data encontrado.
     */
    @Transient
    public AjusteCobranca getUltimoAjusteData() {
        if(ajustes != null && !ajustes.isEmpty()) {
            return ajustes.stream()
                            .filter(AjusteCobranca::isProrrogacaoVencimento)
                            .max(Comparator.comparing(AjusteCobranca::getId))
                            .orElse(null);
        }
        return null;
    }

    /**
     * Verifica se houve um ajuste para a cobrança.
     * @return o valor total ajustado caso tenha ocorrido ajuste desta cobrança e valor total da cobrança, caso contrário.
     */
    @Transient
    public BigDecimal getValorVigente() {
        BigDecimal descontosFrotaLeves = valorDescontoAbastecimentos != null ? valorDescontoAbastecimentos : BigDecimal.ZERO;
        return valorTotalAjustado != null ? valorTotalAjustado : valorTotal.subtract(descontosFrotaLeves);
    }

    public Date getDataVencimentoVigente() {
        return dataVencimentoVigente;
    }

    public void setDataVencimentoVigente(Date dataVencimentoVigente) {
        this.dataVencimentoVigente = dataVencimentoVigente;
    }

    /**
     * Verifica se a cobrança possui um ajuste de desconto.
     * @return true caso exista um ajuste de desconto, false caso contrário.
     */
    @Transient
    public Boolean possuiDesconto(){
        return ajustes != null && ajustes.stream().anyMatch(AjusteCobranca::isDesconto);
    }

    /**
     * Verifica se a cobrança possui um ajuste de acréscimo.
     * @return true caso exista um ajuste de acréscimo, false caso contrário.
     */
    @Transient
    public Boolean possuiAcrescimo(){
        return ajustes != null && ajustes.stream().anyMatch(AjusteCobranca::isAcrescimo);
    }

    /**
     * Incrementa o número de tentativas de envio para o JDE
     */
    public void incrementarNumeroTentativasEnvio() {
        if (this.getNumeroTentativasEnvio() != null) {
            this.setNumeroTentativasEnvio(this.getNumeroTentativasEnvio() + 1);
        } else {
            this.setNumeroTentativasEnvio(1);
        }
    }

    /**
     * Retorna a lista de ajustes de acréscimo ou desconto realizados na cobrança.
     *
     * @return lista de ajustes
     */
    @Transient
    public List<AjusteCobranca> getAjustesComValor() {
        if(ajustes != null && !ajustes.isEmpty()) {
            return ajustes.stream().filter(AjusteCobranca::isAjusteValor).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
