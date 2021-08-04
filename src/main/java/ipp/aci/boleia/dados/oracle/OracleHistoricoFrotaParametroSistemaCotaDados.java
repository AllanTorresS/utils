package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoFrotaParametroSistemaCotaDados;
import ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaCota;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoFrotaParametroSistemaCota}.
 *
 * @author rafael.laranjeiro
 */
@Repository
public class OracleHistoricoFrotaParametroSistemaCotaDados extends OracleRepositorioBoleiaDados<HistoricoFrotaParametroSistemaCota> implements IHistoricoFrotaParametroSistemaCotaDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoFrotaParametroSistemaCotaDados() {
        super(HistoricoFrotaParametroSistemaCota.class);
    }
}
