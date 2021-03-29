package ipp.aci.boleia.dominio;

import java.util.Date;

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

import ipp.aci.boleia.dominio.interfaces.IPersistente;

/**
 * Representa a tabela com os erros no processamento das Transações da Conectcar
 */
@Entity
@Table(name = "ERRO_INTEGRACAO_TRANSACAO_CONECTCAR")
public class ErroIntegracaoTransacaoConectcar implements IPersistente {

	private static final long serialVersionUID = 8095939439819340567L;

	@Id
	@Column(name = "CD_ERRO_TRANS_CONECTCAR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ERRO_TRANS_CONECTCAR")
	@SequenceGenerator(name = "SEQ_ERRO_TRANS_CONECTCAR", sequenceName = "SEQ_ERRO_TRANS_CONECTCAR", allocationSize = 1)
	private Long id;

	@Column(name = "DT_PROCESSAMENTO")
	private Date dataProcessamento;

	@Column(name = "CD_TIPO_TRANSACAO")
	private Integer tipoTransacao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CD_FROTA")
	private Frota frota;
	
	@Column(name = "CD_VEICULO")
	private Long veiculoId;

	@Column(name = "DS_PLACA")
	private String placa;

	@Column(name = "CD_TAG_CONECTCAR")
	private String numeroTag;
	
	/**
	 * Código da transação originada da Conectcar
	 */
	@Column(name = "CD_TRANSACAO_CONECTCAR")
	private Long codigoTransacaoConectcar;
	
    @Lob
    @Column(name = "DS_JSON_REQUEST")
    private String transacaoConectcarJson;

    @Column(name = "DS_ERRO_PROCESSAMENTO")
    private String erroProcessamento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataProcessamento() {
		return dataProcessamento;
	}

	public void setDataProcessamento(Date dataProcessamento) {
		this.dataProcessamento = dataProcessamento;
	}

	public Integer getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(Integer tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public Frota getFrota() {
		return frota;
	}

	public void setFrota(Frota frota) {
		this.frota = frota;
	}

	public Long getVeiculoId() {
		return veiculoId;
	}

	public void setVeiculoId(Long veiculoId) {
		this.veiculoId = veiculoId;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getNumeroTag() {
		return numeroTag;
	}

	public void setNumeroTag(String numeroTag) {
		this.numeroTag = numeroTag;
	}

	public Long getCodigoTransacaoConectcar() {
		return codigoTransacaoConectcar;
	}

	public void setCodigoTransacaoConectcar(Long codigoTransacaoConectcar) {
		this.codigoTransacaoConectcar = codigoTransacaoConectcar;
	}

	public String getTransacaoConectcarJson() {
		return transacaoConectcarJson;
	}

	public void setTransacaoConectcarJson(String transacaoConectcarJson) {
		this.transacaoConectcarJson = transacaoConectcarJson;
	}

	public String getErroProcessamento() {
		return erroProcessamento;
	}

	public void setErroProcessamento(String erroProcessamento) {
		this.erroProcessamento = erroProcessamento;
	}

}