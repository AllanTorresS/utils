package ipp.aci.boleia.util;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * Utilitario para calculo de datas
 */
public final class UtilitarioConsolidado {

	/**
	 * Impede a instanciacao e a heranca
	 */
	private UtilitarioConsolidado() {
		// Impede a instanciacao e a heranca
	}

	/**
	 * Calcular a data de inicio a partir da data de criação
	 * @param dataCriacao data de criação da transação
	 * @param diasCiclo o tamanho do ciclo
	 * @return A data de inicio do ciclo
	 */
	public static Date calcularDataInicio(Date dataCriacao, int diasCiclo) {
		Date dataInicio = UtilitarioCalculoData.obterPrimeiroDiaMes(dataCriacao);
		while(DateUtils.addDays(dataInicio, diasCiclo).compareTo(dataCriacao)<0){
			dataInicio = DateUtils.addDays(dataInicio, diasCiclo);
		}
		return dataInicio;
	}

	/**
	 * Calcular a data final a partir da data de criação
	 * @param dataCriacao data de criação da transação
	 * @param diasCiclo o tamanho do ciclo
	 * @return A data final do ciclo
	 */
	public static Date calcularDataFim(Date dataCriacao, int diasCiclo) {
		final int diasCicloAplicado = diasCiclo - 1;
		if (diasCicloAplicado <= 0) {
			return UtilitarioCalculoData.obterUltimoInstanteDia(dataCriacao);
		}
		Date inicio =  UtilitarioCalculoData.obterUltimoInstanteDia(calcularDataInicio(dataCriacao, diasCiclo));
		return DateUtils.addDays(inicio, diasCicloAplicado);
	}
}
