package ipp.aci.boleia.dominio.vo.conectcar;

import java.util.List;

/**
 * Vo para obtenção da resposta das requisições da ConectCar
 */
public class ConectCarDesvincularResponseVo {

	private Boolean sucesso;
	private ConectCarDadosDesvincularResponseVo dados;
	private List<ConectCarNotificacoesResponseVo> notificacoes;

	/**
	 * Construtor default da classe.
	 */
	public ConectCarDesvincularResponseVo() {

	}

	public String obtemErroPorPlaca(String placa) {
		
		if (placa == null)
			return null;			
		
			if (dados.getErros() != null && dados.getErros().size() > 0) {
				for (ConectCarErroResponseVo voErro : dados.getErros()) {
					return voErro.getMensagem();
				}
			}
		
		return null;
		
	}
	

	public String buscarPrimeiroErro() {
		if (dados != null) {

			if (dados.getErros() != null && dados.getErros().size() > 0) {
				for (ConectCarErroResponseVo voErro : dados.getErros()) {
					return voErro.getMensagem();
				}
			}
		}
		return null;
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

	public ConectCarDadosDesvincularResponseVo getDados() {
		return dados;
	}
	public void setDados(ConectCarDadosDesvincularResponseVo dados) {
		this.dados = dados;
	}
	public List<ConectCarNotificacoesResponseVo> getNotificacoes() {
		return notificacoes;
	}

	public void setNotificacoes(List<ConectCarNotificacoesResponseVo> notificacoes) {
		this.notificacoes = notificacoes;
	}

}
