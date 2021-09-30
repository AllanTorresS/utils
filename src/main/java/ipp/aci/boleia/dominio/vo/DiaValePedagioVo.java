package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Classe com informações referentes à um dia de transações de vale pedágio
 */
public class DiaValePedagioVo {

    private Date dia;
    private BigDecimal saldoInicial;
    private BigDecimal creditosValePedagio;
    private BigDecimal transacoesPfGo;
    private BigDecimal passagensValePedagio;
    private BigDecimal estornosValePegadio;
    private BigDecimal saldoFinal;
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
     * @param saldoInicial Valor referente ao saldo inicial da frota no dia
     * @param creditosValePedagio Valor referente ao crédito total de vale pedágio no dia
     * @param transacoesPfGo Valor referente ao total de transações de Pró-Frotas GO no dia
     * @param passagensValePedagio Valor referente ao total de passagens de vale pedágio no dia
     * @param estornosValePegadio Valor referente ao total de estornos de vale pedágio no dia
     * @param saldoFinal Valor referente ao saldo final do dia
     */
    public DiaValePedagioVo(Date dia, BigDecimal saldoInicial, BigDecimal creditosValePedagio, BigDecimal transacoesPfGo, BigDecimal passagensValePedagio, BigDecimal estornosValePegadio, BigDecimal saldoFinal) {
        this.dia = dia;
        this.saldoInicial = saldoInicial;
        this.creditosValePedagio = creditosValePedagio;
        this.transacoesPfGo = transacoesPfGo;
        this.passagensValePedagio = passagensValePedagio;
        this.estornosValePegadio = estornosValePegadio;
        this.saldoFinal = saldoFinal;
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

	public BigDecimal getEstornosValePegadio() {
		return estornosValePegadio;
	}

	public void setEstornosValePegadio(BigDecimal estornosValePegadio) {
		this.estornosValePegadio = estornosValePegadio;
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

}