package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IReembolsoAntecipadoDados;
import ipp.aci.boleia.dominio.ReembolsoAntecipado;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link ReembolsoAntecipado}.
 */
@Repository
public class OracleReembolsoAntecipadoDados extends OracleRepositorioBoleiaDados<ReembolsoAntecipado> implements IReembolsoAntecipadoDados {

    /**
     * Instancia o repositorio
     */
    public OracleReembolsoAntecipadoDados() {
        super(ReembolsoAntecipado.class);
    }
}
