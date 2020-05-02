package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IMotorCombustivelDados;
import ipp.aci.boleia.dominio.MotorCombustivel;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades de
 * Motor combustivel
 */
@Repository
public class OracleMotorCombustivelDados extends OracleRepositorioBoleiaDados<MotorCombustivel> implements IMotorCombustivelDados {

    /**
     * Instancia o repositorio
     * OracleMotorVeiculoDados
     */
    public OracleMotorCombustivelDados() {
        super(MotorCombustivel.class);
    }

    @Override
    public List<MotorCombustivel> obterPorMotor(Long idMotor) {
        return pesquisar(new ParametroOrdenacaoColuna("tipoCombustivelMtec"), new ParametroPesquisaIgual("motor", idMotor));
    }
}
