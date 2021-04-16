package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;
import ipp.aci.boleia.util.UtilitarioCalculoData;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Nota Fiscal
 */
@Entity
@Audited
@Table(name = "NOTA_FISCAL")
public class NotaFiscal implements IPersistente, IPertenceRevendedor, IPertenceFrota {

    private static final long serialVersionUID = 1307483790977589046L;

    @Id
    @Column(name = "CD_NFE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NOTA_FISCAL")
    @SequenceGenerator(name = "SEQ_NOTA_FISCAL", sequenceName = "SEQ_NOTA_FISCAL", allocationSize = 1)
    private Long id;

    @Size(max=50)
    @Column(name = "NO_NUMERO")
    private String numero;

    @Size(max=10)
    @Column(name = "NO_NUMERO_SERIE")
    private String numeroSerie;

    @Size(max=14)
    @Column(name = "NO_CNPJ_EMIT")
    private String cnpjEmitente;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_ARQUIVO")
    private Arquivo arquivo;

    @NotNull
    @Column(name = "DT_EMISSAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEmissao;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @ManyToMany(fetch = FetchType.LAZY)
    @AuditJoinTable(name = "AUTORIZACAO_NOTA_AUD")
    @JoinTable(name="AUTORIZACAO_NOTA", joinColumns={@JoinColumn(name="CD_NFE")}, inverseJoinColumns={@JoinColumn(name="CD_AUTORIZACAO_PAGAMENTO")})
    private List<AutorizacaoPagamento> autorizacoesPagamento;

    @NotNull
    @Column(name = "ID_DOWNLOAD_FROTA")
    private Boolean downloadFrota;

    @NotNull
    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_TOTAL")
    private BigDecimal valorTotal;

    @Column(name = "DS_JUSTIF")
    private String textoJustificativa;

    @Column(name = "ID_JUSTIF")
    private Boolean isJustificativa;

    @Column(name = "NM_ARQUIVO")
    private String nomeArquivo;

    @NotNull
    @Column(name = "ID_STATUS")
    private Integer statusConciliacao;

    @Column(name = "DS_MOTIVO_CONCILIACAO")
    @Size(max = 200)
    private String motivoFalhaConciliacao;

    @Column(name = "NO_CHAVE_ACESSO")
    @Size(min = 44, max = 44)
    private String chaveAcesso;

    @Column(name = "VR_COMB")
    @Digits(integer = 12, fraction = 4)
    private BigDecimal valorCombustivel;

    @Column(name = "VR_PROD_SERV")
    @Digits(integer = 12, fraction = 4)
    private BigDecimal valorProdutosServicos;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getCnpjEmitente() {
        return cnpjEmitente;
    }

    public void setCnpjEmitente(String cnpjEmitente) {
        this.cnpjEmitente = cnpjEmitente;
    }

    public Arquivo getArquivo() {
        return arquivo;
    }

    public void setArquivo(Arquivo arquivo) {
        this.arquivo = arquivo;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Boolean getDownloadFrota() {
        return downloadFrota;
    }

    public void setDownloadFrota(Boolean downloadFrota) {
        this.downloadFrota = downloadFrota;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<AutorizacaoPagamento> getAutorizacoesPagamento() {
        return autorizacoesPagamento;
    }

    public void setAutorizacoesPagamento(List<AutorizacaoPagamento> autorizacoesPagamento) {
        this.autorizacoesPagamento = autorizacoesPagamento;
    }

    @Override
    public List<Frota> getFrotas() {
        return getAutorizacoesPagamento() != null && !getAutorizacoesPagamento().isEmpty() ? getAutorizacoesPagamento().get(0).getFrotas() : null;
    }

    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return getAutorizacoesPagamento() != null && !getAutorizacoesPagamento().isEmpty() ? getAutorizacoesPagamento().get(0).getPontosDeVenda() : null;
    }

    public String getTextoJustificativa() {
        return textoJustificativa;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public void setTextoJustificativa(String justificativa) {
        this.textoJustificativa = justificativa;
    }

    public Boolean getIsJustificativa() {
        return isJustificativa;
    }

    public void setIsJustificativa(Boolean isJustificativa) {
        this.isJustificativa = isJustificativa;
    }

    public Integer getStatusConciliacao() {
        return statusConciliacao;
    }

    public void setStatusConciliacao(Integer statusConciliacao) {
        this.statusConciliacao = statusConciliacao;
    }

    public String getMotivoFalhaConciliacao() {
        return motivoFalhaConciliacao;
    }

    public void setMotivoFalhaConciliacao(String motivoFalhaConciliacao) {
        this.motivoFalhaConciliacao = motivoFalhaConciliacao;
    }

    public String getChaveAcesso() {
        return chaveAcesso;
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso = chaveAcesso;
    }

    public BigDecimal getValorCombustivel() {
        return valorCombustivel;
    }

    public void setValorCombustivel(BigDecimal valorCombustivel) {
        this.valorCombustivel = valorCombustivel;
    }

    public BigDecimal getValorProdutosServicos() {
        return valorProdutosServicos;
    }

    public void setValorProdutosServicos(BigDecimal valorProdutosServicos) {
        this.valorProdutosServicos = valorProdutosServicos;
    }

    /**
     * Verifica se a nota foi emitida no mes atual
     *
     * @param dataAtual data atual
     * @return true caso emitida no mes corrente
     */
    @Transient
    public boolean emitidaMesAtual(Date dataAtual)  {
        return UtilitarioCalculoData.isMesEAnoCorrente(dataAtual, dataEmissao);
    }

    /**
     * Obtém o nome que deverá ser usado para identificar o arquivo gerado a partir desta nota fiscal (download).
     * Esse nome de arquivo é diferente do campo <b>nomeArquivo</b>, que identifica o nome original da nota fiscal,
     * no momento do envio (upload).
     *
     * @return nome do arquivo
     */
    @Transient
    public String getNomeArquivoSemExtensao() {
        String nomeArquivoSemExtensao = getNumero();
        if(getNumeroSerie() != null) {
            nomeArquivoSemExtensao = nomeArquivoSemExtensao.concat("_").concat(getNumeroSerie());
        }
        return nomeArquivoSemExtensao;
    }
}
