package ipp.aci.boleia.dados.oracle;


import ipp.aci.boleia.dados.IHistoricoFrotaParametroSistemaHorarioDados;
import ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaHorario;
import org.springframework.stereotype.Repository;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoFrotaParametroSistemaHorario}.
 *
 * @author tiago.marques
 */
@Repository
public class OracleHistoricoFrotaParametroSistemaHorarioDados extends OracleRepositorioBoleiaDados<HistoricoFrotaParametroSistemaHorario> implements IHistoricoFrotaParametroSistemaHorarioDados {

    /**
     * Construtor do repositório.
     */
    public OracleHistoricoFrotaParametroSistemaHorarioDados() {
        super(HistoricoFrotaParametroSistemaHorario.class);
    }
}
