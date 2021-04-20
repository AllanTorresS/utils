package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.HistoricoFluxoAbastecimentoMotoristaConfig;
import ipp.aci.boleia.dominio.Motorista;

import java.util.Date;

/**
 * Contrato para implementacao de repositorios de entidades HistoricoFluxoAbastecimentoMotoristaConfig
 */
public interface IHistoricoFluxoAbastecimentoMotoristaDados extends IRepositorioBoleiaDados<HistoricoFluxoAbastecimentoMotoristaConfig> {

    /**
     * Obtém registro da configuração de fluxo na data parametrizada.
     *
     * @param motorista da configuração de fluxo
     * @param data para obter configuração conforme histórico
     * @return configuração do fluxo com valores referentes a data parametrizada
     */
    HistoricoFluxoAbastecimentoMotoristaConfig obterFluxoPorData(Motorista motorista, Date data);
}
