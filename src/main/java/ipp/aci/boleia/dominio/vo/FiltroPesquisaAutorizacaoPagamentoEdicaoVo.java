package ipp.aci.boleia.dominio.vo;


import ipp.aci.boleia.dominio.enums.StatusEdicao;
import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;

/**
 * Filtro para pesquisa de AutorizacaoPagamentoEdicao (histórico de edições de abastecimento).
 */
public class FiltroPesquisaAutorizacaoPagamentoEdicaoVo extends BaseFiltroPaginado {

	private Long autorizacaoPagamentoId;
	private StatusEdicao status;

	/**
	 * Construtor do FiltroPesquisaAutorizacaoPagamentoEdicaoVo
	 */
	public FiltroPesquisaAutorizacaoPagamentoEdicaoVo(){
		super();
	}

	/**
	 * Construtor do FiltroPesquisaAutorizacaoPagamentoEdicaoVo
	 * @param autorizacaoPagamentoId Id do abastecimento
	 * @param statusEdicao O status da edição
	 * @param parametroOrdenacaoColunas O parametro de ordenação
	 */
	public FiltroPesquisaAutorizacaoPagamentoEdicaoVo(Long autorizacaoPagamentoId, StatusEdicao statusEdicao, ParametroOrdenacaoColuna[] parametroOrdenacaoColunas){
		super(new InformacaoPaginacao(parametroOrdenacaoColunas));
		this.autorizacaoPagamentoId = autorizacaoPagamentoId;
		this.status = statusEdicao;
	}


	public Long getAutorizacaoPagamentoId() {
		return autorizacaoPagamentoId;
	}

	public void setAutorizacaoPagamentoId(Long autorizacaoPagamentoId) {
		this.autorizacaoPagamentoId = autorizacaoPagamentoId;
	}

	public StatusEdicao getStatus() {
		return status;
	}

	public void setStatus(StatusEdicao status) {
		this.status = status;
	}
}
