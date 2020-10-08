package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Credenciamento;

/**
 * Contrato para implementacao de repositorios da entidade {@link Credenciamento}
 */
public interface ICredenciamentoDados extends IRepositorioBoleiaDados<Credenciamento> {
	
	/**
     * Obtem o credenciamento através do cnpj informado.
     *
     * @param cnpj Numero do cnpj da empresa.
     * @return {@link Credenciamento}.
     */
	Credenciamento obterPorCnpj(Long cnpj);
}
