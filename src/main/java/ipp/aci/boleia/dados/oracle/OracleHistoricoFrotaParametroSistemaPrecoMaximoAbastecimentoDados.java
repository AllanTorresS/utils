package ipp.aci.boleia.dados.oracle;


import ipp.aci.boleia.dados.IHistoricoFrotaParametroSistemaPrecoMaximoAbastecimentoDados;
import ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaPrecoMaximoAbastecimento;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoFrotaParametroSistemaPrecoMaximoAbastecimento}.
 *
 * @author allan.santos
 */
@Repository
public class OracleHistoricoFrotaParametroSistemaPrecoMaximoAbastecimentoDados extends OracleRepositorioBoleiaDados<HistoricoFrotaParametroSistemaPrecoMaximoAbastecimento> implements IHistoricoFrotaParametroSistemaPrecoMaximoAbastecimentoDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoFrotaParametroSistemaPrecoMaximoAbastecimentoDados() {
        super(HistoricoFrotaParametroSistemaPrecoMaximoAbastecimento.class);
    }
}
