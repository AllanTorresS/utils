package ipp.aci.boleia.dominio.vo.apco;

import ipp.aci.boleia.dominio.enums.StatusFrota;
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
	 * Construtor da frota cliente que realizou transações
	 * em um dado intervalo de exportação APCO.
	 * @param noSeqCliProFrota o código identificador da frota
	 * @param codigoPessoa o cnpj da frota a ser formatado para envio
	 * @param nomePessoa o nome(razão social) da frota cliente
	 * @param statusFrota o status da frota
	 * @param dataInativacao a data de inativação da frota
	 */
	public ClienteProFrotaVo(Long noSeqCliProFrota, Long codigoPessoa, String nomePessoa, Integer statusFrota, Date dataInativacao){
		this.noSeqCliProFrota = noSeqCliProFrota;
		this.codigoTipoPessoa = TipoPessoa.PESSOA_JURIDICA.valorIpiranga();
		this.codigoPessoa = codigoPessoa.toString().substring(0,codigoPessoa.toString().length()-2);
		this.codigoDigitoVerificador = codigoPessoa.toString().substring(codigoPessoa.toString().length()-2,codigoPessoa.toString().length());
		this.nomePessoa = nomePessoa;
		if(StatusFrota.INATIVO.getValue().equals(statusFrota)){
			this.dataInativacao = dataInativacao;
		}
		else{
			this.dataInativacao = null;
		}
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
