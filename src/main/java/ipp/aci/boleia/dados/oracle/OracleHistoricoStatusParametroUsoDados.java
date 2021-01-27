package ipp.aci.boleia.dados.oracle;



import ipp.aci.boleia.dados.IHistoricoStatusParametroUsoDados;
import ipp.aci.boleia.dominio.HistoricoParametroUso;
import ipp.aci.boleia.dominio.HistoricoStatusParametroDeUso;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoStatusParametroDeUso}.
 *
 * @author tiago.marques
 */
@Repository
public class OracleHistoricoStatusParametroUsoDados extends OracleRepositorioBoleiaDados<HistoricoStatusParametroDeUso> implements IHistoricoStatusParametroUsoDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoStatusParametroUsoDados() {
        super(HistoricoStatusParametroDeUso.class);
    }
}
