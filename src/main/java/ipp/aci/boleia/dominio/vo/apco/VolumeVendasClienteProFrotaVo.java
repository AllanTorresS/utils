package ipp.aci.boleia.dominio.vo.apco;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa uma venda consolidada de combustiveis utilizada para realizar o de-para entre APCO e Profrotas,
 * agrupando o volume dos combustiveis pelos dados: posto, frota e tipo de combustivel.
 */
public class VolumeVendasClienteProFrotaVo {

  private Long codComponente;

  private Long noSeqCliProFrota;

  private Long codigoProduto;

  private BigDecimal volumeVenda;

  private Date dataVendaRealizada;

	/**
	 * Construtor Default
	 */
	public VolumeVendasClienteProFrotaVo(){
		// serializacao json

	}

	/**
	 * Construtor das transações de frota cliente em um dado intervalo de integração APCO.
	 *
	 * @param codComponente o código identificador corporativo do ponto de venda
	 * @param noSeqCliProFrota o código identificador da frota
	 * @param codigoProduto o tipo de combustivel do lado do profrotas para ser utilizado no de-para
	 * @param dataVendaRealizada a data da operação
	 * @param volumeVenda o volume de combustivel agrupado durante o período de exportação
	 */
	public VolumeVendasClienteProFrotaVo(Long codComponente, Long noSeqCliProFrota, Long codigoProduto, Date dataVendaRealizada, BigDecimal volumeVenda){
		this.codComponente = codComponente;
		this.noSeqCliProFrota = noSeqCliProFrota;
		this.codigoProduto = codigoProduto;
		this.dataVendaRealizada = dataVendaRealizada;
		this.volumeVenda = volumeVenda;
	}

	public Long getCodComponente() {
		return codComponente;
	}

	public void setCodComponente(Long codComponente) {
		this.codComponente = codComponente;
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
