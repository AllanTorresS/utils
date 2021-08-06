package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoFrotaParametroSistemaIntervaloAbastecimentoDados;
import ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaIntervaloAbastecimento;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaIntervaloAbastecimento}.
 *
 * @author rafael.laranjeiro
 */
@Repository
public class OracleHistoricoFrotaParametroSistemaIntervaloAbastecimentoDados extends OracleRepositorioBoleiaDados<HistoricoFrotaParametroSistemaIntervaloAbastecimento> implements IHistoricoFrotaParametroSistemaIntervaloAbastecimentoDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoFrotaParametroSistemaIntervaloAbastecimentoDados() {
        super(HistoricoFrotaParametroSistemaIntervaloAbastecimento.class);
    }
}
