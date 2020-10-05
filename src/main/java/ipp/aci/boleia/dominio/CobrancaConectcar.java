package ipp.aci.boleia.dominio;

import java.math.BigDecimal;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Formula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

/**
 * Representa a tabela de Cobranca Conectcar
 */
@Audited
@Entity
@Table(name = "COBRANCA_CONECTCAR")
public class CobrancaConectcar implements IPersistente {

	private static final long serialVersionUID = -4270486293974995429L;

	/**
	 * Utilizado para possibilitar a ordenação paginada pela data de vencimento
	 * vigente.
	 */
	private static final String FORMULA_DATA_VENCIMENTO_VIGENTE = "CASE WHEN DT_VENC_PGTO_AJUSTADA IS NOT NULL THEN DT_VENC_PGTO_AJUSTADA ELSE DT_VENC_PGTO END";

	@Id
	@Column(name = "CD_COBRANCA")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COBRANCA_CONECTCAR")
	@SequenceGenerator(name = "SEQ_COBRANCA_CONECTCAR", sequenceName = "SEQ_COBRANCA_CONECTCAR", allocationSize = 1)
	private Long id;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cobranca")
	private List<TransacaoConectcar> transacoesConsolidadas;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_FROTA")
	private Frota frota;
	

	@Column(name = "NO_DOC_JDE")
	private Long numeroDocumento;

	@Column(name = "ID_TIPO_DOC")
	private String tipoDocumento;

	@Column(name = "NM_CIA_DOC")
	private String ciaDocumento;

	@Column(name = "QT_PARCELAS")
	private Integer quantidadeParcelas;

	@Column(name = "DT_VENC_PGTO")
	private Date dataVencimentoPagto;

	@Column(name = "DT_VENC_PGTO_AJUSTADA")
	private Date dataVencimentoPagtoAjustada;

	@NotAudited
	@Formula(FORMULA_DATA_VENCIMENTO_VIGENTE)
	private Date dataVencimentoVigente;

	@Column(name = "DT_PGTO")
	private Date dataPagamento;

	@Column(name = "VR_TOTAL")
	private BigDecimal valorTotal;

	@Column(name = "VR_TOTAL_AJUSTADO")
	private BigDecimal valorTotalAjustado;

	@Column(name = "ID_STATUS")
	private Integer status;

	@Column(name = "ID_STATUS_INT_JDE")
	private Integer statusIntegracaoJDE;

	@Column(name = "DS_MSG_ERRO")
	private String mensagemErro;

	@Column(name = "VR_DESC_CRED")
	private BigDecimal valorDescontoCredito;

	@Column(name = "DT_INI_PER")
	private Date dataInicioPeriodo;

	@Column(name = "DT_FIM_PER")
	private Date dataFimPeriodo;

	@Column(name = "NO_TENTATIVAS_ENVIO")
	private Integer numeroTentativasEnvio;

	@Version
	@Column(name = "NO_VERSAO")
	private Long versao;

	@Column(name = "VR_DESCONTO")
	private BigDecimal valorDesconto;

	@Column(name = "VR_CRED_DISP")
	private BigDecimal creditoDisponivel;

	@Column(name = "VR_UTILIZADO")
	private BigDecimal valorUtilizado;

	@Column(name = "VR_MENSALIDADE")
	private BigDecimal valorMensalidade;

	@Column(name = "ID_TIPO_COBR")
	private Integer tipo;

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

	public Date getDataVencimentoPagto() {
		return dataVencimentoPagto;
	}

	public void setDataVencimentoPagto(Date dataVencimentoPagto) {
		this.dataVencimentoPagto = dataVencimentoPagto;
	}

	public Date getDataVencimentoPagtoAjustada() {
		return dataVencimentoPagtoAjustada;
	}

	public void setDataVencimentoPagtoAjustada(Date dataVencimentoPagtoAjustada) {
		this.dataVencimentoPagtoAjustada = dataVencimentoPagtoAjustada;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorTotalAjustado() {
		return valorTotalAjustado;
	}

	public void setValorTotalAjustado(BigDecimal valorTotalAjustado) {
		this.valorTotalAjustado = valorTotalAjustado;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getStatusIntegracaoJDE() {
		return statusIntegracaoJDE;
	}

	public void setStatusIntegracaoJDE(Integer statusIntegracaoJDE) {
		this.statusIntegracaoJDE = statusIntegracaoJDE;
	}

	public void setVersao(Long versao) {
		this.versao = versao;
	}

	public List<TransacaoConectcar> getTransacoesConsolidadas() {
		return transacoesConsolidadas;
	}

	public void setTransacoesConsolidadas(List<TransacaoConectcar> transacoesConsolidadas) {
		this.transacoesConsolidadas = transacoesConsolidadas;
	}

	public BigDecimal getValorDescontoCredito() {
		return valorDescontoCredito;
	}

	public void setValorDescontoCredito(BigDecimal valorDescontoCredito) {
		this.valorDescontoCredito = valorDescontoCredito;
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

	public BigDecimal getValorDesconto() {
		return valorDesconto != null ? valorDesconto : BigDecimal.ZERO;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public Integer getNumeroTentativasEnvio() {
		return numeroTentativasEnvio;
	}

	public void setNumeroTentativasEnvio(Integer numeroTentativasEnvio) {
		this.numeroTentativasEnvio = numeroTentativasEnvio;
	}

	/**
	 * Verifica se houve um ajuste para a cobrança.
	 * 
	 * @return o valor total ajustado caso tenha ocorrido ajuste desta cobrança e
	 *         valor total da cobrança, caso contrário.
	 */
	@Transient
	public BigDecimal getValorVigente() {
		BigDecimal descontosFrotaLeves = valorDesconto != null ? valorDesconto : BigDecimal.ZERO;
		return valorTotalAjustado != null ? valorTotalAjustado : valorTotal.subtract(descontosFrotaLeves);
	}

	public Date getDataVencimentoVigente() {
		return dataVencimentoVigente;
	}

	public void setDataVencimentoVigente(Date dataVencimentoVigente) {
		this.dataVencimentoVigente = dataVencimentoVigente;
	}

	/**
	 * Incrementa o número de tentativas de envio para o JDE
	 */
	public void incrementarNumeroTentativasEnvio() {
		if (this.getNumeroTentativasEnvio() != null) {
			this.setNumeroTentativasEnvio(this.getNumeroTentativasEnvio() + 1);
		} else {
			this.setNumeroTentativasEnvio(1);
		}
	}

	public BigDecimal getCreditoDisponivel() {
		return creditoDisponivel;
	}

	public void setCreditoDisponivel(BigDecimal creditoDisponivel) {
		this.creditoDisponivel = creditoDisponivel;
	}

	public BigDecimal getValorUtilizado() {
		return valorUtilizado;
	}

	public void setValorUtilizado(BigDecimal valorUtilizado) {
		this.valorUtilizado = valorUtilizado;
	}

	public BigDecimal getValorMensalidade() {
		return valorMensalidade;
	}

	public void setValorMensalidade(BigDecimal valorMensalidade) {
		this.valorMensalidade = valorMensalidade;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

}