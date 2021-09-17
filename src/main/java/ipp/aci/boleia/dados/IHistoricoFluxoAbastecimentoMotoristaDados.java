package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.HistoricoFluxoAbastecimentoMotoristaConfig;
import ipp.aci.boleia.dominio.Motorista;

import java.util.Date;
import java.util.List;

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

    /**
     * Obtém registros da configuração de fluxo.
     *
     * @param motorista da configuração de fluxo
     * @return configurações do fluxo
     */
    List<HistoricoFluxoAbastecimentoMotoristaConfig> obterFluxosPorMotorista(Motorista motorista);

    /**
     * Exclui registros da configuração de fluxo pelo id do motorista.
     *
     * @param idMotorista da configuração de fluxo
     */
    void excluirPermanentementePorIdMotorista(Long idMotorista);
}
