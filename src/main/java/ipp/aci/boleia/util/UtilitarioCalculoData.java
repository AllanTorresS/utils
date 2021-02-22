package ipp.aci.boleia.util;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Utilitario para calculo de datas
 */
public final class UtilitarioCalculoData {

	/**
	 * Impede a instanciacao e a heranca
	 */
	private UtilitarioCalculoData() {
		// Impede a instanciacao e a heranca
	}

	/**
	 * Adiciona meses a data informada
	 *
	 * @param data  A data base
	 * @param meses Os meses a adicionar
	 * @return A data resultante da adicao
	 */
	public static Date adicionarMesesData(Date data, Integer meses) {
		return adicionarTempoData(data, meses, Calendar.MONTH);
	}

	/**
	 * Adiciona dias a data informada
	 *
	 * @param data  A data base
	 * @param dias  Os dias a adicionar
	 * @return A data resultante da adicao
	 */
	public static Date adicionarDiasData(Date data, Integer dias) {
		return adicionarTempoData(data, dias, Calendar.DAY_OF_MONTH);
	}

	/**
	 * Adiciona horas a data informada
	 *
	 * @param data  A data base
	 * @param horas  As horas a adicionar
	 * @return A data resultante da adicao
	 */
	public static Date adicionarHorasData(Date data, Integer horas) {
		return adicionarTempoData(data, horas, Calendar.HOUR_OF_DAY);
	}

	/**
	 * Obtem um campo da data, de acordo com o numero do campo no Calendar
	 *
	 * @param data  A data base
	 * @param campoCalendar  Calendar.DAY, Calendar.MONTH, Calendar.YEAR
	 * @return O valor do campo da data
	 */
	public static Integer obterCampoData(Date data, Integer campoCalendar) {
		if(data==null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		return c.get(campoCalendar);
	}

	/**
	 * Obtem o primeiro instante do dia da data parametrizada
	 * @param data data a obter o primeiro instante
	 * @return primeiro instante do dia da data
	 */
	public static Date obterPrimeiroInstanteDia(Date data) {
		if(data==null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		c.set(Calendar.MILLISECOND,0);

		return c.getTime();
	}

	/**
	 * Obtem o ultimo instante do dia da data parametrizada
	 * @param data data a obter o ultimo instante
	 * @return ultimo instante do dia da data
	 */
	public static Date obterUltimoInstanteDia(Date data) {
		if(data==null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);

		return c.getTime();
	}

	/**
	 * Configura a data de vigência automática de uma negociação pendente.
	 * @param data data a ser configurada
	 * @return data de vigência automática
	 */
	public static  Date configurarDataComHorarioDeVigencia (Date data) {
		if(data==null) {
			return null;
		}

		//São usados valores imediatamente anteriores ao meio dia para evitar concorrência 
		//com horários agendados
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(Calendar.HOUR_OF_DAY, 11);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);

		return c.getTime();
	}

	/**
	 * Adiciona minutos a data informada
	 *
	 * @param data  A data base
	 * @param minutos  Os minutos a adicionar
	 * @return A data resultante da operacao
	 */
    public static Date adicionarMinutosData(Date data, Integer minutos) {
		return adicionarTempoData(data, minutos, Calendar.MINUTE);
    }

	/**
	 * Adiciona minutos a data informada
	 *
	 * @param data  A data base
	 * @param segundos Os segundos a adicionar
	 * @return A data resultante da operacao
	 */
	public static Date adicionarSegundosData(Date data, Integer segundos) {
		return adicionarTempoData(data, segundos, Calendar.SECOND);
	}

	/**
	 * Adiciona um montante de tempo a data informada
	 *
	 * @param data  A data base
	 * @param tempo  Os minutos a adicionar
	 * @param unidadeTempo A unidade de tempo desejada
	 * @return A data resultante da operacao
	 */
	private static Date adicionarTempoData(Date data, int tempo, int unidadeTempo) {
		if (data == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.add(unidadeTempo, tempo);
		return c.getTime();
	}

	/**
	 * Define mes da data informada
	 *
	 * @param data  A data informada
	 * @param mes Mes a definir
	 * @return A data resultante da operacao
	 */
	public static Date definirMesData(Date data, Integer mes) {
		return definirTempoData(data, mes, Calendar.MONTH);
	}

	/**
	 * Define uma unidade de tempo a data informada
	 *
	 * @param data  A data informada
	 * @param valorCampoData O valor do campo da data definido
	 * @param unidadeTempo A unidade de tempo desejada
	 * @return A data resultante da operacao
	 */
	private static Date definirTempoData(Date data, int valorCampoData, int unidadeTempo) {
		if (data == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(unidadeTempo, valorCampoData);
		return c.getTime();
	}

	/**
	 * Obtem uma data a partir dos parâmetros passados
	 *
	 * @param dia O número do dia do mês
	 * @param mes O número do mês
	 * @param ano O ano
	 * @return Um objeto do tipo data
	 */
	public static Date obterData(Integer dia, Integer mes, Integer ano) {

		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH,dia);
		c.set(Calendar.MONTH,mes);
		c.set(Calendar.YEAR,ano);
		c.set(Calendar.HOUR_OF_DAY,c.getActualMinimum(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE,c.getActualMinimum(Calendar.MINUTE));
		c.set(Calendar.SECOND,c.getActualMinimum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND,c.getActualMinimum(Calendar.MILLISECOND));

		return c.getTime();
	}

	/**
	 * Calcula o próximo dia útil a partir de uma data
	 *
	 * @param data data de referência
	 * @return próximo dia útil
	 */
	public static Date obterProximoDiaUtilSemFeriado(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);

		int dow = c.get(Calendar.DAY_OF_WEEK);
		int numeroDiasParaDiaUtil = 0;
		if (dow == Calendar.SUNDAY) {
			numeroDiasParaDiaUtil = 1;
		} else if (dow == Calendar.SATURDAY) {
			numeroDiasParaDiaUtil = 2;
		}
		return adicionarDiasData(data, numeroDiasParaDiaUtil);
	}
	/**
	 * Obtem o primeiro dia do mes da data parametrizada
	 *
	 * @param data data a obter o ultimo dia
	 * @return o primeiro dia do mes da data
	 */
	public static Date obterPrimeiroDiaMes(Date data) {
		if (data == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY,c.getActualMinimum(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE,c.getActualMinimum(Calendar.MINUTE));
		c.set(Calendar.SECOND,c.getActualMinimum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND,c.getActualMinimum(Calendar.MILLISECOND));

		return c.getTime();
	}

	/**
	 * Obtem o primeiro dia do próximo mês
	 *
	 * @return o primeiro dia do próximo mês
	 */
	public static Date obterPrimeiroDiaProximoMes() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY,c.getActualMinimum(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE,c.getActualMinimum(Calendar.MINUTE));
		c.set(Calendar.SECOND,c.getActualMinimum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND,c.getActualMinimum(Calendar.MILLISECOND));
		c.add(Calendar.MONTH, 1);
		return c.getTime();
	}

	/**
	 * Obtem o ultimo dia do mes da data parametrizada
	 *
	 * @param data data a obter o ultimo dia
	 * @return o ultimo dia do mes da data
	 */
	public static Date obterUltimoDiaMes(Date data) {
		if (data == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY,c.getActualMaximum(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE,c.getActualMaximum(Calendar.MINUTE));
		c.set(Calendar.SECOND,c.getActualMaximum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND,c.getActualMaximum(Calendar.MILLISECOND));

		return c.getTime();
	}


	/**
	 * Obtem o ultimo campo conforme data e campo parametrizados
	 *
	 * @param data a obter o ultimo valor referente ao campo
	 * @param campoCalendar campo desejado
	 * @return o ultimo valor do campo em int
	 */
	public static int obterUltimoValorCampo(Date data, Integer campoCalendar) {
		if (data == null) {
			return 0;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		return c.getActualMaximum(campoCalendar);
	}


	/**
	 * Obtem o primeiro dia do mes da data parametrizada
	 *
	 * @param anoMes data a obter o ultimo dia
	 * @return o primeiro dia do mes da data
	 */
	public static Date obterPrimeiroDiaMes(YearMonth anoMes) {
		if (anoMes == null) {
			return null;
		}

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, anoMes.getYear());
		c.set(Calendar.MONTH, anoMes.getMonthValue() - 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY,c.getActualMinimum(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE,c.getActualMinimum(Calendar.MINUTE));
		c.set(Calendar.SECOND,c.getActualMinimum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND,c.getActualMinimum(Calendar.MILLISECOND));

		return c.getTime();
	}

	/**
	 * Obtem o ultimo dia do mes da data parametrizada
	 *
	 * @param anoMes data a obter o ultimo dia
	 * @return o ultimo dia do mes da data
	 */
	public static Date obterUltimoDiaMes(YearMonth anoMes) {
		if (anoMes == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, anoMes.getYear());
		c.set(Calendar.MONTH, anoMes.getMonthValue() - 1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY,c.getActualMaximum(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE,c.getActualMaximum(Calendar.MINUTE));
		c.set(Calendar.SECOND,c.getActualMaximum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND,c.getActualMaximum(Calendar.MILLISECOND));

		return c.getTime();
	}

	/**
	 * Retorna true caso a data informada pertenca ao mes corrente
	 *
	 * @param dataAtual data atual
	 * @param data a data em questao
	 * @return true caso a data informada pertenca ao mes corrente
	 */
	public static boolean isMesCorrente(Date dataAtual, Date data) {
		Calendar corrente = Calendar.getInstance();
		Calendar informado = Calendar.getInstance();
		informado.setTime(data);
		corrente.setTime(dataAtual);
		return  corrente.get(Calendar.MONTH) == informado.get(Calendar.MONTH);
	}

	/**
	 * Retorna true caso a data informada pertenca ao mes e ano corrente corrente
	 *
	 * @param dataAtual data atual
	 * @param data a data em questao
	 * @return true caso a data informada pertenca ao mes e ano corrente
	 */
	public static boolean isMesEAnoCorrente(Date dataAtual, Date data) {
		Calendar corrente = Calendar.getInstance();
		Calendar informado = Calendar.getInstance();
		informado.setTime(data);
		corrente.setTime(dataAtual);
		return  corrente.get(Calendar.MONTH) == informado.get(Calendar.MONTH) && corrente.get(Calendar.YEAR) == informado.get(Calendar.YEAR);
	}

	/**
	 * Verifica se a data informada esta no mesmo dia da data de hoje
	 *
	 * @param dataAtual data atual
	 * @param data data a verificar
	 * @return True para mesmo dia
	 */
	public static boolean isHoje(Date dataAtual, Date data) {
		return obterPrimeiroInstanteDia(dataAtual).compareTo(obterPrimeiroInstanteDia(data)) == 0;
	}

	/**
	 * Verifica se a data informada esta no dia anterior da data de hoje
	 * @param dataAtual data atual
	 * @param data data a verificar
	 * @return True para dia anterior
	 */
	public static boolean isOntem(Date dataAtual, Date data) {
		return obterPrimeiroInstanteDia(dataAtual).compareTo(adicionarDiasData(obterPrimeiroInstanteDia(data),1)) == 0;
	}

	/**
	 * Verifica se a data informada é igual ou posterior à data de hoje
	 * @param dataAtual data atual
	 * @param data data a verificar
	 * @return True para data igual ou posterior à atual
	 */
	public static boolean isHojeOuPosterior(Date dataAtual, Date data){
		return isHoje(dataAtual, data) || obterPrimeiroInstanteDia(data).after(obterPrimeiroInstanteDia(dataAtual));
	}

	/**
	 * Verifica se a data informada é igual ou anterior à data de ontem
	 * @param dataOntem data de ontem
	 * @param data data a verificar
	 * @return True para data igual ou anterior à de ontem
	 */
	public static boolean isOntemOuAnterior(Date dataOntem, Date data){
		return obterPrimeiroInstanteDia(dataOntem).compareTo(obterPrimeiroInstanteDia(data)) == 0 || obterPrimeiroInstanteDia(data).before(obterPrimeiroInstanteDia(dataOntem));
	}

	/**
	 * Calcula a diferença em dias entre duas datas
	 * @param dataInicial a data inicial
	 * @param dataFinal a data final
	 * @return A diferença em dias corridos entre a data inicial e a data final
	 */
	public static long diferencaEmDias(Date dataInicial, Date dataFinal)
	{
		LocalDate dtIni = dataInicial.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate dtFim = dataFinal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return DAYS.between(dtIni, dtFim);
	}

	/**
	 * Calcula a diferença em minutos entre duas datas
	 * @param dataInicial A data inicial
	 * @param dataFinal A data final
	 * @return A diferença em minutos entre as duas datas informadas
	 */
	public static long diferencaEmMinutos(Date dataInicial, Date dataFinal)
	{
		Instant dtIni = dataInicial.toInstant();
		Instant dtFim = dataFinal.toInstant();
		return Duration.between(dtIni, dtFim).toMinutes();
	}

    /**
     * Informa se uma data pertence ao intervalo fechado entre duas datas
     *
     * @param data        A data em questão
     * @param dataInicial A data de início do intervalo
     * @param dataFinal   A data de fim do intervalo
     * @return Se a data está contida no intervalo
     */
    public static Boolean dataEmIntervalo(Date data, Date dataInicial, Date dataFinal) {
    	dataInicial = obterPrimeiroInstanteDia(dataInicial);
    	dataFinal = obterUltimoInstanteDia(dataFinal);
        return dataInicial.compareTo(data) * data.compareTo(dataFinal) >= 0;
    }

	/**
	 * Verifica se a data fornecida é depois de hoje
	 * @param dataAtual data atual
	 * @param data a data a ser verificada
	 * @return se a data é ou não depois de hoje
	 */
	public static Boolean isPosterior(Date dataAtual, Date data) {
    	return obterPrimeiroInstanteDia(data).after(obterPrimeiroInstanteDia(dataAtual));
	}
}
