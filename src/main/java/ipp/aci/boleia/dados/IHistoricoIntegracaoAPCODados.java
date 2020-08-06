package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.historico.HistoricoIntegracaoAPCO;

import java.util.Date;

/**
 * Contrato para implementacao de repositorios de entidades HistoricoIntegracaoAPCO
 */
public interface IHistoricoIntegracaoAPCODados extends IRepositorioBoleiaDados<HistoricoIntegracaoAPCO> {

	/**
	 * Obtem o ultimo registro das operações de integração bem sucedidas anteriores a data vigente.
	 * @param dataVigente a data atual.
	 *
	 * @return o último registro de operações de integração
	 */
	HistoricoIntegracaoAPCO obterUltimaIntegracao(Date dataVigente);


}
