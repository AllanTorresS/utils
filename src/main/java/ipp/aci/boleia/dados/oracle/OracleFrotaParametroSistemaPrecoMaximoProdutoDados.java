package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IFrotaParametroSistemaPrecoMaximoProdutoDados;
import ipp.aci.boleia.dominio.FrotaParametroSistemaPrecoMaximoProduto;
import org.springframework.stereotype.Repository;

/**
 * Implementação oracle para {@link IFrotaParametroSistemaPrecoMaximoProdutoDados}
 */
@Repository
public class OracleFrotaParametroSistemaPrecoMaximoProdutoDados extends OracleRepositorioBoleiaDados<FrotaParametroSistemaPrecoMaximoProduto> implements IFrotaParametroSistemaPrecoMaximoProdutoDados {

    /**
     * Instancia o repositorio
     * OracleFrotaParametroSistemaProdutoDados
     */
    public OracleFrotaParametroSistemaPrecoMaximoProdutoDados() {
        super(FrotaParametroSistemaPrecoMaximoProduto.class);
    }
}
