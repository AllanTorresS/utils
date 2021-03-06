package ipp.aci.boleia.dominio.vo.conectcar;

import java.util.List;

/**
 * Vo para obtenção da resposta das requisições da ConectCar
 */
public class ConectCarBloquearContratoResponseVo {

	private Boolean sucesso;
	private List<ConectCarNotificacoesResponseVo> notificacoes;

	/**
	 * Construtor default da classe.
	 */
	public ConectCarBloquearContratoResponseVo() {

	}
		
	public String buscarPrimeiraMensagem() {
		if (notificacoes != null && notificacoes.size() > 0) {
			for (ConectCarNotificacoesResponseVo vo : notificacoes) {
				return vo.getMensagem();
			}
		}

		return null;

	}

	public Boolean getSucesso() {
		return sucesso;
	}

	public void setSucesso(Boolean sucesso) {
		this.sucesso = sucesso;
	}

	public List<ConectCarNotificacoesResponseVo> getNotificacoes() {
		return notificacoes;
	}

	public void setNotificacoes(List<ConectCarNotificacoesResponseVo> notificacoes) {
		this.notificacoes = notificacoes;
	}

}
