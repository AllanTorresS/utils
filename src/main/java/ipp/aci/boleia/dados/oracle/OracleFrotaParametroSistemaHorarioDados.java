package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IFrotaParametroSistemaHorarioDados;
import ipp.aci.boleia.dominio.FrotaParametroSistemaHorario;
import org.springframework.stereotype.Repository;

/**
 * Implementação oracle para {@link ipp.aci.boleia.dados.IFrotaParametroSistemaHorarioDados}
 */
@Repository
public class OracleFrotaParametroSistemaHorarioDados extends OracleRepositorioBoleiaDados<FrotaParametroSistemaHorario> implements IFrotaParametroSistemaHorarioDados {

    /**
     * Instancia o repositorio
     * OracleFrotaParametroSistemaHorarioDados
     */
    public OracleFrotaParametroSistemaHorarioDados() {
        super(FrotaParametroSistemaHorario.class);
    }
}