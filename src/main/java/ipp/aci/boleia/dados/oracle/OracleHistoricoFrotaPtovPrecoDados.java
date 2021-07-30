package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoFrotaPtovPrecoDados;
import ipp.aci.boleia.dominio.HistoricoFrotaPtovPreco;
import org.springframework.stereotype.Repository;

@Repository
public class OracleHistoricoFrotaPtovPrecoDados extends OracleRepositorioBoleiaDados<HistoricoFrotaPtovPreco> implements IHistoricoFrotaPtovPrecoDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoFrotaPtovPrecoDados(){
        super(HistoricoFrotaPtovPreco.class);
    }

}
