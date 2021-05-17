package ipp.aci.boleia.dominio.vo.apco;

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
	 * Construtor do VO de vínculos.
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
	 * Construtor do VO de vínculos considerando que o conceitode inativação de vínculos atualmente
	 * não existe no Profrotas.
	 *
	 * @param codigoComponente o código identificador corporativo do ponto de venda
	 * @param noSeqCliProFrota o código identificador da frota
	 */
	public FrotaPostoVo(Long codigoComponente, Long noSeqCliProFrota){
		this(codigoComponente,noSeqCliProFrota,null);
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
