package ipp.aci.boleia.dominio.vo;

/**
 * Classe utilizada para o retorno após copia de uma campanha
 */
public class SucessoCopiaCampanhaVo {

	private Long idDaCopia;

	private String mensagem;

	public  SucessoCopiaCampanhaVo() {
		// serializacao json
	}

	/**
	 * Construtor
	 * @param id O id da copia da campanha
	 * @param mensagem A mensagem a ser enviada ao cliente
	 */
	public SucessoCopiaCampanhaVo(Long id, String mensagem) {
		this.idDaCopia = id;
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}


	public Long getIdDaCopia() {
		return idDaCopia;
	}

	public void setIdDaCopia(Long idDaCopia) {
		this.idDaCopia = idDaCopia;
	}
}
