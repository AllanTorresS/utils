package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

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
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Representa a tabela de Parametro Ciclo
 */
@Entity
@Audited
@Table(name = "PARAM_CICLO")
public class ParametroCiclo implements IPersistente {

    private static final long serialVersionUID = -2663290227939724281L;

    @Id
    @Column(name = "CD_PARAM_CICLO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARAM_CICLO")
    @SequenceGenerator(name = "SEQ_PARAM_CICLO", sequenceName = "SEQ_PARAM_CICLO", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "QT_CICLO")
    private Long prazoCiclo;

    @NotNull
    @Column(name = "QT_PRAZO_PGTO")
    private Long prazoPagamento;

    @NotNull
    @Column(name = "QT_PRAZO_REEMB")
    private Long prazoReembolso;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parametroCiclo")
    private List<Frota> frotas;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrazoCiclo() {
        return prazoCiclo;
    }

    public void setPrazoCiclo(Long prazoCiclo) {
        this.prazoCiclo = prazoCiclo;
    }

    public Long getPrazoPagamento() {
        return prazoPagamento;
    }

    public void setPrazoPagamento(Long prazoPagamento) {
        this.prazoPagamento = prazoPagamento;
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

    public List<Frota> getFrotas() {
        return frotas;
    }

    public void setFrotas(List<Frota> frotas) {
        this.frotas = frotas;
    }

    /**
     * Monta o nome de apresentacao de um ParametroCiclo,
     *
     * @return O nome de apresentacao do ParametroCiclo
     */
    @Transient
    public String getNomeApresentacao() {
        return getPrazoCiclo() + "+" + getPrazoPagamento();
    }
}