package ipp.aci.boleia.dados;


import java.util.List;

import ipp.aci.boleia.dominio.CondicoesComerciais;

/**
 * Contrato para implementacao de repositorios de entidades CondicoesComerciais
 */
public interface ICondicoesComerciaisDados extends IRepositorioBoleiaDados<CondicoesComerciais> {

    /**
     * Obtem a última condição comercial por frota
     * 
     * @param id da frota 
     * @return CondicoesComerciais objeto pedido
     */
	List<CondicoesComerciais> buscarPorFrota(Long idFrota);   
}
