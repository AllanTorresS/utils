package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAmbienteDados;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Respositorio para obtencao de dados do ambiente Oracle
 */
@Repository
public class OracleAmbienteDados implements IAmbienteDados {

	private static final String BUSCA_DATA = "SELECT SYSDATE FROM DUAL";
	private static final String BUSCA_TIMEZONE = "SELECT DBTIMEZONE FROM DUAL";
	private static final int TEMPO_MAXIMO_CACHE = 60000;
	private static final Lock LOCK = new ReentrantLock();

	private Date ultimaDataObtidaEm;
	private Timestamp ultimaDataObtida;

	@PersistenceContext
	private EntityManager gerenciadorDeEntidade;

	@Override
	public Date obterDataAmbiente() {
		long tempoDecorrido = calcularTempoDecorrido();
		if(tempoDecorrido > TEMPO_MAXIMO_CACHE) {
			LOCK.lock();
			try {
				tempoDecorrido = calcularTempoDecorrido();
				if(tempoDecorrido > TEMPO_MAXIMO_CACHE) {
					ultimaDataObtidaEm = new Date();
					ultimaDataObtida = (Timestamp) gerenciadorDeEntidade.createNativeQuery(BUSCA_DATA).getSingleResult();
					tempoDecorrido = 0L;
				}
			} finally {
				LOCK.unlock();
			}
		}
		return new Timestamp(ultimaDataObtida.getTime() + tempoDecorrido);
	}

	/**
	 * Calcula o tempo decorrido, em milisegundos, desde a ultima busca de data no banco de dados.
	 * Caso nenhuma busca tenha sido feita ate entao, retorna Long.MAX_VALUE
	 *
	 * @return O tempo decorrido, em milisegundos, desde a ultima busca de data no banco de dados ou Long.MAX_VALUE
	 */
	private long calcularTempoDecorrido() {
		return ultimaDataObtidaEm != null ? (new Date().getTime() - ultimaDataObtidaEm.getTime()) : Long.MAX_VALUE;
	}

	@Override
	public int obterTimeZone() {
		String timezoneString = (String) gerenciadorDeEntidade.createNativeQuery(BUSCA_TIMEZONE).getSingleResult();
		int hour = Integer.parseInt(timezoneString.substring(1,2));
		boolean positive = timezoneString.charAt(0) == '+';
		if(!positive) {
			hour *= -1;
		}

		return hour;
	}
}
