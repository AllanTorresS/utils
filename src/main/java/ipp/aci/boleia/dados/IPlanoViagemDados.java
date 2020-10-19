package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.PlanoViagem;

import java.util.List;

/**
 * Interação com serviço de plano de viagem
 */
public interface IPlanoViagemDados extends IRepositorioBoleiaDados<PlanoViagem> {

    /**
     * Obtem os planos de viagem de uma rota
     * @param idRota o identificador da rota
     * @return A lista de planos de viagem da rota
     */
    List<PlanoViagem> obterPorRota(Long idRota);

}
