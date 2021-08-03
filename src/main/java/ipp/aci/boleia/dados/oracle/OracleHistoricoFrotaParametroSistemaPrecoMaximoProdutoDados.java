package ipp.aci.boleia.dados.oracle;


import ipp.aci.boleia.dados.IHistoricoFrotaParametroSistemaPrecoMaximoProdutoDados;
import ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaPrecoMaximoProduto;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoFrotaParametroSistemaPrecoMaximoProduto}.
 *
 * @author allan.santos
 */
@Repository
public class OracleHistoricoFrotaParametroSistemaPrecoMaximoProdutoDados extends OracleRepositorioBoleiaDados<HistoricoFrotaParametroSistemaPrecoMaximoProduto> implements IHistoricoFrotaParametroSistemaPrecoMaximoProdutoDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoFrotaParametroSistemaPrecoMaximoProdutoDados() {
        super(HistoricoFrotaParametroSistemaPrecoMaximoProduto.class);
    }
}
