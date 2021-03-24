package ipp.aci.boleia.dominio.vo.conectcar;

import java.util.List;

/**
 * Vo para obtenção da resposta das requisições da ConectCar
 */
public class ConectCarConsultaPedidoTagResponseVo {

	private Boolean sucesso;
	private String statusPedido;
	private List<HistoricoStatusPedidoParceiroVo> historicoStatusPedido;
	private List<ConectCarNotificacoesResponseVo> notificacoes;

	/**
	 * Construtor default da classe.
	 */
	public ConectCarConsultaPedidoTagResponseVo() {

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

	public String getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(String statusPedido) {
		this.statusPedido = statusPedido;
	}

	public List<ConectCarNotificacoesResponseVo> getNotificacoes() {
		return notificacoes;
	}

	public void setNotificacoes(List<ConectCarNotificacoesResponseVo> notificacoes) {
		this.notificacoes = notificacoes;
	}

	public List<HistoricoStatusPedidoParceiroVo> getHistoricoStatusPedido() {
		return historicoStatusPedido;
	}

	public void setHistoricoStatusPedido(List<HistoricoStatusPedidoParceiroVo> historicoStatusPedido) {
		this.historicoStatusPedido = historicoStatusPedido;
	}

}
