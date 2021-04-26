package ipp.aci.boleia.dados;
import ipp.aci.boleia.dominio.RotaPostoDesconsiderado;
import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades RotaPostoDesconsiderado
 */
public interface IRotaPostoDesconsideradoDados extends IRepositorioBoleiaDados<RotaPostoDesconsiderado> {

    List<RotaPostoDesconsiderado> obterPorRota(Long idRota);

}
