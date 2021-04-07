package ipp.aci.boleia.dominio.vo.conectcar;

import java.util.List;

/**
 * Vo para obtenção da resposta das requisições da ConectCar
 */
public class ConectCarBloqueioResponseVo {

	private Boolean sucesso;
	private List<ConectCarDadosResponseVo> dados;
	private List<ConectCarNotificacoesResponseVo> notificacoes;

	/**
	 * Construtor default da classe.
	 */
	public ConectCarBloqueioResponseVo() {

	}
	
	//Obtem o código de erro
	public ConectCarDadosResponseVo obtemDadoPorPlaca(String placa) {
		
		if (placa == null)
			return null;			
		
		for (ConectCarDadosResponseVo vo : dados) {
			if (vo.getPlaca() != null && vo.getPlaca().replaceAll("-", "").equals(placa.replaceAll("-", ""))) {
				return vo;
			}
		}
		
		return null;		
	}
	
	public String obtemErroPorPlaca(String placa) {
		
		if (placa == null)
			return null;			
		
		for (ConectCarDadosResponseVo vo : dados) {
			if (vo.getPlaca() != null && vo.getPlaca().replaceAll("-", "").equals(placa.replaceAll("-", "")) && vo.getErros() != null && vo.getErros().size() > 0) {
				for (ConectCarErroResponseVo voErro : vo.getErros()) {
					return voErro.getMensagem();
				}
			}
		}
		
		return null;
		
	}
	

	public String buscarPrimeiroErro() {
		if (dados != null && dados.size() > 0) {
			for (ConectCarDadosResponseVo vo : dados) {
				if (vo.getErros() != null && vo.getErros().size() > 0) {
					for (ConectCarErroResponseVo voErro : vo.getErros()) {
						return voErro.getMensagem();
					}
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

	public List<ConectCarDadosResponseVo> getDados() {
		return dados;
	}

	public void setDados(List<ConectCarDadosResponseVo> dados) {
		this.dados = dados;
	}

	public List<ConectCarNotificacoesResponseVo> getNotificacoes() {
		return notificacoes;
	}

	public void setNotificacoes(List<ConectCarNotificacoesResponseVo> notificacoes) {
		this.notificacoes = notificacoes;
	}

}
