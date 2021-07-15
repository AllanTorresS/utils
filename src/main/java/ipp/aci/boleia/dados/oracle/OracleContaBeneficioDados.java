package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IContaBeneficioDados;
import ipp.aci.boleia.dominio.beneficios.ContaBeneficio;
import org.springframework.stereotype.Repository;

/**
 * Repositório de entidades ContaBeneficio.
 */
@Repository
public class OracleContaBeneficioDados extends OracleRepositorioBoleiaDados<ContaBeneficio> implements IContaBeneficioDados {

    /**
     * Instancia o repositório.
     */
    public OracleContaBeneficioDados() {
        super(ContaBeneficio.class);
    }
}
