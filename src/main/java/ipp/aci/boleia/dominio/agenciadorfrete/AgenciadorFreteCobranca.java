package ipp.aci.boleia.dominio.agenciadorfrete;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Audited
@Table(name = "AG_FRETE_COBRANCA")
public class AgenciadorFreteCobranca implements IPersistente {

    @Id
    @Column(name = "CD_COBRANCA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AG_COBRANCA")
    @SequenceGenerator(name = "SEQ_AG_COBRANCA", sequenceName = "SEQ_AG_COBRANCA", allocationSize = 1)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cobranca")
    private List<Consolidado> consolidados;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_DOCUMENTO")
    private DocumentoJde documentoJde;

    @Column(name = "QT_PARCELAS")
    private Integer quantidadeParcelas;

    @Column(name = "DT_CRIACAO")
    private Date dataCriacao;

    @Column(name = "DT_VENC_PGTO")
    private Date dataVencimento;

    @Column(name = "DT_PGTO")
    private Date dataPagamento;

    @Column(name = "VR_TOTAL")
    private BigDecimal valorTotal;

    @Column(name = "VR_DESCONTO")
    private BigDecimal valorDesconto;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public List<Consolidado> getConsolidados() {
        return consolidados;
    }

    public void setConsolidados(List<Consolidado> consolidados) {
        this.consolidados = consolidados;
    }

    public DocumentoJde getDocumentoJde() {
        return documentoJde;
    }

    public void setDocumentoJde(DocumentoJde documentoJde) {
        this.documentoJde = documentoJde;
    }

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
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

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
