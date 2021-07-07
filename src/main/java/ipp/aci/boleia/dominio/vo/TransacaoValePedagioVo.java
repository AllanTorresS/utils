package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;
import java.util.Date;

import ipp.aci.boleia.dominio.TransacaoConectcar;
import ipp.aci.boleia.dominio.enums.TipoTransacaoConectcar;
import ipp.aci.boleia.util.UtilitarioFormatacao;

/**
 * Representa a transação (assunto) do SMS enviado ao motorista
 */
public class TransacaoValePedagioVo {

	private String cnpjEmbarcador;
	private String embarcador;
	private String placa;
	private String tipoTransacao;
	private BigDecimal valorTotal;
	private Date dataInicioViagem;
	private Date dataFimViagem;

	public TransacaoValePedagioVo() {
		// TODO Auto-generated constructor stub
	}
	
	public TransacaoValePedagioVo(TransacaoConectcar transacao) {
		this.cnpjEmbarcador = transacao.getCnpjEmbarcador() != null ? UtilitarioFormatacao.formatarCnpjApresentacao(transacao.getCnpjEmbarcador()) : null;
		this.embarcador = transacao.getEmbarcador();
		this.placa = transacao.getPlaca();
		this.tipoTransacao = TipoTransacaoConectcar.obterPorValor(transacao.getTipoTransacao()).getLabel();
		this.valorTotal = transacao.getValorInvertido();
		this.dataInicioViagem = transacao.getDataInicioViagem();
		this.dataFimViagem = transacao.getDataFimViagem();
	}
	
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(String tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Date getDataInicioViagem() {
		return dataInicioViagem;
	}

	public void setDataInicioViagem(Date dataInicioViagem) {
		this.dataInicioViagem = dataInicioViagem;
	}

	public Date getDataFimViagem() {
		return dataFimViagem;
	}

	public void setDataFimViagem(Date dataFimViagem) {
		this.dataFimViagem = dataFimViagem;
	}

	public String getCnpjEmbarcador() {
		return cnpjEmbarcador;
	}

	public void setCnpjEmbarcador(String cnpjEmbarcador) {
		this.cnpjEmbarcador = cnpjEmbarcador;
	}

	public String getEmbarcador() {
		return embarcador;
	}

	public void setEmbarcador(String embarcador) {
		this.embarcador = embarcador;
	}

}
