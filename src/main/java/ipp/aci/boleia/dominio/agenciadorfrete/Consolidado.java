package ipp.aci.boleia.dominio.agenciadorfrete;

import ipp.aci.boleia.dominio.PontoDeVenda;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Transação financeira de um Abastecimento
 */
@Entity
@Audited
@Table(name = "AG_FRETE_CONSOLIDADO")
public class Consolidado implements IPersistente {

    @Id
    @Column(name = "CD_CONSOLIDADO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AG_FRETE_CONSOL")
    @SequenceGenerator(name = "SEQ_AG_FRETE_CONSOL", sequenceName = "SEQ_AG_FRETE_CONSOL", allocationSize = 1)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "consolidado")
    private List<Transacao> transacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_AGENCIADOR_FRETE")
    private AgenciadorFrete agenciadorFrete;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda posto;

    @NotNull
    @Column(name = "DT_INI_PER")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInicioPeriodo;

    @NotNull
    @Column(name = "DT_FIM_PER")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataFimPeriodo;

    @NotNull
    @Column(name = "DT_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_COBRANCA")
    private AgenciadorFreteCobranca cobranca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_REEMBOLSO")
    private Reembolso reembolso;

    @Column(name = "NO_VERSAO")
    @Version
    private Long versao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
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

    public PontoDeVenda getPosto() {
        return posto;
    }

    public void setPosto(PontoDeVenda posto) {
        this.posto = posto;
    }

    public AgenciadorFrete getAgenciadorFrete() {
        return agenciadorFrete;
    }

    public void setAgenciadorFrete(AgenciadorFrete agenciadorFrete) {
        this.agenciadorFrete = agenciadorFrete;
    }

    public AgenciadorFreteCobranca getCobranca() {
        return cobranca;
    }

    public void setCobranca(AgenciadorFreteCobranca cobranca) {
        this.cobranca = cobranca;
    }

    public Reembolso getReembolso() {
        return reembolso;
    }

    public void setReembolso(Reembolso reembolso) {
        this.reembolso = reembolso;
    }
}
