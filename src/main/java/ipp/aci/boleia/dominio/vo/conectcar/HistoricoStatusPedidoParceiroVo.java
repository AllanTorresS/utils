package ipp.aci.boleia.dominio.vo.conectcar;

/**
 * Vo para obtenção da resposta do histórico de pedido de tags. 
 */
public class HistoricoStatusPedidoParceiroVo {

	private String dataReferencia;
	private String observacao;

	public String getDataReferencia() {
		return dataReferencia;
	}

	public void setDataReferencia(String dataReferencia) {
		this.dataReferencia = dataReferencia;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}