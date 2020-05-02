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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entidade que mantém as informações da tabela armazém
 * de anexos da recolha automática de notas fiscais
 */
@Entity
@Audited
@Table(name = "NFE_ANEXOS_ARMAZEM")
public class NfeAnexosArmazem implements IPersistente {

    private static final long serialVersionUID = 3917391421079838457L;

    @Id
    @Column(name = "CD_NFE_ANEXOS_ARMAZEM")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NFE_ANEXOS_ARMAZEM")
    @SequenceGenerator(name = "SEQ_NFE_ANEXOS_ARMAZEM", sequenceName = "SEQ_NFE_ANEXOS_ARMAZEM", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "ID_STATUS")
    private Integer status;

    @Column(name = "CD_CNPJ_FROTA")
    private Long cnpjFrota;

    @Column(name = "CD_CNPJ_PTOV")
    private Long cnpjPtov;

    @Column(name = "DT_EMISSAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEmissao;

    @Column(name = "NO_NUMERO")
    @Size(max = 50)
    private String numeroNota;

    @Column(name = "NO_SERIE")
    @Size(max = 50)
    private String serieNota;

    @Digits(integer = 12, fraction = 4)
    @Column(name = "VR_TOTAL_NOTA")
    private BigDecimal valorTotalNota;

    @Size(max = 200)
    @Column(name = "DS_MOTIVO")
    private String motivoFalhaImportacao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_NFE_ARMAZEM")
    private NfeArmazem email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TRANS_CONSOL")
    private TransacaoConsolidada ciclo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_NOTA_FISCAL")
    private NotaFiscal notaFiscal;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_ARQUIVO")
    private Arquivo arquivo;

    @Column(name = "NO_TENTATIVAS_ATUALIZACAO")
    private Integer numeroTentativasAtualizacao;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public NfeArmazem getEmail() {
        return email;
    }

    public void setEmail(NfeArmazem email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCnpjFrota() {
        return cnpjFrota;
    }

    public void setCnpjFrota(Long cnpjFrota) {
        this.cnpjFrota = cnpjFrota;
    }

    public Long getCnpjPtov() {
        return cnpjPtov;
    }

    public void setCnpjPtov(Long cnpjPtov) {
        this.cnpjPtov = cnpjPtov;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public BigDecimal getValorTotalNota() {
        return valorTotalNota;
    }

    public void setValorTotalNota(BigDecimal valorTotalNota) {
        this.valorTotalNota = valorTotalNota;
    }

    public TransacaoConsolidada getCiclo() {
        return ciclo;
    }

    public void setCiclo(TransacaoConsolidada ciclo) {
        this.ciclo = ciclo;
    }

    public NotaFiscal getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(NotaFiscal notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public String getMotivoFalhaImportacao() {
        return motivoFalhaImportacao;
    }

    public void setMotivoFalhaImportacao(String motivoFalhaImportacao) {
        this.motivoFalhaImportacao = motivoFalhaImportacao;
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

    public String getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(String numeroNota) {
        this.numeroNota = numeroNota;
    }

    public String getSerieNota() {
        return serieNota;
    }

    public void setSerieNota(String serieNota) {
        this.serieNota = serieNota;
    }

    public Integer getNumeroTentativasAtualizacao() {
        return numeroTentativasAtualizacao;
    }

    public void setNumeroTentativasAtualizacao(Integer numeroTentativasAtualizacao) {
        this.numeroTentativasAtualizacao = numeroTentativasAtualizacao;
    }
}
