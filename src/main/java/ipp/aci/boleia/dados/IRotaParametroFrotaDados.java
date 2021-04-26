package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.RotaParametroFrota;
import java.util.List;


/**
 * Contrato para implementacao de repositorios de entidades RotaParametroFrota
 */
public interface IRotaParametroFrotaDados extends IRepositorioBoleiaDados<RotaParametroFrota> {

    List<RotaParametroFrota> obterPorRota(Long idRota);
}
