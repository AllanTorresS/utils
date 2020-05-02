package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IFrotaParametroSistemaConsumoDados;
import ipp.aci.boleia.dominio.FrotaParametroSistemaConsumo;
import org.springframework.stereotype.Repository;

/**
 * Implementação oracle para {@link IFrotaParametroSistemaConsumoDados}
 */
@Repository
public class OracleFrotaParametroSistemaConsumoDados extends OracleRepositorioBoleiaDados<FrotaParametroSistemaConsumo> implements IFrotaParametroSistemaConsumoDados {

    /**
     * Instancia o repositorio
     * OracleFrotaParametroSistemaProdutoDados
     */
    public OracleFrotaParametroSistemaConsumoDados() {
        super(FrotaParametroSistemaConsumo.class);
    }
}
