package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoFrotaDados;
import ipp.aci.boleia.dominio.historico.HistoricoFrota;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoFrota}.
 */
@Repository
public class OracleHistoricoFrotaDados extends OracleRepositorioBoleiaDados<HistoricoFrota> implements IHistoricoFrotaDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoFrotaDados() {
        super(HistoricoFrota.class);
    }
}
