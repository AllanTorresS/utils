package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.agenciadorfrete.MotoristaAutonomo;
import ipp.aci.boleia.dominio.agenciadorfrete.MotoristaAutonomoToken;

/**
 * Contrato para implementacao de acesso aos dados de MotoristaAutonomoToken
 */
public interface IMotoristaAutonomoTokenDados  extends IRepositorioBoleiaDados<MotoristaAutonomoToken> {


    /**
     * Obtem o token do motorista Autonomo por CPF
     * @param cpf do motorista
     * @return O token do motorista Autonomo
     */
    MotoristaAutonomoToken obterPorCpf(String cpf);

    /**
     * Exclui o token do motorista Autonomo
     * @param motoristaAutonomo O motorista autonomo
     */
    void excluirTokenPorMotorista(MotoristaAutonomo motoristaAutonomo);
}
