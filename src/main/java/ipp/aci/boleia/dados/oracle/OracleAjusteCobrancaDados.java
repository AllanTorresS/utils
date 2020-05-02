package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAjusteCobrancaDados;
import ipp.aci.boleia.dominio.AjusteCobranca;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link AjusteCobranca}.
 */
@Repository
public class OracleAjusteCobrancaDados extends OracleRepositorioBoleiaDados<AjusteCobranca> implements IAjusteCobrancaDados {

    /**
     * Construtor do repositório.
     */
    public OracleAjusteCobrancaDados() {
        super(AjusteCobranca.class);
    }
}
