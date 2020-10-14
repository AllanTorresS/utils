package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.agenciadorfrete.MotoristaAutonomo;

/**
 * Contrato para implementacao de acesso aos dados de MotoristaAutonomo
 */
public interface IMotoristaAutonomoDados  extends IRepositorioBoleiaDados<MotoristaAutonomo> {


    /**
     * Obtem o motorista Autonomo por CPF
     * @param cpf do motorista
     * @return O motorista Autonomo
     */
    MotoristaAutonomo obterPorCpf(String cpf);
}