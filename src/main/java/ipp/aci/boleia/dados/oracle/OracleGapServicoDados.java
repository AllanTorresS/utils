package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IGapServicoDados;
import ipp.aci.boleia.dominio.GapServico;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades GapServico
 */
@Repository
public class OracleGapServicoDados extends OracleRepositorioBoleiaDados<GapServico> implements IGapServicoDados {

    /**
     * Instancia o repositorio
     */
    public OracleGapServicoDados() {
        super(GapServico.class);
    }

}
