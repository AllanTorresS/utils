package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.acumulo.AcumuloKmv;

import java.util.Date;
import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades AcumuloKmv
 */
public interface IAcumuloKmvDados extends IRepositorioBoleiaDados<AcumuloKmv> {


	/**
	 * Recupera o acumulo mensal de um determinado dono de diversas frota.
	 *
	 * @param cpfDono o cpf do dono da frota.
	 * @param dataProcessamento a data do abastecimento realizado.
	 * @return o valor de Acúmulo mensal do Dono da Frota
	 */
	Integer recuperarKmAcumuladoMensalDonoFrota(Long cpfDono, Date dataProcessamento);

	/**
	 * Recupera o acumulo mensal de um determinado dono de uma das suas frotas.
	 *
	 * @param cpfDono o cpf do dono da frota.
	 * @param idFrota o id da frota
	 * @param dataProcessamento a data do abastecimento realizado.
	 * @return o valor de Acúmulo mensal do Dono da Frota
	 */
	Integer recuperarKmAcumuladoMensalDonoFrotaIndividual(Long cpfDono, Long idFrota, Date dataProcessamento);

	/**
	 * Recupera o acumulo de um abastecimento já realizado para motorista.
	 *
	 * @param idAbastecimento o identificador do abastecimento
	 * @return o  Acúmulo associado.
	 */
	AcumuloKmv recuperarAcumuloRealizadoPorAbastecimentoMotorista(Long idAbastecimento);

	/**
	 * Recupera o acumulo de um abastecimento pendente para um tipo de acúmulo.
	 *
	 * @param idAbastecimento o identificador do abastecimento
	 * @param tipoAcumulo tipo do acumulo processado
	 * @return o  Acúmulo associado.
	 */
	AcumuloKmv recuperarAcumuloPendentePorAbastecimento(Long idAbastecimento, Integer tipoAcumulo);

	/**
	 * Obtem a lista de notas que ainda não acumularam Km de Vantagens
	 *
	 * @param numeroDeRegistros Limita o número de registros a serem retornados.
	 * @return A lista de notas que ainda não acumularam Km de Vantagens
	 */
	List<AcumuloKmv> obterAcumulosPendentes(Integer numeroDeRegistros);

	/**
	 * Recupera o acumulo de um abastecimento para Dono da frota já criado em função
	 * de um acúmulo genérico de motorista.
	 *
	 * @param idAbastecimento o identificador do abastecimento
	 * @return o  Acúmulo associado.
	 */
	AcumuloKmv recuperarAcumuloDonoFrotaJaCriado(Long idAbastecimento);
}
