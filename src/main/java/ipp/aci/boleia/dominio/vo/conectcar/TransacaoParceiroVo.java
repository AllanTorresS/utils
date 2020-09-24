package ipp.aci.boleia.dominio.vo.conectcar;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransacaoParceiroVo {

	@JsonProperty("TipoEventoTransacao")
	private Integer tipoEventoTransacao;

	@JsonProperty("AdesaoId")
	private Integer adesaoId;

	@JsonProperty("DataAdesao")
	private Date dataAdesao;

	@JsonProperty("PlanoId")
	private Integer planoId;

	@JsonProperty("ClienteId")
	private Integer clienteId;

	@JsonProperty("Documento")
	private Long documento;

	@JsonProperty("PessoaFisica")
	private Boolean pessoaFisica;

	@JsonProperty("SaldoId")
	private Integer saldoId;

	@JsonProperty("ValorSaldo")
	private Double valorSaldo;

	@JsonProperty("VeiculoId")
	private Integer veiculoId;

	@JsonProperty("TagId")
	private Integer tagId;

	@JsonProperty("DataTransacao")
	private Date dataTransacao;

	@JsonProperty("TransacaoId")
	private Long transacaoId;

	@JsonProperty("TipoOperacaoId")
	private Integer tipoOperacaoId;

	@JsonProperty("Credito")
	private Boolean credito;

	@JsonProperty("ValorTransacao")
	private Double valorTransacao;

	@JsonProperty("StatusTransacao")
	private Integer statusTransacao;

	@JsonProperty("DataCancelamento")
	private Date dataCancelamento;

	@JsonProperty("DocumentType")
	private String documentType;

	@JsonProperty("DocumentId")
	private String documentId;

	@JsonProperty("DocumentPartitionKey")
	private String documentPartitionKey;

	@JsonProperty("Placa")
	private String placa;

	@JsonProperty("SaldoAnterior")
	private Double saldoAnterior;

	@JsonProperty("SubDescricao")
	private String subDescricao;

	@JsonProperty("Descricao")
	private String descricao;

	@JsonProperty("Propriedades")
	private TransacaoParceiroPropriedadesVo propriedades;

	public TransacaoParceiroVo() {

	}

	public Integer getAdesaoId() {
		return adesaoId;
	}

	public void setAdesaoId(Integer adesaoId) {
		this.adesaoId = adesaoId;
	}

	public Date getDataAdesao() {
		return dataAdesao;
	}

	public void setDataAdesao(Date dataAdesao) {
		this.dataAdesao = dataAdesao;
	}

	public Integer getPlanoId() {
		return planoId;
	}

	public void setPlanoId(Integer planoId) {
		this.planoId = planoId;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public Long getDocumento() {
		return documento;
	}

	public void setDocumento(Long documento) {
		this.documento = documento;
	}

	public Boolean getPessoaFisica() {
		return pessoaFisica;
	}

	public void setPessoaFisica(Boolean pessoaFisica) {
		this.pessoaFisica = pessoaFisica;
	}

	public Integer getSaldoId() {
		return saldoId;
	}

	public void setSaldoId(Integer saldoId) {
		this.saldoId = saldoId;
	}

	public Double getValorSaldo() {
		return valorSaldo;
	}

	public void setValorSaldo(Double valorSaldo) {
		this.valorSaldo = valorSaldo;
	}

	public Integer getVeiculoId() {
		return veiculoId;
	}

	public void setVeiculoId(Integer veiculoId) {
		this.veiculoId = veiculoId;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public Date getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}

	public Long getTransacaoId() {
		return transacaoId;
	}

	public void setTransacaoId(Long transacaoId) {
		this.transacaoId = transacaoId;
	}

	public Integer getTipoOperacaoId() {
		return tipoOperacaoId;
	}

	public void setTipoOperacaoId(Integer tipoOperacaoId) {
		this.tipoOperacaoId = tipoOperacaoId;
	}

	public Boolean getCredito() {
		return credito;
	}

	public void setCredito(Boolean credito) {
		this.credito = credito;
	}

	public Double getValorTransacao() {
		return valorTransacao;
	}

	public void setValorTransacao(Double valorTransacao) {
		this.valorTransacao = valorTransacao;
	}

	public Integer getStatusTransacao() {
		return statusTransacao;
	}

	public void setStatusTransacao(Integer statusTransacao) {
		this.statusTransacao = statusTransacao;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDocumentPartitionKey() {
		return documentPartitionKey;
	}

	public void setDocumentPartitionKey(String documentPartitionKey) {
		this.documentPartitionKey = documentPartitionKey;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Double getSaldoAnterior() {
		return saldoAnterior;
	}

	public void setSaldoAnterior(Double saldoAnterior) {
		this.saldoAnterior = saldoAnterior;
	}

	public String getSubDescricao() {
		return subDescricao;
	}

	public void setSubDescricao(String subDescricao) {
		this.subDescricao = subDescricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TransacaoParceiroPropriedadesVo getPropriedades() {
		return propriedades;
	}

	public void setPropriedades(TransacaoParceiroPropriedadesVo propriedades) {
		this.propriedades = propriedades;
	}

	public Integer getTipoEventoTransacao() {
		return tipoEventoTransacao;
	}

	public void setTipoEventoTransacao(Integer tipoEventoTransacao) {
		this.tipoEventoTransacao = tipoEventoTransacao;
	}

}
