package ipp.aci.boleia.dados;
import ipp.aci.boleia.dominio.RotaPostoDesconsiderado;
import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades RotaPostoDesconsiderado
 */
public interface IRotaPostoDesconsideradoDados extends IRepositorioBoleiaDados<RotaPostoDesconsiderado> {

    /**
     * Obtem os postos desconsiderados de uma rota
     * @param idRota A rota
     * @return A lista de postos desconsiderados de uma rota
     */
    List<RotaPostoDesconsiderado> obterPorRota(Long idRota);

}
