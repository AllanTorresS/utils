package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IFrotaParametroSistemaHodometroHorimetroDados;
import ipp.aci.boleia.dominio.FrotaParametroSistemaHodometroHorimetro;
import org.springframework.stereotype.Repository;

/**
 * Implementação oracle para {@link IFrotaParametroSistemaHodometroHorimetroDados}
 */
@Repository
public class OracleFrotaParametroSistemaHodometroHorimetroDados extends OracleRepositorioBoleiaDados<FrotaParametroSistemaHodometroHorimetro> implements IFrotaParametroSistemaHodometroHorimetroDados {

    /**
     * Instancia o repositorio
     * FrotaParametroSistemaHodometroHorimetro
     */
    public OracleFrotaParametroSistemaHodometroHorimetroDados() {
        super(FrotaParametroSistemaHodometroHorimetro.class);
    }
}
