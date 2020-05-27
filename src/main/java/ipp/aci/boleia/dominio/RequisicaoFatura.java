package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Entidade que contém informações de requisição de inclusão de fatura no JDE.
 */
@Entity
@Table(name = "REQUISICAO_FATURA")
public class RequisicaoFatura implements IPersistente {

    private static final long serialVersionUID = 7623713395345985491L;

    @Id
    @Column(name = "CD_REQUISICAO_FATURA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REQUISICAO_FATURA")
    @SequenceGenerator(name = "SEQ_REQUISICAO_FATURA", sequenceName = "SEQ_REQUISICAO_FATURA", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_COBRANCA")
    private Cobranca cobranca;

    @NotNull
    @Column(name = "DT_REQUISICAO")
    private Date dataRequisicao;

    @Lob
    @Column(name = "DS_REQUISICAO")
    private String conteudoRequisicao;

    @Version
    @Column(name = "NO_VERSAO")
    private Long version;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Cobranca getCobranca() {
        return cobranca;
    }

    public void setCobranca(Cobranca cobranca) {
        this.cobranca = cobranca;
    }

    public Date getDataRequisicao() {
        return dataRequisicao;
    }

    public void setDataRequisicao(Date dataRequisicao) {
        this.dataRequisicao = dataRequisicao;
    }

    public String getConteudoRequisicao() {
        return conteudoRequisicao;
    }

    public void setConteudoRequisicao(String conteudoRequisicao) {
        this.conteudoRequisicao = conteudoRequisicao;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
