package ipp.aci.boleia.dominio.vo.apco;

import ipp.aci.boleia.dominio.PontoDeVenda;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa as informações do volume de uma frota cliente.
 *
 */
public class InformacoesVolumeVo {

	private PontoDeVenda pontoDeVenda;

	private Long noSeqCliProFrota;

	private Long codigoProduto;

	private BigDecimal volumeVenda;

	private Date dataVendaRealizada;

	/**
	 * Construtor Default
	 */
	public InformacoesVolumeVo(){
		// serializacao json
	}

	/**
	 * Construtor dos volumes de uma frota cliente em um dado intervalo de integração APCO.
	 *
	 * @param pv o ponto de venda que iremos utilizar para filtrar as componentes de área de abastecimento
	 * @param noSeqCliProFrota o código identificador da frota
	 * @param codigoProduto o tipo de combustivel do lado do profrotas para ser utilizado no de-para
	 * @param dataVendaRealizada a data da operação
	 * @param volumeVenda o volume de combustivel agrupado durante o período de exportação
	 */
	public InformacoesVolumeVo(PontoDeVenda pv, Long noSeqCliProFrota, Long codigoProduto, Date dataVendaRealizada, BigDecimal volumeVenda){
		this.pontoDeVenda = pv;
		this.noSeqCliProFrota = noSeqCliProFrota;
		this.codigoProduto = codigoProduto;
		this.dataVendaRealizada = dataVendaRealizada;
		this.volumeVenda = volumeVenda;
	}

	public PontoDeVenda getPontoDeVenda() {
		return pontoDeVenda;
	}

	public void setPontoDeVenda(PontoDeVenda pontoDeVenda) {
		this.pontoDeVenda = pontoDeVenda;
	}

	public Long getNoSeqCliProFrota() {
		return noSeqCliProFrota;
	}

	public void setNoSeqCliProFrota(Long noSeqCliProFrota) {
		this.noSeqCliProFrota = noSeqCliProFrota;
	}

	public Long getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(Long codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public BigDecimal getVolumeVenda() {
		return volumeVenda;
	}

	public void setVolumeVenda(BigDecimal volumeVenda) {
		this.volumeVenda = volumeVenda;
	}

	public Date getDataVendaRealizada() {
		return dataVendaRealizada;
	}

	public void setDataVendaRealizada(Date dataVendaRealizada) {
		this.dataVendaRealizada = dataVendaRealizada;
	}
}
