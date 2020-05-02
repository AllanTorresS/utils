package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.PrecoMedioProduto;

/**
 * Contrato para implementacao de repositorios de entidades PrecoMedioProduto
 */
public interface IPrecoMedioProdutoDados extends IRepositorioBoleiaDados<PrecoMedioProduto> {

    /**
     * Obtem o ultimo preco medio calculado para um dado produto
     * @param id do produto
     * @return Preco medio cadastrado
     */
    PrecoMedioProduto pesquisarUltimoPorProduto(Long id);
}
