package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.beneficios.ConfiguracaoDistribuicaoAutomatica;

/**
 * Contrato para implementação de repositírios de entidades ConfiguracaoDistribuicaoAutomatica
 */
public interface IConfiguracaoDistribuicaoAutomaticaDados extends IRepositorioBoleiaDados<ConfiguracaoDistribuicaoAutomatica>{

	/**
	 * Pesquisa uma configuração de distribuição automática a partir da sua frota 
	 * 
	 * @param idFrota O id da frota a qual cuja configuração deve ser retornada
	 * @return A configuração encontrada
	 */
	ConfiguracaoDistribuicaoAutomatica obterConfiguracaoPorFrota(Long idFrota);
}
