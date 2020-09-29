package ipp.aci.boleia.dominio.vo.conectcar;

import java.util.List;

/**
 * Vo para obtenção da resposta das requisições da ConectCar
 */
public class ConectCarPedidoResponseVo {

	private Boolean sucesso;
	private Integer pedidoParceiroId;
	private List<ConectCarNotificacoesResponseVo> notificacoes;

	/**
	 * Construtor default da classe.
	 */
	public ConectCarPedidoResponseVo() {

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

	public Integer getPedidoParceiroId() {
		return pedidoParceiroId;
	}

	public void setPedidoParceiroId(Integer pedidoParceiroId) {
		this.pedidoParceiroId = pedidoParceiroId;
	}

	public List<ConectCarNotificacoesResponseVo> getNotificacoes() {
		return notificacoes;
	}

	public void setNotificacoes(List<ConectCarNotificacoesResponseVo> notificacoes) {
		this.notificacoes = notificacoes;
	}

}
