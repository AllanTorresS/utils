package ipp.aci.boleia.dominio.vo.conectcar;

/**
 * Vo para obtenção da resposta das requisições da ConectCar
 */
public class ConectCarNotificacoesResponseVo {

	private String codigo;
	private String mensagem;

	/**
	 * Construtor default da classe.
	 */
	public ConectCarNotificacoesResponseVo() {
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
