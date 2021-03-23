package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IReembolsoAntecipadoDados;
import ipp.aci.boleia.dominio.ReembolsoAntecipado;

public class OracleReembolsoAntecipadoDados extends OracleRepositorioBoleiaDados<ReembolsoAntecipado> implements IReembolsoAntecipadoDados {

    /**
     * Instancia o repositorio
     */
    public OracleReembolsoAntecipadoDados() {
        super(ReembolsoAntecipado.class);
    }
}
