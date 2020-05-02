package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.MotorVeiculo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades de Motor dos veiculos.
 */
public interface IMotorVeiculoDados extends IRepositorioBoleiaDados<MotorVeiculo> {

    /**
     * Obtem motores por modelo
     * @param idModelo id do modelo
     * @return motores do modelo
     */
    List<MotorVeiculo> pesquisarPorModelo(Long idModelo);

    /**
     * Obtem um motor por pelo id do modelo e descricao do motor
     * @param idModelo id do modelo
     * @param descricao descricao do modelo
     * @return O MotorVeiculo caso encontrado
     */
    MotorVeiculo pesquisarPorModeloDescricao(Long idModelo, String descricao);
}
