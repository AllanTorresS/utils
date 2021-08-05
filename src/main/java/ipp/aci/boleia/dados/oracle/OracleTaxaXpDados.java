package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ITaxaXpDados;
import ipp.aci.boleia.dominio.TaxaXp;
import org.springframework.stereotype.Repository;

/**
 * Repositório de entidades TaxaXp
 */
@Repository
public class OracleTaxaXpDados extends OracleRepositorioBoleiaDados<TaxaXp> implements ITaxaXpDados {

    /**
     * Instancia o repositorio
     */
    public OracleTaxaXpDados() {
        super(TaxaXp.class);
    }
}
