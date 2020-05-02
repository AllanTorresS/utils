package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.TermosContrato;

/**
 * Contrato para implementacao de repositorios de entidades {@link TermosContrato}
 */
public interface ITermosContratoDados extends IRepositorioBoleiaDados<TermosContrato> {
	
	/**
     * Obtem a versão atual do termos de contrato.
     *
     * @return O {@link TermosContrato}.
     */
	TermosContrato obterVersaoAtual();

}
