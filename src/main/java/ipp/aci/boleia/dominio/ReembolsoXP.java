package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa a tabela de Reembolso XP
 */
@Audited
@Entity
@Table(name = "REEMBOLSO_XP")
public class ReembolsoXP implements IPersistente {

    @Id
    @Column(name = "CD_REEMBOLSO_XP")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REEMBOLSO_XP")
    @SequenceGenerator(name = "SEQ_REEMBOLSO_XP", sequenceName = "SEQ_REEMBOLSO_XP", allocationSize = 1)
    private Long id;

    @Column(name = "NO_DOC_JDE")
    private Long numeroDocumento;

    @Column(name = "ID_TIPO_DOC")
    private String tipoDocumento;

    @Column(name = "NM_CIA_DOC")
    private String ciaDocumento;

    @Column(name = "QT_PARCELAS")
    private Integer quantidadeParcelas;

    @Column(name = "VR_TOTAL")
    private BigDecimal valorTotal;

    @Column(name = "VR_TAXA")
    private BigDecimal valorTaxa;

    @Column(name = "VR_REEMB")
    private BigDecimal valorReembolso;

    @Column(name = "DT_VENC_PGTO")
    private Date dataVencimentoPagto;

    @Column(name = "ID_STATUS_INT_JDE")
    private Integer statusIntegracao;

    @Column(name = "DS_MSG_ERRO")
    private String mensagemErro;

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

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorTaxa() {
        return valorTaxa;
    }

    public void setValorTaxa(BigDecimal valorTaxa) {
        this.valorTaxa = valorTaxa;
    }

    public BigDecimal getValorReembolso() {
        return valorReembolso;
    }

    public void setValorReembolso(BigDecimal valorReembolso) {
        this.valorReembolso = valorReembolso;
    }

    public Date getDataVencimentoPagto() {
        return dataVencimentoPagto;
    }

    public void setDataVencimentoPagto(Date dataVencimentoPagto) {
        this.dataVencimentoPagto = dataVencimentoPagto;
    }

    public Integer getStatusIntegracao() {
        return statusIntegracao;
    }

    public void setStatusIntegracao(Integer statusIntegracao) {
        this.statusIntegracao = statusIntegracao;
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

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}