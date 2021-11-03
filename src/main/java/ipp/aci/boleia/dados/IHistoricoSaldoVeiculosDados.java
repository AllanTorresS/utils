package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.HistoricoSaldoVeiculo;

import java.util.List;

/**
 * Repositório de dados da classe {@link HistoricoSaldoVeiculo}.
 *
 * @author rafael.laranjeiro
 */
public interface IHistoricoSaldoVeiculosDados extends IRepositorioBoleiaDados<HistoricoSaldoVeiculo> {

    /**
     * Obtém o histórico de saldos de um veículo.
     * @param idVeiculo id do veículo
     * @return A lista de registros históricos do saldo do veículo.
     */
    List<HistoricoSaldoVeiculo> obterPorIdVeiculo(Long idVeiculo);
}
