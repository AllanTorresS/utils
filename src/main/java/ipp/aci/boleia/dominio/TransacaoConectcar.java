package ipp.aci.boleia.dominio;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Version;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import org.hibernate.envers.Audited;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.seguranca.UtilitarioCriptografia;

/**
 * Representa a tabela de Transacao Conectcar Consolidada
 */
@Entity
@Audited
@Table(name = "TRANSACAO_CONECTCAR")
public class TransacaoConectcar implements IPersistente, IPertenceFrota {

	private static final long serialVersionUID = 8095939439819340567L;

	@Id
	@Column(name = "CD_TRANS_CONSOL")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANS_CONECTCAR_CONSOL")
	@SequenceGenerator(name = "SEQ_TRANS_CONECTCAR_CONSOL", sequenceName = "SEQ_TRANS_CONECTCAR_CONSOL", allocationSize = 1)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_FROTA")
	private Frota frota;

	@DecimalMin("-999999999999.9999")
	@DecimalMax("999999999999.9999")
	@Digits(integer = 12, fraction = 4)
	@Column(name = "VR_TOTAL")
	private BigDecimal valorTotal;

	@Column(name = "CD_TIPO_TRANSACAO")
	private Integer tipoTransacao;

	@Column(name = "DT_INI_PER")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicioPeriodo;

	@Column(name = "DT_FIM_PER")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFimPeriodo;

	@Column(name = "QT_PRAZO_PGTO_DIAS")
	private Long prazoPagtoDias;

	@Column(name = "QT_PRAZO_REEMB_DIAS")
	private Long prazoReembolsoDias;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_COBRANCA")
	private CobrancaConectcar cobranca;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_REEMBOLSO")
	private ReembolsoConectcar reembolso;

	@Column(name = "VR_REEMB")
	private BigDecimal valorReembolso;

	@Version
	@Column(name = "NO_VERSAO")
	private Long versao;

	@Column(name = "DT_ATUALIZACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtualizacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_EMPR_AGREGADA")
	private EmpresaAgregada empresaAgregada;

	@Column(name = "CD_CHAVE")
	private String chave;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_UNIDADE")
	private Unidade unidade;

	@Column(name = "DS_PLACA")
	private String placa;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_TAG_CONECTCAR")
	private TagConectcar tag;

	@Column(name = "DS_PRACA")
	private String praca;

	@Column(name = "DT_TRANSACAO", columnDefinition = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataTransacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_TRANSACAO_ORIGEM")
	private TransacaoConectcar transacaoOrigem;

	/**
	 * Código da transação originada da Conectcar
	 */
	@Column(name = "CD_TRANSACAO_CONECTCAR")
	private Long codigoTransacaoConectcar;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Frota getFrota() {
		return frota;
	}

	public void setFrota(Frota frota) {
		this.frota = frota;
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

	public Long getVersao() {
		return versao;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Long getPrazoPagtoDias() {
		return prazoPagtoDias;
	}

	public void setPrazoPagtoDias(Long prazoPagtoDias) {
		this.prazoPagtoDias = prazoPagtoDias;
	}

	public Long getPrazoReembolsoDias() {
		return prazoReembolsoDias;
	}

	public void setPrazoReembolsoDias(Long prazoReembolsoDias) {
		this.prazoReembolsoDias = prazoReembolsoDias;
	}

	public CobrancaConectcar getCobranca() {
		return cobranca;
	}

	public void setCobranca(CobrancaConectcar cobranca) {
		this.cobranca = cobranca;
	}

	public ReembolsoConectcar getReembolso() {
		return reembolso;
	}

	public void setReembolso(ReembolsoConectcar reembolso) {
		this.reembolso = reembolso;
	}

	public BigDecimal getValorReembolso() {
		return valorReembolso;
	}

	public void setValorReembolso(BigDecimal valorReembolso) {
		this.valorReembolso = valorReembolso;
	}

	@Transient
	@Override
	public List<Frota> getFrotas() {
		return frota != null ? Collections.singletonList(frota) : Collections.emptyList();
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public EmpresaAgregada getEmpresaAgregada() {
		return empresaAgregada;
	}

	public void setEmpresaAgregada(EmpresaAgregada empresaAgregada) {
		this.empresaAgregada = empresaAgregada;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public Integer getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(Integer tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getPraca() {
		return praca;
	}

	public void setPraca(String praca) {
		this.praca = praca;
	}

	/**
	 * Gera uma chave unica para cada TransacaoConsolidada, tendo o objetivo de
	 * garantir que cada ciclo seja unico no banco de dados. Existe uma constraint
	 * (UQ_CHAVE_TRANS_CONSOL) no banco que valida a unicidade da chave.
	 */
	public void preencherChave() {
		String key = frota.getId().toString() + "|" + UtilitarioFormatacaoData.formatarDataCurta(dataInicioPeriodo)
				+ "|" + UtilitarioFormatacaoData.formatarDataCurta(dataFimPeriodo) + "|";
		if (empresaAgregada != null && empresaAgregada.getId() != null) {
			key = key + empresaAgregada.getId();
		} else if (unidade != null && unidade.getId() != null) {
			key = key + unidade.getId();
		}
		this.chave = UtilitarioCriptografia.calcularHashSHA256(key);
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Date getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}

	public TransacaoConectcar getTransacaoOrigem() {
		return transacaoOrigem;
	}

	public void setTransacaoOrigem(TransacaoConectcar transacaoOrigem) {
		this.transacaoOrigem = transacaoOrigem;
	}

	public Long getCodigoTransacaoConectcar() {
		return codigoTransacaoConectcar;
	}

	public void setCodigoTransacaoConectcar(Long codigoTransacaoConectcar) {
		this.codigoTransacaoConectcar = codigoTransacaoConectcar;
	}

	public TagConectcar getTag() {
		return tag;
	}

	public void setTag(TagConectcar tag) {
		this.tag = tag;
	}

}