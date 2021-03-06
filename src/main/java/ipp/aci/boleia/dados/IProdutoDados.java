package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Produto;

import java.util.List;

/**
 * Contrato para implementacao de repositorios
 * de entidades de produto.
 */
public interface IProdutoDados extends IRepositorioBoleiaDados<Produto> {

    /**
     * Lista os produtos existentes em um consolidado.
     *
     * @param idConsolidado Identificador do consolidado.
     * @return Lista de produtos
     */
    List<Produto> listarPorConsolidado(Long idConsolidado);

    /**
     * Obtem todos os produtos cadastrados, ordenando-os pelo nome, mas mantendo
     * o produto "Outros" como o ultimo da lisa.
     *
     * @return A lista de todos os produtos cadastrados
     */
    List<Produto> obterTodosOrdenadosPeloNome();

    /**
     * Obtem os produtos cadastrados com a exceção do produto "Outros".
     * O resultado é ordenandos pelo nome.
     *
     * @return A lista de todos os produtos
     */
    List<Produto> obterProdutosExcetoOutrosOrdenadosPeloNome();

    /**
     * Lista os produtos existentes em um grupo de consolidados
     *
     * @param idsConsolidados Identificadores dos consolidados.
     * @return Lista de produtos
     */
    List<Produto> listarPorMultiplosConsolidados(List<Long> idsConsolidados);

}
