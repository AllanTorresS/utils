package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IFrotaParametroSistemaPrecoMaximoAbastecimentoDados;
import ipp.aci.boleia.dominio.FrotaParametroSistemaPrecoMaximoAbastecimento;
import org.springframework.stereotype.Repository;

/**
 * Implementação oracle para {@link IFrotaParametroSistemaPrecoMaximoAbastecimentoDados}
 */
@Repository
public class OracleFrotaParametroSistemaPrecoMaximoAbastecimentoDados extends OracleRepositorioBoleiaDados<FrotaParametroSistemaPrecoMaximoAbastecimento> implements IFrotaParametroSistemaPrecoMaximoAbastecimentoDados {

    /**
     * Instancia o repositorio
     * OracleFrotaParametroSistemaProdutoDados
     */
    public OracleFrotaParametroSistemaPrecoMaximoAbastecimentoDados() {
        super(FrotaParametroSistemaPrecoMaximoAbastecimento.class);
    }
}
