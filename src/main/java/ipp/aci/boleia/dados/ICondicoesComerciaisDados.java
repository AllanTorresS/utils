package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.CondicoesComerciais;

/**
 * Contrato para implementacao de repositorios de entidades CondicoesComerciais
 */
public interface ICondicoesComerciaisDados extends IRepositorioBoleiaDados<CondicoesComerciais> {

    /**
     * Obtem a última condição comercial por frota
     * 
     * @param idFrota da frota
     * @return CondicoesComerciais objeto pedido
     */
	CondicoesComerciais buscarPorFrota(Long idFrota);   
}
