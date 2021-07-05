package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPendenciaChamadoDados;
import ipp.aci.boleia.dominio.PendenciaChamado;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados de uma {@link PendenciaChamado}.
 *
 * @author pedro.silva
 */
@Repository
public class OraclePendenciaChamadoDados extends OracleRepositorioBoleiaDados<PendenciaChamado> implements IPendenciaChamadoDados {

    /**
     * Instancia o repositorio
     */
    public OraclePendenciaChamadoDados() {
        super(PendenciaChamado.class);
    }
}
