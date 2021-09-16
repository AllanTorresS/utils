package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoPrecoBaseDados;
import ipp.aci.boleia.dominio.HistoricoPrecoBase;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de registro de histórico de preços base
 */
@Repository
public class OracleHistoricoPrecoBaseDados extends OracleRepositorioBoleiaDados<HistoricoPrecoBase> implements IHistoricoPrecoBaseDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoPrecoBaseDados() {
        super(HistoricoPrecoBase.class);
    }


}
