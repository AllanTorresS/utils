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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Representa a tabela de Transacao Consolidada Prazos.
 */
@Entity
@Audited
@Table(name = "TRANS_CONSOL_PRAZOS")
public class TransacaoConsolidadaPrazos implements IPersistente {
    
    private static final long serialVersionUID = 5966796280765949090L;
    
    @Id
    @Column(name = "CD_TRANS_CONSOL_PRAZOS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANS_CONSOL_PRAZOS")
    @SequenceGenerator(name = "SEQ_TRANS_CONSOL_PRAZOS", sequenceName = "SEQ_TRANS_CONSOL_PRAZOS", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "QT_PRAZO_PGTO")
    private Long prazoPgto;

    @Column(name = "DT_PRAZO_PGTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataLimitePagamento;

    @NotNull
    @Column(name = "DT_PRAZO_EMIS_NFE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataLimiteEmissaoNfe;
    
    @Column(name = "DT_GER_COBRANCA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataGeracaoCobranca;

    @NotNull
    @Column(name = "QT_PRAZO_REEMB")
    private Long prazoReembolso;

    @NotNull
    @Column(name = "ID_POSSUI_PRAZO_AJUSTE")
    private Boolean possuiPrazoAjuste;

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

    public Long getPrazoPgto() {
        return prazoPgto;
    }

    public void setPrazoPgto(Long prazoPgto) {
        this.prazoPgto = prazoPgto;
    }

    public Date getDataLimitePagamento() {
        return dataLimitePagamento;
    }

    public void setDataLimitePagamento(Date dataLimitePagamento) {
        this.dataLimitePagamento = dataLimitePagamento;
    }

    public Date getDataLimiteEmissaoNfe() {
        return dataLimiteEmissaoNfe;
    }

    public void setDataLimiteEmissaoNfe(Date dataLimiteEmissaoNfe) {
        this.dataLimiteEmissaoNfe = dataLimiteEmissaoNfe;
    }

    public Date getDataGeracaoCobranca() {
        return dataGeracaoCobranca;
    }

    public void setDataGeracaoCobranca(Date dataGeracaoCobranca) {
        this.dataGeracaoCobranca = dataGeracaoCobranca;
    }

    public Long getPrazoReembolso() {
        return prazoReembolso;
    }

    public void setPrazoReembolso(Long prazoReembolso) {
        this.prazoReembolso = prazoReembolso;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public Boolean getPossuiPrazoAjuste() {
        return possuiPrazoAjuste;
    }

    public void setPossuiPrazoAjuste(Boolean possuiPrazoAjuste) {
        this.possuiPrazoAjuste = possuiPrazoAjuste;
    }
}