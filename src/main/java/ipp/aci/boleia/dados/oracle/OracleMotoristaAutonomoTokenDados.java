package ipp.aci.boleia.dados.oracle;


import ipp.aci.boleia.dados.IMotoristaAutonomoTokenDados;
import ipp.aci.boleia.dominio.agenciadorfrete.MotoristaAutonomo;
import ipp.aci.boleia.dominio.agenciadorfrete.MotoristaAutonomoToken;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

/**
 * Respositorio de entidades Motorista Autonomo
 */
@Repository
public class OracleMotoristaAutonomoTokenDados extends OracleRepositorioBoleiaDados<MotoristaAutonomoToken> implements IMotoristaAutonomoTokenDados {

    public static final String QUERY_EXCLUSAO_TOKEN = "DELETE FROM MotoristaAutonomoToken t " +
            "WHERE t.id in " +
            "(SELECT token.id " +
            "FROM MotoristaAutonomoToken token " +
            "JOIN token.motorista motorista " +
            "WHERE motorista.id = :idMotorista) ";

    /**
     * Instancia o repositorio
     *
     */
    public OracleMotoristaAutonomoTokenDados() {
        super(MotoristaAutonomoToken.class);
    }

    @Override
    public MotoristaAutonomoToken obterPorCpf(String cpf) {
        return pesquisarUnico(new ParametroPesquisaIgual("motorista.cpf", cpf));
    }

    @Override
    public void excluirTokenPorMotorista(MotoristaAutonomo motoristaAutonomo) {
        Query query = getGerenciadorDeEntidade().createQuery(QUERY_EXCLUSAO_TOKEN);
        query.setParameter("idMotorista", motoristaAutonomo.getId());
        query.executeUpdate();
    }
}
