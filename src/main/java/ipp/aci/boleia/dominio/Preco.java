package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.enums.StatusPreco;
import ipp.aci.boleia.dominio.enums.StatusPrecoNegociacao;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;
import org.hibernate.annotations.Formula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * Representa a tabela de Preco
 */
@Entity
@Audited
@Table(name = "FROTA_PTOV_PRECO")
public class Preco implements IPersistente, IPertenceRevendedor, IPertenceFrota {

    private static final long serialVersionUID = 8665679213043737610L;

    private static final String FORMULA_DATA_FIM = "(SELECT * " +
        "FROM (SELECT DISTINCT FPP2.dt_vigencia " +
        "FROM boleia_schema.frota_ptov_preco FPP " +
        "JOIN boleia_schema.ptov_preco PP " +
        "ON FPP.cd_ptov_preco = PP.cd_ptov_preco " +
        "JOIN boleia_schema.micromercado_preco MP " +
        "ON MP.cd_micromercado_preco = PP.cd_micromercado_preco " +
        "JOIN boleia_schema.micromercado_preco MP2 " +
        "ON MP.cd_tipo_combustivel = MP2.cd_tipo_combustivel " +
        "JOIN boleia_schema.ptov_preco PP2 " +
        "ON MP2.cd_micromercado_preco = PP2.cd_micromercado_preco " +
        "JOIN boleia_schema.frota_ptov_preco FPP2 " +
        "ON PP2.cd_ptov_preco = FPP2.cd_ptov_preco AND FPP.cd_frota_ptov = FPP2.cd_frota_ptov AND FPP.cd_frota_ptov_preco <> FPP2.cd_frota_ptov_preco " +
        "WHERE " +
        "FPP.CD_FROTA_PTOV_PRECO = CD_FROTA_PTOV_PRECO " +
        "AND ((FPP.dt_vigencia IS NOT NULL AND FPP2.dt_vigencia > DT_VIGENCIA) " +
        "OR (FPP.dt_vigencia IS NULL AND FPP2.dt_vigencia IS NOT NULL)) " +
        "AND FPP2.id_status NOT IN (4,7) " +
        "ORDER BY FPP2.dt_vigencia) " +
        "WHERE rownum <= 1) ";

    @Id
    @Column(name = "CD_FROTA_PTOV_PRECO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FROTA_PTOV_PRECO")
    @SequenceGenerator(name = "SEQ_FROTA_PTOV_PRECO", sequenceName = "SEQ_FROTA_PTOV_PRECO", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PTOV")
    private FrotaPontoVenda frotaPtov;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV_PRECO")
    private PrecoBase precoBase;

    @NotNull
    @Column(name = "VA_PRECO")
    private BigDecimal preco;

    @Column(name = "VA_PRECO_ANTERIOR")
    private BigDecimal precoAnterior;

    @Column(name = "VA_DESCONTO_VIGENTE")
    private BigDecimal descontoVigente;

    @Column(name = "VA_DESCONTO_SOLICITADO")
    private BigDecimal descontoSolicitado;

    @Column(name = "VA_VOLUMEESTIMADO")
    private BigDecimal volumeEstimado;

    @Size(max=1000)
    @Column(name = "DS_JUSTIFICATIVA")
    private String justificativa;

    @NotNull
    @Column(name = "ID_STATUS")
    private Integer status;

    @Column(name = "DT_SOLICITACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataSolicitacao;

    @NotNull
    @Column(name = "DT_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @Column(name = "DT_VIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataVigencia;

    @Column(name = "DT_AGENDAMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAgendamento;

    @NotAudited
    @Formula(StatusPreco.DECODE_FORMULA)
    private String statusConvertidoAcordo;

    @NotAudited
    @Formula(StatusPrecoNegociacao.DECODE_FORMULA)
    private String statusConvertidoNegociado;

    @NotAudited
    @Formula(FORMULA_DATA_FIM)
    private Date dataFim;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public FrotaPontoVenda getFrotaPtov() {
        return frotaPtov;
    }

    public void setFrotaPtov(FrotaPontoVenda frotaPtov) {
        this.frotaPtov = frotaPtov;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusConvertidoAcordo() {
        return statusConvertidoAcordo;
    }

    public void setStatusConvertidoAcordo(String statusConvertidoAcordo) {
        this.statusConvertidoAcordo = statusConvertidoAcordo;
    }

    public String getStatusConvertidoNegociado() {
        return statusConvertidoNegociado;
        }

    public void setStatusConvertidoNegociado(String statusConvertidoNegociado) {
        this.statusConvertidoNegociado = statusConvertidoNegociado;
    }

    public PrecoBase getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(PrecoBase precoBase) {
        this.precoBase = precoBase;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public BigDecimal getPrecoAnterior() {
        return precoAnterior;
    }

    public void setPrecoAnterior(BigDecimal precoAnterior) {
        this.precoAnterior = precoAnterior;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public BigDecimal getDescontoVigente() {
        return descontoVigente;
    }

    public void setDescontoVigente(BigDecimal descontoVigente) {
        this.descontoVigente = descontoVigente;
    }

    public BigDecimal getDescontoSolicitado() {
        return descontoSolicitado;
    }

    public void setDescontoSolicitado(BigDecimal descontoSolicitado) {
        this.descontoSolicitado = descontoSolicitado;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public Date getDataVigencia() {
        return dataVigencia;
    }

    public void setDataVigencia(Date dataVigencia) {
        this.dataVigencia = dataVigencia;
    }

    @Transient
    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return frotaPtov != null ? Collections.singletonList(frotaPtov.getPontoVenda()) : Collections.emptyList();
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return frotaPtov != null ? Collections.singletonList(frotaPtov.getFrota()) : Collections.emptyList();
    }

    @Transient
    public Frota getFrota() {
        return frotaPtov != null ? frotaPtov.getFrota() : null;
    }

    @Transient
    public PontoDeVenda getPontoVenda() {
        return frotaPtov != null ? frotaPtov.getPontoVenda() : null;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public BigDecimal getVolumeEstimado() {
        return volumeEstimado;
    }

    public void setVolumeEstimado(BigDecimal volumeEstimado) {
        this.volumeEstimado = volumeEstimado;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    @Transient
    public BigDecimal getPrecoComAcordo(){
        return getDescontoSolicitado() != null ? precoBase.getPreco().add(getDescontoSolicitado()) : preco;
    }

    public Date getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(Date dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    /**
	 * Verifica se a data atual é anterior à data de agendamento
	 * @param dataHoraCorrente data atual
	 * @return se a data atual é ou não anterior à data de agendamento
	 */
    @Transient
    public boolean isAnteriorDataAgendamento(Date dataHoraCorrente) {
        return dataAgendamento != null && dataAgendamento.compareTo(dataHoraCorrente) > 0;
    }

    /**
	 * Verifica se a data atual é posterior à data fim
	 * @param dataHoraCorrente data atual
	 * @return se a data atual é ou não posterior à data fim
	 */
    @Transient
    public boolean isPosteriorDataFim(Date dataHoraCorrente) {
        return dataFim != null && dataFim.compareTo(dataHoraCorrente) < 0;
    }

    /**
	 * Verifica se a data atual é posterior à data de vigência
	 * @param dataHoraCorrente data atual
	 * @return se a data atual é ou não posterior à data de vigência
	 */
    @Transient
    public boolean isPosteriorDataVigencia(Date dataHoraCorrente) {
        return dataVigencia != null && dataVigencia.compareTo(dataHoraCorrente) < 0;
    }
}
