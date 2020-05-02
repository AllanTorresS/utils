package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.AbastecimentoVeiculoMes;

import java.util.Date;
import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades AbastecimentoVeiculoMes
 */
public interface IAbastecimentoVeiculoMesDados extends IRepositorioBoleiaDados<AbastecimentoVeiculoMes> {

	/**
	 * Obtem todos as consolidacoes por frota e mes ano
	 * @param mesAno A data do mes/ano
	 * @param idFrota O identificador da frota
	 * @return As consolidacoes encontradas
	 */
	List<AbastecimentoVeiculoMes> obterPorMesAnoFrota(Date mesAno, Long idFrota);

	/**
	 * Obtem todos as consolidacoes por frota com data minima
	 * @param idFrota O identificador da frota
	 * @param dataMinima para consulta de abastecimentos
	 * @return As consolidacoes encontradas
	 */
	List<AbastecimentoVeiculoMes> obterPorFrotaDataMinima(Long idFrota, Date dataMinima);

	/**
	 * Obtem a data maxima de atualizacao para relatorio
	 * @return Ultima data de atualizacao
	 */
    Date obterDataMaxima();
}
