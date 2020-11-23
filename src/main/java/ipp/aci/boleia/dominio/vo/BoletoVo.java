package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Classe com informações referentes à um boleto.
 */
public class BoletoVo {

	private String nomeBanco;
	private String usoDoBanco;
	private String linhaDigitavel;
	private String cedente;
	private String dataDeVencimento;
	private String nossoNumeroFormatado;
	private String numeroDocumento;
	private String tipoDocumento;
	private String dataDocumento;
	private String codigoAgencia;
	private BigDecimal valorDocumento;
	private BigDecimal valorAcrescimos;
	private BigDecimal valorCobrado;
	private String sacado;
	private String cpfCnpj;
	private String localPagamento;
	private String aceite;
	private String dataProcessamento;
	private String carteira;
	private String moeda;
	private BigDecimal quantidade;
	private BigDecimal valorMoeda;
	private BigDecimal valorDesconto;
	private BigDecimal valorDeducoes;
	private BigDecimal valorMulta;
	private String codigoBarras;
	private String instrucoesLinha1;
	private String instrucoesLinha2;
	private String instrucoesLinha3;
	private String instrucoesLinha4;
	private String instrucoesLinha5;
	private String instrucoesLinha6;
	private String instrucoesLinha7;
	private String instrucoesLinha8;
	private String codigoCliente;
	private String numeroBoleto;
	private String codigoErro;
	private boolean boletoGerado;
	private String codigoBanco;
	private String sacador;
	private String companhia;
	private String documento;
	private String parcela;
	private String tipoDocumentoAbadi;
	private AvisoDebitoVo avisoDebito;
	private List<AjusteCobrancaVo> ajustes;
	private PedidoTagVo pedidoTag;

	/**
	 * Serializacao JSON
	 */
	public BoletoVo() {
		// serializacao json
	}

	public String getNomeBanco() {
		return nomeBanco;
	}

	public void setNomeBanco(String nomeBanco) {
		this.nomeBanco = nomeBanco;
	}

	public String getUsoDoBanco() {
		return usoDoBanco;
	}

	public void setUsoDoBanco(String usoDoBanco) {
		this.usoDoBanco = usoDoBanco;
	}

	public String getLinhaDigitavel() {
		return linhaDigitavel;
	}

	public void setLinhaDigitavel(String linhaDigitavel) {
		this.linhaDigitavel = linhaDigitavel;
	}

	public String getCedente() {
		return cedente;
	}

	public void setCedente(String cedente) {
		this.cedente = cedente;
	}

	public String getDataDeVencimento() {
		return dataDeVencimento;
	}

	public void setDataDeVencimento(String dataDeVencimento) {
		this.dataDeVencimento = dataDeVencimento;
	}

	public String getNossoNumeroFormatado() {
		return nossoNumeroFormatado;
	}

	public void setNossoNumeroFormatado(String nossoNumeroFormatado) {
		this.nossoNumeroFormatado = nossoNumeroFormatado;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getDataDocumento() {
		return dataDocumento;
	}

	public void setDataDocumento(String dataDocumento) {
		this.dataDocumento = dataDocumento;
	}

	public String getCodigoAgencia() {
		return codigoAgencia;
	}

	public void setCodigoAgencia(String codigoAgencia) {
		this.codigoAgencia = codigoAgencia;
	}

	public BigDecimal getValorDocumento() {
		return valorDocumento;
	}

	public void setValorDocumento(BigDecimal valorDocumento) {
		this.valorDocumento = valorDocumento;
	}

	public BigDecimal getValorAcrescimos() {
		return valorAcrescimos;
	}

	public void setValorAcrescimos(BigDecimal valorAcrescimos) {
		this.valorAcrescimos = valorAcrescimos;
	}

	public BigDecimal getValorCobrado() {
		return valorCobrado;
	}

	public void setValorCobrado(BigDecimal valorCobrado) {
		this.valorCobrado = valorCobrado;
	}

	public String getSacado() {
		return sacado;
	}

	public void setSacado(String sacado) {
		this.sacado = sacado;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getLocalPagamento() {
		return localPagamento;
	}

	public void setLocalPagamento(String localPagamento) {
		this.localPagamento = localPagamento;
	}

	public String getAceite() {
		return aceite;
	}

	public void setAceite(String aceite) {
		this.aceite = aceite;
	}

	public String getDataProcessamento() {
		return dataProcessamento;
	}

	public void setDataProcessamento(String dataProcessamento) {
		this.dataProcessamento = dataProcessamento;
	}

	public String getCarteira() {
		return carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}

	public String getMoeda() {
		return moeda;
	}

	public void setMoeda(String moeda) {
		this.moeda = moeda;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getValorMoeda() {
		return valorMoeda;
	}

	public void setValorMoeda(BigDecimal valorMoeda) {
		this.valorMoeda = valorMoeda;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public BigDecimal getValorDeducoes() {
		return valorDeducoes;
	}

	public void setValorDeducoes(BigDecimal valorDeducoes) {
		this.valorDeducoes = valorDeducoes;
	}

	public BigDecimal getValorMulta() {
		return valorMulta;
	}

	public void setValorMulta(BigDecimal valorMulta) {
		this.valorMulta = valorMulta;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getInstrucoesLinha1() {
		return instrucoesLinha1;
	}

	public void setInstrucoesLinha1(String instrucoesLinha1) {
		this.instrucoesLinha1 = instrucoesLinha1;
	}

	public String getInstrucoesLinha2() {
		return instrucoesLinha2;
	}

	public void setInstrucoesLinha2(String instrucoesLinha2) {
		this.instrucoesLinha2 = instrucoesLinha2;
	}

	public String getInstrucoesLinha3() {
		return instrucoesLinha3;
	}

	public void setInstrucoesLinha3(String instrucoesLinha3) {
		this.instrucoesLinha3 = instrucoesLinha3;
	}

	public String getInstrucoesLinha4() {
		return instrucoesLinha4;
	}

	public void setInstrucoesLinha4(String instrucoesLinha4) {
		this.instrucoesLinha4 = instrucoesLinha4;
	}

	public String getInstrucoesLinha5() {
		return instrucoesLinha5;
	}

	public void setInstrucoesLinha5(String instrucoesLinha5) {
		this.instrucoesLinha5 = instrucoesLinha5;
	}

	public String getInstrucoesLinha6() {
		return instrucoesLinha6;
	}

	public void setInstrucoesLinha6(String instrucoesLinha6) {
		this.instrucoesLinha6 = instrucoesLinha6;
	}

	public String getInstrucoesLinha7() {
		return instrucoesLinha7;
	}

	public void setInstrucoesLinha7(String instrucoesLinha7) {
		this.instrucoesLinha7 = instrucoesLinha7;
	}

	public String getInstrucoesLinha8() {
		return instrucoesLinha8;
	}

	public void setInstrucoesLinha8(String instrucoesLinha8) {
		this.instrucoesLinha8 = instrucoesLinha8;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getNumeroBoleto() {
		return numeroBoleto;
	}

	public void setNumeroBoleto(String numeroBoleto) {
		this.numeroBoleto = numeroBoleto;
	}

	public String getCodigoErro() {
		return codigoErro;
	}

	public void setCodigoErro(String codigoErro) {
		this.codigoErro = codigoErro;
	}

	public boolean isBoletoGerado() {
		return boletoGerado;
	}

	public void setBoletoGerado(boolean boletoGerado) {
		this.boletoGerado = boletoGerado;
	}

	public String getCodigoBanco() {
		return codigoBanco;
	}

	public void setCodigoBanco(String codigoBanco) {
		this.codigoBanco = codigoBanco;
	}

	public String getSacador() {
		return sacador;
	}

	public void setSacador(String sacador) {
		this.sacador = sacador;
	}

	public String getCompanhia() {
		return companhia;
	}

	public void setCompanhia(String companhia) {
		this.companhia = companhia;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getParcela() {
		return parcela;
	}

	public void setParcela(String parcela) {
		this.parcela = parcela;
	}

	public String getTipoDocumentoAbadi() {
		return tipoDocumentoAbadi;
	}

	public void setTipoDocumentoAbadi(String tipoDocumentoAbadi) {
		this.tipoDocumentoAbadi = tipoDocumentoAbadi;
	}

	public AvisoDebitoVo getAvisoDebito() {
		return avisoDebito;
	}

	public void setAvisoDebito(AvisoDebitoVo avisoDebito) {
		this.avisoDebito = avisoDebito;
	}

	public List<AjusteCobrancaVo> getAjustes() {
		return ajustes;
	}

	public void setAjustes(List<AjusteCobrancaVo> ajustes) {
		this.ajustes = ajustes;
	}

	public PedidoTagVo getPedidoTag() {
		return pedidoTag;
	}

	public void setPedidoTag(PedidoTagVo pedidoTag) {
		this.pedidoTag = pedidoTag;
	}

}