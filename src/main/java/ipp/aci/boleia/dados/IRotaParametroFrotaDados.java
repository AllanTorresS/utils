package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.RotaParametroFrota;
import java.util.List;


/**
 * Contrato para implementacao de repositorios de entidades RotaParametroFrota
 */
public interface IRotaParametroFrotaDados extends IRepositorioBoleiaDados<RotaParametroFrota> {

    /**
     * Obtem os parametros de sistema de uma rota
     * @param idRota A rota
     * @return A lista de parametros de sistema de uma rota
     */
    List<RotaParametroFrota> obterPorRota(Long idRota);
}
