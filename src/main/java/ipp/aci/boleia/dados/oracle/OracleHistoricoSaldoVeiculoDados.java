package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoSaldoVeiculosDados;
import ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaCota;
import ipp.aci.boleia.dominio.HistoricoSaldoVeiculo;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoFrotaParametroSistemaCota}.
 *
 * @author rafael.laranjeiro
 */
@Repository
public class OracleHistoricoSaldoVeiculoDados extends OracleRepositorioBoleiaDados<HistoricoSaldoVeiculo> implements IHistoricoSaldoVeiculosDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoSaldoVeiculoDados() {
        super(HistoricoSaldoVeiculo.class);
    }
}
