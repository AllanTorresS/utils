package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoFrotaParametroSistemaPostosAutorizados;
import ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaHorario;
import ipp.aci.boleia.dominio.HistoricoFrotaParametroSistemaPostosAutorizados;

/**
 * Implementação do repositório de dados da entidade {@link HistoricoFrotaParametroSistemaPostosAutorizados}.
 *
 * @author felipe.chaves
 */
public class OracleHistoricoFrotaParametroSistemaPostosAutorizados extends OracleRepositorioBoleiaDados<HistoricoFrotaParametroSistemaPostosAutorizados> implements IHistoricoFrotaParametroSistemaPostosAutorizados {

    public OracleHistoricoFrotaParametroSistemaPostosAutorizados() {
        super(HistoricoFrotaParametroSistemaPostosAutorizados.class);
    }
}
