package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Lead;


/**
 * Contrato para implementacao de repositorios
 * de entidades Lead .
 */
public interface ILeadDados extends IRepositorioBoleiaDados<Lead>  {
	
	Lead pesquisarLeadPendentePelaFrota(Long idFrota);
	
	Lead pesquisarUltimoLeadParaFrota(Long idFrota);
		
}
