package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IHistoricoFluxoAbastecimentoMotoristaDados;
import ipp.aci.boleia.dominio.HistoricoFluxoAbastecimentoMotoristaConfig;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades FluxoAbastecimentoFrotaConfig
 */
@Repository
public class OracleHistoricoFluxoAbastecimentoMotoristaDados extends OracleRepositorioBoleiaDados<HistoricoFluxoAbastecimentoMotoristaConfig> implements IHistoricoFluxoAbastecimentoMotoristaDados {

    /**
     * Instancia o repositorio
     */
    public OracleHistoricoFluxoAbastecimentoMotoristaDados() {
        super(HistoricoFluxoAbastecimentoMotoristaConfig.class);
    }

}