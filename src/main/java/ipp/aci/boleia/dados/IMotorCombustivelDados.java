package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.MotorCombustivel;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades de Motor combustível.
 */
public interface IMotorCombustivelDados extends IRepositorioBoleiaDados<MotorCombustivel> {

    /**
     * Obtem motor combustivel por id do motor
     * @param idMotor id do motor
     * @return Combustiveis do motor
     */
    List<MotorCombustivel> obterPorMotor(Long idMotor);
}
