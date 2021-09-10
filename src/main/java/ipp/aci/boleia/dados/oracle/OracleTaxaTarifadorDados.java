package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ITaxaTarifadorDados;
import ipp.aci.boleia.dominio.tarifador.TaxaTarifador;
import org.springframework.stereotype.Repository;

@Repository
public class OracleTaxaTarifadorDados extends OracleRepositorioBoleiaDados<TaxaTarifador> implements ITaxaTarifadorDados {

    /**
     * Instancia o repositorio
     */
    public OracleTaxaTarifadorDados() {
        super(TaxaTarifador.class);
    }
}
