package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.util.UtilitarioCoordenadasGeograficas;

/**
 * Filtro para pesquisa de pontos de venda
 */
public class FiltroPesquisaLocalizacaoVo {

	private Double latitudeInicial;
	private Double latitudeFinal;
	private Double longitudeInicial;
	private Double longitudeFinal;

	public FiltroPesquisaLocalizacaoVo() {
		// serializacao json
	}

	/**
	 * Pesquisa a localiacao geografica de um ponto
	 * @param ponto O ponto de referencia
	 * @param distancia A distancia
	 */
	public FiltroPesquisaLocalizacaoVo(CoordenadaGeograficaVo ponto, double distancia) {
		double distanciaLatitude = UtilitarioCoordenadasGeograficas.converterDistanciaLatitude(distancia);
		double distanciaLongitude = UtilitarioCoordenadasGeograficas.converterDistanciaLongitude(distancia, ponto.getLatitude());
		this.latitudeInicial = ponto.getLatitude()  - distanciaLatitude;
		this.latitudeFinal = ponto.getLatitude()  + distanciaLatitude;
		this.longitudeInicial = ponto.getLongitude() - distanciaLongitude;
		this.longitudeFinal = ponto.getLongitude() + distanciaLongitude;
	}

	public Double getLatitudeInicial() {
		return latitudeInicial;
	}

	public Double getLatitudeFinal() {
		return latitudeFinal;
	}

	public Double getLongitudeInicial() {
		return longitudeInicial;
	}

	public Double getLongitudeFinal() {
		return longitudeFinal;
	}

	public void setLatitudeInicial(Double latitudeInicial) {
		this.latitudeInicial = latitudeInicial;
	}

	public void setLatitudeFinal(Double latitudeFinal) {
		this.latitudeFinal = latitudeFinal;
	}

	public void setLongitudeInicial(Double longitudeInicial) {
		this.longitudeInicial = longitudeInicial;
	}

	public void setLongitudeFinal(Double longitudeFinal) {
		this.longitudeFinal = longitudeFinal;
	}

}
