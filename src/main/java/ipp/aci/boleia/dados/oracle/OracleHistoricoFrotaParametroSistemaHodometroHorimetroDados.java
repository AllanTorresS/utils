package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoFrotaParametroSistemaHodometroHorimetroDados;
import ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaHodometroHorimetro;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaHodometroHorimetro}.
 *
 * @author rafael.laranjeiro
 */
@Repository
public class OracleHistoricoFrotaParametroSistemaHodometroHorimetroDados extends OracleRepositorioBoleiaDados<HistoricoFrotaParametroSistemaHodometroHorimetro> implements IHistoricoFrotaParametroSistemaHodometroHorimetroDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoFrotaParametroSistemaHodometroHorimetroDados() {
        super(HistoricoFrotaParametroSistemaHodometroHorimetro.class);
    }
}
