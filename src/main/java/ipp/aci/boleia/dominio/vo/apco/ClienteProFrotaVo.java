package ipp.aci.boleia.dominio.vo.apco;
import ipp.aci.boleia.dominio.enums.TipoPessoa;

import java.util.Date;

/**
 * Representa uma frota cliente que realizou transações
 * em um dado intervalo de exportação APCO.
 */
public class ClienteProFrotaVo {

	private Long noSeqCliProFrota;

	private String codigoTipoPessoa;

	private String codigoPessoa;

	private String codigoDigitoVerificador;

	private String nomePessoa;

	private Date dataInativacao;

	/**
	 * Construtor Default
	 */
	public ClienteProFrotaVo(){
		// serializacao json
	}

	/**
	 * Construtor dos dados básicos da frota cliente que realizou transações
	 * em um dado intervalo de exportação APCO.
	 * @param noSeqCliProFrota o código identificador da frota
	 * @param codigoPessoa o cnpj da frota a ser formatado para envio
	 * @param nomePessoa o nome(razão social) da frota cliente
	 */
	public ClienteProFrotaVo(Long noSeqCliProFrota, Long codigoPessoa, String nomePessoa){
		this.noSeqCliProFrota = noSeqCliProFrota;
		this.codigoTipoPessoa = TipoPessoa.PESSOA_JURIDICA.valorIpiranga();
		this.codigoPessoa = codigoPessoa.toString().substring(0,codigoPessoa.toString().length()-2);
		this.codigoDigitoVerificador = codigoPessoa.toString().substring(codigoPessoa.toString().length()-2,codigoPessoa.toString().length());
		this.nomePessoa = nomePessoa;
	}

	/**
	 * Construtor da frota cliente que foi inativada, porém, realizou transações
	 * em um dado intervalo de exportação APCO.
	 * @param noSeqCliProFrota o código identificador da frota
	 * @param codigoPessoa o cnpj da frota a ser formatado para envio
	 * @param nomePessoa o nome(razão social) da frota cliente
	 * @param dataInativacao a data de inativação da frota
	 */
	public ClienteProFrotaVo(Long noSeqCliProFrota, Long codigoPessoa, String nomePessoa, Date dataInativacao){
		this(noSeqCliProFrota, codigoPessoa, nomePessoa);
		this.dataInativacao = dataInativacao;
	}

	public Long getNoSeqCliProFrota() {
		return noSeqCliProFrota;
	}

	public void setNoSeqCliProFrota(Long noSeqCliProFrota) {
		this.noSeqCliProFrota = noSeqCliProFrota;
	}

	public String getCodigoTipoPessoa() {
		return codigoTipoPessoa;
	}

	public void setCodigoTipoPessoa(String codigoTipoPessoa) {
		this.codigoTipoPessoa = codigoTipoPessoa;
	}

	public String getCodigoDigitoVerificador() {
		return codigoDigitoVerificador;
	}

	public void setCodigoDigitoVerificador(String codigoDigitoVerificador) {
		this.codigoDigitoVerificador = codigoDigitoVerificador;
	}

	public String getCodigoPessoa() {
		return codigoPessoa;
	}

	public void setCodigoPessoa(String codigoPessoa) {
		this.codigoPessoa = codigoPessoa;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public Date getDataInativacao() {
		return dataInativacao;
	}

	public void setDataInativacao(Date dataInativacao) {
		this.dataInativacao = dataInativacao;
	}
}
