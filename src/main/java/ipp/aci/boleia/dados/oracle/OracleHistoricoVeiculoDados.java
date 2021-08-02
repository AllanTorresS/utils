package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoVeiculoDados;
import ipp.aci.boleia.dominio.HistoricoVeiculo;
import org.springframework.stereotype.Repository;

/**
 * Implementacao do repositorio de dados da entidade {@link HistoricoVeiculo}.
 * 
 * @author allan.santos
 */
@Repository
public class OracleHistoricoVeiculoDados extends OracleRepositorioBoleiaDados<HistoricoVeiculo> implements IHistoricoVeiculoDados {

    /**
     * Construtor do repositorio.
     */
    public OracleHistoricoVeiculoDados() {
        super(HistoricoVeiculo.class);
    }

}
