package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IFrotaParametroSistemaProdutoDados;
import ipp.aci.boleia.dominio.FrotaParametroSistemaProduto;
import org.springframework.stereotype.Repository;

/**
 * Implementação oracle para {@link IFrotaParametroSistemaProdutoDados}
 */
@Repository
public class OracleFrotaParametroSistemaProdutoDados extends OracleRepositorioBoleiaDados<FrotaParametroSistemaProduto> implements IFrotaParametroSistemaProdutoDados {

    /**
     * Instancia o repositorio
     * OracleFrotaParametroSistemaProdutoDados
     */
    public OracleFrotaParametroSistemaProdutoDados() {
        super(FrotaParametroSistemaProduto.class);
    }
}
