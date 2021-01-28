package ipp.aci.boleia.dados.oracle;



import ipp.aci.boleia.dados.IHistoricoFrotaParametroSistemaProdutoAbastecimentoDados;
import ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaProdutoAbastecimento;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoFrotaParametroSistemaProdutoAbastecimento}.
 *
 * @author tiago.marques
 */
@Repository
public class OracleHistoricoFrotaParametroSistemaProdutoAbastecimentoDadosDados extends OracleRepositorioBoleiaDados<HistoricoFrotaParametroSistemaProdutoAbastecimento> implements IHistoricoFrotaParametroSistemaProdutoAbastecimentoDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoFrotaParametroSistemaProdutoAbastecimentoDadosDados() {
        super(HistoricoFrotaParametroSistemaProdutoAbastecimento.class);
    }
}
