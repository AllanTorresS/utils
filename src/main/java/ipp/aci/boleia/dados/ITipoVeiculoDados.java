package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.TipoVeiculo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios
 * de entidades das Tipo dos veiculos.
 */
public interface ITipoVeiculoDados extends IRepositorioBoleiaDados<TipoVeiculo> {

    /**
     * Obtem os tipos de veiculo com os quais uma frota trabalha
     *
     * @param idFrota O id da frota
     * @return Os tipos de veiculo com os quais uma frota trabalha
     */
    List<TipoVeiculo> obterTodosPorFrota(Long idFrota);
}
