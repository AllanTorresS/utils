package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoFluxoAbastecimentoFrotaDados;
import ipp.aci.boleia.dominio.HistoricoFluxoAbastecimentoFrotaConfig;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades FluxoAbastecimentoGlobalConfig
 */
@Repository
public class OracleHistoricoFluxoAbastecimentoFrotaDados extends OracleRepositorioBoleiaDados<HistoricoFluxoAbastecimentoFrotaConfig> implements IHistoricoFluxoAbastecimentoFrotaDados {

    /**
     * Instancia o repositorio
     */
    public OracleHistoricoFluxoAbastecimentoFrotaDados() {
        super(HistoricoFluxoAbastecimentoFrotaConfig.class);
    }

}