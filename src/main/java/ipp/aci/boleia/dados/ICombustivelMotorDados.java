package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.CombustivelMotor;

import java.util.Collection;
import java.util.List;

/**
 * Contrato para implementacao de repositorios da entidade CombustivelMotor.
 */
public interface ICombustivelMotorDados extends IRepositorioBoleiaDados<CombustivelMotor> {

    /**
     * Obtem o tipo de combustivel pela descricao
     * @param descricao descricao
     * @return tipo de combustivel
     */
    CombustivelMotor obterPorDescricao(String descricao);

    /**
     * Obtem CombustivelMotor pela descricao mtec
     * @param combustivelMtec listas de descricoes mtec
     * @return Combustiveis motor compativeis
     */
    List<CombustivelMotor> pesquisarPorTipoCombustivelMtec(Collection<String> combustivelMtec);
}
