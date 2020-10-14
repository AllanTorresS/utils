package ipp.aci.boleia.dominio.vo.apco;
import ipp.aci.boleia.dominio.FrotaPontoVenda;

import java.util.Date;

/**
 * Representa uma associação operacional entre uma frota e a componente de um posto.
 */
public class FrotaPostoVo {

	private Long codComponente;

	private Long noSeqCliProFrota;

	private Date dataInativacao;

	/**
	 * Construtor Default
	 */
	public FrotaPostoVo(){
		// serializacao json
	}

	/**
	 * Construtor do DTO de transações.
	 *
	 * @param codComponente o código identificador corporativo do ponto de venda
	 * @param noSeqCliProFrota o código identificador da frota
	 * @param dataInativacao a data de inativação da frota
	 */
	public FrotaPostoVo(Long codComponente, Long noSeqCliProFrota, Date dataInativacao){
		this.codComponente = codComponente;
		this.noSeqCliProFrota = noSeqCliProFrota;
		this.dataInativacao = dataInativacao;
	}

	/**
	 * Construtor do vo.
	 *
	 * @param frotaPontoVenda Entidade utilizada na criação do vo.
	 */
	public FrotaPostoVo(FrotaPontoVenda frotaPontoVenda){
		this(frotaPontoVenda.getPontoVenda().getComponenteAreaAbastecimento().getCodigoCorporativo(),frotaPontoVenda.getFrota().getId(),null);
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

	public Date getDataInativacao() {
		return dataInativacao;
	}

	public void setDataInativacao(Date dataInativacao) {
		this.dataInativacao = dataInativacao;
	}
}
