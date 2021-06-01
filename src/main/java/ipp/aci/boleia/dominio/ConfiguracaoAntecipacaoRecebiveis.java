package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.util.UtilitarioCalculoData;
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
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

import static ipp.aci.boleia.util.UtilitarioCalculoData.adicionarSegundosData;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterPrimeiroInstanteDia;

/**
 * Representa a tabela de Configuração de Antecipação
 */
@Entity
@Audited
@Table(name = "CONFIGURACAO_ANTECIPACAO")
public class ConfiguracaoAntecipacaoRecebiveis implements IPersistente {

    private static final long serialVersionUID = 4434610589650086327L;

    @Id
    @Column(name = "CD_CONFIGURACAO_ANTECIPACAO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONFIGURACAO_ANTECIPACAO")
    @SequenceGenerator(name = "SEQ_CONFIGURACAO_ANTECIPACAO", sequenceName = "SEQ_CONFIGURACAO_ANTECIPACAO", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "ID_TAXA_PERCENTUAL")
    private Boolean taxaPercentual;

    @Column(name = "VA_TAXA_PROFROTAS_PERC")
    private BigDecimal taxaProFrotasPercentual;

    @Column(name = "VA_TAXA_PARCEIRO_PERC")
    private BigDecimal taxaParceiroPercentual;

    @Column(name = "VA_TAXA_PROFROTAS_VAL")
    private BigDecimal taxaProFrotasFixa;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    public ConfiguracaoAntecipacaoRecebiveis() {
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

    public Boolean getTaxaPercentual() {
        return taxaPercentual;
    }

    public void setTaxaPercentual(Boolean taxaPercentual) {
        this.taxaPercentual = taxaPercentual;
    }

    public BigDecimal getTaxaProFrotasPercentual() {
        return taxaProFrotasPercentual;
    }

    public void setTaxaProFrotasPercentual(BigDecimal taxaProFrotasPercentual) {
        this.taxaProFrotasPercentual = taxaProFrotasPercentual;
    }

    public BigDecimal getTaxaParceiroPercentual() {
        return taxaParceiroPercentual;
    }

    public void setTaxaParceiroPercentual(BigDecimal taxaParceiroPercentual) {
        this.taxaParceiroPercentual = taxaParceiroPercentual;
    }

    public BigDecimal getTaxaProFrotasFixa() {
        return taxaProFrotasFixa;
    }

    public void setTaxaProFrotasFixa(BigDecimal taxaProFrotasFixa) {
        this.taxaProFrotasFixa = taxaProFrotasFixa;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Transient
    public Integer getHorarioLimiteVigente(Date dataReferencia) {
        return novoHorarioLimite != null && UtilitarioCalculoData.isPosterior(dataAtualizacaoHorarioLimite, dataReferencia) ? novoHorarioLimite : horarioLimite;
    }

    /**
     * Obtém o prazo de vencimento correto baseado no horário limite para antecipação
     * @param dataReferencia a data que deve ser usada como data ambiente
     * @return o prazo de vencimento, em dias
     */
    @Transient
    public Integer getPrazoVencimentoVigente(Date dataReferencia) {
        Date dataLimite = adicionarSegundosData(obterPrimeiroInstanteDia(dataReferencia), getHorarioLimiteVigente(dataReferencia));
        return dataReferencia.after(dataLimite) ? prazoVencimento + 1 : prazoVencimento;
    }

    /**
     * Atualiza a taxa percentual do Pró-Frotas e do Parceiro
     * @param taxaProFrotasPercentual a taxa do Pró-Frotas
     * @param taxaParceiroPercentual a taxa do Parceiro
     */
    public void atualizarTaxaPercentual(BigDecimal taxaProFrotasPercentual, BigDecimal taxaParceiroPercentual) {
        this.setTaxaPercentual(true);
        this.setTaxaProFrotasPercentual(taxaProFrotasPercentual);
        this.setTaxaParceiroPercentual(taxaParceiroPercentual);
        this.setTaxaProFrotasFixa(null);
        this.setTaxaParceiroFixa(null);
    }

    /**
     * Atualiza a taxa fixa do Pró-Frotas e do Parceiro
     * @param taxaProFrotasFixa a taxa do Pró-Frotas
     * @param taxaParceiroFixa a taxa do Parceiro
     */
    public void atualizarTaxaFixa(BigDecimal taxaProFrotasFixa, BigDecimal taxaParceiroFixa) {
        this.setTaxaPercentual(false);
        this.setTaxaProFrotasFixa(taxaProFrotasFixa);
        this.setTaxaParceiroFixa(taxaParceiroFixa);
        this.setTaxaProFrotasPercentual(null);
        this.setTaxaParceiroPercentual(null);
    }

    /**
     * Atualiza o horário limite para antecipações
     * @param novoHorarioLimite o novo horário limite
     * @param dataAmbiente a data atual
     */
    public void atualizarHorarioLimite(Integer novoHorarioLimite, Date dataAmbiente) {
        Integer horarioLimiteVigente = getHorarioLimiteVigente(dataAmbiente);
        Date dataLimite = adicionarSegundosData(obterPrimeiroInstanteDia(dataAmbiente), horarioLimiteVigente);
        if (!horarioLimiteVigente.equals(novoHorarioLimite)) {
            this.setDataAtualizacaoHorarioLimite(dataAmbiente);
            if (dataAmbiente.after(dataLimite)) {
                this.setNovoHorarioLimite(novoHorarioLimite);
            } else {
                this.setHorarioLimite(novoHorarioLimite);
                this.setNovoHorarioLimite(null);
            }
        }
    }
}
