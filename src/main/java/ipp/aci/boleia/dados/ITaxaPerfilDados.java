package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.TaxaPerfil;
import ipp.aci.boleia.dominio.enums.PerfilPontoDeVenda;

/**
 * Contrato para implementacao de repositorios de entidades {@link TaxaPerfil}
 */
public interface ITaxaPerfilDados extends IRepositorioBoleiaDados<TaxaPerfil> {
	
	/**
     * Obtem a taxa do perfil de ponto de venda através do tipo de perfil informado.
     *
     * @param tipo {@link PerfilPontoDeVenda} Tipo da taxa de perfil do ponto de venda.
     * @return A {@link TaxaPerfil}.
     */
	TaxaPerfil obterPorTipo(PerfilPontoDeVenda tipo);

}
