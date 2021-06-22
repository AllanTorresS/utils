package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IBeneficioDados;
import ipp.aci.boleia.dominio.beneficios.Beneficio;
import org.springframework.stereotype.Repository;

/**
 * Repositório de entidades Beneficiario.
 */
@Repository
public class OracleBeneficioDados extends OracleRepositorioBoleiaDados<Beneficio> implements IBeneficioDados {

    /**
     * Instancia o repositório.
     */
    public OracleBeneficioDados() {
        super(Beneficio.class);
    }
}
