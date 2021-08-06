package ipp.aci.boleia.dados.oracle;



import ipp.aci.boleia.dados.IHistoricoFrotaParametroSistemaConsumoDados;
import ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaConsumo;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoFrotaParametroSistemaConsumo}.
 *
 * @author tiago.marques
 */
@Repository
public class OracleHistoricoFrotaParametroSistemaProdutoAbastecimentoDados extends OracleRepositorioBoleiaDados<HistoricoFrotaParametroSistemaConsumo> implements IHistoricoFrotaParametroSistemaConsumoDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoFrotaParametroSistemaProdutoAbastecimentoDados() {
        super(HistoricoFrotaParametroSistemaConsumo.class);
    }
}
