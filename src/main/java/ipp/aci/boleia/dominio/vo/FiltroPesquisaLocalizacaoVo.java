package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.enums.PerfilPontoDeVenda;
import ipp.aci.boleia.util.UtilitarioCoordenadasGeograficas;

import java.math.BigDecimal;
import java.util.List;

/**
 * Filtro para pesquisa de pontos de venda
 */
public class FiltroPesquisaLocalizacaoVo {

	private Double latitudeInicial;
	private Double latitudeFinal;
	private Double longitudeInicial;
	private Double longitudeFinal;
	private List<List<CoordenadaVo>> filtrosCoordenadas;
	private BigDecimal margemGrausFiltroCoordenadas;
	private PerfilPontoDeVenda perfilPontoDeVenda;

	public FiltroPesquisaLocalizacaoVo() {
		// serializacao json
	}

	/**
	 * Pesquisa a localiacao geografica de um ponto
	 * @param ponto O ponto de referencia
	 * @param distancia A distancia
	 */
	public FiltroPesquisaLocalizacaoVo(CoordenadaGeograficaVo ponto, double distancia, PerfilPontoDeVenda perfilPontoDeVenda) {
		double distanciaLatitude = UtilitarioCoordenadasGeograficas.converterDistanciaLatitude(distancia);
		double distanciaLongitude = UtilitarioCoordenadasGeograficas.converterDistanciaLongitude(distancia, ponto.getLatitude());
		this.latitudeInicial = ponto.getLatitude()  - distanciaLatitude;
		this.latitudeFinal = ponto.getLatitude()  + distanciaLatitude;
		this.longitudeInicial = ponto.getLongitude() - distanciaLongitude;
		this.longitudeFinal = ponto.getLongitude() + distanciaLongitude;
		this.perfilPontoDeVenda = perfilPontoDeVenda;
	}

	/**
	 * Pesquisa a localizacao geografica de uma rota, dado um grupo de pontos
	 * @param filtrosCoordenadas Os filtros de coordenadas
	 * @param margemGrausFiltroCoordenadas A margem em graus do filtro de coordenadas
	 */
	public FiltroPesquisaLocalizacaoVo(List<List<CoordenadaVo>> filtrosCoordenadas, BigDecimal margemGrausFiltroCoordenadas, PerfilPontoDeVenda perfilPontoDeVenda) {
		this.filtrosCoordenadas = filtrosCoordenadas;
		this.margemGrausFiltroCoordenadas = margemGrausFiltroCoordenadas;
		this.perfilPontoDeVenda = perfilPontoDeVenda;
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

	public List<List<CoordenadaVo>> getFiltrosCoordenadas() {
		return filtrosCoordenadas;
	}

	public void setFiltrosCoordenadas(List<List<CoordenadaVo>> filtrosCoordenadas) {
		this.filtrosCoordenadas = filtrosCoordenadas;
	}

	public BigDecimal getMargemGrausFiltroCoordenadas() {
		return margemGrausFiltroCoordenadas;
	}

	public void setMargemGrausFiltroCoordenadas(BigDecimal margemGrausFiltroCoordenadas) {
		this.margemGrausFiltroCoordenadas = margemGrausFiltroCoordenadas;
	}

	public PerfilPontoDeVenda getPerfilPontoDeVenda() {
		return perfilPontoDeVenda;
	}

	public void setPerfilPontoDeVenda(PerfilPontoDeVenda perfilPontoDeVenda) {
		this.perfilPontoDeVenda = perfilPontoDeVenda;
	}
}
