package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.PontoRota;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades PontoRota
 */
public interface IPontoRotaDados extends IRepositorioBoleiaDados<PontoRota> {

    /**
     * Obtem os pontos de uma rota
     * @param idRota A rota
     * @return A lista de pontos da rota
     */
    List<PontoRota> obterPorRota(Long idRota);

}
