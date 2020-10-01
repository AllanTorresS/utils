package ipp.aci.boleia.dominio.vo.conectcar;

/**
 * Classe utilizada para o retorno após a integração com o SalesForce
 */
public class SucessoIntegacaoSalesforceVo {

	private Boolean sucesso;
	private String mensagem;

	public Boolean getSucesso() {
		return sucesso;
	}

	public void setSucesso(Boolean sucesso) {
		this.sucesso = sucesso;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}