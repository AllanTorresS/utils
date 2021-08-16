package ipp.aci.boleia.dados.oracle;


import ipp.aci.boleia.dados.IHistoricoFrotaParametroSistemaProdutoDados;
import ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaProduto;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoFrotaParametroSistemaProduto}.
 *
 * @author allan.santos
 */
@Repository
public class OracleHistoricoFrotaParametroSistemaProdutoDados extends OracleRepositorioBoleiaDados<HistoricoFrotaParametroSistemaProduto> implements IHistoricoFrotaParametroSistemaProdutoDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoFrotaParametroSistemaProdutoDados() {
        super(HistoricoFrotaParametroSistemaProduto.class);
    }
}
