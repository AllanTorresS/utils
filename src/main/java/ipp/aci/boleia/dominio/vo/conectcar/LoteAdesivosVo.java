package ipp.aci.boleia.dominio.vo.conectcar;

/**
 * Vo de integração dos lotes de adesivos com a conectcar 
 *
 */
public class LoteAdesivosVo {

	private String codigoInternoParceiro;
	private String[] placas;
	private String motivo;
	private String[] servicosBloqueio;

	public String getCodigoInternoParceiro() {
		return codigoInternoParceiro;
	}

	public void setCodigoInternoParceiro(String codigoInternoParceiro) {
		this.codigoInternoParceiro = codigoInternoParceiro;
	}

	public String[] getPlacas() {
		return placas;
	}

	public void setPlacas(String[] placas) {
		for(int i = 0; i < placas.length; i++) {
			if(placas[i] != null && !placas[i].contains("-") && placas[i].length() > 3) {
				placas[i] = placas[i].substring(0, 3) + "-" + placas[i].substring(3);
			} 
		}
		this.placas = placas;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String[] getServicosBloqueio() {
		return servicosBloqueio;
	}

	public void setServicosBloqueio(String[] servicosBloqueio) {
		this.servicosBloqueio = servicosBloqueio;
	}

}