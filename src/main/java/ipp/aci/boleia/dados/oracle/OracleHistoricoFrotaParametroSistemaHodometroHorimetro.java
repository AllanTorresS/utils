package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoFrotaParametroSistemaHodometroHorimetro;
import ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaHodometroHorimetro;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaHodometroHorimetro}.
 *
 * @author rafael.laranjeiro
 */
@Repository
public class OracleHistoricoFrotaParametroSistemaHodometroHorimetro extends OracleRepositorioBoleiaDados<HistoricoFrotaParametroSistemaHodometroHorimetro> implements IHistoricoFrotaParametroSistemaHodometroHorimetro {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoFrotaParametroSistemaHodometroHorimetro() {
        super(HistoricoFrotaParametroSistemaHodometroHorimetro.class);
    }
}
