package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.SaldoVeiculo;

/**
 * Contrato para implementacao de repositorios de entidades Saldoveiculo
 */
public interface ISaldoVeiculoDados extends IRepositorioBoleiaDados<SaldoVeiculo> {

    /**
     * Obtem o saldo da cota do veiculo
     * @param idVeiculo O veiculo
     * @return O saldo corrente
     */
    SaldoVeiculo obterPorVeiculo(Long idVeiculo);

    /**
     * Renova as cotas mensais de abastecimento dos veiculos
     */
    void renovarCotasDosVeiculos();
}
