package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ITarifadorDados;
import ipp.aci.boleia.dominio.tarifador.Tarifador;
import org.springframework.stereotype.Repository;

/**
 * Repositório de Tarifador
 */
@Repository
public class OracleTarifadorDados extends OracleRepositorioBoleiaDados<Tarifador> implements ITarifadorDados {

    /**
     * Instancia o repositorio
     */
    public OracleTarifadorDados() {
        super(Tarifador.class);
    }
}
