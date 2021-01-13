package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoParametroNotaFiscalDados;
import ipp.aci.boleia.dominio.historico.HistoricoParametroNotaFiscal;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoParametroNotaFiscal}.
 */
@Repository
public class OracleHistoricoParametroNotaFiscalDados extends OracleRepositorioBoleiaDados<HistoricoParametroNotaFiscal> implements IHistoricoParametroNotaFiscalDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoParametroNotaFiscalDados() {
        super(HistoricoParametroNotaFiscal.class);
    }
}
