package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.HistoricoFluxoAbastecimentoFrotaConfig;

import java.util.Date;

/**
 * Contrato para implementacao de repositorios de entidades HistoricoFluxoAbastecimentoFrotaConfig
 */
public interface IHistoricoFluxoAbastecimentoFrotaDados extends IRepositorioBoleiaDados<HistoricoFluxoAbastecimentoFrotaConfig> {

    /**
     * Obtém registro da configuração de fluxo na data parametrizada.
     *
     * @param frota da configuração de fluxo
     * @param data para obter configuração conforme histórico
     * @return configuração do fluxo com valores referentes a data parametrizada
     */
    HistoricoFluxoAbastecimentoFrotaConfig obterFluxoPorData(Frota frota, Date data);
}
