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
 * Representa a tabela de Cobrança XP
 */
@Audited
@Entity
@Table(name = "COBRANCA_XP")
public class CobrancaXP implements IPersistente {

    @Id
    @Column(name = "CD_COBRANCA_XP")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COBRANCA_XP")
    @SequenceGenerator(name = "SEQ_COBRANCA_XP", sequenceName = "SEQ_COBRANCA_XP", allocationSize = 1)
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

    @Column(name = "DT_VENC_PGTO")
    private Date dataVencimentoPagto;

    @Column(name = "DT_FATURA")
    private Date dataFatura;

    @Column(name = "ID_STATUS_INT_JDE")
    private Integer statusIntegracaoJDE;

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

    public Date getDataVencimentoPagto() {
        return dataVencimentoPagto;
    }

    public void setDataVencimentoPagto(Date dataVencimentoPagto) {
        this.dataVencimentoPagto = dataVencimentoPagto;
    }

    public Date getDataFatura() {
        return dataFatura;
    }

    public void setDataFatura(Date dataFatura) {
        this.dataFatura = dataFatura;
    }

    public Integer getStatusIntegracaoJDE() {
        return statusIntegracaoJDE;
    }

    public void setStatusIntegracaoJDE(Integer statusIntegracaoJDE) {
        this.statusIntegracaoJDE = statusIntegracaoJDE;
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