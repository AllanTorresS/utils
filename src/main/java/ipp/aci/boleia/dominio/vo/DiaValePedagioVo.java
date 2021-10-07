package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.enums.TipoTransacaoConectcar;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Classe com informações referentes à um dia de transações de vale pedágio
 */
public class DiaValePedagioVo {

    private Date dia;
    private Date dataPagamentoCobranca;
	private Date dataTransacao;
    private BigDecimal saldoInicial;
    private BigDecimal creditosValePedagio;
    private BigDecimal transacoesPfGo;
    private BigDecimal passagensValePedagio;
    private BigDecimal estornosValePedagio;
    private BigDecimal saldoFinal;
    private BigDecimal valorTotalTransacao;
    private BigDecimal valorTotalCobranca;
    private Integer tipoTransacao;
    private BigDecimal valorMensalidade;
    private BigDecimal creditoTotal;
    private List<TransacaoValePedagioVo> transacoes;

    /**
     * Serializacao json
     */
    public DiaValePedagioVo() {
        // serializacao json
    }

    /**
     * Constroi o VO de detalhe a partir de uma transação ConectCar
     * @param dia Data desejada
     * @param valorTotalTransacao Valor referente ao total da transação
     * @param tipoTransacao Valor referente ao tipo da transação
     * @param valorMensalidade Valor referente a mensalidade da Frota no Pró-Frotas GO
     * @param valorTotalCobranca Valor referente ao total da cobrança do PFGO da Frota
     * @param dataPagamentoCobranca Valor referente a data de pagamento da cobrança do PFGO
     * @param dataTransacao Valor referente a data da transação
     */
    public DiaValePedagioVo(Date dia, BigDecimal valorTotalTransacao, Integer tipoTransacao, BigDecimal valorMensalidade, BigDecimal valorTotalCobranca,
							Date dataPagamentoCobranca, Date dataTransacao) {
        this.dia = dia;
        this.valorTotalTransacao = valorTotalTransacao;
        this.tipoTransacao = tipoTransacao;
        this.valorMensalidade = valorMensalidade;
        this.valorTotalCobranca = valorTotalCobranca;
        this.dataPagamentoCobranca = dataPagamentoCobranca;
		this.dataTransacao = dataTransacao;
        this.creditosValePedagio = obterRecargasDeValePedagio().negate();
        this.transacoesPfGo = obterTransacoesDoPfGo();
        this.passagensValePedagio = obterTransacoesDeValePedagio();
        this.estornosValePedagio = obterEstornoValePedagio();
    }

	/**
	 * Método que obtem o total de operações do PFGO na transação
	 * @return o total de operações do PFGO
	 */
	private BigDecimal obterTransacoesDoPfGo() {
    	BigDecimal transacoesDoGo = BigDecimal.ZERO;
    	if(!tipoTransacao.equals(TipoTransacaoConectcar.RECARGA_VALE_PEDAGIO.getValue())
			&&!tipoTransacao.equals(TipoTransacaoConectcar.PASSAGEM_VALE_PEDAGIO.getValue())
			&&!tipoTransacao.equals(TipoTransacaoConectcar.ESTORNO_VALE_PEDAGIO.getValue())
			&&!tipoTransacao.equals(TipoTransacaoConectcar.CANCELAMENTO_VALE_PEDAGIO.getValue())){
    		transacoesDoGo = transacoesDoGo.add(valorTotalTransacao);
		}
		return  transacoesDoGo;
	}

	/**
	 * Método que obtem o total de operações de VPO na transação
	 * @return o total de operações de VPO
	 */
	private BigDecimal obterTransacoesDeValePedagio() {
		BigDecimal passagensValePedagio = BigDecimal.ZERO;
		if(tipoTransacao.equals(TipoTransacaoConectcar.PASSAGEM_VALE_PEDAGIO.getValue())
				||tipoTransacao.equals(TipoTransacaoConectcar.ESTORNO_VALE_PEDAGIO.getValue())){
			passagensValePedagio = passagensValePedagio.add(valorTotalTransacao);
		}
		return  passagensValePedagio;
	}

	/**
	 * Método que obtem o total de recargas de VPO na transação
	 * @return o total de recargas de VPO
	 */
	private BigDecimal obterRecargasDeValePedagio() {
		BigDecimal recargasDeValePedagio = BigDecimal.ZERO;
		if(tipoTransacao.equals(TipoTransacaoConectcar.RECARGA_VALE_PEDAGIO.getValue())){
			recargasDeValePedagio = recargasDeValePedagio.add(valorTotalTransacao);
		}
		return  recargasDeValePedagio;
	}

	/**
	 * Método que obtem o total de estornos de VPO na transação
	 * @return o total de estornos de VPO
	 */
	private BigDecimal obterEstornoValePedagio() {
		BigDecimal estornoValePedagio = BigDecimal.ZERO;
		if(tipoTransacao.equals(TipoTransacaoConectcar.ESTORNO_VALE_PEDAGIO.getValue())){
			estornoValePedagio = estornoValePedagio.add(valorTotalTransacao);
		}
		return  estornoValePedagio;
	}

	public Date getDia() {
		return dia;
	}

	public void setDia(Date dia) {
		this.dia = dia;
	}

	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public BigDecimal getCreditosValePedagio() {
		return creditosValePedagio;
	}

	public void setCreditosValePedagio(BigDecimal creditosValePedagio) {
		this.creditosValePedagio = creditosValePedagio;
	}

	public BigDecimal getTransacoesPfGo() {
		return transacoesPfGo;
	}

	public void setTransacoesPfGo(BigDecimal transacoesPfGo) {
		this.transacoesPfGo = transacoesPfGo;
	}

	public BigDecimal getPassagensValePedagio() {
		return passagensValePedagio;
	}

	public void setPassagensValePedagio(BigDecimal passagensValePedagio) {
		this.passagensValePedagio = passagensValePedagio;
	}

	public BigDecimal getEstornosValePedagio() {
		return estornosValePedagio;
	}

	public void setEstornosValePedagio(BigDecimal estornosValePedagio) {
		this.estornosValePedagio = estornosValePedagio;
	}

	public BigDecimal getSaldoFinal() {
		return saldoFinal;
	}

	public void setSaldoFinal(BigDecimal saldoFinal) {
		this.saldoFinal = saldoFinal;
	}

	public List<TransacaoValePedagioVo> getTransacoes() {
		return transacoes;
	}

	public void setTransacoes(List<TransacaoValePedagioVo> transacoes) {
		this.transacoes = transacoes;
	}

	public BigDecimal getValorTotalTransacao() {
		return valorTotalTransacao;
	}

	public void setValorTotalTransacao(BigDecimal valorTotalTransacao) {
		this.valorTotalTransacao = valorTotalTransacao;
	}

	public Integer getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(Integer tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public BigDecimal getValorMensalidade() {
		return valorMensalidade;
	}

	public void setValorMensalidade(BigDecimal valorMensalidade) {
		this.valorMensalidade = valorMensalidade;
	}

	public BigDecimal getCreditoTotal() {
		return creditoTotal;
	}

	public void setCreditoTotal(BigDecimal creditoTotal) {
		this.creditoTotal = creditoTotal;
	}

	public BigDecimal getValorTotalCobranca() {
		return valorTotalCobranca;
	}

	public Date getDataPagamentoCobranca() {
		return dataPagamentoCobranca;
	}

	public void setDataPagamentoCobranca(Date dataPagamentoCobranca) {
		this.dataPagamentoCobranca = dataPagamentoCobranca;
	}

	public Date getDataTransacao() {
		return dataTransacao;
	}

	public void setDataTransacao(Date dataTransacao) {
		this.dataTransacao = dataTransacao;
	}
}