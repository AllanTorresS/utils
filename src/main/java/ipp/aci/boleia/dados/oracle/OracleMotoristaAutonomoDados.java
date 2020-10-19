package ipp.aci.boleia.dados.oracle;


import ipp.aci.boleia.dados.IMotoristaAutonomoDados;
import ipp.aci.boleia.dominio.agenciadorfrete.MotoristaAutonomo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades Motorista Autonomo
 */
@Repository
public class OracleMotoristaAutonomoDados extends OracleRepositorioBoleiaDados<MotoristaAutonomo> implements IMotoristaAutonomoDados {
    /**
     * Instancia o repositorio
     *
     */
    public OracleMotoristaAutonomoDados() {
        super(MotoristaAutonomo.class);
    }

    @Override
    public MotoristaAutonomo obterPorCpf(String cpf) {
        return pesquisarUnico(new ParametroPesquisaIgual("cpf", cpf));
    }

}
