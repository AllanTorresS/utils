package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.enums.GrupoExecucaoParametroSistema;
import ipp.aci.boleia.dominio.enums.ParametroSistema;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades FrotaParametroSistema
 */
public interface IFrotaParametroSistemaDados extends IRepositorioBoleiaDados<FrotaParametroSistema> {

    /**
     * Lista os parametros configurados para a frota pertencentes ao grupo informado, ordenados pela criticidade da regra.
     * Regras restritivas vem em primeiro lugar, seguidas pelas versateis configuradas como restritivas. Por ultimo vem as
     * versateis configuradas como informativas.
     *
     * @param idFrota O id da frota
     * @param grupo O grupo de execucao desejado
     * @return Os parametros configurados para a frota e grupo informados
     */
    List<FrotaParametroSistema> buscarPorFrotaGrupoExecucaoOrdenadosPorRestritividade(Long idFrota, GrupoExecucaoParametroSistema grupo);

    /**
     * Busca todos parametros sistema de determinada frota
     * @param idFrota id da frota a buscar
     * @return parametros da frota
     */
    List<FrotaParametroSistema> buscarPorFrota(Long idFrota);

    /**
     * Obtem um determinado parametro de sistema para uma frota
     * @param idFrota id da frota
     * @param parametroSistema parametro a obter
     * @return Parametro sistema da frota
     */
    FrotaParametroSistema obterPorParametroSistema(Long idFrota, ParametroSistema parametroSistema);
    
    /**
     * Altera o estado da entidade para desanexado
     *
     * @param entidade entidade transiente
     * @return A entidade desanexada
     */
    FrotaParametroSistema desanexar(FrotaParametroSistema parametroSistema);
}
