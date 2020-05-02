package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.FrotaParametroSistemaProdutoAbastecimento;
import ipp.aci.boleia.dominio.Veiculo;

import java.util.List;

/**
 * Repositório da entidade {@link FrotaParametroSistemaProdutoAbastecimento}
 */
public interface IFrotaParametroSistemaProdutoAbastecimentoDados extends IRepositorioBoleiaDados<FrotaParametroSistemaProdutoAbastecimento> {

    /**
     * Obtem uma lista de combustiveis definidos no parametro para um determinado veiculo
     * @param idVeiculo o id do veiculo
     * @return a lista de combustiveis definidos no parametro de uso
     */
    List<FrotaParametroSistemaProdutoAbastecimento> obterPorVeiculo(Long idVeiculo);

    /**
     * Salva a lista de ProdutoAbastecimento de um novo veiculo
     * @param veiculo o veiculo com os produtosAbastecimentos
     */
    void incluirVeiculo(Veiculo veiculo);
}
