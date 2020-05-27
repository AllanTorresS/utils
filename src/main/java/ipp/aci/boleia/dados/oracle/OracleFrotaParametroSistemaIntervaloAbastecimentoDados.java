package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IFrotaParametroSistemaIntervaloAbastecimentoDados;
import ipp.aci.boleia.dominio.FrotaParametroSistemaIntervaloAbastecimento;
import org.springframework.stereotype.Repository;

/**
 * Implementação oracle para {@link IFrotaParametroSistemaIntervaloAbastecimentoDados}
 */
@Repository
public class OracleFrotaParametroSistemaIntervaloAbastecimentoDados extends OracleRepositorioBoleiaDados<FrotaParametroSistemaIntervaloAbastecimento> implements IFrotaParametroSistemaIntervaloAbastecimentoDados {

    /**
     * Instancia o repositorio
     */
    public OracleFrotaParametroSistemaIntervaloAbastecimentoDados() {
        super(FrotaParametroSistemaIntervaloAbastecimento.class);
    }
}
