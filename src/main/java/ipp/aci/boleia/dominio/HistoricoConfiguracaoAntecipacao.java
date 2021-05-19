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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa a tabela de Histórico de Configuração de Antecipação
 */
@Entity
@Audited
@Table(name = "HIST_CONFIG_ANTECIPACAO")
public class HistoricoConfiguracaoAntecipacao implements IPersistente {

    private static final long serialVersionUID = -2151497914048988420L;

    @Id
    @Column(name = "CD_HIST_CONFIG_ANTECIPACAO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIST_CONFIG_ANTECIPACAO")
    @SequenceGenerator(name = "SEQ_HIST_CONFIG_ANTECIPACAO", sequenceName = "SEQ_HIST_CONFIG_ANTECIPACAO", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @NotNull
    @Column(name = "ID_TAXA_PERCENTUAL")
    private Boolean taxaPercentual;

    @Column(name = "VA_TAXA_PROFROTAS_PERC")
    private BigDecimal taxaProfrotasPercentual;

    @Column(name = "VA_TAXA_PARCEIRO_PERC")
    private BigDecimal taxaParceiroPercentual;

    @Column(name = "VA_TAXA_PROFROTAS_VAL")
    private BigDecimal taxaProfrotasFixa;

    @Column(name = "VA_TAXA_PARCEIRO_VAL")
    private BigDecimal taxaParceiroFixa;

    @Column(name = "VA_ANTECIPACAO_MIN")
    private BigDecimal valorAntecipacaoMinimo;

    @Column(name = "VA_ANTECIPACAO_MAX")
    private BigDecimal valorAntecipacaoMaximo;

    @NotNull
    @Column(name = "QT_DIAS_VENCIMENTO")
    private Integer prazoVencimento;

    @NotNull
    @Column(name = "VA_HORARIO_LIMITE")
    private Integer horarioLimite;

    @Column(name = "VA_NOVO_HORARIO_LIMITE")
    private Integer novoHorarioLimite;

    @Column(name = "DT_ATUALIZACAO_HOR_LIM")
    private Date dataAtualizacaoHorarioLimite;

    @Column(name = "DT_ATUALIZACAO")
    private Date dataAtualizacao;

    public HistoricoConfiguracaoAntecipacao() {
        // Construtor padrão
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Boolean getTaxaPercentual() {
        return taxaPercentual;
    }

    public void setTaxaPercentual(Boolean taxaPercentual) {
        this.taxaPercentual = taxaPercentual;
    }

    public BigDecimal getTaxaProfrotasPercentual() {
        return taxaProfrotasPercentual;
    }

    public void setTaxaProfrotasPercentual(BigDecimal taxaProfrotasPercentual) {
        this.taxaProfrotasPercentual = taxaProfrotasPercentual;
    }

    public BigDecimal getTaxaParceiroPercentual() {
        return taxaParceiroPercentual;
    }

    public void setTaxaParceiroPercentual(BigDecimal taxaParceiroPercentual) {
        this.taxaParceiroPercentual = taxaParceiroPercentual;
    }

    public BigDecimal getTaxaProfrotasFixa() {
        return taxaProfrotasFixa;
    }

    public void setTaxaProfrotasFixa(BigDecimal taxaProfrotasFixa) {
        this.taxaProfrotasFixa = taxaProfrotasFixa;
    }

    public BigDecimal getTaxaParceiroFixa() {
        return taxaParceiroFixa;
    }

    public void setTaxaParceiroFixa(BigDecimal taxaParceiroFixa) {
        this.taxaParceiroFixa = taxaParceiroFixa;
    }

    public BigDecimal getValorAntecipacaoMinimo() {
        return valorAntecipacaoMinimo;
    }

    public void setValorAntecipacaoMinimo(BigDecimal valorAntecipacaoMinimo) {
        this.valorAntecipacaoMinimo = valorAntecipacaoMinimo;
    }

    public BigDecimal getValorAntecipacaoMaximo() {
        return valorAntecipacaoMaximo;
    }

    public void setValorAntecipacaoMaximo(BigDecimal valorAntecipacaoMaximo) {
        this.valorAntecipacaoMaximo = valorAntecipacaoMaximo;
    }

    public Integer getPrazoVencimento() {
        return prazoVencimento;
    }

    public void setPrazoVencimento(Integer prazoVencimento) {
        this.prazoVencimento = prazoVencimento;
    }

    public Integer getHorarioLimite() {
        return horarioLimite;
    }

    public void setHorarioLimite(Integer horarioLimite) {
        this.horarioLimite = horarioLimite;
    }

    public Integer getNovoHorarioLimite() {
        return novoHorarioLimite;
    }

    public void setNovoHorarioLimite(Integer novoHorarioLimite) {
        this.novoHorarioLimite = novoHorarioLimite;
    }

    public Date getDataAtualizacaoHorarioLimite() {
        return dataAtualizacaoHorarioLimite;
    }

    public void setDataAtualizacaoHorarioLimite(Date dataAtualizacaoHorarioLimite) {
        this.dataAtualizacaoHorarioLimite = dataAtualizacaoHorarioLimite;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
